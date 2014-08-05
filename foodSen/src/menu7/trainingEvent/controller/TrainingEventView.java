package menu7.trainingEvent.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import menu7.trainingEvent.dto.TrainingEventDTO;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import common.Constants;

/*
 * 작성자: 류재욱
 * 설  명: 연수행사 view 클래스
 * 용  도: 연수행사 게시글 뷰를 위함.
*/

@Controller
public class TrainingEventView {
	
	//뷰페이지 레코드
	private TrainingEventDTO paramClass = new TrainingEventDTO();
	private TrainingEventDTO resultClass = new TrainingEventDTO();
	private int currentPage;
	private int searchingNow; // 전체글, 검색글을 판단하여 각종 논리성을 판가르는 논리값
	private String searchType;
	private String userinput;
	private int canWrite; // 결과등록을 할 수 있는지 없는지 판단하는 논리값
	
	//파일다운로드 관련
	private String file_path = Constants.COMMON_FILE_PATH + Constants.MENU7_TRAININGEVENT_FILE_PATH;
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public TrainingEventView() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	
	//연수행사(원글) 뷰페이지
	@RequestMapping("/trainingEventView.do")
	public String trainingEventView(HttpServletRequest request1, HttpSession session) throws SQLException{
		
		int seq = Integer.parseInt(request1.getParameter("seq"));
		
		if(null == request1.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request1.getParameter("currentPage"));
		}
		
		
		/*
		searchingNow
		달력 리스트에서 view로 진입할 경우 0
		전체글 리스트에서 view로 진입할 경우 0
		검색중인 리스트에서 view로 진입 할 경우 1
		
		이 변수는 view 에서 목록을 클릭했을 때 
		다시 이전 리스트를 기억하기 위해 정의된 변수이다.
		*/
		
		//이 값이 null로 들어오는 경우는 달력리스트에서 진입했을 때만 해당. (현재 달력리스트에서는 seq만 보냄)
		if(null == request1.getParameter("searchingNow")){ 
			searchingNow = 0;
		}else{
			searchingNow = Integer.parseInt(request1.getParameter("searchingNow"));
		}
		
		
		//검색일 경우 존재하는 변수 초기화
		if(searchingNow==1){
			searchType = request1.getParameter("searchType");
			userinput = request1.getParameter("userinput");
			
			//뷰로 아래의 변수 3개를 보내는 이유는
			//목록을 클릭했을 때 다시 검색중인 리스트로 돌아가기 위함이다. +위의 currentPage와 함께!
			request1.setAttribute("searchType", searchType);
			request1.setAttribute("userinput", userinput);
			request1.setAttribute("searchingNow", 1);
		}else{
			
			//검색중이 아닌경우에는 아래의 변수값이 존재하지 않음을 의미하는 0을 초기화 시켜서 해당 jsp 로 보낸다.
			request1.setAttribute("searchType", 0);
			request1.setAttribute("userinput", 0);
			request1.setAttribute("searchingNow", 0);
		}
		
		
		//조회수를 위한 get
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		//뷰페이지의 조회수를 +1 업데이트
		paramClass.setSeq(seq);
		paramClass.setHits(resultClass.getHits()+1);
		sqlMapper.update("TrainingEvent.updateHits", paramClass);
		
		
		//뷰페이지에 보여질 레코드 1개를 get
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		
		
		/*
		 * 행사도중에는 삭제불가능 하도록 하는 판단함수
		 * 1. 결과등록(답글)은 행사종료일이거나 그 이상되어야만 가능함을 판단하는 변수
		 * 2. 글의 삭제를 판단하는 변수
		 * 3. 0은 등록불가 및 삭제불가, 1은 등록가능 및 삭제가능
		 * str_date - 행사시작날짜
		 * end_date - 행사종료날짜
		 * current_date - 현재날짜
		*/
		
		//행사 시작 날짜(str_date)
		int str_date = Integer.parseInt(resultClass.getStr_date().replace("-", ""));
		//행사 종료 날짜(end_date)
		int end_date = Integer.parseInt(resultClass.getEnd_date().replace("-", ""));
		//현재 날짜(current_date)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int current_date = Integer.parseInt(sdf.format(today.getTime()));
		
				// 행사가 시작했고,		종료되기 전이면
		if(current_date >= str_date && current_date <= end_date){
			canWrite = 0; // 0은 등록불가 or 삭제불가
		}else{
			canWrite = 1; // 1은 등록가능 or 삭제가능
		}
		
		
		
		//현재날짜를 String으로 받음
		//행사가 종료될경우 글을 수정할 수 없게 하기 위하여, 현재시간을 String Type으로 jsp 로 보내어, end_date 값과 판단한다.
		Calendar today1 = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String current_date1 = sdf1.format(today1.getTime());
		
		
		
		request1.setAttribute("current_date", current_date);
		request1.setAttribute("current_date1", current_date1); //수정논리(기능추가)
		request1.setAttribute("seq", seq); //뷰의 시퀀스넘버
		request1.setAttribute("currentPage", currentPage); //현재페이지
		request1.setAttribute("searchingNow", searchingNow); //전체글 or 검색글 논리변수
		request1.setAttribute("canWrite", canWrite); //결과등록(답글) 입력 권한 논리변수
		request1.setAttribute("resultClass", resultClass); //뷰에 출력될 레코드1개 참조변수
		return "/view/menu7/trainingEvent/trainingEventView.jsp";
	}
	
	
	
	//연수행사(답글) 뷰페이지
	@RequestMapping("/trainingEventRepView.do")
	public String trainingEventRepView(HttpServletRequest request1, HttpSession session) throws SQLException{
		
		
		//-------------------------------------------------------------------------------------------------------------------//
		/*seq, currentPage, searchingNow (+ searchType, userinput) 의 변수값을 초기화 한다.*/
		
		
		int seq = Integer.parseInt(request1.getParameter("seq"));
		if(null == request1.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request1.getParameter("currentPage"));
		}
		searchingNow = Integer.parseInt(request1.getParameter("searchingNow"));
		
		//검색일 경우 존재하는 변수 초기화
		if(searchingNow==1){
			searchType = request1.getParameter("searchType");
			userinput = request1.getParameter("userinput");
			
			//뷰로 아래의 변수 3개를 보내는 이유는
			//목록을 클릭했을 때 다시 검색중인 리스트로 돌아가기 위함이다. +위의 currentPage와 함께!
			request1.setAttribute("searchType", searchType);
			request1.setAttribute("userinput", userinput);
			request1.setAttribute("searchingNow", 1);
		}else{
			
			//검색중이 아닌경우에는 아래의 변수값이 존재하지 않음을 의미하는 0을 초기화 시켜서 해당 jsp 로 보낸다.
			request1.setAttribute("searchType", 0);
			request1.setAttribute("userinput", 0);
			request1.setAttribute("searchingNow", 0);
		}
		//.-------------------------------------------------------------------------------------------------------------------//
		
		
		
		
		//-------------------------------------------------------------------------------------------------------------------//
		/*조회수 +1 하기*/
		
		//조회수 +1 시킬 레코드를 불러온다.
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		//뷰페이지의 조회수를 +1 업데이트
		paramClass.setSeq(seq);
		paramClass.setHits(resultClass.getHits()+1);
		sqlMapper.update("TrainingEvent.updateHits", paramClass);
		//.-------------------------------------------------------------------------------------------------------------------//
		
		
		
		
		
		//뷰페이지에 보여질 레코드 1개를 get
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		request1.setAttribute("seq", seq);
		request1.setAttribute("currentPage", currentPage);
		request1.setAttribute("searchingNow", searchingNow);
		request1.setAttribute("resultClass", resultClass); //레코드1개
		
		return "/view/menu7/trainingEvent/trainingEventRepView.jsp";
	}
	
	
	//연수행사 첨부파일 다운로드 (새글+답글 모두 이 uri를 이용함)
	@RequestMapping(value = "/trainingEvent_FileDownload.do")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String uploadPath = file_path;
		String requestedFile = request.getParameter("attach_name");
		//String attach_path = request.getParameter("attach_path"); //파일과 모든 경로를 포함한 변수(향후 쓰일 수 있어 주석처리함)

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
			printwriter.println("<br><br><br><h2>다음 파일을 찾을 수 없습니다. </h2><br> 파일명 : " + requestedFile);
			printwriter
			.println("<br><br><br><center><h3>이전페이지로 돌아가시려면 <a href='javascript: history.go(-1)'>여기</a>를 클릭하세요.</h3></center>");
			printwriter.println("<br><br><br>&copy; webAccess");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
		}
	}
	
	
}
