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
	
	//��������
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
	
	/*
	//�������� DB insert
	@RequestMapping(value="/researchCreate.do", method=RequestMethod.POST)
	public String researchCreate(HttpServletRequest request, HttpSession session) throws Exception{
		
		//���ڵ�
		request.setCharacterEncoding("euc-kr");
		
		//�ۼ��� �����(���� �α����� ���Ǿ��̵�)
		String session_id = (String) session.getAttribute("session_id");
		
		//����ڰ� �Է��� ��
		String title = request.getParameter("title");
		String loc = request.getParameter("loc");
		String job = request.getParameter("job");
		String gubun = request.getParameter("gubun");
		String description = request.getParameter("description");
		String pw = request.getParameter("pw");
		Calendar today = Calendar.getInstance();
		
		//DTO Set()
		paramClass.setTitle(title);
		paramClass.setLoc_seq(loc);
		paramClass.setJob(job);
		paramClass.setGubun(gubun);
		paramClass.setDescription(description);
		paramClass.setPw(pw);
		paramClass.setHits(1);
		paramClass.setReg_name(session_id);
		paramClass.setReg_date(today.getTime());
		paramClass.setUdt_name(session_id);
		paramClass.setUdt_date(today.getTime());
		paramClass.setSchool_name(resultClass1.getSchool_name());
		paramClass.setSchool_type(resultClass1.getSchool_type());
		paramClass.setEmail(resultClass1.getSen_email());
		paramClass.setPhone(resultClass1.getPhone());
		paramClass.setWriter(session_id);
		
		//��ϸ��� N
		paramClass.setEnd_yn("N");
		
		//DB�� insert �ϱ� (�� ���)
		sqlMapper.insert("Recruit.insertRecruit", paramClass);
		
		return "redirect:/recruitList.do"; //����Ʈ�� �����̷�Ʈ
	}
	*/
} //end of class
