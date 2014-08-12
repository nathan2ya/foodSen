package menu6.research.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import menu6.research.dto.ResearchDTO;
import menu6.research.dto.ResearchDTO1;
import menu6.research.dto.ResearchDTO2;
import menu6.research.dto.ResearchDTO3;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

@Controller
public class ResearchEdit {
	
	//설문조사(정보)
	private ResearchDTO paramClass = new ResearchDTO();
	private ResearchDTO resultClass = new ResearchDTO();
	//설문조사(문제)
	private ResearchDTO1 paramClass1 = new ResearchDTO1();
	private List<ResearchDTO1> resultClass1 = new ArrayList<ResearchDTO1>();
	//설문조사(정보)
	private ResearchDTO2 paramClass2 = new ResearchDTO2();
	private List<ResearchDTO2> resultClass2 = new ArrayList<ResearchDTO2>();
	//설문조사(결과)
	private ResearchDTO3 paramClass3 = new ResearchDTO3();
	private List<ResearchDTO3> resultClass3 = new ArrayList<ResearchDTO3>();
	private List<ResearchDTO3> resultClass33 = new ArrayList<ResearchDTO3>();
	
	//설문조사 문제 모음
	private int[] resultClass1_seq = new int[16];//문제의시퀀스 모음
	private String[] title = new String[16];
	
	//설문조사 문항 모음
	private int[] resultClass2_seq = new int[16];//문항의시퀀스 모음
	private String[] i_title1 = new String[16];
	private String[] i_title2 = new String[16];
	private String[] i_title3 = new String[16];
	private String[] i_title4 = new String[16];
	private String[] i_title5 = new String[16];
	
	//설문조사 결과 배열
	private int[][] res_cnt_arr = new int[16][5];
	
	//설문조사 번호만 잘라낸 결과
	private String chosen;
	
	//뷰정보
	private int currentPage;
	private int searchingNow; // 전체글, 검색글을 판단하여 각종 논리성을 판가르는 논리값
	private String permit; // 0 내용수정가능, 1 내용수정불가능
	
	//검색중인 경우에만 발생하는 뷰정보
	private String searchType;
	private String userinput;
	
	//설문조사 문제개수
	private int cnt;
	
	//결과return path
	private String uri;
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public ResearchEdit() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	//설문조사 수정폼 페이지
	@RequestMapping("/researchEditForm.do")
	public String researchEditForm(HttpServletRequest request, HttpSession session) throws SQLException{
		
		/*
		 * 요청한 뷰정보 초기화
		 * sur_seq, currentPage, searchingNow, permit
		*/
		int sur_seq = Integer.parseInt(request.getParameter("sur_seq"));
		if(null == request.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		
		//검색일 경우 존재하는 변수 초기화
		if(searchingNow==1){
			searchType = request.getParameter("searchType");
			userinput = request.getParameter("userinput");
			
			//뷰로 아래의 변수 3개를 보내는 이유는
			//목록을 클릭했을 때 다시 검색중인 리스트로 돌아가기 위함이다. +위의 currentPage와 함께!
			request.setAttribute("searchType", searchType);
			request.setAttribute("userinput", userinput);
			request.setAttribute("searchingNow", 1);
		}else{
			//검색중이 아닌경우에는 아래의 변수값이 존재하지 않음을 의미하는 0을 초기화 시켜서 해당 jsp 로 보낸다.
			request.setAttribute("searchType", 0);
			request.setAttribute("userinput", 0);
			request.setAttribute("searchingNow", 0);
		}
		
		permit = request.getParameter("permit");
		//.요청한 뷰정보
		
		
		//설문조사(정보) 레코드get
		resultClass = (ResearchDTO)sqlMapper.queryForObject("Research.selectResearchOne", sur_seq);
		int cnt = Integer.parseInt(resultClass.getQue_cnt());
		
		//설문조사(문제) 레코드get
		resultClass1 = sqlMapper.queryForList("Research.selectResearchOne1", sur_seq);
		for(int i=0; i<resultClass1.size(); i++){
			title[i] = resultClass1.get(i).getSurq_title();
		}
		
		//설문조사(문항) 레코드get
		resultClass2 = sqlMapper.queryForList("Research.selectResearchOne2", sur_seq);
		for(int j=0; j<resultClass2.size(); j++){
			i_title1[j] = resultClass2.get(j).getSuri_title1();
			i_title2[j] = resultClass2.get(j).getSuri_title2();
			i_title3[j] = resultClass2.get(j).getSuri_title3();
			i_title4[j] = resultClass2.get(j).getSuri_title4();
			i_title5[j] = resultClass2.get(j).getSuri_title5();
		}
		
		//설문조사(결과) 레코드get
		resultClass3 = sqlMapper.queryForList("Research.selectResearchOne3", sur_seq);
		int res_cnt = resultClass3.size(); //결과개수(수정,삭제 유효성을 위해 jsp로 보냄)
		
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String current_date = sdf.format(today.getTime());
		
		request.setAttribute("sur_seq", sur_seq);//설문조사(정보)시퀀스
		request.setAttribute("res_cnt", res_cnt); //결과레코드 개수
		request.setAttribute("permit", permit); //0내용수정가능, 1내용수정불가능
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("current_date", current_date);//오늘날짜
		request.setAttribute("cnt", cnt); //설문조사 문제개수
		request.setAttribute("resultClass", resultClass); //설문조사(정보)
		request.setAttribute("resultClass1", resultClass1); //설문조사(문제)
		request.setAttribute("title", title); //설문조사(문제배열)
		request.setAttribute("resultClass2", resultClass2); //설문조사(문항)
		request.setAttribute("i_title1", i_title1); //설문조사(문항배열)
		request.setAttribute("i_title2", i_title2); //설문조사(문항배열)
		request.setAttribute("i_title3", i_title3); //설문조사(문항배열)
		request.setAttribute("i_title4", i_title4); //설문조사(문항배열)
		request.setAttribute("i_title5", i_title5); //설문조사(문항배열)
		return "/view/menu6/research/researchEdit.jsp";
	}
	
	//설문조사 수정 페이지
	@RequestMapping("/researchEdit.do")
	public String researchEdit(HttpServletRequest request, HttpSession session) throws Exception{
		
		//인코딩
		request.setCharacterEncoding("euc-kr");
		
		//날짜
		Calendar today = Calendar.getInstance();
		
		//작성한 사용자(현재 로그인한 세션아이디)
		String session_id = (String) session.getAttribute("session_id");
		

		/*
		 * 요청한 뷰정보 초기화
		 * sur_seq, currentPage, searchingNow, permit
		*/
		int sur_seq = Integer.parseInt(request.getParameter("sur_seq"));
		if(null == request.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		
		//검색일 경우 존재하는 변수 초기화
		if(searchingNow==1){
			searchType = request.getParameter("searchType");
			userinput = request.getParameter("userinput");
			uri = "redirect:/researchView.do?sur_seq="+sur_seq+"&currentPage="+currentPage+"&searchingNow=1&searchType="+searchType+"&userinput="+userinput;
		}else{
			uri = "redirect:/researchView.do?sur_seq="+sur_seq+"&currentPage="+currentPage+"&searchingNow=0&searchType=0&userinput=0";
		}
		
		permit = request.getParameter("permit");
		cnt = Integer.parseInt(request.getParameter("cnt")); //문제개수
		//.요청한 뷰정보
		
		
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//

		/*
		 * 설문조사 정보 update
		 * 설문조사 게시글 데이터
		 * PK : sur_seq
		*/
		//사용자가 입력한 값(정보)
		String sur_title = request.getParameter("sur_title");
		String sur_sat_date = request.getParameter("sur_sat_date");
		String sur_end_date = request.getParameter("sur_end_date");
		
		//DTO Set()
		paramClass.setSur_seq(sur_seq);
		paramClass.setSur_title(sur_title);
		paramClass.setSur_sat_date(sur_sat_date);
		paramClass.setSur_end_date(sur_end_date);
		paramClass.setUdt_name(session_id);
		paramClass.setUdt_date(today.getTime());
		sqlMapper.update("Research.updateResearch", paramClass);
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		
		
		
		
		
		
		
		
		
		
		
		return uri; //수정요청했던 뷰페이지로 리다이렉트
	}
	
} //end of class
