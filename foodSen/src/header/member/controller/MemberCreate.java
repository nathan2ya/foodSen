package header.member.controller;

import header.member.dto.MemberDTO;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/*
 * 작성자: 류재욱
 * 설  명: 회원가입 클래스.
 * 			1. 회원정보 isnert메소드
 * 			2. insert 도중 호출되는 아이디중복체크 메소드
*/

@Controller
public class MemberCreate {
	
	//주소테스트
	private String city;
	private String gun;
	
	//아이디중복체크
	private String viewPath; //return경로
	private int notFound; //ID중복여부를 판단하는 논리값
	
	//회원가입 insert DTO
	private MemberDTO paramClass = new MemberDTO();
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public MemberCreate() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//회원가입 form
	@RequestMapping("/memberCreateFrom.do")
	public String memberCreateFrom() throws Exception{
		
		return "/view/member/memberCreate.jsp";
	}
	
	//회원가입 DB insert
	@RequestMapping("/memberCreate.do")
	public String memberCreate(HttpServletRequest request) throws Exception{
		
		//인코딩정의
		request.setCharacterEncoding("euc-kr");
		
		//사용자가 입력한 정보
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String member_name = request.getParameter("member_name");
		String school_name = request.getParameter("school_name");
		String school_type = request.getParameter("school_type");
		String position = request.getParameter("position");
		String sen_email = request.getParameter("sen_email");
		String phone = request.getParameter("phone");
		Calendar today = Calendar.getInstance(); //날짜
		//주소테스트
		String post1 = request.getParameter("post1");
		String post2 = request.getParameter("post2");
		String addr = request.getParameter("addr");
		String addr2 = request.getParameter("addr2");
		
		//도,시 와 군,구 분류 시작
		int space = addr.indexOf(" ");
		city = addr.substring(0, space);//도,시
		String tempAddr = addr.substring(space+1);
		space = tempAddr.indexOf(" ");
		gun = tempAddr.substring(0, space);//군,구
		//도,시 와 군,구 분류 종료
		
		System.out.println("우편번호 : "+post1+"-"+post2);
		System.out.println("주소 : "+addr);
		System.out.println("상세주소 : "+addr2);
		System.out.println("#######################################");
		System.out.println("도,시 : "+city);
		System.out.println("군,구 : "+gun);
		//.주소테스트 종료
		
		//사용자 정보 DB insert
		paramClass.setUser_id(user_id);
		paramClass.setUser_pw(user_pw);
		paramClass.setMember_name(member_name);
		paramClass.setSchool_name(school_name);
		paramClass.setPosition(position);
		paramClass.setSen_email(sen_email);
		paramClass.setPhone(phone);
		paramClass.setAdmin_yn("n");
		paramClass.setApprove_yn("n");
		paramClass.setSchool_type(school_type);
		paramClass.setReg_date(today.getTime());
		sqlMapper.insert("Member.insertMember",paramClass);
		
		return "redirect:/main.do";
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//아이디중복체크 form
	@RequestMapping("/checkUser_idFrom.do")
	public String checkUser_idFrom(HttpServletRequest request) throws Exception{
		
		//사용자가 입력한 정보
		String user_id = request.getParameter("user_id");
		
		/*
		 * 아이디중복체크 폼으로 가기전 notFound 의 변수를 정의한다.
		 * notFound 변수정의
		 *  0 : 중복아님
		 *  1 : 중복
		 *  2 : 최초진입
		*/
		if(null == request.getParameter("notFound")){ //notFound 변수 값이 없을 경우 : 최초 진입시
			notFound = 2;
		}else{ //중복아님 0, 중복 1 로 초기화.
			notFound = Integer.parseInt(request.getParameter("notFound"));
		}
		//.notFound 정의 종료
		
		request.setAttribute("user_id", user_id);
		request.setAttribute("notFound", notFound);
		return "/view/member/checkUser_idFrom.jsp";
	}
	
	//아이디중복체크 실행
	@RequestMapping("/checkUser_id.do")
	public String checkUser_id(HttpServletRequest request) throws Exception{
		
		//사용자가 입력한 정보
		String user_id = request.getParameter("user_id");
		
		/*
		 * 아이디중복체크 폼으로 가기전 notFound 의 변수를 정의한다.
		 *  1 : 중복
		 *  2 : 최초진입
		*/
		Integer count = (Integer) sqlMapper.queryForObject("Member.selectUser_id", user_id); //해당 아이디가 있는지 없는지 판단
		
		if(count == 0){ //사용자가 입력한 정보와 같은 레코드가 없음, 중복안됨
			viewPath = "redirect:/checkUser_idFrom.do?user_id="+user_id+"&notFound=0"; //notFound 0으로 초기화
		}else{ //사용자가 입력한 정보와 같은 레코드가 있음, 중복됨
			viewPath = "redirect:/checkUser_idFrom.do?user_id="+user_id+"&notFound=1"; //notFound 1로 초기화
		}
		
		return viewPath;
	}
	
} //end of class
