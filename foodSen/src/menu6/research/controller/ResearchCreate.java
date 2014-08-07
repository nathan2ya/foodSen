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
	
	//��������(����)
	private ResearchDTO paramClass = new ResearchDTO();//
	private ResearchDTO resultClass = new ResearchDTO();//
	
	//�ִ�������ѹ�
	private int seq;
	
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
		paramClass.setReg_name(session_id);
		paramClass.setReg_date(today.getTime());
		paramClass.setUdt_name(session_id);
		paramClass.setUdt_date(today.getTime());
		paramClass.setWriter(session_id);
		sqlMapper.insert("Research.insertResearch", paramClass);
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		/*
		 * �������� ���� insert
		 * �������� ������ ���Ե� ����
		 * PK : surq_seq
		 * FK : sur_seq
		*/
		//����ڰ� �Է��� ��(����)
		
		
		//DTO set()
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		/*
		 * �������� ���� insert
		 * �������� ������ ���Ե� ���� �� ����
		 * PK : suri_seq
		 * FK : sur_seq, surq_seq
		*/
		//����ڰ� �Է��� ��(����)
		
		
		//DTO set()
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		return "redirect:/recruitList.do"; //����Ʈ�� �����̷�Ʈ
	}
} //end of class
