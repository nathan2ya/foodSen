package menu7.trainingEvent.controller;


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

import menu7.trainingEvent.dto.TrainingEventDTO;

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
 * 설  명: 연수행사 리스트2 클래스
 * 용  도: 연수행사 게시글 리스트 출력을 위함.
*/

@Controller
public class TrainingEventList {
	
	//리스트
	private List<TrainingEventDTO> list = new ArrayList<TrainingEventDTO>(); //일반
	private List<TrainingEventDTO> list1 = new ArrayList<TrainingEventDTO>(); //정렬된
	private List<TrainingEventDTO> cntList = new ArrayList<TrainingEventDTO>(); //가변을 위한 새글리스트
	
	
	//검색중
	private String tempInput;
	private int searchingNow; // 전체글, 검색글을 판단하여 각종 논리성을 판가르는 논리값
	
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
	public TrainingEventList() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	//연수행사 (전체글) 리스트
	@RequestMapping("/TrainingEventList.do")
	public String TrainingEventList(HttpServletRequest request) throws SQLException{
		
		// 전체글, 검색글 판단값.
		searchingNow = 0; //0 == 전체글//1 == 검색글//
				
		list = sqlMapper.queryForList("TrainingEvent.selectAll"); //전체글
		//int numberCount = list.size(); // 전체 레코드 개수
		
		/*
		 * PagingAction 클래스를 이용하여 페이지정의
		 * 현재게시판의 페이지단위나 레코드개수를 정의하여 파라미터로 호출
		*/
		//PagingAction 파라미터 정의
		blockCount = 10;// 한 페이지의 게시물의 수
		blockPage = 5;// 한 화면에 보여줄 페이지 수
		serviceName = "TrainingEventList";// 호출 URI 정의
		totalCount = list.size(); // 전체 글 갯수
		//currentPage 초기화
		if(null == request.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		//객체생성 및 결과반환(형변환)
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
		
		
		//제목 16글자 이상 자르기
		String first;
		//String second;
		String resultSubject;
		
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getTitle().length() > 16){ //제목이 18글자 이상이면
				first = list.get(i).getTitle().substring(0, 16); //잘라내기
				
				resultSubject = first + "..."; 
				list.get(i).setTitle(resultSubject);
			}
		}
		//.제목 16글자 이상 자르기 종료
				
		
		System.out.println("현재페이지 : "+page.getCurrentPage());
		System.out.println("마지막페이지 : "+page.getTotalPage());
		
		
		//새글만 가져옴
		cntList = sqlMapper.queryForList("TrainingEvent.newList");
		int numberCount = cntList.size(); // 새글의 레코드 총 개수

		int number; // 가변 시작 글 넘버
		int cnt=0; // 댓글 카운터변수

		if(page.getCurrentPage()==1){ // 시작페이지 일 경우
			number=numberCount-(page.getCurrentPage()-1)*blockCount;
		}else{ // 중간페이지일 경우 or 마지막페이지일 경우 //현재페이지 미만의 답글수를 구해서 기본식에서 + 함.
			int temp = page.getCurrentPage();

			for(int i=temp-1; i>0; i--){ // 현재페이지 이전 답글을 체크하기 위해 -1함
				int temp1 = i*blockCount-blockCount; //지금페이지에서 시작 인덱스 값
				int temp2 = i*blockCount; //지금페이지에서 마지막 인덱스 값

				list1 = sqlMapper.queryForList("TrainingEvent.selectAllrownum"); //정렬된 리스트
				list1 = list1.subList(temp1, temp2); // 지금페이지를 시작,마지막 값으로 자름

				for(int j=0; j<list1.size(); j++){
					//답글인것만 = 1
					if(Integer.parseInt(list1.get(j).getGubun()) == 1){
						cnt++;//지금페이지의 답글 개수를 누적시킴
					}
				}
			}
			//기존 계산식에서 -> 최종적으로 현재페이지 이전의 모든페이지의 답글을 더함.
			//그러면 이전페이지의 답글개수를 뺀 수부터 가변으로 넘버를 감소시키기 시작함.
			number=(numberCount-(page.getCurrentPage()-1)*blockCount)+cnt;
		}
		
		/*
		 * 가변 시퀀스 넘버(새글답글전체)
		 * 향후 사용가능성이 있어 주석처리.
		*/
		//int number = numberCount-(page.getCurrentPage()-1)*blockCount;
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pagingHtml", pagingHtml);
		request.setAttribute("list", list);
		request.setAttribute("number", number); //글넘버 - 가변으로 선정되는 게시글의 숫자
		request.setAttribute("searchingNow", searchingNow); // 전체,검색글을 판단함
		return "/view/menu7/trainingEvent/trainingEventList.jsp";
	}
	
	
	
	//연수행사 (검색글) 리스트
	@RequestMapping("/trainingEventSearch.do")
	public String trainingEventSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 전체글, 검색글 판단값.
		searchingNow = 1; //0 == 전체글//1 == 검색글//
		
		request.setCharacterEncoding("euc-kr");
		
		//사용자가 입력한 값
		String searchType = request.getParameter("searchType"); //검색 종류 // 제목,내용,작성자
		String userinput = request.getParameter("userinput"); //검색어
		
		
		//검색종류&검색어를 만족하는 레코드 검색
		if(searchType.equals("title")){
			//제목 검색
			list = sqlMapper.queryForList("TrainingEvent.selectWithTitle", userinput);
		}else if(searchType.equals("writer")){
			//작성자 검색
			list = sqlMapper.queryForList("TrainingEvent.selectWithWriter", userinput);
		}
		
		/*
		 * PagingAction 클래스를 이용하여 페이지정의
		 * 현재게시판의 페이지단위나 레코드개수를 정의하여 파라미터로 호출
		*/
		//PagingAction 파라미터 정의
		blockCount = 10;// 한 페이지의 게시물의 수
		blockPage = 5;// 한 화면에 보여줄 페이지 수
		serviceName = "trainingEventSearch";// 호출 URI 정의
		totalCount = list.size(); // 전체 글 갯수
		
		//currentPage 초기화
		if(null == request.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		page = new PagingAction(serviceName, currentPage, totalCount, blockCount, blockPage, searchType, userinput); // pagingAction 객체 생성.
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
		request.setAttribute("userinput", userinput);
		request.setAttribute("tempInput", tempInput);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("searchingNow", searchingNow);
		return  "/view/menu7/trainingEvent/trainingEventList.jsp";
	}
	
} //end of class
