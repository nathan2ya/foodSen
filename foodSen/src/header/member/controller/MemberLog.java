package header.member.controller;

import header.member.dto.MemberDTO;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;


@Controller
public class MemberLog {
	
	private MemberDTO paramClass = new MemberDTO(); // 로그인 비밀번호 확인 DTO
	private MemberDTO resultClass = new MemberDTO(); // 통과된 아이디의 레코드 정보 가져오기 DTO
	
	private String viewPath; // 리턴 뷰 경로
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public MemberLog() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	
	
	//logIN
	@RequestMapping("/login.do")
	public String login(){
		return "/view/member/memberLoginFrom.jsp";
	}
	
	
	//logIN 실행
	@RequestMapping("/loginPro.do")
	public String loginPro(HttpServletRequest request, HttpSession session) throws Exception{
		
		//사용자가 로그인 요청한 입력값
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		
		
		//아이디 비밀번호 일치하는 레코드가 있는지 판단
		paramClass.setUser_id(user_id);
		paramClass.setUser_pw(user_pw);
		Integer count = (Integer) sqlMapper.queryForObject("Member.selectCountForLogin", paramClass);
		
		
		if(count==1){ // 아이디, 비밀번호 일치
			//해당 아이디의 레코드를 불러옴
			resultClass = (MemberDTO)sqlMapper.queryForObject("Member.selectMemberOne", user_id);
			
			session.setAttribute("session_id", resultClass.getUser_id()); // id 세션생성
			session.setAttribute("session_type", resultClass.getPosition()); //type 세션생성
			
			viewPath = "redirect:/main.do";
		}else{ // 아이디, 비밀번호 불일치
			
			viewPath = "redirect:/login.do";
		}
		
		return viewPath;
	}
}
