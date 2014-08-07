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
	
	//설문조사(정보)
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
	
	//설문조사 DB insert
	@RequestMapping(value="/researchCreate.do", method=RequestMethod.POST)
	public String researchCreate(HttpServletRequest request, HttpSession session) throws Exception{
		
		//인코딩
		request.setCharacterEncoding("euc-kr");
		//날짜
		Calendar today = Calendar.getInstance();
		
		//작성한 사용자(현재 로그인한 세션아이디)
		String session_id = (String) session.getAttribute("session_id");
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		/*
		 * 설문조사 정보 insert
		 * 설문조사 게시글 데이터
		 * PK : sur_seq
		*/
		//사용자가 입력한 값(정보)
		String sur_title = request.getParameter("sur_title");
		String sur_sat_date = request.getParameter("sur_sat_date");
		String sur_end_date = request.getParameter("sur_end_date");
		String que_cnt = request.getParameter("que_cnt");
		
		//DTO Set()
		paramClass.setSur_title(sur_title);
		paramClass.setSur_sat_date(sur_sat_date);
		paramClass.setSur_end_date(sur_end_date);
		paramClass.setQue_cnt(que_cnt);
		paramClass.setHits(0);
		paramClass.setReg_name(session_id);
		paramClass.setReg_date(today.getTime());
		paramClass.setUdt_name(session_id);
		paramClass.setUdt_date(today.getTime());
		paramClass.setWriter(session_id);
		sqlMapper.insert("Research.insertResearch", paramClass);
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		/*
		 * 설문조사 문제 insert
		 * 설문조사 정보에 포함된 문제
		 * PK : surq_seq
		 * FK : sur_seq
		*/
		//사용자가 입력한 값(문제)
		
		
		//DTO set()
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		/*
		 * 설문조사 문항 insert
		 * 설문조사 정보에 포함된 문제 속 문항
		 * PK : suri_seq
		 * FK : sur_seq, surq_seq
		*/
		//사용자가 입력한 값(문제)
		
		
		//DTO set()
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		return "redirect:/recruitList.do"; //리스트로 리다이렉트
	}
} //end of class
