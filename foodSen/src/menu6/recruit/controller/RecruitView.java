package menu6.recruit.controller;


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

import menu6.recruit.dto.RecruitDTO;

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
public class RecruitView {

	//뷰페이지 레코드
	private RecruitDTO paramClass = new RecruitDTO();
	private RecruitDTO resultClass = new RecruitDTO();
	
	
	private int currentPage;
	private int searchingNow; // 전체글, 검색글을 판단하여 각종 논리성을 판가르는 논리값
	
	
	//검색중인 경우에만 발생하는 변수(it's for uri)
	private String searchType;
	private String subType;
	private String subValue;
	
	//파일다운로드 관련
	private String file_path = Constants.COMMON_FILE_PATH + Constants.MENU6_RECRUIT_FILE_PATH;
	
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public RecruitView() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	//학교급식인력풀(구인) 뷰페이지
	@RequestMapping("/recruitView.do")
	public String inspectionResultView(HttpServletRequest request1, HttpSession session) throws SQLException{
		
		
		//뷰에서 보낸 값 초기화
			int seq = Integer.parseInt(request1.getParameter("seq"));
			if(null == request1.getParameter("currentPage")){
				currentPage = 1;
			}else{
				currentPage = Integer.parseInt(request1.getParameter("currentPage"));
			}
			searchingNow = Integer.parseInt(request1.getParameter("searchingNow"));
			
			
			//검색중 프로퍼티 초기화
			if(searchingNow==1){
				searchType = request1.getParameter("searchType");
				
				if(searchType.equals("job")){
					subType = "job";
					subValue = request1.getParameter("job");
					request1.setAttribute("job", subValue);
				}else if(searchType.equals("gubun")){
					subType = "gubun";
					subValue = request1.getParameter("gubun");
					request1.setAttribute("gubun", subValue);
				}else if(searchType.equals("loc_seq")){
					subType = "loc_seq";
					subValue = request1.getParameter("loc_seq");
					request1.setAttribute("loc_seq", subValue);
				}else{
					subType = "school_type";
					subValue = request1.getParameter("school_type");
					request1.setAttribute("school_type", subValue);
				}
				
				//뷰로 아래의 변수 3개를 보내는 이유는
				//목록을 클릭했을 때 다시 검색중인 리스트로 돌아가기 위함이다. +위의 currentPage와 함께!
				request1.setAttribute("searchType", searchType);
				request1.setAttribute("searchingNow", 1);
			}else{
				
				//검색중이 아닌경우에는 아래의 변수값이 존재하지 않음을 의미하는 0을 초기화 시켜서 해당 jsp 로 보낸다.
				request1.setAttribute("searchType", 0);
				request1.setAttribute("searchingNow", 0);
			}
			//.검색중 프로퍼티 초기화 종료
			
		//.뷰에서 보낸 값 초기화 종료
		
		
		
		
		//조회수를 위한 get
		resultClass = (RecruitDTO)sqlMapper.queryForObject("Recruit.selectRecruitOne", seq);
		
		//뷰페이지의 조회수를 +1 업데이트
		paramClass.setSeq(seq);
		paramClass.setHits(resultClass.getHits()+1);
		sqlMapper.update("Recruit.updateHits", paramClass);
		//뷰페이지의 조회수를 +1 업데이트 종료
		
		
		//뷰페이지에 보여질 레코드 1개를 get
		resultClass = (RecruitDTO)sqlMapper.queryForObject("Recruit.selectRecruitOne", seq);
		
		
		request1.setAttribute("seq", seq);
		request1.setAttribute("currentPage", currentPage);
		request1.setAttribute("searchingNow", searchingNow);
		request1.setAttribute("resultClass", resultClass); //레코드1개
		
		
		return "/view/menu6/recruit_application/recruitView.jsp";
	}
	
	
	
	//학교급식인력풀(구인) 첨부파일 다운로드
	@RequestMapping(value = "/recruit_FileDownload.do")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String uploadPath = file_path;
		String requestedFile = request.getParameter("attach_name");
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
			printwriter
			.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
			printwriter.println("<br><br><br>&copy; webAccess");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
		}
	}
	
	
}
