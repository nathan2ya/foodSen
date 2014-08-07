package menu6.research.controller;


import header.member.dto.MemberDTO;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import menu2.inspectionResult.dto.InspectionResultDTO;
import menu6.research.dto.ResearchDTO;
import menu6.research.dto.ResearchDTO1;
import menu6.research.dto.ResearchDTO2;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import common.Constants;

/*
 * �ۼ���: �����
 * ��  ��: �������� insert Ŭ����
 * ��  ��: �������� �Խñ� ����� ����.
*/

@Controller
public class ResearchCreate {
	
	//��������
	private ResearchDTO paramClass = new ResearchDTO();//�������� ����
	private ResearchDTO resultClass = new ResearchDTO();//�������� ����(�ִ������get)
	
	private ResearchDTO1 paramClass1 = new ResearchDTO1();//�������� ����
	private ResearchDTO1 resultClass1 = new ResearchDTO1(); //�������� ����(�ִ������ get)
	
	private ResearchDTO2 paramClass2 = new ResearchDTO2();//�������� ����
	
	//�ִ�������ѹ�
	private int seq;//�������� ����
	private int surq_seq;//�������� ����
	
	//�������� ����
	private String surq_title1;
	private String surq_title2;
	private String surq_title3;
	private String surq_title4;
	private String surq_title5;
	private String surq_title6;
	private String surq_title7;
	private String surq_title8;
	private String surq_title9;
	private String surq_title10;
	private String surq_title11;
	private String surq_title12;
	private String surq_title13;
	private String surq_title14;
	private String surq_title15;
	private String surq_title16;
	
	//�������� ����
	private String item11;
	private String item21;
	private String item31;
	private String item41;
	private String item51;
	
	private String item12;
	private String item22;
	private String item32;
	private String item42;
	private String item52;
	
	private String item13;
	private String item23;
	private String item33;
	private String item43;
	private String item53;
	
	private String item14;
	private String item24;
	private String item34;
	private String item44;
	private String item54;
	
	private String item15;
	private String item25;
	private String item35;
	private String item45;
	private String item55;
	
	private String item16;
	private String item26;
	private String item36;
	private String item46;
	private String item56;
	
	private String item17;
	private String item27;
	private String item37;
	private String item47;
	private String item57;
	
	private String item18;
	private String item28;
	private String item38;
	private String item48;
	private String item58;
	
	private String item19;
	private String item29;
	private String item39;
	private String item49;
	private String item59;
	
	private String item110;
	private String item210;
	private String item310;
	private String item410;
	private String item510;
	
	private String item111;
	private String item211;
	private String item311;
	private String item411;
	private String item511;
	
	private String item112;
	private String item212;
	private String item312;
	private String item412;
	private String item512;
	
	private String item113;
	private String item213;
	private String item313;
	private String item413;
	private String item513;
	
	private String item114;
	private String item214;
	private String item314;
	private String item414;
	private String item514;
	
	private String item115;
	private String item215;
	private String item315;
	private String item415;
	private String item515;
	
	private String item116;
	private String item216;
	private String item316;
	private String item416;
	private String item516;
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public ResearchCreate() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	//�������� �Է���
	@RequestMapping("/researchCreateForm.do")
	public String researchCreateForm(){
		return "/view/menu6/research/researchCreate.jsp";
	}
	
	//�������� DB insert
	@RequestMapping(value="/researchCreate.do", method=RequestMethod.POST)
	public String researchCreate(HttpServletRequest request, HttpSession session) throws Exception{
		
		//���ڵ�
		request.setCharacterEncoding("euc-kr");
		//��¥
		Calendar today = Calendar.getInstance();
		
		//�ۼ��� �����(���� �α����� ���Ǿ��̵�)
		String session_id = (String) session.getAttribute("session_id");
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		/*
		 * �������� ���� insert
		 * �������� �Խñ� ������
		 * PK : sur_seq
		*/
		//����ڰ� �Է��� ��(����)
		String sur_title = request.getParameter("sur_title");
		String sur_sat_date = request.getParameter("sur_sat_date");
		String sur_end_date = request.getParameter("sur_end_date");
		String que_cnt = request.getParameter("que_cnt");
		
		//DTO Set()
		paramClass.setSur_title(sur_title);
		paramClass.setSur_sat_date(sur_sat_date);
		paramClass.setSur_end_date(sur_end_date);
		paramClass.setQue_cnt(que_cnt);
		paramClass.setHits(0);
		paramClass.setWriter(session_id);
		paramClass.setReg_name(session_id);
		paramClass.setReg_date(today.getTime());
		paramClass.setUdt_name(session_id);
		paramClass.setUdt_date(today.getTime());
		sqlMapper.insert("Research.insertResearch", paramClass);
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		/*
		 * �������� ���� insert
		 * �������� ������ ���Ե� ����
		 * PK : surq_seq
		 * FK : sur_seq
		 * 
		 * �ִ� 16���� ������ �� �� ������,
		 * 16���� ���ڵ� ������ ����.
		*/
		
		//��������(����)�� �������ѹ� get
		resultClass = (ResearchDTO) sqlMapper.queryForObject("Research.selectLastNum");
		int sur_seq = (int)(resultClass.getSur_seq());
		
		//�������� ����
		int que_cnt_size = Integer.parseInt(que_cnt);
		
		//�������� ���� �������� ���� ���ڵ�.
		paramClass1.setSur_seq(sur_seq);
		paramClass1.setWriter(session_id);
		paramClass1.setReg_name(session_id);
		paramClass1.setReg_date(today.getTime());
		paramClass1.setUdt_name(session_id);
		paramClass1.setUdt_date(today.getTime());
		
		//�������� ���� �������� ���� ���ڵ�
		paramClass2.setSur_seq(sur_seq);
		paramClass2.setWriter(session_id);
		paramClass2.setReg_name(session_id);
		paramClass2.setReg_date(today.getTime());
		paramClass2.setUdt_name(session_id);
		paramClass2.setUdt_date(today.getTime());
		
		//�������� ���� insert(�ִ�16�� insert)
		//�� ������ ���Ե� ���� insert

		
		/*
		 * ����ڰ� ���� ���װ�����ŭ ����, ���׿� ���� insert�� �ǽ��Ѵ�.
		 * �������� ���� insert (1��~�ִ�16�� ����)
		 *    �� ������ ���Ե� ����5�� insert (1��~�ִ�16�� ����)
		*/
		if(1 <= que_cnt_size){
			surq_title1 = request.getParameter("surq_title1");
			paramClass1.setSurq_title(surq_title1);
			//�������� ���� insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);
			
			//�������� ���� �ִ������
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			//����ڰ� �Է��� ��(����)
			item11 = request.getParameter("item11");
			item21 = request.getParameter("item21");
			item31 = request.getParameter("item31");
			item41 = request.getParameter("item41");
			item51 = request.getParameter("item51");
			
			paramClass2.setSurq_seq(surq_seq);//1�������� ������
			paramClass2.setSuri_title1(item11);
			paramClass2.setSuri_title2(item21);
			paramClass2.setSuri_title3(item31);
			paramClass2.setSuri_title4(item41);
			paramClass2.setSuri_title5(item51);
			paramClass2.setSuri_num1(11);
			paramClass2.setSuri_num2(21);
			paramClass2.setSuri_num3(31);
			paramClass2.setSuri_num4(41);
			paramClass2.setSuri_num5(51);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(2 <= que_cnt_size){
			surq_title2 = request.getParameter("surq_title2");
			paramClass1.setSurq_title(surq_title2);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(3 <= que_cnt_size){
			surq_title3 = request.getParameter("surq_title3");
			paramClass1.setSurq_title(surq_title3);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(4 <= que_cnt_size){
			surq_title4 = request.getParameter("surq_title4");
			paramClass1.setSurq_title(surq_title4);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(5 <= que_cnt_size){
			surq_title5 = request.getParameter("surq_title5");
			paramClass1.setSurq_title(surq_title5);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(6 <= que_cnt_size){
			surq_title6 = request.getParameter("surq_title6");
			paramClass1.setSurq_title(surq_title6);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(7 <= que_cnt_size){
			surq_title7 = request.getParameter("surq_title7");
			paramClass1.setSurq_title(surq_title7);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(8 <= que_cnt_size){
			surq_title8 = request.getParameter("surq_title8");
			paramClass1.setSurq_title(surq_title8);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(9 <= que_cnt_size){
			surq_title9 = request.getParameter("surq_title9");
			paramClass1.setSurq_title(surq_title9);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(10 <= que_cnt_size){
			surq_title10 = request.getParameter("surq_title10");
			paramClass1.setSurq_title(surq_title10);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(11 <= que_cnt_size){
			surq_title11 = request.getParameter("surq_title11");
			paramClass1.setSurq_title(surq_title11);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(12 <= que_cnt_size){
			surq_title12 = request.getParameter("surq_title12");
			paramClass1.setSurq_title(surq_title12);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(13 <= que_cnt_size){
			surq_title13 = request.getParameter("surq_title13");
			paramClass1.setSurq_title(surq_title13);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(14 <= que_cnt_size){
			surq_title14 = request.getParameter("surq_title14");
			paramClass1.setSurq_title(surq_title14);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(15 <= que_cnt_size){
			surq_title15 = request.getParameter("surq_title15");
			paramClass1.setSurq_title(surq_title15);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(16 <= que_cnt_size){
			surq_title16 = request.getParameter("surq_title16");
			paramClass1.setSurq_title(surq_title16);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		
		
		return "redirect:/researchList.do"; //����Ʈ�� �����̷�Ʈ
	}
} //end of class
