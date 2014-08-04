package header.member.controller;

import header.member.dto.MemberDTO;
import java.io.IOException;
import java.io.Reader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/*
 * �ۼ���: �����
 * ��  ��: ȸ������ Ŭ����
 * 			1. ȸ������ update�޼ҵ�
*/

@Controller
public class MemberEdit {
	
	//ȸ������
	private MemberDTO resultClass = new MemberDTO(); //ȸ������ form DTO
	private MemberDTO paramClass = new MemberDTO(); //ȸ������ update DTO
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public MemberEdit() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//ȸ������ form
	@RequestMapping("/memberEditFrom.do")
	public String memberEditFrom(HttpServletRequest request, HttpSession session) throws Exception{
		
		//���ڵ�����
		request.setCharacterEncoding("euc-kr");
		
		//update �� ���ϴ� ���� �α��ε� ����� �ƾƵ�
		String session_id = (String) session.getAttribute("session_id");
		
		//udate ��� ���ڵ� get
		resultClass = (MemberDTO)sqlMapper.queryForObject("Member.selectMemberOne", session_id);
		
		request.setAttribute("resultClass", resultClass);
		return "/view/member/memberEdit.jsp";
	}
	
	//ȸ������ DB update
	@RequestMapping("/memberEdit.do")
	public String memberEdit(HttpServletRequest request, HttpSession session) throws Exception{
		
		//���ڵ�����
		request.setCharacterEncoding("euc-kr");
		
		//update ��� ���ڵ�
		String session_id = (String) session.getAttribute("session_id");
		
		//����ڰ� �Է��� ����
		String user_pw = request.getParameter("user_pw");
		String school_name = request.getParameter("school_name");
		String school_type = request.getParameter("school_type");
		String position = request.getParameter("position");
		String sen_email = request.getParameter("sen_email");
		String phone = request.getParameter("phone");
		
		//����� ���� DB update
		paramClass.setUser_id(session_id); // update ��� ���ڵ�
		paramClass.setUser_pw(user_pw);
		paramClass.setSchool_name(school_name);
		paramClass.setPosition(position);
		paramClass.setSen_email(sen_email);
		paramClass.setSchool_type(school_type);
		paramClass.setPhone(phone);
		sqlMapper.update("Member.updateMember", paramClass);
		
		return "redirect:/main.do";
	}
	
} //end of class
