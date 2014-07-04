package header.member.controller;

import header.member.dto.MemberDTO;

import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;


@Controller
public class MemberCreate {
	//회원가입 insert 용 DTO
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
	
	
	
	//회원가입 폼
	@RequestMapping("/memberCreateFrom.do")
	public String memberCreateFrom(){
		return "/view/member/memberCreate.jsp";
	}
	
	
	//회원가입 db insert
	@RequestMapping("/memberCreate.do")
	public String memberCreate(HttpServletRequest request) throws Exception{
		
		//사용자가 입력한 정보
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String member_name = request.getParameter("member_name");
		String school_name = request.getParameter("school_name");
		int school_type = Integer.parseInt(request.getParameter("school_type"));
		int position = Integer.parseInt(request.getParameter("position"));
		String sen_email = request.getParameter("sen_email");
		Calendar today = Calendar.getInstance(); //날짜

		
		//사용자 정보 DB insert
		paramClass.setUser_id(user_id);
		paramClass.setUser_pw(user_pw);
		paramClass.setMember_name(member_name);
		paramClass.setSchool_name(school_name);
		paramClass.setPosition(position);
		paramClass.setSen_email(sen_email);
		paramClass.setAdmin_yn("n");
		paramClass.setApprove_yn("n");
		paramClass.setSchool_type(school_type);
		paramClass.setReg_date(today.getTime());
		
		sqlMapper.insert("Member.insertMember",paramClass);
		
		return "redirect:/main.do";
	}
	
	
	
	
}
