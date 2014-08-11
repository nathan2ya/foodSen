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

import common.Constants;

@Controller
public class ResearchView {
	
	//설분조사(정보)
	private ResearchDTO paramClass = new ResearchDTO();
	private ResearchDTO resultClass = new ResearchDTO();
	//설분조사(문제)
	private ResearchDTO1 paramClass1 = new ResearchDTO1();
	private List<ResearchDTO1> resultClass1 = new ArrayList<ResearchDTO1>();
	//설분조사(정보)
	private ResearchDTO2 paramClass2 = new ResearchDTO2();
	private List<ResearchDTO2> resultClass2 = new ArrayList<ResearchDTO2>();
	
	//설문조사 문제 모음
	private String[] title = new String[16];
	
	//설문조사 문항 모음
	private String[] i_title1 = new String[16];
	private String[] i_title2 = new String[16];
	private String[] i_title3 = new String[16];
	private String[] i_title4 = new String[16];
	private String[] i_title5 = new String[16];

	//뷰정보
	private int currentPage;
	private int searchingNow; // 전체글, 검색글을 판단하여 각종 논리성을 판가르는 논리값
	
	//검색중인 경우에만 발생하는 뷰정보
	private String searchType;
	private String userinput;
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public ResearchView() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	//설문조사 뷰페이지
	@RequestMapping("/researchView.do")
	public String researchView(HttpServletRequest request, HttpSession session) throws SQLException{
		
		/*
		 * 요청한 뷰정보 초기화
		 * sur_seq, currentPage, searchingNow
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
		//.요청한 뷰정보
		
		
		//설문조사(정보) 조회수를 +1 업데이트
		resultClass = (ResearchDTO)sqlMapper.queryForObject("Research.selectResearchOne", sur_seq);
		paramClass.setSur_seq(sur_seq);
		paramClass.setHits(resultClass.getHits()+1);
		sqlMapper.update("Research.updateHits", paramClass);
		//.조회수 +1 종료
		
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
		
		request.setAttribute("sur_seq", sur_seq);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("cnt", cnt); //설문조사 문항개수
		request.setAttribute("resultClass", resultClass); //설문조사(정보)
		request.setAttribute("resultClass1", resultClass1); //설문조사(문제)
		request.setAttribute("title", title); //설문조사(문제배열)
		request.setAttribute("resultClass2", resultClass2); //설문조사(문항)
		request.setAttribute("i_title1", i_title1); //설문조사(문항배열)
		request.setAttribute("i_title2", i_title2); //설문조사(문항배열)
		request.setAttribute("i_title3", i_title3); //설문조사(문항배열)
		request.setAttribute("i_title4", i_title4); //설문조사(문항배열)
		request.setAttribute("i_title5", i_title5); //설문조사(문항배열)
		return "/view/menu6/research/researchView.jsp";
	}
	
	//설문조사 결과 팝업페이지
	@RequestMapping("/researchResult.do")
	public String researchResult(HttpServletRequest request, HttpSession session) throws SQLException{
		
		//요청한 뷰정보 초기화
		int sur_seq = Integer.parseInt(request.getParameter("sur_seq"));
		System.out.println("sur_seq : "+sur_seq);
		/*
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
		*/
		
		request.setAttribute("sur_seq", sur_seq);
		return "/view/menu6/research/researchPopup.jsp";
	}
	
} //end of class
