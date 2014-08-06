package menu6.research.controller;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import menu6.research.dto.ResearchDTO;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import common.PagingAction;

/*
 * 작성자: 류재욱
 * 설  명: 설문조사 list 클래스
 * 용  도: 설문조사 게시글 리스트를 보여주기 위함.
*/

@Controller
public class ResearchList {
	
	//설문조사 list
	private List<ResearchDTO> list = new ArrayList<ResearchDTO>();
	
	//검색중
	private String tempInput;
	private int searchingNow; // 전체글, 검색글을 판단하여 각종 논리성을 판가르는 논리값 //0 == 전체글//1 == 검색글
	
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
	public ResearchList() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//설문조사 (전체글) 리스트
	@RequestMapping("/researchList.do")
	public String researchList(HttpServletRequest request) throws SQLException{
		
		//전체글, 검색글 판단값.
		searchingNow = 0; //0 전체글//1 검색글
		
		//전체글 list
		list = sqlMapper.queryForList("Research.selectAll"); //전체글
		int numberCount = list.size(); // 전체 레코드 개수 (가변 글번호를 생성하기 위함)
		
		/*
		 * PagingAction 클래스를 이용하여 페이지정의
		 * 현재게시판의 페이지단위나 레코드개수를 정의하여 파라미터로 호출
		*/
		//PagingAction 파라미터 정의
		blockCount = 10;// 한 페이지의 게시물의 수
		blockPage = 5;// 한 화면에 보여줄 페이지 수
		serviceName = "researchList";// 호출 URI 정의
		totalCount = list.size(); // 전체 글 갯수
		//currentPage 초기화
		if(null == request.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		//PagingAction 호출 및 결과반환
		page = new PagingAction(serviceName, currentPage, totalCount, blockCount, blockPage); // pagingAction 객체 생성.
		pagingHtml = page.getPagingHtml().toString(); // 페이지 HTML 생성(String 형변환)
		
		
		/*
		 * 한페이지의 list에서 startCound ~ endCound 만큼의 레코드를 잘라내는 과정이다.
		 * 참고 : startCound, endCound 는 현재페이지에 의해 정의되는 변수이다.
		*/
		int lastCount = totalCount; //실제 전체레코드 갯수
		if(page.getEndCount() < totalCount){ //현재페이지 endCound가 / 실제페이지 마지막 totalCount 보다 작을경우 +1 해줌.(이유는 -1했기때문..)
			lastCount = page.getEndCount() + 1; //+1
		}
		list = list.subList(page.getStartCount(), lastCount); // 전체 리스트에서 현재 페이지만큼의 리스트만 가져온다.
		
		
		//마지막페이지 산출
		lastPage = (int) Math.ceil((double) totalCount / blockCount); // 전체레코드개수 / 한페이지당 보여줄 레코드개수 = 반올림(1이라도 있으면 페이지 1개가 추가되므로)
		
		//레코드 없을경우, 또는 1페이지 뿐인 경우 마지막페이지는 1로 정의
		if (lastPage == 0) {
			lastPage = 1;
		}
		
		//방어코드_ 현재페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (currentPage > lastPage) {
			currentPage = lastPage;
		}
		//.페이지 종료
		
		/*
		 * 제목 18글자 단위로 개행
		 * 현재 list에서 제목이 18글자 이상인 것만 get 한 뒤,
		 * 재정의한 뒤에 다시 list에 set 하고 있다. (list의 인덱스에 set하는 이유는 : DB제목을 바꾸지 않기 위함)
		*/
		String first; //18자
		String resultSubject; //결과
		
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getSur_title().length() > 18){ //제목이 18글자 이상이면
				first = list.get(i).getSur_title().substring(0, 18); //18글자 잘라냄
				resultSubject = first + "..."; //18글자 + "..."
				list.get(i).setSur_title(resultSubject); //현재 for문 list의 인덱스.setTitle(재정의된 제목);
			}
		}
		//.제목 15글자 단위로 개행 종료
				
		//가변 시퀀스 넘버
		int number = numberCount-(page.getCurrentPage()-1)*blockCount;
		
		//현재날짜
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentTime = sdf.format(cal.getTime());
		//.현재날짜
		
		request.setAttribute("currentTime", currentTime);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pagingHtml", pagingHtml);
		request.setAttribute("list", list);
		request.setAttribute("number", number); //글넘버 - 가변으로 선정되는 게시글의 숫자
		request.setAttribute("searchingNow", searchingNow); // 전체,검색글을 판단함
		return "/view/menu6/research/researchList.jsp";
	}
	
	/*
	//설문조사 (검색글) 리스트
	@RequestMapping("/researchSearch.do")
	public String researchSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 전체글, 검색글 판단값.
		searchingNow = 1; //0 == 전체글//1 == 검색글//
		
		//인코딩 설정
		request.setCharacterEncoding("euc-kr");
		
		//사용자가 입력한 값
		String searchType = request.getParameter("searchType"); //검색 종류 // 제목,내용,작성자
		String userinput = request.getParameter("userinput"); //검색어
		
		//검색종류&검색어를 만족하는 레코드 검색
		if(searchType.equals("title")){
			list = sqlMapper.queryForList("InspectionResult.selectWithTitle", userinput); //제목 검색
		}else if(searchType.equals("writer")){
			list = sqlMapper.queryForList("InspectionResult.selectWithWriter", userinput); //작성자 검색
		}
			
		
		 * PagingAction 클래스를 이용하여 페이지정의
		 * 현재게시판의 페이지단위나 레코드개수를 정의하여 파라미터로 호출
		
		//PagingAction 파라미터 정의
		blockCount = 10;// 한 페이지의 게시물의 수
		blockPage = 5;// 한 화면에 보여줄 페이지 수
		serviceName = "inspectionResultSearch";// 호출 URI 정의
		totalCount = list.size(); // 전체 글 갯수
		//currentPage 초기화
		if(null == request.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		//PagingAction 호출 및 결과반환
		page = new PagingAction(serviceName, currentPage, totalCount, blockCount, blockPage, searchType, userinput); // pagingAction 객체 생성.
		pagingHtml = page.getPagingHtml().toString(); // 페이지 HTML 생성.
		
		
		 * 한페이지의 list에서 startCound ~ endCound 만큼의 레코드를 잘라내는 과정이다.
		 * 참고 : startCound, endCound 는 현재페이지에 의해 정의되는 변수이다.
		
		int lastCount = totalCount; // 마지막 레코드 = 개수
		if (page.getEndCount() < totalCount) //현재페이지 endCound가 / 실제페이지 마지막 totalCount 보다 작을경우 +1 해줌.(이유는 -1했기때문..)
			lastCount = page.getEndCount() + 1; //+1
		
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
		return  "/view/menu2/inspectionResultList.jsp";
	}
	*/
} //end of class
