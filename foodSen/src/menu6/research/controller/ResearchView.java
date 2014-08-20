package menu6.research.controller;

import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import menu6.research.dto.ResearchDTO;
import menu6.research.dto.ResearchDTO1;
import menu6.research.dto.ResearchDTO2;
import menu6.research.dto.ResearchDTO3;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import common.Constants;

@Controller
public class ResearchView {
	
	//��������(����)
	private ResearchDTO paramClass = new ResearchDTO();
	private ResearchDTO resultClass = new ResearchDTO();
	//��������(����)
	private ResearchDTO1 paramClass1 = new ResearchDTO1();
	private List<ResearchDTO1> resultClass1 = new ArrayList<ResearchDTO1>();
	//��������(����)
	private ResearchDTO2 paramClass2 = new ResearchDTO2();
	private List<ResearchDTO2> resultClass2 = new ArrayList<ResearchDTO2>();
	//��������(���)
	private ResearchDTO3 paramClass3 = new ResearchDTO3();
	private ResearchDTO3 resultClassForExcel = new ResearchDTO3();
	private List<ResearchDTO3> resultClass3 = new ArrayList<ResearchDTO3>();
	private List<ResearchDTO3> resultClass33 = new ArrayList<ResearchDTO3>();
	//��������(�������ϰ��)
	private String file_path = Constants.COMMON_FILE_PATH + Constants.MENU6_RESEARCH_FILE_PATH;
	
	//�������� ���� ����
	private int[] resultClass1_seq = new int[16];//�����ǽ����� ����
	private String[] title = new String[1000];
	
	//�������� ���� ����
	private int[] resultClass2_seq = new int[16];//�����ǽ����� ����
	private String[] i_title1 = new String[16];
	private String[] i_title2 = new String[16];
	private String[] i_title3 = new String[16];
	private String[] i_title4 = new String[16];
	private String[] i_title5 = new String[16];
	
	//������
	private String[]totalTitle = new String[9000];//��繮�� ����(������)
	private String[]totalItem = new String[9000];//��繮�� ����(������)
	private String[]totalCount = new String[9000];//������ ����Ƚ�� ����(������)
	private String[]totalDescription = new String[9000];//������ ���û��� ����(������)
	
	//�������� ��� ����
	private int[] resultClass3_seq = new int[16];//����ǽ����� ����
	
	//�������� ���� �����ֱ� �迭
	private String[] item = new String[1000];
	private String[] description = new String[1000];
	
	//�������� ��� �迭
	private int[][] res_cnt_arr = new int[16][5];
	
	//�������� ��ȣ�� �߶� ���
	private String chosen;
	
	//�������� ����write ����
	private String[] sur_title = new String[1000];//��������
	private String[] sur_item = new String[1000];//���׸���
	private String[] sur_description = new String[1000];//��������
	
	//������
	private int currentPage;
	private int searchingNow; // ��ü��, �˻����� �Ǵ��Ͽ� ���� ������ �ǰ����� ����
	
	//�˻����� ��쿡�� �߻��ϴ� ������
	private String searchType;
	private String userinput;
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public ResearchView() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	//�������� ��������
	@RequestMapping("/researchView.do")
	public String researchView(HttpServletRequest request, HttpSession session) throws SQLException{
		
		/*
		 * ��û�� ������ �ʱ�ȭ
		 * sur_seq, currentPage, searchingNow
		*/
		int sur_seq = Integer.parseInt(request.getParameter("sur_seq"));
		if(null == request.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		
		//�˻��� ��� �����ϴ� ���� �ʱ�ȭ
		if(searchingNow==1){
			searchType = request.getParameter("searchType");
			userinput = request.getParameter("userinput");
			
			//��� �Ʒ��� ���� 3���� ������ ������
			//����� Ŭ������ �� �ٽ� �˻����� ����Ʈ�� ���ư��� �����̴�. +���� currentPage�� �Բ�!
			request.setAttribute("searchType", searchType);
			request.setAttribute("userinput", userinput);
			request.setAttribute("searchingNow", 1);
		}else{
			//�˻����� �ƴѰ�쿡�� �Ʒ��� �������� �������� ������ �ǹ��ϴ� 0�� �ʱ�ȭ ���Ѽ� �ش� jsp �� ������.
			request.setAttribute("searchType", 0);
			request.setAttribute("userinput", 0);
			request.setAttribute("searchingNow", 0);
		}
		//.��û�� ������
		
		
		//��������(����) ��ȸ���� +1 ������Ʈ
		resultClass = (ResearchDTO)sqlMapper.queryForObject("Research.selectResearchOne", sur_seq);
		paramClass.setSur_seq(sur_seq);
		paramClass.setHits(resultClass.getHits()+1);
		sqlMapper.update("Research.updateHits", paramClass);
		//.��ȸ�� +1 ����
		
		//��������(����) ���ڵ�get
		resultClass = (ResearchDTO)sqlMapper.queryForObject("Research.selectResearchOne", sur_seq);
		int cnt = Integer.parseInt(resultClass.getQue_cnt());
		
		//��������(����) ���ڵ�get
		resultClass1 = sqlMapper.queryForList("Research.selectResearchOne1", sur_seq);
		for(int i=0; i<resultClass1.size(); i++){
			title[i] = resultClass1.get(i).getSurq_title();
		}
		
		//��������(����) ���ڵ�get
		resultClass2 = sqlMapper.queryForList("Research.selectResearchOne2", sur_seq);
		for(int j=0; j<resultClass2.size(); j++){
			i_title1[j] = resultClass2.get(j).getSuri_title1();
			i_title2[j] = resultClass2.get(j).getSuri_title2();
			i_title3[j] = resultClass2.get(j).getSuri_title3();
			i_title4[j] = resultClass2.get(j).getSuri_title4();
			i_title5[j] = resultClass2.get(j).getSuri_title5();
		}
		
		//��������(���) ���ڵ�get
		resultClass3 = sqlMapper.queryForList("Research.selectResearchOne3", sur_seq);
		int res_cnt = resultClass3.size(); //�������(����,���� ��ȿ���� ���� jsp�� ����)
		
		//����α��ε� ������ �������� �̷����ִ��� �Ǵ�
		String session_id = (String) session.getAttribute("session_id");
		
		paramClass3.setSur_seq(sur_seq);
		paramClass3.setWriter(session_id);
		Integer count = (Integer) sqlMapper.queryForObject("Research.selectCountForPermit", paramClass3);
		
		int canSave = 0; // �������� ��������
		if(count > 0){
			canSave = 1; // �������� ��������
		}
		//.�����̷� �Ǵ� ����
		
		//���糯¥
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String current_date = sdf.format(today.getTime());
		//.���糯¥
		
		request.setAttribute("sur_seq", sur_seq);
		request.setAttribute("res_cnt", res_cnt);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("current_date", current_date);//���ó�¥
		request.setAttribute("canSave", canSave); //�������� ��������0, �������������Ұ���1
		request.setAttribute("cnt", cnt); //�������� ���װ���
		request.setAttribute("resultClass", resultClass); //��������(����)
		request.setAttribute("resultClass1", resultClass1); //��������(����)
		request.setAttribute("title", title); //��������(�����迭)
		request.setAttribute("resultClass2", resultClass2); //��������(����)
		request.setAttribute("i_title1", i_title1); //��������(���׹迭)
		request.setAttribute("i_title2", i_title2); //��������(���׹迭)
		request.setAttribute("i_title3", i_title3); //��������(���׹迭)
		request.setAttribute("i_title4", i_title4); //��������(���׹迭)
		request.setAttribute("i_title5", i_title5); //��������(���׹迭)
		return "/view/menu6/research/researchView.jsp";
	}
	
	//�������� ��� �˾�������
	@RequestMapping("/researchResult.do")
	public String researchResult(HttpServletRequest request, HttpSession session) throws SQLException{
		
		//��û�� ������ �ʱ�ȭ
		int sur_seq = Integer.parseInt(request.getParameter("sur_seq"));
		
		//��������(����) ���ڵ�get
		resultClass = (ResearchDTO)sqlMapper.queryForObject("Research.selectResearchOne", sur_seq);
		int cnt = Integer.parseInt(resultClass.getQue_cnt());
		
		//��������(����) ���ڵ�get
		resultClass1 = sqlMapper.queryForList("Research.selectResearchOne1", sur_seq);
		for(int i=0; i<resultClass1.size(); i++){
			resultClass1_seq[i] = resultClass1.get(i).getSurq_seq(); //�����ǽ����� ����
			title[i] = resultClass1.get(i).getSurq_title(); //��������
		}
		
		//��������(����) ���ڵ�get
		resultClass2 = sqlMapper.queryForList("Research.selectResearchOne2", sur_seq);
		for(int j=0; j<resultClass2.size(); j++){
			resultClass2_seq[j] = resultClass2.get(j).getSuri_seq(); //�����ǽ����� ����
			i_title1[j] = resultClass2.get(j).getSuri_title1();//����1����
			i_title2[j] = resultClass2.get(j).getSuri_title2();//����2����
			i_title3[j] = resultClass2.get(j).getSuri_title3();//����3����
			i_title4[j] = resultClass2.get(j).getSuri_title4();//����4����
			i_title5[j] = resultClass2.get(j).getSuri_title5();//����5����
		}
		
		//��������(���) ���ڵ�get
		
		/*
		 * �����, ��������� ��������� �����ߴ��� �������
		*/
		//�������
		paramClass3.setSur_seq(sur_seq); //�����
		
		//���迭�� �ʱ�ȭ
		for(int k=0; k<resultClass2.size(); k++){
			res_cnt_arr[k][0]=0;
			res_cnt_arr[k][1]=0;
			res_cnt_arr[k][2]=0;
			res_cnt_arr[k][3]=0;
			res_cnt_arr[k][4]=0;
		}
		
		//�Ź� �˾��� Ŭ���Ҷ����� ���Ӱ� ī��Ʈ
		for(int k=0; k<cnt; k++){
			paramClass3.setSurq_seq(resultClass1_seq[k]);//�����ǽ�����
			paramClass3.setSuri_seq(resultClass2_seq[k]);//�����ǽ�����
			resultClass33 = sqlMapper.queryForList("Research.selectResearchOne33", paramClass3);//����and���� ���ڵ�get
			
			for(int z=0; z<resultClass33.size(); z++){//����and���� ���ڵ��� ���� 1�ϰ�� 0��, 2�ϰ�� 1��, 3�ϰ�� 2��, 4�ϰ�� 3��, 5�ϰ�� 4�� _ ���� ������Ŵ
				chosen = resultClass33.get(z).getSuri_num().substring(0, 1);
				if(chosen.equals("��")) res_cnt_arr[k][0]++;
				if(chosen.equals("��")) res_cnt_arr[k][1]++;
				if(chosen.equals("��")) res_cnt_arr[k][2]++;
				if(chosen.equals("��")) res_cnt_arr[k][3]++;
				if(chosen.equals("��")) res_cnt_arr[k][4]++;
			}
		}
		
		//���糯¥
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String current_date = sdf.format(today.getTime());
		//.���糯¥
		
		request.setAttribute("current_date", current_date); //���糯¥
		request.setAttribute("sur_seq", sur_seq);
		request.setAttribute("resultClass", resultClass);//��������(����)���ڵ�
		request.setAttribute("cnt", cnt); //�������� ���װ���
		request.setAttribute("resultClass1", resultClass1);//��������(����)���ڵ�
		request.setAttribute("title", title); //��������(�����迭)
		request.setAttribute("resultClass2", resultClass2); //��������(����)
		request.setAttribute("i_title1", i_title1); //��������(����1�迭)
		request.setAttribute("i_title2", i_title2); //��������(����2�迭)
		request.setAttribute("i_title3", i_title3); //��������(����3�迭)
		request.setAttribute("i_title4", i_title4); //��������(����4�迭)
		request.setAttribute("i_title5", i_title5); //��������(����5�迭)
		request.setAttribute("resultClass3", resultClass3);//��������(���)���ڵ�
		request.setAttribute("res_cnt_arr", res_cnt_arr);//��������(���)���� ī��Ʈ
		//return "/view/menu6/research/researchChart.jsp"; //��Ʈ�����ֱ�
		return "/view/menu6/research/researchPopup.jsp"; //����(�Ϲݱ׷��������ֱ�)
	}
	
	//�������� ���_���� �˾�������
	@RequestMapping("/researchResult1.do")
	public String researchResult1(HttpServletRequest request, HttpSession session) throws SQLException{
		
		//��û�� ������ �ʱ�ȭ
		int sur_seq = Integer.parseInt(request.getParameter("sur_seq"));
		
		//��������(����) ���ڵ�get
		resultClass = (ResearchDTO)sqlMapper.queryForObject("Research.selectResearchOne", sur_seq);
		int cnt = Integer.parseInt(resultClass.getQue_cnt());
		
		//��������(����) ���ڵ�get
		resultClass1 = sqlMapper.queryForList("Research.selectResearchOne1", sur_seq);
		for(int i=0; i<resultClass1.size(); i++){
			resultClass1_seq[i] = resultClass1.get(i).getSurq_seq(); //�����ǽ����� ����
			//title[i] = resultClass1.get(i).getSurq_title(); //��������
		}
		
		//��������(���) ���ڵ�get
		resultClass3 = sqlMapper.queryForList("Research.selectResearchOne3", sur_seq);
		
		for(int i = 0; i<resultClass3.size(); i++){
			title[i] = resultClass3.get(i).getSurq_title();
			item[i] = resultClass3.get(i).getSuri_num();
			description[i] = resultClass3.get(i).getDescription();
		}
		
		int selectedCnt = 0;
		for(int j = 0; j<resultClass3.size(); j++){
			if(!(resultClass3.get(j).getDescription().equals(" "))){
				selectedCnt++;
			}
		}
		
		//���糯¥
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��hh:mm:ss");
		String current_date = sdf.format(today.getTime());
		//.���糯¥
		
		request.setAttribute("current_date", current_date); //���糯¥
		request.setAttribute("selectedCnt", selectedCnt); //�������� ���û����� ������ �����ڼ�
		request.setAttribute("resultClass3Cnt", resultClass3.size());
		request.setAttribute("sur_seq", sur_seq);
		request.setAttribute("item", item);//���׹迭
		request.setAttribute("description", description);//�����迭
		request.setAttribute("resultClass", resultClass);//��������(����)���ڵ�
		request.setAttribute("cnt", cnt); //�������� ���װ���
		request.setAttribute("resultClass1", resultClass1);//��������(����)���ڵ�
		request.setAttribute("title", title); //��������(�����迭)
		request.setAttribute("resultClass3", resultClass3);//��������(���)���ڵ�
		return "/view/menu6/research/researchChoiceReasonPopup.jsp";
	}
	
	//���� ���ϻ���
	@RequestMapping("/writeExcel.do") 
	public void writeExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session)  throws Exception {
		
		//���糯¥
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.hh:mm:ss");
		String current_date = sdf.format(today.getTime());
		//.���糯¥
		
		
		//1. ������ ���Ե� ������
		int sur_seq = Integer.parseInt(request.getParameter("sur_seq")); //��û�Ѻ��� �������ѹ�
		
		//��������(����) ���ڵ�get
		resultClass = (ResearchDTO)sqlMapper.queryForObject("Research.selectResearchOne", sur_seq);
		int cnt = Integer.parseInt(resultClass.getQue_cnt());//�����ǰ���
		String mainTitle = resultClass.getSur_title();//����
		
		//��������(����) ���ڵ�get
		resultClass1 = sqlMapper.queryForList("Research.selectResearchOne1", sur_seq);
		for(int i=0; i<resultClass1.size(); i++){
			resultClass1_seq[i] = resultClass1.get(i).getSurq_seq();//�����ǽ����� ����
			title[i] = resultClass1.get(i).getSurq_title();//��������
		}
		
		//��������(����) ���ڵ�get
		resultClass2 = sqlMapper.queryForList("Research.selectResearchOne2", sur_seq);
		for(int j=0; j<resultClass2.size(); j++){
			resultClass2_seq[j] = resultClass2.get(j).getSuri_seq();//�����ǽ����� ����
			i_title1[j] = resultClass2.get(j).getSuri_title1();//����1����
			i_title2[j] = resultClass2.get(j).getSuri_title2();//����2����
			i_title3[j] = resultClass2.get(j).getSuri_title3();//����3����
			i_title4[j] = resultClass2.get(j).getSuri_title4();//����4����
			i_title5[j] = resultClass2.get(j).getSuri_title5();//����5����
		}
		
		//��� ������
		resultClass3 = sqlMapper.queryForList("Research.selectResearchOne3", sur_seq);
		for(int k=0; k<resultClass3.size(); k++){
			resultClass3_seq[k] = resultClass3.get(k).getSurr_seq(); //����ǽ����� ����
			
		}
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		
		//��������(����) �����ؼ� ��δ�����ֱ�
		int temp = resultClass1.size();
		int temp1 = temp * 5;
		
		int a = 0; int b = 0;
		
		for(a=0; a<temp1; a++){
			totalTitle[a] = title[b];
			a++;
			totalTitle[a] = "";
			a++;
			totalTitle[a] = "";
			a++;
			totalTitle[a] = "";
			a++;
			totalTitle[a] = "";
			b++;
		}
		
		//��������(����) �����ؼ� ��δ�����ֱ�
		temp = resultClass2.size();
		temp1 = temp * 5;
		
		a = 0; b = 0;

		for(a=0; a<temp1; a++){
			totalItem[a] = i_title1[b];
			a++;
			totalItem[a] = i_title2[b];
			a++;
			totalItem[a] = i_title3[b];
			a++;
			totalItem[a] = i_title4[b];
			a++;
			totalItem[a] = i_title5[b];
			b++;
		}
		
		//��������(����ȸ��) �����ؼ� ��δ�����ֱ�
		
		a = 0; b = 0;
		int item1Cnt = 0; int item2Cnt = 0; int item3Cnt = 0; int item4Cnt = 0; int item5Cnt = 0;
		String item1Description = ""; String item2Description = ""; String item3Description = ""; String item4Description = ""; String item5Description = ""; 
		
		for(a=0; a<cnt*5; a++){
			
			paramClass3.setSur_seq(sur_seq);
			paramClass3.setSurq_seq(resultClass1_seq[b]);
			paramClass3.setSuri_seq(resultClass2_seq[b]);
			resultClass33 = sqlMapper.queryForList("Research.selectResearchOne33", paramClass3);//����and���� ���ڵ�get
			
			item1Cnt = 0; item2Cnt = 0; item3Cnt = 0; item4Cnt = 0; item5Cnt = 0;
			item1Description = ""; item2Description = ""; item3Description = ""; item4Description = ""; item5Description = ""; 
			
			for(int i=0; i<resultClass33.size(); i++){
				if(resultClass33.get(i).getSuri_num().substring(0, 1).equals("��")){
					item1Cnt++;
					item1Description += resultClass33.get(i).getDescription();
					item1Description += "   ";
				}
				if(resultClass33.get(i).getSuri_num().substring(0, 1).equals("��")){
					item2Cnt++;
					item2Description += resultClass33.get(i).getDescription();
					item2Description += "   ";
				}
				if(resultClass33.get(i).getSuri_num().substring(0, 1).equals("��")){
					item3Cnt++;
					item3Description += resultClass33.get(i).getDescription();
					item3Description += "   ";
				}
				if(resultClass33.get(i).getSuri_num().substring(0, 1).equals("��")){
					item4Cnt++;
					item4Description += resultClass33.get(i).getDescription();
					item4Description += "   ";
				}
				if(resultClass33.get(i).getSuri_num().substring(0, 1).equals("��")){
					item5Cnt++;
					item5Description += resultClass33.get(i).getDescription();
					item5Description += "   ";
				}
			}
			
			//������ ���� ������ �������� �迭�� ����
			totalCount[a] = Integer.toString(item1Cnt);
			totalDescription[a] = item1Description;
			a++;
			totalCount[a] = Integer.toString(item2Cnt); 
			totalDescription[a] = item2Description;
			a++;
			totalCount[a] = Integer.toString(item3Cnt); 
			totalDescription[a] = item3Description;
			a++;
			totalCount[a] = Integer.toString(item4Cnt); 
			totalDescription[a] = item4Description;
			a++;
			totalCount[a] = Integer.toString(item5Cnt);
			totalDescription[a] = item5Description;
			
			b++;
		}
		
		
		
		//��������(���û���) �����ؼ� ��δ�����ֱ�
		
		
		
		//totalCount[], totalDescription
		
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//

		
		//2. map�� ����������
																	 /*
		-------------------[ �������ϻ��� ]-------------------
		                   
		// �������� ���� // �������� ���ù��� //  �������� ���û���  //
		//  sur_title[i] //    sur_item[j]    //  sur_description[k] //
		
		-------------------------------------------------------
		*/
		
		File file = new File(file_path+"researchResult.xls");

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		//map�� ����
		for(int i=0; i<temp1; i++){
			map = new HashMap<String, Object>();
			map.put("sur_title", totalTitle[i]);//1. ����
			map.put("sur_item", totalItem[i]);//2. ����
			map.put("sur_count", totalCount[i]);//3. ����Ƚ��
			map.put("sur_description", totalDescription[i]); //4. ���û�������
			data.add(map);
		}
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		
		//3. ���� write
		
		//WorkBook ����
		WritableWorkbook wb = Workbook.createWorkbook(file);
		
		//WorkSheet ����
		WritableSheet sh = wb.createSheet("����������("+sur_seq+"�� �Խñ�)", 0);
		
		//������ ���� (�� ��ġ, ����)
		sh.setColumnView(0, 60);
		sh.setColumnView(1, 60);
		sh.setColumnView(2, 20);
		sh.setColumnView(3, 200);
		
		//��Ʈ
		//WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);
		WritableFont font1 = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false);
		WritableFont font2 = new WritableFont(WritableFont.ARIAL, 28, WritableFont.BOLD, false); 

		// ������
		WritableCellFormat textFormat = new WritableCellFormat();
		WritableCellFormat textFormat1 = new WritableCellFormat(font1);
		WritableCellFormat textFormat2 = new WritableCellFormat(font2);
		
		//����
		textFormat.setAlignment(Alignment.CENTRE);
		textFormat1.setAlignment(Alignment.CENTRE);
		textFormat2.setAlignment(Alignment.CENTRE);
		
		//�׵θ�
		textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		textFormat1.setBorder(Border.ALL, BorderLineStyle.THIN);
		textFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
		
		//����row
		int row = 0;
		
		//�������
		Label label = new jxl.write.Label(0, row, "", textFormat2);
		sh.addCell(label);
		label = new jxl.write.Label(1, row, mainTitle, textFormat2);
		sh.addCell(label);
		label = new jxl.write.Label(2, row, "����", textFormat2);
		sh.addCell(label);
		label = new jxl.write.Label(3, row, "�����Ͻ� : "+current_date, textFormat);
		sh.addCell(label);
		row+=3;
		
		//���
		label = new jxl.write.Label(0, row, "����", textFormat1);
		sh.addCell(label);
		label = new jxl.write.Label(1, row, "����", textFormat1);
		sh.addCell(label);
		label = new jxl.write.Label(2, row, "����Ƚ��", textFormat1);
		sh.addCell(label);
		label = new jxl.write.Label(3, row, "���û���", textFormat1);
		sh.addCell(label);
		row++;
		
		//������
		for (Map<String, Object> tem : data) {
			//����
			label = new jxl.write.Label(0, row, (String) tem.get("sur_title"), textFormat);
			sh.addCell(label);
			//����
			label = new jxl.write.Label(1, row, (String) tem.get("sur_item"),textFormat);
			sh.addCell(label);
			
			//����Ƚ��
			label = new jxl.write.Label(2, row, (String) tem.get("sur_count"),textFormat);
			sh.addCell(label);
			//���û���
			label = new jxl.write.Label(3, row, (String) tem.get("sur_description"),textFormat);
			sh.addCell(label);
			row++;
		}
		
		wb.write(); //WorkSheet ����
		wb.close(); //WorkSheet �ݱ�
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		
		//4. �����ٿ�ε� ����
		String uploadPath = file_path; //���������� ����Ǿ��ִ� ���
		String requestedFile = "researchResult.xls"; //������ ���� �������ϸ�.Ȯ����
		//String attach_path = request.getParameter("attach_path"); //���ϰ� ��� ��θ� ������ ����(���� ���� �� ����)

		File uFile = new File(uploadPath, requestedFile); //���,���ϸ����� ���ϰ�ü ����
		int fSize = (int) uFile.length();
		boolean ctrl = uFile.exists(); //������������
		
		if (ctrl){
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(uFile)); //������ �о���� // ���ۿ�
			String mimetype = "text/html";
			response.setBufferSize(fSize); //����ũ�⼳��
			response.setContentType(mimetype); //���������ļ���
			response.setHeader("Content-Disposition", "attachment; filename=\"" + requestedFile + "\"");
			response.setContentLength(fSize); //������ ��ü�� ����

			FileCopyUtils.copy(in, response.getOutputStream());
			in.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}else{ //���������� ȭ�� ����
			//setContentType�� ������Ʈ ȯ�濡 ���߾� ����
			response.setContentType("application/x-msdownload");
			PrintWriter printwriter = response.getWriter();
			printwriter.println("<html>");
			printwriter.println("<br><br><br><h2>Could not get file name:<br>" + requestedFile + "</h2>");
			printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
			printwriter.println("<br><br><br>&copy; webAccess");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
		}
	}
	
} //end of class
