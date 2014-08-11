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
public class ResearchView {
	
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
	
	//설문조사 결과 선택개수
	private int cnt1;
	private int cnt2;
	private int cnt3;
	private int cnt4;
	private int cnt5;
	

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
		
		//설문조사(결과) 레코드get
		resultClass3 = sqlMapper.queryForList("Research.selectResearchOne3", sur_seq);
		int res_cnt = resultClass3.size(); //결과개수(수정,삭제 유효성을 위해 jsp로 보냄)
		
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String current_date = sdf.format(today.getTime());
		
		request.setAttribute("sur_seq", sur_seq);
		request.setAttribute("res_cnt", res_cnt);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("current_date", current_date);//오늘날짜
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
		
		//설문조사(정보) 레코드get
		resultClass = (ResearchDTO)sqlMapper.queryForObject("Research.selectResearchOne", sur_seq);
		int cnt = Integer.parseInt(resultClass.getQue_cnt());
		
		//설문조사(문제) 레코드get
		resultClass1 = sqlMapper.queryForList("Research.selectResearchOne1", sur_seq);
		for(int i=0; i<resultClass1.size(); i++){
			resultClass1_seq[i] = resultClass1.get(i).getSurq_seq(); //문제의시퀀스 모음
			title[i] = resultClass1.get(i).getSurq_title(); //문제모음
		}
		
		//설문조사(문항) 레코드get
		resultClass2 = sqlMapper.queryForList("Research.selectResearchOne2", sur_seq);
		for(int j=0; j<resultClass2.size(); j++){
			resultClass2_seq[j] = resultClass2.get(j).getSuri_seq(); //문항의시퀀스 모음
			i_title1[j] = resultClass2.get(j).getSuri_title1();//문항1모음
			i_title2[j] = resultClass2.get(j).getSuri_title2();//문항2모음
			i_title3[j] = resultClass2.get(j).getSuri_title3();//문항3모음
			i_title4[j] = resultClass2.get(j).getSuri_title4();//문항4모음
			i_title5[j] = resultClass2.get(j).getSuri_title5();//문항5모음
		}
		
		//설문조사(결과) 레코드get
		
		/*
		 * 몇번글, 몇번문제에 몇번문항을 선택했는지 결과산출
		*/
		//공통사항
		paramClass3.setSur_seq(sur_seq); //몇번글
		
		//모든배열값 초기화
		for(int k=0; k<resultClass2.size(); k++){
			res_cnt_arr[k][0]=0;
			res_cnt_arr[k][1]=0;
			res_cnt_arr[k][2]=0;
			res_cnt_arr[k][3]=0;
			res_cnt_arr[k][4]=0;
		}
		
		//매번 팝업을 클릭할때마다 새롭게 카운트
		for(int k=0; k<cnt; k++){
			paramClass3.setSurq_seq(resultClass1_seq[k]);//문제의시퀀스
			paramClass3.setSuri_seq(resultClass2_seq[k]);//문항의시퀀스
			resultClass33 = sqlMapper.queryForList("Research.selectResearchOne33", paramClass3);//문제and문항 레코드get
			
			for(int z=0; z<resultClass33.size(); z++){//문제and문항 레코드의 답이 1일경우 0번, 2일경우 1번, 3일경우 2번, 4일경우 3번, 5일경우 4번 _ 값을 누적시킴
				chosen = resultClass33.get(z).getSuri_num().substring(0, 1);
				if(chosen.equals("①")) res_cnt_arr[k][0]++;
				if(chosen.equals("②")) res_cnt_arr[k][1]++;
				if(chosen.equals("③")) res_cnt_arr[k][2]++;
				if(chosen.equals("④")) res_cnt_arr[k][3]++;
				if(chosen.equals("⑤")) res_cnt_arr[k][4]++;
			}
		}
		
		request.setAttribute("sur_seq", sur_seq);
		request.setAttribute("resultClass", resultClass);//설문조사(정보)레코드
		request.setAttribute("cnt", cnt); //설문조사 문항개수
		request.setAttribute("resultClass1", resultClass1);//설문조사(문제)레코드
		request.setAttribute("title", title); //설문조사(문제배열)
		request.setAttribute("resultClass2", resultClass2); //설문조사(문항)
		request.setAttribute("i_title1", i_title1); //설문조사(문항1배열)
		request.setAttribute("i_title2", i_title2); //설문조사(문항2배열)
		request.setAttribute("i_title3", i_title3); //설문조사(문항3배열)
		request.setAttribute("i_title4", i_title4); //설문조사(문항4배열)
		request.setAttribute("i_title5", i_title5); //설문조사(문항5배열)
		request.setAttribute("resultClass3", resultClass3);//설문조사(결과)레코드
		request.setAttribute("res_cnt_arr", res_cnt_arr);//설문조사(결과)선택 카운트
		return "/view/menu6/research/researchPopup.jsp";
	}
	
	//설문조사 결과_사유 팝업페이지
	@RequestMapping("/researchResult1.do")
	public String researchResult1(HttpServletRequest request, HttpSession session) throws SQLException{
		
		//요청한 뷰정보 초기화
		int sur_seq = Integer.parseInt(request.getParameter("sur_seq"));
		
		//설문조사(정보) 레코드get
		resultClass = (ResearchDTO)sqlMapper.queryForObject("Research.selectResearchOne", sur_seq);
		int cnt = Integer.parseInt(resultClass.getQue_cnt());
		
		//설문조사(문제) 레코드get
		resultClass1 = sqlMapper.queryForList("Research.selectResearchOne1", sur_seq);
		for(int i=0; i<resultClass1.size(); i++){
			resultClass1_seq[i] = resultClass1.get(i).getSurq_seq(); //문제의시퀀스 모음
			title[i] = resultClass1.get(i).getSurq_title(); //문제모음
		}
		
		//설문조사(결과) 레코드get
		resultClass3 = sqlMapper.queryForList("Research.selectResearchOne3", sur_seq);
		
		
		request.setAttribute("sur_seq", sur_seq);
		request.setAttribute("resultClass", resultClass);//설문조사(정보)레코드
		request.setAttribute("cnt", cnt); //설문조사 문항개수
		request.setAttribute("resultClass1", resultClass1);//설문조사(문제)레코드
		request.setAttribute("title", title); //설문조사(문제배열)
		request.setAttribute("resultClass3", resultClass3);//설문조사(결과)레코드
		return "/view/menu6/research/researchChoiceReasonPopup.jsp";
	}
	
} //end of class
