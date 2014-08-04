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
 * 작성자: 류재욱
 * 설  명: 회원수정 클래스
 * 			1. 회원정보 update메소드
*/

@Controller
public class MemberEdit {
	
	//회원수정
	private MemberDTO resultClass = new MemberDTO(); //회원수정 form DTO
	private MemberDTO paramClass = new MemberDTO(); //회원수정 update DTO
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public MemberEdit() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//회원수정 form
	@RequestMapping("/memberEditFrom.do")
	public String memberEditFrom(HttpServletRequest request, HttpSession session) throws Exception{
		
		//인코딩정의
		request.setCharacterEncoding("euc-kr");
		
		//update 를 원하는 현재 로그인된 사용자 아아디
		String session_id = (String) session.getAttribute("session_id");
		
		//udate 대상 레코드 get
		resultClass = (MemberDTO)sqlMapper.queryForObject("Member.selectMemberOne", session_id);
		
		request.setAttribute("resultClass", resultClass);
		return "/view/member/memberEdit.jsp";
	}
	
	//회원수정 DB update
	@RequestMapping("/memberEdit.do")
	public String memberEdit(HttpServletRequest request, HttpSession session) throws Exception{
		
		//인코딩정의
		request.setCharacterEncoding("euc-kr");
		
		//update 대상 레코드
		String session_id = (String) session.getAttribute("session_id");
		
		//사용자가 입력한 정보
		String user_pw = request.getParameter("user_pw");
		String school_name = request.getParameter("school_name");
		String school_type = request.getParameter("school_type");
		String position = request.getParameter("position");
		String sen_email = request.getParameter("sen_email");
		String phone = request.getParameter("phone");
		
		//사용자 정보 DB update
		paramClass.setUser_id(session_id); // update 대상 레코드
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
