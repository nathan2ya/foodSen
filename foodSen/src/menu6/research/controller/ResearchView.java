package menu6.research.controller;

import java.awt.Color;
import java.awt.Font;
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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import common.Constants;

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
	private ResearchDTO3 resultClassForExcel = new ResearchDTO3();
	private List<ResearchDTO3> resultClass3 = new ArrayList<ResearchDTO3>();
	private List<ResearchDTO3> resultClass33 = new ArrayList<ResearchDTO3>();
	//설문조사(엑셀파일경로)
	private String file_path = Constants.COMMON_FILE_PATH + Constants.MENU6_RESEARCH_FILE_PATH;
	
	//설문조사 문제 모음
	private int[] resultClass1_seq = new int[9000];//문제의시퀀스 모음
	private String[] title = new String[1000];
	
	//설문조사 문항 모음
	private int[] resultClass2_seq = new int[9000];//문항의시퀀스 모음
	private String[] i_title1 = new String[16];
	private String[] i_title2 = new String[16];
	private String[] i_title3 = new String[16];
	private String[] i_title4 = new String[16];
	private String[] i_title5 = new String[16];
	
	//엑셀용
	private String[]totalTitle = new String[9000];//모든문제 모음(엑셀용)
	private String[]totalItem = new String[9000];//모든문항 모음(엑셀용)
	private String[]totalCount = new String[9000];//각문항 선택횟수 모음(엑셀용)
	private String[]totalDescription = new String[9000];//각문항 선택사유 모음(엑셀용)
	
	//설문조사 결과 모음
	private int[] resultClass3_seq = new int[9000];//결과의시퀀스 모음
	
	//설문조사 사유 보여주기 배열
	private String[] item = new String[1000];
	private String[] description = new String[1000];
	
	//설문조사 결과 배열
	private int[][] res_cnt_arr = new int[16][5];
	
	//설문조사 번호만 잘라낸 결과
	private String chosen;
	
	//설문조사 엑셀write 변수
	private String[] sur_title = new String[1000];//문제모음
	private String[] sur_item = new String[1000];//문항모음
	private String[] sur_description = new String[1000];//사유모음
	
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
		
		//현재로그인된 세션이 결과등록한 이력이있는지 판단
		String session_id = (String) session.getAttribute("session_id");
		
		paramClass3.setSur_seq(sur_seq);
		paramClass3.setWriter(session_id);
		Integer count = (Integer) sqlMapper.queryForObject("Research.selectCountForPermit", paramClass3);
		
		int canSave = 0; // 설문조사 참여가능
		if(count > 0){
			canSave = 1; // 설문조사 참여못함
		}
		//.참여이력 판단 종료
		
		//현재날짜
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String current_date = sdf.format(today.getTime());
		//.현재날짜
		
		request.setAttribute("sur_seq", sur_seq);
		request.setAttribute("res_cnt", res_cnt);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("current_date", current_date);//오늘날짜
		request.setAttribute("canSave", canSave); //설문조사 참여가능0, 설문조사참여불가능1
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
		
		//현재날짜
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String current_date = sdf.format(today.getTime());
		//.현재날짜
		
		request.setAttribute("current_date", current_date); //현재날짜
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
		//return "/view/menu6/research/researchChart.jsp"; //차트보여주기
		return "/view/menu6/research/researchPopup.jsp"; //원본(일반그래프보여주기)
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
			//title[i] = resultClass1.get(i).getSurq_title(); //문제모음
		}
		
		//설문조사(결과) 레코드get
		resultClass3 = sqlMapper.queryForList("Research.selectResearchOne3", sur_seq);
		
		for(int i = 0; i<resultClass3.size(); i++){
			title[i] = resultClass3.get(i).getSurq_title();
			item[i] = resultClass3.get(i).getSuri_num();
			description[i] = resultClass3.get(i).getDescription();
		}
		
		int selectedCnt = 0;
		for(int j = 0; j<resultClass3.size(); j++){
			if(!(resultClass3.get(j).getDescription().equals(" "))){
				selectedCnt++;
			}
		}
		
		//현재날짜
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일hh:mm:ss");
		String current_date = sdf.format(today.getTime());
		//.현재날짜
		
		request.setAttribute("current_date", current_date); //현재날짜
		request.setAttribute("selectedCnt", selectedCnt); //설문조사 선택사유를 기입한 참여자수
		request.setAttribute("resultClass3Cnt", resultClass3.size());
		request.setAttribute("sur_seq", sur_seq);
		request.setAttribute("item", item);//문항배열
		request.setAttribute("description", description);//사유배열
		request.setAttribute("resultClass", resultClass);//설문조사(정보)레코드
		request.setAttribute("cnt", cnt); //설문조사 문항개수
		request.setAttribute("resultClass1", resultClass1);//설문조사(문제)레코드
		request.setAttribute("title", title); //설문조사(문제배열)
		request.setAttribute("resultClass3", resultClass3);//설문조사(결과)레코드
		return "/view/menu6/research/researchChoiceReasonPopup.jsp";
	}
	
	//엑셀 파일생성
	@RequestMapping("/writeExcel.do") 
	public void writeExcel(HttpServletRequest request, HttpServletResponse response, HttpSession session)  throws Exception {
		
		//현재날짜
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일 hh:mm:ss");
		String current_date = sdf.format(today.getTime());
		//.현재날짜
		
		
		
		
		//1. 엑셀에 기입될 데이터
		int sur_seq = Integer.parseInt(request.getParameter("sur_seq")); //요청한뷰의 시퀀스넘버
		
		//설문조사(정보) 레코드get
		resultClass = (ResearchDTO)sqlMapper.queryForObject("Research.selectResearchOne", sur_seq);
		int cnt = Integer.parseInt(resultClass.getQue_cnt());//문항의개수
		String mainTitle = resultClass.getSur_title();//제목
		Date tempDate = resultClass.getReg_date();
		String reg_date = sdf.format(tempDate);//작성일
		String sat_date = resultClass.getSur_sat_date();//시작일
		String end_date = resultClass.getSur_end_date();//종료일
		String writer = resultClass.getWriter(); //작성자
		
		
		//설문조사(문제) 레코드get
		resultClass1 = sqlMapper.queryForList("Research.selectResearchOne1", sur_seq);
		for(int i=0; i<resultClass1.size(); i++){
			resultClass1_seq[i] = resultClass1.get(i).getSurq_seq();//문제의시퀀스 모음
			title[i] = resultClass1.get(i).getSurq_title();//문제모음
		}
		int surq_seq = resultClass1.get(0).getSurq_seq();
		
		//설문조사(문항) 레코드get
		resultClass2 = sqlMapper.queryForList("Research.selectResearchOne2", sur_seq);
		for(int j=0; j<resultClass2.size(); j++){
			resultClass2_seq[j] = resultClass2.get(j).getSuri_seq();//문항의시퀀스 모음
			i_title1[j] = resultClass2.get(j).getSuri_title1();//문항1모음
			i_title2[j] = resultClass2.get(j).getSuri_title2();//문항2모음
			i_title3[j] = resultClass2.get(j).getSuri_title3();//문항3모음
			i_title4[j] = resultClass2.get(j).getSuri_title4();//문항4모음
			i_title5[j] = resultClass2.get(j).getSuri_title5();//문항5모음
		}
		
		//결과 데이터
		resultClass3 = sqlMapper.queryForList("Research.selectResearchOne3", sur_seq);
		for(int k=0; k<resultClass3.size(); k++){
			resultClass3_seq[k] = resultClass3.get(k).getSurr_seq(); //결과의시퀀스 모음
			
		}
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		
		//설문조사(문제) 정렬해서 모두다집어넣기
		int temp = resultClass1.size();
		int temp1 = temp * 5;
		
		int a = 0; int b = 0;
		
		for(a=0; a<temp1; a++){
			totalTitle[a] = title[b];
			a++;
			totalTitle[a] = "";
			a++;
			totalTitle[a] = "";
			a++;
			totalTitle[a] = "";
			a++;
			totalTitle[a] = "";
			b++;
		}
		
		//설문조사(문항) 정렬해서 모두다집어넣기
		temp = resultClass2.size();
		temp1 = temp * 5;
		
		a = 0; b = 0;

		for(a=0; a<temp1; a++){
			totalItem[a] = i_title1[b];
			a++;
			totalItem[a] = i_title2[b];
			a++;
			totalItem[a] = i_title3[b];
			a++;
			totalItem[a] = i_title4[b];
			a++;
			totalItem[a] = i_title5[b];
			b++;
		}
		
		//설문조사(선택회수) 정렬해서 모두다집어넣기
		
		a = 0; b = 0;
		int item1Cnt = 0; int item2Cnt = 0; int item3Cnt = 0; int item4Cnt = 0; int item5Cnt = 0;
		String item1Description = ""; String item2Description = ""; String item3Description = ""; String item4Description = ""; String item5Description = ""; 
		
		for(a=0; a<cnt*5; a++){
			
			paramClass3.setSur_seq(sur_seq);
			paramClass3.setSurq_seq(resultClass1_seq[b]);
			paramClass3.setSuri_seq(resultClass2_seq[b]);
			resultClass33 = sqlMapper.queryForList("Research.selectResearchOne33", paramClass3);//문제and문항 레코드get
			
			item1Cnt = 0; item2Cnt = 0; item3Cnt = 0; item4Cnt = 0; item5Cnt = 0;
			item1Description = ""; item2Description = ""; item3Description = ""; item4Description = ""; item5Description = ""; 
			
			for(int i=0; i<resultClass33.size(); i++){
				if(resultClass33.get(i).getSuri_num().substring(0, 1).equals("①")){
					item1Cnt++;
					item1Description += resultClass33.get(i).getDescription();
					item1Description += "   ";
				}
				if(resultClass33.get(i).getSuri_num().substring(0, 1).equals("②")){
					item2Cnt++;
					item2Description += resultClass33.get(i).getDescription();
					item2Description += "   ";
				}
				if(resultClass33.get(i).getSuri_num().substring(0, 1).equals("③")){
					item3Cnt++;
					item3Description += resultClass33.get(i).getDescription();
					item3Description += "   ";
				}
				if(resultClass33.get(i).getSuri_num().substring(0, 1).equals("④")){
					item4Cnt++;
					item4Description += resultClass33.get(i).getDescription();
					item4Description += "   ";
				}
				if(resultClass33.get(i).getSuri_num().substring(0, 1).equals("⑤")){
					item5Cnt++;
					item5Description += resultClass33.get(i).getDescription();
					item5Description += "   ";
				}
			}
			
			String totalCountTemp1 = Integer.toString(item1Cnt);
			if(totalCountTemp1.equals("0")){
				totalCountTemp1 = "";
			}
			String totalCountTemp2 = Integer.toString(item2Cnt);
			if(totalCountTemp2.equals("0")){
				totalCountTemp2 = "";
			}
			String totalCountTemp3 = Integer.toString(item3Cnt);
			if(totalCountTemp3.equals("0")){
				totalCountTemp3 = "";
			}
			String totalCountTemp4 = Integer.toString(item4Cnt);
			if(totalCountTemp4.equals("0")){
				totalCountTemp4 = "";
			}
			String totalCountTemp5 = Integer.toString(item5Cnt);
			if(totalCountTemp5.equals("0")){
				totalCountTemp5 = "";
			}
			
			//위에서 만든 데이터 순서데로 배열에 저장
			totalCount[a] = totalCountTemp1;
			totalDescription[a] = item1Description;
			a++;
			totalCount[a] = totalCountTemp2; 
			totalDescription[a] = item2Description;
			a++;
			totalCount[a] = totalCountTemp3; 
			totalDescription[a] = item3Description;
			a++;
			totalCount[a] = totalCountTemp4; 
			totalDescription[a] = item4Description;
			a++;
			totalCount[a] = totalCountTemp5;
			totalDescription[a] = item5Description;
			
			b++;
		}
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//

		
		//2. map에 데이터저장
																	 /*
		-------------------[ 엑셀파일생성 ]-------------------
		                   
		// 설문조사 문제 // 설문조사 선택문항 //  설문조사 선택사유  //
		//  sur_title[i] //    sur_item[j]    //  sur_description[k] //
		
		-------------------------------------------------------
		*/
		
		File file = new File(file_path+"researchResult.xls");

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		//map에 저장
		for(int i=0; i<temp1; i++){
			map = new HashMap<String, Object>();
			map.put("sur_title", totalTitle[i]);//1. 문제
			map.put("sur_item", totalItem[i]);//2. 문항
			map.put("sur_count", totalCount[i]);//3. 선택횟수
			map.put("sur_description", totalDescription[i]); //4. 선택사유모음
			data.add(map);
		}
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		
		//3. 엑셀 write
		
		//WorkBook 생성
		WritableWorkbook wb = Workbook.createWorkbook(file);
		
		//WorkSheet 생성
		WritableSheet sh = wb.createSheet("설문조사결과("+sur_seq+"번 게시글)", 0);
		
		//열넓이 설정 (열 위치, 넓이)
		sh.setColumnView(0, 60);
		sh.setColumnView(1, 70);
		sh.setColumnView(2, 20);
		sh.setColumnView(3, 70);
		
		//셀 합칠 부분 정의
		//sh.mergeCells(1, 0, 2, 0);
		sh.mergeCells(1, 1, 2, 1);
		
		//폰트
		WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false);
		WritableFont font1 = new WritableFont(WritableFont.ARIAL, 15, WritableFont.BOLD, false);
		WritableFont font11 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false);
		WritableFont font111 = new WritableFont(WritableFont.ARIAL, 13, WritableFont.NO_BOLD, false);
		WritableFont font2 = new WritableFont(WritableFont.ARIAL, 28, WritableFont.BOLD, false); 
		
		// 셀형식
		WritableCellFormat textFormat = new WritableCellFormat(font);
		WritableCellFormat textFormat1 = new WritableCellFormat(font1);
		WritableCellFormat textFormat11 = new WritableCellFormat(font11);
		WritableCellFormat textFormat111 = new WritableCellFormat(font111);
		WritableCellFormat textFormat2 = new WritableCellFormat(font2);
		WritableCellFormat textFormat3 = new WritableCellFormat(font);
		
		//정렬
		textFormat.setAlignment(Alignment.CENTRE);
		textFormat1.setAlignment(Alignment.CENTRE);
		textFormat11.setAlignment(Alignment.CENTRE);
		textFormat111.setAlignment(Alignment.CENTRE);
		textFormat2.setAlignment(Alignment.CENTRE);
		textFormat3.setAlignment(Alignment.LEFT);
		
		//테두리
		textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		textFormat1.setBorder(Border.ALL, BorderLineStyle.THIN);
		textFormat11.setBorder(Border.ALL, BorderLineStyle.THIN);
		textFormat111.setBorder(Border.ALL, BorderLineStyle.THIN);
		textFormat2.setBorder(Border.ALL, BorderLineStyle.NONE);
		textFormat3.setBorder(Border.ALL, BorderLineStyle.NONE);
		
		//색상
		textFormat1.setBackground(Colour.AQUA);
		textFormat11.setBackground(Colour.GRAY_25);
		
		//시작row
		int row = 0;
		
		row++;
		
		//제목헤더
		Label label = new jxl.write.Label(0, row, "", textFormat2);
		sh.addCell(label);
		label = new jxl.write.Label(1, row, "설문결과 보고서", textFormat2);
		sh.addCell(label);
		label = new jxl.write.Label(2, row, "", textFormat2);
		sh.addCell(label);
		label = new jxl.write.Label(3, row, "", textFormat3);
		sh.addCell(label);
		row+=2;
		
		
		//문서정보
		label = new jxl.write.Label(0, row, "문서번호", textFormat11);
		sh.addCell(label);
		label = new jxl.write.Label(1, row, sur_seq+"-"+surq_seq, textFormat111);
		sh.addCell(label);
		label = new jxl.write.Label(2, row, "작성일자", textFormat11);
		sh.addCell(label);
		label = new jxl.write.Label(3, row, reg_date, textFormat111);
		sh.addCell(label);
		row++;
		
		label = new jxl.write.Label(0, row, "기간", textFormat11);
		sh.addCell(label);
		label = new jxl.write.Label(1, row, sat_date+" ~ "+end_date, textFormat111);
		sh.addCell(label);
		label = new jxl.write.Label(2, row, "작성자", textFormat11);
		sh.addCell(label);
		label = new jxl.write.Label(3, row, writer, textFormat111);
		sh.addCell(label);
		row++;
		
		label = new jxl.write.Label(0, row, "제목", textFormat11);
		sh.addCell(label);
		label = new jxl.write.Label(1, row, mainTitle, textFormat111);
		sh.addCell(label);
		label = new jxl.write.Label(2, row, "집계일자", textFormat11);
		sh.addCell(label);
		label = new jxl.write.Label(3, row, current_date, textFormat111);
		sh.addCell(label);
		row+=4;
		
		
		//헤더
		label = new jxl.write.Label(0, row, "문제", textFormat1);
		sh.addCell(label);
		label = new jxl.write.Label(1, row, "문항", textFormat1);
		sh.addCell(label);
		label = new jxl.write.Label(2, row, "선택횟수", textFormat1);
		sh.addCell(label);
		label = new jxl.write.Label(3, row, "선택사유", textFormat1);
		sh.addCell(label);
		row++;
		
		//데이터
		for (Map<String, Object> tem : data) {
			//문제
			label = new jxl.write.Label(0, row, (String) tem.get("sur_title"), textFormat);
			sh.addCell(label);
			//문항
			label = new jxl.write.Label(1, row, (String) tem.get("sur_item"),textFormat);
			sh.addCell(label);
			
			//선택횟수
			label = new jxl.write.Label(2, row, (String) tem.get("sur_count"),textFormat);
			sh.addCell(label);
			//선택사유
			label = new jxl.write.Label(3, row, (String) tem.get("sur_description"),textFormat);
			sh.addCell(label);
			row++;
		}
		
		wb.write(); //WorkSheet 쓰기
		wb.close(); //WorkSheet 닫기
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		
		//4. 엑셀다운로드 제공
		String uploadPath = file_path; //엑셀파일이 저장되어있는 경로
		String requestedFile = "researchResult.xls"; //위에서 만든 엑셀파일명.확장자
		//String attach_path = request.getParameter("attach_path"); //파일과 모든 경로를 포함한 변수(향후 쓰일 수 있음)

		File uFile = new File(uploadPath, requestedFile); //경로,파일명으로 파일객체 생성
		int fSize = (int) uFile.length();
		boolean ctrl = uFile.exists(); //파일존재유무
		
		if (ctrl){
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(uFile)); //파일을 읽어오되 // 버퍼에
			String mimetype = "text/html";
			response.setBufferSize(fSize); //버퍼크기설정
			response.setContentType(mimetype); //컨텐츠형식설정
			response.setHeader("Content-Disposition", "attachment; filename=\"" + requestedFile + "\"");
			response.setContentLength(fSize); //컨텐츠 본체의 길이

			FileCopyUtils.copy(in, response.getOutputStream());
			in.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}else{ //에러페이지 화면 구성
			//setContentType을 프로젝트 환경에 맞추어 변경
			response.setContentType("application/x-msdownload");
			PrintWriter printwriter = response.getWriter();
			printwriter.println("<html>");
			printwriter.println("<br><br><br><h2>Could not get file name:<br>" + requestedFile + "</h2>");
			printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
			printwriter.println("<br><br><br>&copy; webAccess");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
		}
	}
	
} //end of class
