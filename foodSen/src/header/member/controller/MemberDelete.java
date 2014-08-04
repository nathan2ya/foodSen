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
 * 설  명: 회원탈퇴 클래스
 * 			1. 회원정보 delete메소드
 * 			2. delete 전 회원정보(비밀번호) 재확인
*/

@Controller
public class MemberDelete {
	
	//회원탈퇴 delete DTO
	private MemberDTO resultClass = new MemberDTO(); // 회원탈퇴 form DTO
	
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

	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//회원탈퇴 form
	@RequestMapping("/memberDeleteFrom.do")
	public String memberDeleteFrom(HttpServletRequest request, HttpSession session) throws Exception{
		
		//인코딩정의
		request.setCharacterEncoding("euc-kr");
		
		//현재세션 (현재 로그인된 사용자가 회원탈퇴를 요청)
		String session_id = (String) session.getAttribute("session_id");
		
		//현재 로그인된 사용자의 레코드를 저장(비밀번호 재확인을 위함)
		resultClass = (MemberDTO)sqlMapper.queryForObject("Member.selectMemberOne", session_id); //해당세션의 레코드 get
		
		request.setAttribute("resultClass", resultClass);
		return "/view/member/memberDelete.jsp";
	}
	
	//회원탈퇴 DB delete
	@RequestMapping("memberDelete.do")
	public String memberDelete(HttpSession session) throws Exception{
		
		//탈퇴를 요청한 사용자 정보
		String session_id = (String) session.getAttribute("session_id");
		
		//해당 레코드 delete
		sqlMapper.delete("Member.deleteMember", session_id);
		
		//세션제거 (회원정보상실, 로그아웃을 위한 세션제거)
		session.invalidate();
		
		return "redirect:/main.do";
	}
	
} //end of class
