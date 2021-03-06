package menu6.application.controller;


import java.io.File;
import java.io.IOException;
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

import menu6.application.dto.ApplicationDTO;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import common.PagingAction;

/*
 * 작성자: 류재욱
 * 설  명: 학교급식인력풀(구직) list 클래스
 * 용  도: 학교급식인력풀(구직) 게시글 리스트 출력을 위함.
*/

@Controller
public class ApplicationList {
	
	//리스트
	private List<ApplicationDTO> list = new ArrayList<ApplicationDTO>();
	
	//검색중
	private String tempInput;
	private int searchingNow; // 전체글, 검색글을 판단하여 각종 논리성을 판가르는 논리값
	private String subType; //검색종류
	private String subValue; //검색어
	
	//페이지
	private int totalCount;// 총 게시물의 수
	private int blockCount;// 한 페이지의 게시물의 수
	private int blockPage;// 한 화면에 보여줄 페이지 수
	private String pagingHtml;// 페이징을 구현한 HTML
	private PagingAction page;// 페이징 클래스
	private String serviceName;// 호출페이지
	private int currentPage; // 현재페이지
	private int lastPage; //마지막 페이지
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public ApplicationList() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	
	//학교급식인력풀(구직) (전체글) 리스트
	@RequestMapping("/applicationList.do")
	public String applicationList(HttpServletRequest request) throws SQLException{
		
		// 전체글, 검색글 판단값.
		searchingNow = 0; //0 == 전체글//1 == 검색글//
		
		list = sqlMapper.queryForList("Application.selectAll"); //전체글
		int numberCount = list.size(); // 전체 레코드 개수
		
		/*
		 * PagingAction 클래스를 이용하여 페이지정의
		 * 현재게시판의 페이지단위나 레코드개수를 정의하여 파라미터로 호출
		*/
		//PagingAction 파라미터 정의
		blockCount = 10;// 한 페이지의 게시물의 수
		blockPage = 5;// 한 화면에 보여줄 페이지 수
		serviceName = "recruitList";// 호출 URI 정의
		totalCount = list.size(); // 전체 글 갯수
		//currentPage 초기화
		if(null == request.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		page = new PagingAction(serviceName, currentPage, totalCount, blockCount, blockPage); // pagingAction 객체 생성.
		pagingHtml = page.getPagingHtml().toString(); // 페이지 HTML 생성.

		int lastCount = totalCount; //전체글의 갯수
		
		// 현재 페이지의 마지막 글의 번호가 전체의 마지막 글 번호보다 작으면 lastCount를 +1 번호로 설정.
		if (page.getEndCount() < totalCount)
			lastCount = page.getEndCount() + 1;
		
		
		// 전체 리스트에서 현재 페이지만큼의 리스트만 가져온다.
		list = list.subList(page.getStartCount(), lastCount);
		
		//마지막페이지 산출
		lastPage = (int) Math.ceil((double) totalCount / blockCount);
		//리스트size() / 한페이지에 보여줄 게시글 수 = 페이지수(반올림)
		if (lastPage == 0) {
			lastPage = 1;
		}
		// 현재 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (currentPage > lastPage) {
			currentPage = lastPage;
		}
		
		//.페이지 종료
		
		//제목 15글자 단위로 개행
		String first;
		//String second;
		String resultSubject;
		
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getTitle().length() > 18){ //제목이 18글자 이상이면
				//0~19 잘라내기//18로 수정함
				first = list.get(i).getTitle().substring(0, 18);
				
				resultSubject = first + "..."; //"<br/>" + second;
				list.get(i).setTitle(resultSubject);
			}
		}
		//.제목 15글자 단위로 개행 종료
				
		
		//가변 시퀀스 넘버
		int number = numberCount-(page.getCurrentPage()-1)*blockCount;
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pagingHtml", pagingHtml);
		request.setAttribute("list", list);
		request.setAttribute("number", number); //글넘버 - 가변으로 선정되는 게시글의 숫자
		request.setAttribute("searchingNow", searchingNow); // 전체,검색글을 판단함
		return "/view/menu6/recruit_application/applicationList.jsp";
	}
	
	//학교급식인력풀(구직) (검색글) 리스트
		@RequestMapping("/applicationSearch.do")
		public String applicationSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			// 전체글, 검색글 판단값.
			searchingNow = 1; //0 == 전체글//1 == 검색글//
			request.setCharacterEncoding("euc-kr");
			
			//사용자가 입력한 값
			String searchType = request.getParameter("searchType"); //검색 종류 // 직종,근무형태,지역,학교급
			
			//검색 타입에 따라 다른 추가입력 셀렉트에 따른 list를 get
			if(searchType.equals("job")){
				subType = "job";
				subValue = request.getParameter("job");
				list = sqlMapper.queryForList("Application.selectWithJob", subValue);
				request.setAttribute("job", subValue);
			}else if(searchType.equals("gubun")){
				subType = "gubun";
				subValue = request.getParameter("gubun");
				list = sqlMapper.queryForList("Application.selectWithGubun", subValue);
				request.setAttribute("gubun", subValue);
			}else{
				subType = "loc_seq";
				subValue = request.getParameter("loc_seq");
				list = sqlMapper.queryForList("Application.selectWithLoc", subValue);
				request.setAttribute("loc_seq", subValue);
			}
			//.검색 타입에 따라 다른 추가입력 셀렉트
			
			
			/*
			 * PagingAction 클래스를 이용하여 페이지정의
			 * 현재게시판의 페이지단위나 레코드개수를 정의하여 파라미터로 호출
			*/
			//PagingAction 파라미터 정의
			blockCount = 10;// 한 페이지의 게시물의 수
			blockPage = 5;// 한 화면에 보여줄 페이지 수
			serviceName = "recruitSearch";// 호출 URI 정의
			totalCount = list.size(); // 전체 글 갯수
			//currentPage 초기화
			if(null == request.getParameter("currentPage")){
				currentPage = 1;
			}else{
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}
			page = new PagingAction(serviceName, currentPage, totalCount, blockCount, blockPage, searchType, subType, subValue); // pagingAction 객체 생성.
			pagingHtml = page.getPagingHtml().toString(); // 페이지 HTML 생성.
			int lastCount = totalCount; // 마지막 레코드 = 개수
			
			// 현재 페이지의 마지막 글의 번호가 전체의 마지막 글 번호보다 작으면 lastCount를 +1 번호로 설정.
			if (page.getEndCount() < totalCount)
				lastCount = page.getEndCount() + 1;
			
			// 전체 리스트에서 현재 페이지만큼의 리스트만 가져온다.
			list = list.subList(page.getStartCount(), lastCount);
			//.페이지처리 종료
			
			//리스트 글번호 가변 계산
			int number=totalCount-(page.getCurrentPage()-1)*blockCount;
					
			request.setAttribute("number", number);	
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("pagingHtml", pagingHtml);
			request.setAttribute("list", list);
			request.setAttribute("searchType", searchType);
			request.setAttribute("totalCount", totalCount);
			request.setAttribute("searchingNow", searchingNow);
			return "/view/menu6/recruit_application/applicationList.jsp";
		}
	
} //end of class
