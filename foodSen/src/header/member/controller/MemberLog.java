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
 * 설  명: 회원 LOG in,out 클래스
 * 용  도: 회원 세션생성 및 세션제거를 위함.
*/

@Controller
public class MemberLog {
	
	//회원 로그인
	private MemberDTO paramClass = new MemberDTO(); // 로그인 비밀번호 확인 DTO
	private MemberDTO resultClass = new MemberDTO(); // 통과된 아이디의 레코드 정보 가져오기 DTO
	private String viewPath; //return 경로
	private int notFound; //로그인 성공 or 실패에 관한 논리값
	private String user_id; //로그인 실패에 관한 id 저장변수 (다시 로그인폼으로 돌아갔을 때 ID input 공간에 id를 남겨두기 위함)
	
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
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//회원로그인 form
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request){
		
		/*
		 * 회원로그인시 notFound값에 의해서 로그인성공 or 로그인실패 분기
		*/
		
		if(null == request.getParameter("notFound") && null == request.getParameter("user_id")){ //최초진입시
			notFound = 0; // 로그인성공 또는 최초진입을 의미하고, 회원로그인 form 정상진입
			user_id = "";
		}else{ //로그인 실패
			notFound = Integer.parseInt(request.getParameter("notFound")); // 로그인실패를 의미하고, 회원로그인 form 진입시 alert
			user_id = request.getParameter("user_id"); //로그인이 실패했으므로, 이전에 사용자가 요청했던 id를 그대로 다시 회원로그인 form으로 넘김
		}
		
		request.setAttribute("notFound", notFound);
		request.setAttribute("user_id", user_id);
		return "/view/member/memberLoginFrom.jsp";
	}
	
	//log in 실행
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
			
			//로그인 성공시 다음의 세션을 생성함
			session.setAttribute("session_id", resultClass.getUser_id()); // id 세션생성
			session.setAttribute("session_position", resultClass.getPosition()); //type 세션생성
			session.setAttribute("session_admin_yn", resultClass.getAdmin_yn()); // 관리자 Yes or No
			session.setAttribute("session_resultClass", resultClass); // 멤버에 대한 정보
			session.setAttribute("session_memberName", resultClass.getMember_name()); // 멤버이름
			
			viewPath = "redirect:/main.do";
		}else{ // 아이디, 비밀번호 불일치
			viewPath = "redirect:/login.do?notFound=1&user_id="+user_id; //notFound 값을 1로 초기화, 사용자 입력 아이디를 로그인폼으로 리다이렉트
		}
		
		return viewPath;
	}
	
	//log out 실행
	@RequestMapping("/logoutPro.do")
	public String logoutPro(HttpServletRequest request, HttpSession session) throws Exception{
		
		//세션제거
		session.invalidate();
		
		return "redirect:/login.do";
	}
	
} //end of class
