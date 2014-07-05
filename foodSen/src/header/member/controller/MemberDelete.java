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
public class MemberDelete {
	
	private MemberDTO resultClass = new MemberDTO(); // 회원탈퇴 폼 DTO
	private MemberDTO paramClass = new MemberDTO(); // 로그인 비밀번호 확인 DTO
	
	private String viewPath; // 리턴 뷰 경로
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public MemberDelete() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	
	//회원탈퇴 폼
	@RequestMapping("/memberDeleteFrom.do")
	public String memberDeleteFrom(HttpServletRequest request, HttpSession session) throws Exception{
		request.setCharacterEncoding("euc-kr");
		
		//현재세션
		String session_id = (String) session.getAttribute("session_id");
		
		//해당세션의 레코드 정보
		resultClass = (MemberDTO)sqlMapper.queryForObject("Member.selectMemberOne", session_id);
		
		request.setAttribute("resultClass", resultClass);
		
		return "/view/member/memberDelete.jsp";
	}
	
	
	//회원탈퇴 실행
	@RequestMapping("memberDelete.do")
	public String memberDelete(HttpSession session) throws Exception{
		
		//탈퇴를 요청한 사용자 정보
		String session_id = (String) session.getAttribute("session_id");
		
		//해당 레코드 delete
		sqlMapper.delete("Member.deleteMember", session_id);
		
		//세션제거
		session.invalidate();
		
		return "redirect:/main.do";
	}
}
