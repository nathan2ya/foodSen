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
	private int notFound; // 로그인 성공 or 실패에 관한 논리값
	private String user_id; // 로그인 실패에 관한 id 저장변수
	
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
	
	
	
	
	//log 폼
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request){
		
		//로그인 실패시 else구문으로 초기화됨
		if(null == request.getParameter("notFound") && null == request.getParameter("user_id")){ //로그인성공
			notFound = 0;
			user_id = "";
		}else{ //로그인 실패
			notFound = Integer.parseInt(request.getParameter("notFound"));
			user_id = request.getParameter("user_id");
		}
		
		request.setAttribute("notFound", notFound);
		request.setAttribute("user_id", user_id);
		
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
			session.setAttribute("session_position", resultClass.getPosition()); //type 세션생성
			session.setAttribute("session_admin_yn", resultClass.getAdmin_yn()); // 관리자 Yes or No
			session.setAttribute("session_resultClass", resultClass); // 멤버에 대한 정보
			
			viewPath = "redirect:/main.do";
		}else{ // 아이디, 비밀번호 불일치
			viewPath = "redirect:/login.do?notFound=1&user_id="+user_id; //notFound 값을 1로 초기화, 사용자 입력 아이디를 로그인폼으로 리다이렉트
		}
		
		return viewPath;
	}
	
	
	//logOUT 실행
	@RequestMapping("/logoutPro.do")
	public String logoutPro(HttpServletRequest request, HttpSession session) throws Exception{
		
		//세션제거
		session.invalidate();
		
		return "redirect:/login.do";
	}
	
}
