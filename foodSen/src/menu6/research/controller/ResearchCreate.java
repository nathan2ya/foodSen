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
 * 작성자: 류재욱
 * 설  명: 설문조사 insert 클래스
 * 용  도: 설문조사 게시글 등록을 위함.
*/

@Controller
public class ResearchCreate {
	
	//설문조사
	private ResearchDTO paramClass = new ResearchDTO();//
	private ResearchDTO resultClass = new ResearchDTO();//
	
	//최대시퀀스넘버
	private int seq;
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public ResearchCreate() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	//설문조사 입력폼
	@RequestMapping("/researchCreateForm.do")
	public String researchCreateForm(){
		return "/view/menu6/research/researchCreate.jsp";
	}
	
	/*
	//설문조사 DB insert
	@RequestMapping(value="/researchCreate.do", method=RequestMethod.POST)
	public String researchCreate(HttpServletRequest request, HttpSession session) throws Exception{
		
		//인코딩
		request.setCharacterEncoding("euc-kr");
		
		//작성한 사용자(현재 로그인한 세션아이디)
		String session_id = (String) session.getAttribute("session_id");
		
		//사용자가 입력한 값
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
		
		//등록만기 N
		paramClass.setEnd_yn("N");
		
		//DB에 insert 하기 (글 등록)
		sqlMapper.insert("Recruit.insertRecruit", paramClass);
		
		return "redirect:/recruitList.do"; //리스트로 리다이렉트
	}
	*/
} //end of class
