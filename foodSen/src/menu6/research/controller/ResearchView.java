package menu6.research.controller;

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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	private ResearchDTO resultClass3 = new ResearchDTO();
	
	//�������� ���� ����
	private int[] resultClass1_seq = new int[16];//�����ǽ����� ����
	private String[] title = new String[16];
	
	//�������� ���� ����
	private int[] resultClass2_seq = new int[16];//�����ǽ����� ����
	private String[] i_title1 = new String[16];
	private String[] i_title2 = new String[16];
	private String[] i_title3 = new String[16];
	private String[] i_title4 = new String[16];
	private String[] i_title5 = new String[16];
	
	//�������� ��� �迭
	private String[] res_cnt_arr = new String[16];
	
	//�������� ��� ���ð���
	private int cnt1;
	private int cnt2;
	private int cnt3;
	private int cnt4;
	private int cnt5;
	

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
		
		request.setAttribute("sur_seq", sur_seq);
		request.setAttribute("currentPage", currentPage);
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
		
		for(int k=0; k<resultClass2.size(); k++){
			paramClass3.setSurq_seq(resultClass1_seq[k]);
			paramClass3.setSuri_seq(resultClass2_seq[k]);
			paramClass3 = (ResearchDTO3)sqlMapper.queryForObject("Research.selectResearchOne33", paramClass3);
			
			//String chosen = paramClass3.getSuri_num().substring(0, 1);
			res_cnt_arr[k] = paramClass3.getSuri_num().substring(0, 1);
		}
		
		
		
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
		request.setAttribute("resultClass3", resultClass3);//��������(����)���ڵ�
		return "/view/menu6/research/researchPopup.jsp";
	}
	
} //end of class
