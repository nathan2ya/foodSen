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
	private ResearchDTO1 resultClass11 = new ResearchDTO1();
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
	private int que_cnt_size; //기존
	private int que_cnt; //수정시 들어온값.
	
	//설문조사 문제
	private String surq_title1;
	private String surq_title2;
	private String surq_title3;
	private String surq_title4;
	private String surq_title5;
	private String surq_title6;
	private String surq_title7;
	private String surq_title8;
	private String surq_title9;
	private String surq_title10;
	private String surq_title11;
	private String surq_title12;
	private String surq_title13;
	private String surq_title14;
	private String surq_title15;
	private String surq_title16;
	
	//설문조사 문항
	private String item11;
	private String item21;
	private String item31;
	private String item41;
	private String item51;
	
	private String item12;
	private String item22;
	private String item32;
	private String item42;
	private String item52;
	
	private String item13;
	private String item23;
	private String item33;
	private String item43;
	private String item53;
	
	private String item14;
	private String item24;
	private String item34;
	private String item44;
	private String item54;
	
	private String item15;
	private String item25;
	private String item35;
	private String item45;
	private String item55;
	
	private String item16;
	private String item26;
	private String item36;
	private String item46;
	private String item56;
	
	private String item17;
	private String item27;
	private String item37;
	private String item47;
	private String item57;
	
	private String item18;
	private String item28;
	private String item38;
	private String item48;
	private String item58;
	
	private String item19;
	private String item29;
	private String item39;
	private String item49;
	private String item59;
	
	private String item110;
	private String item210;
	private String item310;
	private String item410;
	private String item510;
	
	private String item111;
	private String item211;
	private String item311;
	private String item411;
	private String item511;
	
	private String item112;
	private String item212;
	private String item312;
	private String item412;
	private String item512;
	
	private String item113;
	private String item213;
	private String item313;
	private String item413;
	private String item513;
	
	private String item114;
	private String item214;
	private String item314;
	private String item414;
	private String item514;
	
	private String item115;
	private String item215;
	private String item315;
	private String item415;
	private String item515;
	
	private String item116;
	private String item216;
	private String item316;
	private String item416;
	private String item516;
	
	//설문조사 문제시퀀스
	private int resultClass1_seq1;
	private int resultClass1_seq2;
	private int resultClass1_seq3;
	private int resultClass1_seq4;
	private int resultClass1_seq5;
	private int resultClass1_seq6;
	private int resultClass1_seq7;
	private int resultClass1_seq8;
	private int resultClass1_seq9;
	private int resultClass1_seq10;
	private int resultClass1_seq11;
	private int resultClass1_seq12;
	private int resultClass1_seq13;
	private int resultClass1_seq14;
	private int resultClass1_seq15;
	private int resultClass1_seq16;
	
	private int surq_seq1;
	private int surq_seq2;
	private int surq_seq3;
	private int surq_seq4;
	private int surq_seq5;
	private int surq_seq6;
	private int surq_seq7;
	private int surq_seq8;
	private int surq_seq9;
	private int surq_seq10;
	private int surq_seq11;
	private int surq_seq12;
	private int surq_seq13;
	private int surq_seq14;
	private int surq_seq15;
	private int surq_seq16;
	
	//설문조사 문항시퀀스
	private int resultClass2_seq1;
	private int resultClass2_seq2;
	private int resultClass2_seq3;
	private int resultClass2_seq4;
	private int resultClass2_seq5;
	private int resultClass2_seq6;
	private int resultClass2_seq7;
	private int resultClass2_seq8;
	private int resultClass2_seq9;
	private int resultClass2_seq10;
	private int resultClass2_seq11;
	private int resultClass2_seq12;
	private int resultClass2_seq13;
	private int resultClass2_seq14;
	private int resultClass2_seq15;
	private int resultClass2_seq16;
	
	private int suri_seq1;
	private int suri_seq2;
	private int suri_seq3;
	private int suri_seq4;
	private int suri_seq5;
	private int suri_seq6;
	private int suri_seq7;
	private int suri_seq8;
	private int suri_seq9;
	private int suri_seq10;
	private int suri_seq11;
	private int suri_seq12;
	private int suri_seq13;
	private int suri_seq14;
	private int suri_seq15;
	private int suri_seq16;
	
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
			resultClass1_seq[i] = resultClass1.get(i).getSurq_seq(); //문제의시퀀스 모음
			title[i] = resultClass1.get(i).getSurq_title(); //문제모음
		}
		
		//설문조사(문항) 레코드get
		resultClass2 = sqlMapper.queryForList("Research.selectResearchOne2", sur_seq);
		for(int j=0; j<resultClass2.size(); j++){
			resultClass2_seq[j] = resultClass2.get(j).getSuri_seq(); //문항의시퀀스 모음
			i_title1[j] = resultClass2.get(j).getSuri_title1(); //문항1 모음
			i_title2[j] = resultClass2.get(j).getSuri_title2(); //문항2 모음
			i_title3[j] = resultClass2.get(j).getSuri_title3(); //문항3 모음
			i_title4[j] = resultClass2.get(j).getSuri_title4(); //문항4 모음
			i_title5[j] = resultClass2.get(j).getSuri_title5(); //문항5 모음
		}
		
		//설문조사(결과)레코드 삭제
		//아래의 설문조사(결과) 레코드get 부분은 필요없어짐. (설문조사 결과가있을때, 수정할 경우 삭제하는 것이 맞지 않다면 아래의 설문조사(결과) 레코드get이 필요하게됨)
		if(permit.equals("1")){
			sqlMapper.delete("Research.deleteResearch3", sur_seq);
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
		request.setAttribute("resultClass1_seq", resultClass1_seq); //설문조사(문제시퀀스)
		request.setAttribute("title", title); //설문조사(문제배열)
		request.setAttribute("resultClass2", resultClass2); //설문조사(문항)
		request.setAttribute("resultClass2_seq", resultClass2_seq); //설문조사(문항시퀀스)
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
		que_cnt_size = Integer.parseInt(request.getParameter("cnt")); //기존 수정요청한view의 문제개수
		que_cnt = Integer.parseInt(request.getParameter("que_cnt"));;//수정시에 방금 사용자가 정의한 문개개수
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
		paramClass.setQue_cnt(Integer.toString(que_cnt));
		paramClass.setSur_title(sur_title);
		paramClass.setSur_sat_date(sur_sat_date);
		paramClass.setSur_end_date(sur_end_date);
		paramClass.setUdt_name(session_id);
		paramClass.setUdt_date(today.getTime());
		sqlMapper.update("Research.updateResearch", paramClass);
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		
		/*
		 * 설문조사 문제,문항 update
		 * 설문조사 정보에 포함된 문제,문항
		 * PK : surq_seq
		 * FK : sur_seq
		 * 
		 * 최대 16개의 문제가 들어갈 수 있으며,
		 * 16개는 레코드 단위로 들어간다.
		*/
		
		//설문조사 문제 공통으로 들어가는 레코드.
		paramClass1.setSur_seq(sur_seq);
		paramClass1.setUdt_name(session_id);
		paramClass1.setUdt_date(today.getTime());
		
		//설문조사 문항 공통으로 들어가는 레코드
		paramClass2.setSur_seq(sur_seq);
		paramClass2.setUdt_name(session_id);
		paramClass2.setUdt_date(today.getTime());
		
		//설문조사 문제 insert(최대16번 insert)
		//각 문제에 포함된 문항 insert

		
		/*
		 * 사용자가 정한 문항개수만큼 문제, 문항에 대해 insert를 실시한다.
		 * 설문조사 문제 insert (1번~최대16번 실행)
		 *  - 각 문제에 포함된 문항5개 insert (1번~최대16번 실행)
		 *  
		 *  que_cnt_size : 기존문항개수
		 *  que_cnt : 방금사용자가 선택한 수정개수
		*/
		
		//시나리오1. 사용자가 문제수를 감소했거나, 유지했을때
		if(que_cnt_size >= que_cnt){
		
			if(1 <= que_cnt_size){
				surq_seq1 = Integer.parseInt(request.getParameter("resultClass1_seq1"));//문제의시퀀스
				suri_seq1 = Integer.parseInt(request.getParameter("resultClass2_seq1"));//문항의시퀀스
				//문제DTO SET
				paramClass1.setSurq_seq(surq_seq1);
				//문항DTO SET
				paramClass2.setSurq_seq(surq_seq1);
				paramClass2.setSuri_seq(suri_seq1);
				
				//수정시 문제를 줄였을 경우
				if(1 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 업데이트
					
					//사용자가 입력한 값(문제)
					surq_title1 = request.getParameter("surq_title1");
					//문제DTO SET
					paramClass1.setSurq_title(surq_title1);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					//사용자가 입력한 값(문항)
					item11 = request.getParameter("item11");
					item21 = request.getParameter("item21");
					item31 = request.getParameter("item31");
					item41 = request.getParameter("item41");
					item51 = request.getParameter("item51");
					//문항DTO SET
					paramClass2.setSuri_title1(item11);
					paramClass2.setSuri_title2(item21);
					paramClass2.setSuri_title3(item31);
					paramClass2.setSuri_title4(item41);
					paramClass2.setSuri_title5(item51);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
			}
			
			if(2 <= que_cnt_size){
				surq_seq2 = Integer.parseInt(request.getParameter("resultClass1_seq2"));//문제의시퀀스
				suri_seq2 = Integer.parseInt(request.getParameter("resultClass2_seq2"));//문항의시퀀스
				paramClass1.setSurq_seq(surq_seq2);
				paramClass2.setSurq_seq(surq_seq2);
				paramClass2.setSuri_seq(suri_seq2);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(2 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					//사용자가 입력한 값(문제)
					surq_title2 = request.getParameter("surq_title2");
					//문제DTO SET
					paramClass1.setSurq_title(surq_title2);
					//설문조사(문제) update
					sqlMapper.update("Research.updateResearch1", paramClass1);
					
					
					//사용자가 입력한 값(문항)
					item12 = request.getParameter("item12");
					item22 = request.getParameter("item22");
					item32 = request.getParameter("item32");
					item42 = request.getParameter("item42");
					item52 = request.getParameter("item52");
					//문항DTO SET
					paramClass2.setSuri_title1(item12);
					paramClass2.setSuri_title2(item22);
					paramClass2.setSuri_title3(item32);
					paramClass2.setSuri_title4(item42);
					paramClass2.setSuri_title5(item52);
					//설문조사(문항) update
					sqlMapper.update("Research.updateResearch2", paramClass2);
				}
			}
			
			
			if(3 <= que_cnt_size){
				surq_seq3 = Integer.parseInt(request.getParameter("resultClass1_seq3"));//문제의시퀀스
				suri_seq3 = Integer.parseInt(request.getParameter("resultClass2_seq3"));//문항의시퀀스
				paramClass1.setSurq_seq(surq_seq3);
				paramClass2.setSurq_seq(surq_seq3);
				paramClass2.setSuri_seq(suri_seq3);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(3 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					surq_title3 = request.getParameter("surq_title3");
					paramClass1.setSurq_title(surq_title3);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					//사용자가 입력한 값(문항)
					item13 = request.getParameter("item13");
					item23 = request.getParameter("item23");
					item33 = request.getParameter("item33");
					item43 = request.getParameter("item43");
					item53 = request.getParameter("item53");
					paramClass2.setSuri_title1(item13);
					paramClass2.setSuri_title2(item23);
					paramClass2.setSuri_title3(item33);
					paramClass2.setSuri_title4(item43);
					paramClass2.setSuri_title5(item53);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
			}
			
			  
			if(4 <= que_cnt_size){
				//문제의시퀀스
				surq_seq4 = Integer.parseInt(request.getParameter("resultClass1_seq4"));
				paramClass1.setSurq_seq(surq_seq4);
				//문항의시퀀스
				suri_seq4 = Integer.parseInt(request.getParameter("resultClass2_seq4"));
				paramClass2.setSurq_seq(surq_seq4);
				paramClass2.setSuri_seq(suri_seq4);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(4 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					surq_title4 = request.getParameter("surq_title4");
					paramClass1.setSurq_seq(surq_seq4);
					paramClass1.setSurq_title(surq_title4);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					
					//문항의시퀀스
					suri_seq4 = Integer.parseInt(request.getParameter("resultClass2_seq4"));
					
					//사용자가 입력한 값(문항)
					item14 = request.getParameter("item14");
					item24 = request.getParameter("item24");
					item34 = request.getParameter("item34");
					item44 = request.getParameter("item44");
					item54 = request.getParameter("item54");
					
					paramClass2.setSurq_seq(surq_seq4);
					paramClass2.setSuri_seq(suri_seq4);
					paramClass2.setSuri_title1(item14);
					paramClass2.setSuri_title2(item24);
					paramClass2.setSuri_title3(item34);
					paramClass2.setSuri_title4(item44);
					paramClass2.setSuri_title5(item54);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
			}
			
			if(5 <= que_cnt_size){
				//문제의시퀀스
				surq_seq5 = Integer.parseInt(request.getParameter("resultClass1_seq5"));
				paramClass1.setSurq_seq(surq_seq5);
				//문항의시퀀스
				suri_seq5 = Integer.parseInt(request.getParameter("resultClass2_seq5"));
				paramClass2.setSurq_seq(surq_seq5);
				paramClass2.setSuri_seq(suri_seq5);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(5 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					//문제의시퀀스
					surq_seq5 = Integer.parseInt(request.getParameter("resultClass1_seq5"));
					surq_title5 = request.getParameter("surq_title5");
					paramClass1.setSurq_seq(surq_seq5);
					paramClass1.setSurq_title(surq_title5);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					
					//사용자가 입력한 값(문항)
					item15 = request.getParameter("item15");
					item25 = request.getParameter("item25");
					item35 = request.getParameter("item35");
					item45 = request.getParameter("item45");
					item55 = request.getParameter("item55");
					
					paramClass2.setSurq_seq(surq_seq5);
					paramClass2.setSuri_seq(suri_seq5);
					paramClass2.setSuri_title1(item15);
					paramClass2.setSuri_title2(item25);
					paramClass2.setSuri_title3(item35);
					paramClass2.setSuri_title4(item45);
					paramClass2.setSuri_title5(item55);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
			}
			
			if(6 <= que_cnt_size){
				//문제의시퀀스
				surq_seq6 = Integer.parseInt(request.getParameter("resultClass1_seq6"));
				paramClass1.setSurq_seq(surq_seq6);
				//문항의시퀀스
				suri_seq6 = Integer.parseInt(request.getParameter("resultClass2_seq6"));
				paramClass2.setSurq_seq(surq_seq6);
				paramClass2.setSuri_seq(suri_seq6);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(6 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					surq_title6 = request.getParameter("surq_title6");
					paramClass1.setSurq_seq(surq_seq6);
					paramClass1.setSurq_title(surq_title6);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					
					//문항의시퀀스
					suri_seq6 = Integer.parseInt(request.getParameter("resultClass2_seq6"));
					
					//사용자가 입력한 값(문항)
					item16 = request.getParameter("item16");
					item26 = request.getParameter("item26");
					item36 = request.getParameter("item36");
					item46 = request.getParameter("item46");
					item56 = request.getParameter("item56");
					
					paramClass2.setSurq_seq(surq_seq6);
					paramClass2.setSuri_seq(suri_seq6);
					paramClass2.setSuri_title1(item16);
					paramClass2.setSuri_title2(item26);
					paramClass2.setSuri_title3(item36);
					paramClass2.setSuri_title4(item46);
					paramClass2.setSuri_title5(item56);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
			}
			
			if(7 <= que_cnt_size){
				//문제의시퀀스
				surq_seq7 = Integer.parseInt(request.getParameter("resultClass1_seq7"));
				paramClass1.setSurq_seq(surq_seq7);
				//문항의시퀀스
				suri_seq7 = Integer.parseInt(request.getParameter("resultClass2_seq7"));
				paramClass2.setSurq_seq(surq_seq7);
				paramClass2.setSuri_seq(suri_seq7);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(7 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					surq_title7 = request.getParameter("surq_title7");
					paramClass1.setSurq_seq(surq_seq7);
					paramClass1.setSurq_title(surq_title7);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					
					//사용자가 입력한 값(문항)
					item17 = request.getParameter("item17");
					item27 = request.getParameter("item27");
					item37 = request.getParameter("item37");
					item47 = request.getParameter("item47");
					item57 = request.getParameter("item57");
					
					paramClass2.setSurq_seq(surq_seq7);
					paramClass2.setSuri_seq(suri_seq7);
					paramClass2.setSuri_title1(item17);
					paramClass2.setSuri_title2(item27);
					paramClass2.setSuri_title3(item37);
					paramClass2.setSuri_title4(item47);
					paramClass2.setSuri_title5(item57);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
			}
			
			if(8 <= que_cnt_size){
				//문제의시퀀스
				surq_seq8 = Integer.parseInt(request.getParameter("resultClass1_seq8"));
				paramClass1.setSurq_seq(surq_seq8);
				//문항의시퀀스
				suri_seq8 = Integer.parseInt(request.getParameter("resultClass2_seq8"));
				paramClass2.setSurq_seq(surq_seq8);
				paramClass2.setSuri_seq(suri_seq8);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(8 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					surq_title8 = request.getParameter("surq_title8");
					paramClass1.setSurq_seq(surq_seq8);
					paramClass1.setSurq_title(surq_title8);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					//문항의시퀀스
					suri_seq8 = Integer.parseInt(request.getParameter("resultClass2_seq8"));
					
					//사용자가 입력한 값(문항)
					item18 = request.getParameter("item18");
					item28 = request.getParameter("item28");
					item38 = request.getParameter("item38");
					item48 = request.getParameter("item48");
					item58 = request.getParameter("item58");
					
					paramClass2.setSurq_seq(surq_seq8);
					paramClass2.setSuri_seq(suri_seq8);
					paramClass2.setSuri_title1(item18);
					paramClass2.setSuri_title2(item28);
					paramClass2.setSuri_title3(item38);
					paramClass2.setSuri_title4(item48);
					paramClass2.setSuri_title5(item58);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
			}
			
			if(9 <= que_cnt_size){
				//문제의시퀀스
				surq_seq9 = Integer.parseInt(request.getParameter("resultClass1_seq9"));
				paramClass1.setSurq_seq(surq_seq9);
				//문항의시퀀스
				suri_seq9 = Integer.parseInt(request.getParameter("resultClass2_seq9"));
				paramClass2.setSurq_seq(surq_seq9);
				paramClass2.setSuri_seq(suri_seq9);
				
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(9 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					surq_title9 = request.getParameter("surq_title9");
					paramClass1.setSurq_seq(surq_seq9);
					paramClass1.setSurq_title(surq_title9);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					//사용자가 입력한 값(문항)
					item19 = request.getParameter("item19");
					item29 = request.getParameter("item29");
					item39 = request.getParameter("item39");
					item49 = request.getParameter("item49");
					item59 = request.getParameter("item59");
					
					paramClass2.setSurq_seq(surq_seq9);
					paramClass2.setSuri_seq(suri_seq9);
					paramClass2.setSuri_title1(item19);
					paramClass2.setSuri_title2(item29);
					paramClass2.setSuri_title3(item39);
					paramClass2.setSuri_title4(item49);
					paramClass2.setSuri_title5(item59);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
			}
			
			if(10 <= que_cnt_size){
				//문제의시퀀스
				surq_seq10 = Integer.parseInt(request.getParameter("resultClass1_seq10"));
				paramClass1.setSurq_seq(surq_seq10);
				//문항의시퀀스
				suri_seq10 = Integer.parseInt(request.getParameter("resultClass2_seq10"));
				paramClass2.setSurq_seq(surq_seq10);
				paramClass2.setSuri_seq(suri_seq10);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(10 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					surq_title10 = request.getParameter("surq_title10");
					paramClass1.setSurq_seq(surq_seq10);
					paramClass1.setSurq_title(surq_title10);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					
					//사용자가 입력한 값(문항)
					item110 = request.getParameter("item110");
					item210 = request.getParameter("item210");
					item310 = request.getParameter("item310");
					item410 = request.getParameter("item410");
					item510 = request.getParameter("item510");
					
					paramClass2.setSurq_seq(surq_seq10);
					paramClass2.setSuri_seq(suri_seq10);
					paramClass2.setSuri_title1(item110);
					paramClass2.setSuri_title2(item210);
					paramClass2.setSuri_title3(item310);
					paramClass2.setSuri_title4(item410);
					paramClass2.setSuri_title5(item510);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
				
			}
			
			if(11 <= que_cnt_size){
				//문제의시퀀스
				surq_seq11 = Integer.parseInt(request.getParameter("resultClass1_seq11"));
				paramClass1.setSurq_seq(surq_seq11);
				//문항의시퀀스
				suri_seq11 = Integer.parseInt(request.getParameter("resultClass2_seq11"));
				paramClass2.setSurq_seq(surq_seq11);
				paramClass2.setSuri_seq(suri_seq11);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(11 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					surq_title11 = request.getParameter("surq_title11");
					paramClass1.setSurq_seq(surq_seq11);
					paramClass1.setSurq_title(surq_title11);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					//사용자가 입력한 값(문항)
					item111 = request.getParameter("item111");
					item211 = request.getParameter("item211");
					item311 = request.getParameter("item311");
					item411 = request.getParameter("item411");
					item511 = request.getParameter("item511");
					
					paramClass2.setSurq_seq(surq_seq11);
					paramClass2.setSuri_seq(suri_seq11);
					paramClass2.setSuri_title1(item111);
					paramClass2.setSuri_title2(item211);
					paramClass2.setSuri_title3(item311);
					paramClass2.setSuri_title4(item411);
					paramClass2.setSuri_title5(item511);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
					
				}
			}
			
			if(12 <= que_cnt_size){
				//문제의시퀀스
				surq_seq12 = Integer.parseInt(request.getParameter("resultClass1_seq12"));
				paramClass1.setSurq_seq(surq_seq12);
				//문항의시퀀스
				suri_seq12 = Integer.parseInt(request.getParameter("resultClass2_seq12"));
				paramClass2.setSurq_seq(surq_seq12);
				paramClass2.setSuri_seq(suri_seq12);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(12 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					surq_title12 = request.getParameter("surq_title12");
					paramClass1.setSurq_title(surq_title12);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					
					//사용자가 입력한 값(문항)
					item112 = request.getParameter("item112");
					item212 = request.getParameter("item212");
					item312 = request.getParameter("item312");
					item412 = request.getParameter("item412");
					item512 = request.getParameter("item512");
					
					paramClass2.setSurq_seq(surq_seq12);
					paramClass2.setSuri_seq(suri_seq12);
					paramClass2.setSuri_title1(item112);
					paramClass2.setSuri_title2(item212);
					paramClass2.setSuri_title3(item312);
					paramClass2.setSuri_title4(item412);
					paramClass2.setSuri_title5(item512);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
				
			}
			
			if(13 <= que_cnt_size){
				//문제의시퀀스
				surq_seq13 = Integer.parseInt(request.getParameter("resultClass1_seq13"));
				paramClass1.setSurq_seq(surq_seq13);
				//문항의시퀀스
				suri_seq13 = Integer.parseInt(request.getParameter("resultClass2_seq13"));
				paramClass2.setSurq_seq(surq_seq13);
				paramClass2.setSuri_seq(suri_seq13);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(13 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					surq_title13 = request.getParameter("surq_title13");
					paramClass1.setSurq_title(surq_title13);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					
					//사용자가 입력한 값(문항)
					item113 = request.getParameter("item113");
					item213 = request.getParameter("item213");
					item313 = request.getParameter("item313");
					item413 = request.getParameter("item413");
					item513 = request.getParameter("item513");
					
					paramClass2.setSurq_seq(surq_seq13);
					paramClass2.setSuri_seq(suri_seq13);
					paramClass2.setSuri_title1(item113);
					paramClass2.setSuri_title2(item213);
					paramClass2.setSuri_title3(item313);
					paramClass2.setSuri_title4(item413);
					paramClass2.setSuri_title5(item513);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
				
			}
			
			
			if(14 <= que_cnt_size){
				//문제의시퀀스
				surq_seq14 = Integer.parseInt(request.getParameter("resultClass1_seq14"));
				paramClass1.setSurq_seq(surq_seq14);
				//문항의시퀀스
				suri_seq14 = Integer.parseInt(request.getParameter("resultClass2_seq14"));
				paramClass2.setSurq_seq(surq_seq14);
				paramClass2.setSuri_seq(suri_seq14);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(14 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					surq_title14 = request.getParameter("surq_title14");
					paramClass1.setSurq_seq(surq_seq14);
					paramClass1.setSurq_title(surq_title14);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					//문항의시퀀스
					suri_seq14 = Integer.parseInt(request.getParameter("resultClass2_seq14"));
					
					//사용자가 입력한 값(문항)
					item114 = request.getParameter("item114");
					item214 = request.getParameter("item214");
					item314 = request.getParameter("item314");
					item414 = request.getParameter("item414");
					item514 = request.getParameter("item514");
					
					paramClass2.setSuri_title1(item114);
					paramClass2.setSuri_title2(item214);
					paramClass2.setSuri_title3(item314);
					paramClass2.setSuri_title4(item414);
					paramClass2.setSuri_title5(item514);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
			}
			
			if(15 <= que_cnt_size){
				//문제의시퀀스
				surq_seq15 = Integer.parseInt(request.getParameter("resultClass1_seq15"));
				paramClass1.setSurq_seq(surq_seq15);
				//문항의시퀀스
				suri_seq15 = Integer.parseInt(request.getParameter("resultClass2_seq15"));
				paramClass2.setSurq_seq(surq_seq15);
				paramClass2.setSuri_seq(suri_seq15);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(15 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					surq_title15 = request.getParameter("surq_title15");
					paramClass1.setSurq_title(surq_title15);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					//사용자가 입력한 값(문항)
					item115 = request.getParameter("item115");
					item215 = request.getParameter("item215");
					item315 = request.getParameter("item315");
					item415 = request.getParameter("item415");
					item515 = request.getParameter("item515");
					
					paramClass2.setSuri_title1(item115);
					paramClass2.setSuri_title2(item215);
					paramClass2.setSuri_title3(item315);
					paramClass2.setSuri_title4(item415);
					paramClass2.setSuri_title5(item515);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
			}
			
			if(16 <= que_cnt_size){
				//문제의시퀀스
				surq_seq16 = Integer.parseInt(request.getParameter("resultClass1_seq16"));
				paramClass1.setSurq_seq(surq_seq16);
				//문항의시퀀스
				suri_seq16 = Integer.parseInt(request.getParameter("resultClass2_seq16"));
				paramClass2.setSurq_seq(surq_seq16);
				paramClass2.setSuri_seq(suri_seq16);
				
				//수정시 문제를 줄였을 경우 - 레코드삭제
				if(16 > que_cnt){
					sqlMapper.delete("Research.deleteResearchWhenUpdate1", paramClass1);//설문조사(문제) 삭제
					sqlMapper.delete("Research.deleteResearchWhenUpdate2", paramClass2);//설문조사(문항) 삭제
				}else{ //아닐경우 - 레코드업데이트
					surq_title16 = request.getParameter("surq_title16");
					paramClass1.setSurq_seq(surq_seq16);
					paramClass1.setSurq_title(surq_title16);
					sqlMapper.update("Research.updateResearch1", paramClass1); //설문조사(문제) update
					
					//사용자가 입력한 값(문항)
					item116 = request.getParameter("item116");
					item216 = request.getParameter("item216");
					item316 = request.getParameter("item316");
					item416 = request.getParameter("item416");
					item516 = request.getParameter("item516");
					
					paramClass2.setSurq_seq(surq_seq16);
					paramClass2.setSuri_seq(suri_seq16);
					paramClass2.setSuri_title1(item116);
					paramClass2.setSuri_title2(item216);
					paramClass2.setSuri_title3(item316);
					paramClass2.setSuri_title4(item416);
					paramClass2.setSuri_title5(item516);
					sqlMapper.update("Research.updateResearch2", paramClass2); //설문조사(문항) update
				}
			}
		}//.시나리오1 종료
		
		
		//설문조사 문제 공통으로 들어가는 레코드.
		paramClass1.setSur_seq(sur_seq);
		paramClass1.setWriter(session_id);
		paramClass1.setReg_name(session_id);
		paramClass1.setReg_date(today.getTime());
		paramClass1.setUdt_name(session_id);
		paramClass1.setUdt_date(today.getTime());
		
		//설문조사 문항 공통으로 들어가는 레코드
		paramClass2.setSur_seq(sur_seq);
		paramClass2.setWriter(session_id);
		paramClass2.setReg_name(session_id);
		paramClass2.setReg_date(today.getTime());
		paramClass2.setUdt_name(session_id);
		paramClass2.setUdt_date(today.getTime());
		
		//시나리오2. 사용자가 문제수를 감소했거나, 유지했을때
		if(que_cnt_size < que_cnt){
			
			if(que_cnt_size < 1 && 1 <= que_cnt){
				surq_title1 = request.getParameter("surq_title1");
				paramClass1.setSurq_title(surq_title1);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);
				
				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				//사용자가 입력한 값(문항)
				item11 = request.getParameter("item11");
				item21 = request.getParameter("item21");
				item31 = request.getParameter("item31");
				item41 = request.getParameter("item41");
				item51 = request.getParameter("item51");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item11);
				paramClass2.setSuri_title2(item21);
				paramClass2.setSuri_title3(item31);
				paramClass2.setSuri_title4(item41);
				paramClass2.setSuri_title5(item51);
				paramClass2.setSuri_num1(11);
				paramClass2.setSuri_num2(21);
				paramClass2.setSuri_num3(31);
				paramClass2.setSuri_num4(41);
				paramClass2.setSuri_num5(51);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 2 && 2 <= que_cnt){
				surq_title2 = request.getParameter("surq_title2");
				paramClass1.setSurq_title(surq_title2);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				//사용자가 입력한 값(문항)
				item12 = request.getParameter("item12");
				item22 = request.getParameter("item22");
				item32 = request.getParameter("item32");
				item42 = request.getParameter("item42");
				item52 = request.getParameter("item52");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item12);
				paramClass2.setSuri_title2(item22);
				paramClass2.setSuri_title3(item32);
				paramClass2.setSuri_title4(item42);
				paramClass2.setSuri_title5(item52);
				paramClass2.setSuri_num1(12);
				paramClass2.setSuri_num2(22);
				paramClass2.setSuri_num3(32);
				paramClass2.setSuri_num4(42);
				paramClass2.setSuri_num5(52);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 3 && 3 <= que_cnt){
				surq_title3 = request.getParameter("surq_title3");
				paramClass1.setSurq_title(surq_title3);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				//사용자가 입력한 값(문항)
				item13 = request.getParameter("item13");
				item23 = request.getParameter("item23");
				item33 = request.getParameter("item33");
				item43 = request.getParameter("item43");
				item53 = request.getParameter("item53");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item13);
				paramClass2.setSuri_title2(item23);
				paramClass2.setSuri_title3(item33);
				paramClass2.setSuri_title4(item43);
				paramClass2.setSuri_title5(item53);
				paramClass2.setSuri_num1(13);
				paramClass2.setSuri_num2(23);
				paramClass2.setSuri_num3(33);
				paramClass2.setSuri_num4(43);
				paramClass2.setSuri_num5(53);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 4 && 4 <= que_cnt){
				surq_title4 = request.getParameter("surq_title4");
				paramClass1.setSurq_title(surq_title4);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				//사용자가 입력한 값(문항)
				item14 = request.getParameter("item14");
				item24 = request.getParameter("item24");
				item34 = request.getParameter("item34");
				item44 = request.getParameter("item44");
				item54 = request.getParameter("item54");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item14);
				paramClass2.setSuri_title2(item24);
				paramClass2.setSuri_title3(item34);
				paramClass2.setSuri_title4(item44);
				paramClass2.setSuri_title5(item54);
				paramClass2.setSuri_num1(14);
				paramClass2.setSuri_num2(24);
				paramClass2.setSuri_num3(34);
				paramClass2.setSuri_num4(44);
				paramClass2.setSuri_num5(54);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 5 && 5 <= que_cnt){
				surq_title5 = request.getParameter("surq_title5");
				paramClass1.setSurq_title(surq_title5);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				
				//사용자가 입력한 값(문항)
				item15 = request.getParameter("item15");
				item25 = request.getParameter("item25");
				item35 = request.getParameter("item35");
				item45 = request.getParameter("item45");
				item55 = request.getParameter("item55");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item15);
				paramClass2.setSuri_title2(item25);
				paramClass2.setSuri_title3(item35);
				paramClass2.setSuri_title4(item45);
				paramClass2.setSuri_title5(item55);
				paramClass2.setSuri_num1(15);
				paramClass2.setSuri_num2(25);
				paramClass2.setSuri_num3(35);
				paramClass2.setSuri_num4(45);
				paramClass2.setSuri_num5(55);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 6 && 6 <= que_cnt){
				surq_title6 = request.getParameter("surq_title6");
				paramClass1.setSurq_title(surq_title6);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				
				//사용자가 입력한 값(문항)
				item16 = request.getParameter("item16");
				item26 = request.getParameter("item26");
				item36 = request.getParameter("item36");
				item46 = request.getParameter("item46");
				item56 = request.getParameter("item56");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item16);
				paramClass2.setSuri_title2(item26);
				paramClass2.setSuri_title3(item36);
				paramClass2.setSuri_title4(item46);
				paramClass2.setSuri_title5(item56);
				paramClass2.setSuri_num1(16);
				paramClass2.setSuri_num2(26);
				paramClass2.setSuri_num3(36);
				paramClass2.setSuri_num4(46);
				paramClass2.setSuri_num5(56);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 7 && 7 <= que_cnt){
				surq_title7 = request.getParameter("surq_title7");
				paramClass1.setSurq_title(surq_title7);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				
				//사용자가 입력한 값(문항)
				item17 = request.getParameter("item17");
				item27 = request.getParameter("item27");
				item37 = request.getParameter("item37");
				item47 = request.getParameter("item47");
				item57 = request.getParameter("item57");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item17);
				paramClass2.setSuri_title2(item27);
				paramClass2.setSuri_title3(item37);
				paramClass2.setSuri_title4(item47);
				paramClass2.setSuri_title5(item57);
				paramClass2.setSuri_num1(17);
				paramClass2.setSuri_num2(27);
				paramClass2.setSuri_num3(37);
				paramClass2.setSuri_num4(47);
				paramClass2.setSuri_num5(57);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 8 && 8 <= que_cnt){
				surq_title8 = request.getParameter("surq_title8");
				paramClass1.setSurq_title(surq_title8);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());

				//사용자가 입력한 값(문항)
				item18 = request.getParameter("item18");
				item28 = request.getParameter("item28");
				item38 = request.getParameter("item38");
				item48 = request.getParameter("item48");
				item58 = request.getParameter("item58");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item18);
				paramClass2.setSuri_title2(item28);
				paramClass2.setSuri_title3(item38);
				paramClass2.setSuri_title4(item48);
				paramClass2.setSuri_title5(item58);
				paramClass2.setSuri_num1(18);
				paramClass2.setSuri_num2(28);
				paramClass2.setSuri_num3(38);
				paramClass2.setSuri_num4(48);
				paramClass2.setSuri_num5(58);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 9 && 9 <= que_cnt){
				surq_title9 = request.getParameter("surq_title9");
				paramClass1.setSurq_title(surq_title9);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				//사용자가 입력한 값(문항)
				item19 = request.getParameter("item19");
				item29 = request.getParameter("item29");
				item39 = request.getParameter("item39");
				item49 = request.getParameter("item49");
				item59 = request.getParameter("item59");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item19);
				paramClass2.setSuri_title2(item29);
				paramClass2.setSuri_title3(item39);
				paramClass2.setSuri_title4(item49);
				paramClass2.setSuri_title5(item59);
				paramClass2.setSuri_num1(19);
				paramClass2.setSuri_num2(29);
				paramClass2.setSuri_num3(39);
				paramClass2.setSuri_num4(49);
				paramClass2.setSuri_num5(59);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 10 && 10 <= que_cnt){
				surq_title10 = request.getParameter("surq_title10");
				paramClass1.setSurq_title(surq_title10);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				//사용자가 입력한 값(문항)
				item110 = request.getParameter("item110");
				item210 = request.getParameter("item210");
				item310 = request.getParameter("item310");
				item410 = request.getParameter("item410");
				item510 = request.getParameter("item510");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item110);
				paramClass2.setSuri_title2(item210);
				paramClass2.setSuri_title3(item310);
				paramClass2.setSuri_title4(item410);
				paramClass2.setSuri_title5(item510);
				paramClass2.setSuri_num1(110);
				paramClass2.setSuri_num2(210);
				paramClass2.setSuri_num3(310);
				paramClass2.setSuri_num4(410);
				paramClass2.setSuri_num5(510);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 11 && 11 <= que_cnt){
				surq_title11 = request.getParameter("surq_title11");
				paramClass1.setSurq_title(surq_title11);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				//사용자가 입력한 값(문항)
				item111 = request.getParameter("item111");
				item211 = request.getParameter("item211");
				item311 = request.getParameter("item311");
				item411 = request.getParameter("item411");
				item511 = request.getParameter("item511");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item111);
				paramClass2.setSuri_title2(item211);
				paramClass2.setSuri_title3(item311);
				paramClass2.setSuri_title4(item411);
				paramClass2.setSuri_title5(item511);
				paramClass2.setSuri_num1(111);
				paramClass2.setSuri_num2(211);
				paramClass2.setSuri_num3(311);
				paramClass2.setSuri_num4(411);
				paramClass2.setSuri_num5(511);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 12 && 12 <= que_cnt){
				surq_title12 = request.getParameter("surq_title12");
				paramClass1.setSurq_title(surq_title12);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				//사용자가 입력한 값(문항)
				item112 = request.getParameter("item112");
				item212 = request.getParameter("item212");
				item312 = request.getParameter("item312");
				item412 = request.getParameter("item412");
				item512 = request.getParameter("item512");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item112);
				paramClass2.setSuri_title2(item212);
				paramClass2.setSuri_title3(item312);
				paramClass2.setSuri_title4(item412);
				paramClass2.setSuri_title5(item512);
				paramClass2.setSuri_num1(112);
				paramClass2.setSuri_num2(212);
				paramClass2.setSuri_num3(312);
				paramClass2.setSuri_num4(412);
				paramClass2.setSuri_num5(512);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 13 && 13 <= que_cnt){
				surq_title13 = request.getParameter("surq_title13");
				paramClass1.setSurq_title(surq_title13);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				//사용자가 입력한 값(문항)
				item113 = request.getParameter("item113");
				item213 = request.getParameter("item213");
				item313 = request.getParameter("item313");
				item413 = request.getParameter("item413");
				item513 = request.getParameter("item513");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item113);
				paramClass2.setSuri_title2(item213);
				paramClass2.setSuri_title3(item313);
				paramClass2.setSuri_title4(item413);
				paramClass2.setSuri_title5(item513);
				paramClass2.setSuri_num1(113);
				paramClass2.setSuri_num2(213);
				paramClass2.setSuri_num3(313);
				paramClass2.setSuri_num4(413);
				paramClass2.setSuri_num5(513);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 14 && 14 <= que_cnt){
				surq_title14 = request.getParameter("surq_title14");
				paramClass1.setSurq_title(surq_title14);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				//사용자가 입력한 값(문항)
				item114 = request.getParameter("item114");
				item214 = request.getParameter("item214");
				item314 = request.getParameter("item314");
				item414 = request.getParameter("item414");
				item514 = request.getParameter("item514");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item114);
				paramClass2.setSuri_title2(item214);
				paramClass2.setSuri_title3(item314);
				paramClass2.setSuri_title4(item414);
				paramClass2.setSuri_title5(item514);
				paramClass2.setSuri_num1(114);
				paramClass2.setSuri_num2(214);
				paramClass2.setSuri_num3(314);
				paramClass2.setSuri_num4(414);
				paramClass2.setSuri_num5(514);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 15 && 15 <= que_cnt){
				surq_title15 = request.getParameter("surq_title15");
				paramClass1.setSurq_title(surq_title15);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				//사용자가 입력한 값(문항)
				item115 = request.getParameter("item115");
				item215 = request.getParameter("item215");
				item315 = request.getParameter("item315");
				item415 = request.getParameter("item415");
				item515 = request.getParameter("item515");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item115);
				paramClass2.setSuri_title2(item215);
				paramClass2.setSuri_title3(item315);
				paramClass2.setSuri_title4(item415);
				paramClass2.setSuri_title5(item515);
				paramClass2.setSuri_num1(115);
				paramClass2.setSuri_num2(215);
				paramClass2.setSuri_num3(315);
				paramClass2.setSuri_num4(415);
				paramClass2.setSuri_num5(515);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
			if(que_cnt_size < 16 && 16 <= que_cnt){
				surq_title16 = request.getParameter("surq_title16");
				paramClass1.setSurq_title(surq_title16);
				//설문조사 문제 insert
				sqlMapper.insert("Research.insertResearch1", paramClass1);

				//설문조사 문제 최대시퀀스
				resultClass11 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
				int surq_seq = (int)(resultClass11.getSurq_seq());
				
				//사용자가 입력한 값(문항)
				item116 = request.getParameter("item116");
				item216 = request.getParameter("item216");
				item316 = request.getParameter("item316");
				item416 = request.getParameter("item416");
				item516 = request.getParameter("item516");
				
				paramClass2.setSurq_seq(surq_seq);
				paramClass2.setSuri_title1(item116);
				paramClass2.setSuri_title2(item216);
				paramClass2.setSuri_title3(item316);
				paramClass2.setSuri_title4(item416);
				paramClass2.setSuri_title5(item516);
				paramClass2.setSuri_num1(116);
				paramClass2.setSuri_num2(216);
				paramClass2.setSuri_num3(316);
				paramClass2.setSuri_num4(416);
				paramClass2.setSuri_num5(516);
				sqlMapper.insert("Research.insertResearch2", paramClass2);
			}
		}//.시나리오2 종료
		
		return uri; //수정요청했던 뷰페이지로 리다이렉트
	}
	
} //end of class
