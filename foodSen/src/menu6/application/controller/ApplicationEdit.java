package menu6.application.controller;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import menu6.application.dto.ApplicationDTO;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import common.Constants;


@Controller
public class ApplicationEdit {
	
	//수정할 레코드 가져오기 DTO
	private ApplicationDTO resultClass = new ApplicationDTO();
	//수정 진행 레코드
	private ApplicationDTO paramClass = new ApplicationDTO();
	
	//업로드 파일 경로
	private String file_path = Constants.COMMON_FILE_PATH + Constants.MENU6_APPLICATION_FILE_PATH;
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public ApplicationEdit() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	
	//학교급식인력풀(구직) 수정폼
	@RequestMapping("/applicationEditFrom.do")
	public String applicationEditFrom(HttpServletRequest request) throws SQLException{
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		
		//수정에 보여줄 해당 레코드
		resultClass = (ApplicationDTO)sqlMapper.queryForObject("Application.selectApplicationOne", seq);
		
		request.setAttribute("seq", seq);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("searchingNow", searchingNow);
		request.setAttribute("resultClass", resultClass);
		
		return "/view/menu6/recruit_application/applicationEdit.jsp";
	}
	
	
	
	//학교급식인력풀(구직) 수정
	@RequestMapping(value="/applicationEdit.do",method=RequestMethod.POST)
	public String applicationEdit(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session) throws Exception{
		
		//작성한 사용자(현재 로그인한 세션아이디)
		String session_id = (String) session.getAttribute("session_id");
		
		//수정요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
				
		//사용자가 입력한 값
		String description = request1.getParameter("description");
		String end_yn = request1.getParameter("end_yn");
		String job = request1.getParameter("job");
		String gubun = request1.getParameter("gubun");
		Calendar today = Calendar.getInstance();
		

		//DTO Set()
		paramClass.setSeq(seq);
		paramClass.setDescription(description); //수정내용
		paramClass.setEnd_yn(end_yn);
		paramClass.setJob(job);
		paramClass.setGubun(gubun);
		paramClass.setUdt_name(session_id); //수정인
		paramClass.setUdt_date(today.getTime()); //수정일
		
		
		//DB에 update 하기 (글 수정)
		sqlMapper.update("Application.updateApplication", paramClass);
		
		
		//파일삭제를 위해 생성 //파일삭제시 기존 업로드된파일의 경로를 얻기위함
		resultClass = (ApplicationDTO)sqlMapper.queryForObject("Application.selectApplicationOne", seq);
		
		
		//파일첨부
			MultipartFile file = request.getFile("filename"); // 업로드된 원본
			String orgName = file.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
			
			if(orgName != ""){ //파일을 첨부했을 경우
				
				//기존파일 삭제시작
				if(resultClass.getAttach_path() != null){
					File deleteFile = new File(resultClass.getAttach_path());
					deleteFile.delete();
				}
				//.기존파일 삭제종료
				
				
				//새로운파일 생성시작
					String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
					String fileName = "file_application_"+randNum;//서버저장 파일명(file_application_랜덤번호)
					String fileExt = orgName.substring(orgName.lastIndexOf('.'));//서버저장 확장자
					
					File save = new File(file_path+fileName+fileExt); //복사대상 생성 (경로+파일명+확장자)
					file.transferTo(save);  // 복사본 생성
					
					//DB 파일 경로 저장용
					
					//상대경로 path
					//String path = save.getPath().replace("\\", "/").substring(42); // 42전까지가 절대경로
					
					//절대경로 path
					String path = file_path+fileName+fileExt;
					
					paramClass.setSeq(seq); //시퀀스넘버
					paramClass.setAttach_name(fileName+fileExt); //파일명
					paramClass.setAttach_path(path); // 파일경로(img src 경로를 의미함)
					
					//파일 정보 업데이트.
					sqlMapper.update("Application.updateFile", paramClass);
				///새로운파일 생성종료
			}
		//.파일첨부
		
		
		return "redirect:/applicationView.do?seq="+seq+"&currentPage="+currentPage+"&searchingNow="+searchingNow; // 호출한 뷰페이지로 리다이렉트
	}
	
}
