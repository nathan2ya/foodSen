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

import common.Constants;

/*
 * 작성자: 류재욱
 * 설  명: 연수행사 update 클래스
 * 용  도: 연수행사 게시글 수정을 위함.
*/

@Controller
public class TrainingEventEdit {
	
	//수정할 레코드 가져오기 DTO
	private TrainingEventDTO resultClass = new TrainingEventDTO();
	//수정 진행 레코드
	private TrainingEventDTO paramClass = new TrainingEventDTO();
	
	//업로드 파일 경로
	String file_path = Constants.COMMON_FILE_PATH + Constants.MENU7_TRAININGEVENT_FILE_PATH;
	
	//업로드 이미지 파일 경로
	private String image_path = Constants.MENU7_TRAININGEVENT_IMAGE_PATH;
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public TrainingEventEdit() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	
	//연수행사(새글) 수정폼
	@RequestMapping("/trainingEventEditFrom.do")
	public String trainingEventEditFrom(HttpServletRequest request) throws SQLException{
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		int permit = Integer.parseInt(request.getParameter("permit"));
		
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		//현재날짜
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentTime = sdf.format(cal.getTime());
		//.현재날짜
		
		request.setAttribute("currentTime", currentTime);
		request.setAttribute("seq", seq);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("searchingNow", searchingNow);
		request.setAttribute("resultClass", resultClass);
		request.setAttribute("permit", permit);
		
		return "/view/menu7/trainingEvent/trainingEventEdit.jsp";
	}
	
	
	//연수행사(새글) 수정
	@RequestMapping(value="/trainingEventEdit.do",method=RequestMethod.POST)
	public String trainingEventEdit(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session) throws Exception{
		
		//작성한 사용자(현재 로그인한 세션아이디)
		String session_id = (String) session.getAttribute("session_id");
		
		//수정요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		String permit = request.getParameter("permit");
				
		//사용자가 입력한 값
		String description = request1.getParameter("description");
		Calendar today = Calendar.getInstance();
		
		//DTO Set()
		paramClass.setSeq(seq);
		paramClass.setDescription(description); //수정내용
		paramClass.setUdt_name(session_id); //수정인
		paramClass.setUdt_date(today.getTime()); //수정일
		
		
		if(permit.equals("0")){ //시작,종료일이 있을때만
			String str_date = request1.getParameter("str_date");
			String end_date = request1.getParameter("end_date");
			
			paramClass.setStr_date(str_date); //시작일
			paramClass.setEnd_date(end_date); //종료일
			//DB에 update 하기 (글 수정)//시작,종료일이 있을때만
			sqlMapper.update("TrainingEvent.updateTrainingEvent", paramClass);
		}
		
		//DB에 update 하기 (글 수정)//시작,종료일이 없을때만
		sqlMapper.update("TrainingEvent.updateTrainingEvent1", paramClass);
		
		
		//파일삭제를 위해 생성 //파일삭제시 기존 업로드된파일의 경로를 얻기위함
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		
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
				String fileName = "file_inspectionResult_"+randNum;//서버저장 파일명(file_inspctionResult_랜덤번호)
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
				sqlMapper.update("TrainingEvent.updateFile", paramClass);
			///새로운파일 생성종료
		}
		//.파일첨부
		
		return "redirect:/trainingEventView.do?seq="+seq+"&currentPage="+currentPage+"&searchingNow="+searchingNow; // 호출한 뷰페이지로 리다이렉트
	}
	
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------------------//
	
	
	
	
	//연수행사 (답글) 수정폼
	@RequestMapping("/trainingEventRepEditFrom.do")
	public String trainingEventRepEditFrom(HttpServletRequest request) throws SQLException{
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		request.setAttribute("seq", seq);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("searchingNow", searchingNow);
		request.setAttribute("resultClass", resultClass);
		
		return "/view/menu7/trainingEvent/trainingEventResEdit.jsp";
	}
	
	
	//연수행사 (답글) 수정
	@RequestMapping(value="/trainingEventRepEdit.do",method=RequestMethod.POST)
	public String trainingEventRepEdit(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session) throws Exception{
		
		//작성한 사용자(현재 로그인한 세션아이디)
		String session_id = (String) session.getAttribute("session_id");
		
		//수정요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
				
		//사용자가 입력한 값
		String description = request1.getParameter("description");
		String str_date = request1.getParameter("str_date");
		String end_date = request1.getParameter("end_date");
		
		Calendar today = Calendar.getInstance();
		

		//DTO Set()
		paramClass.setSeq(seq);
		paramClass.setStr_date(str_date); //시작일
		paramClass.setEnd_date(end_date); //종료일
		paramClass.setDescription(description); //수정내용
		paramClass.setUdt_name(session_id); //수정인
		paramClass.setUdt_date(today.getTime()); //수정일
		
		
		//DB에 update 하기 (글 수정)
		sqlMapper.update("TrainingEvent.updateTrainingEvent", paramClass);
		
		
		//파일삭제를 위해 생성 //파일삭제시 기존 업로드된파일의 경로를 얻기위함
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		
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
				String fileName = "file_inspectionResult_"+randNum;//서버저장 파일명(file_inspctionResult_랜덤번호)
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
				sqlMapper.update("TrainingEvent.updateFile", paramClass);
			///새로운파일 생성종료
		}
		//.파일첨부
			
		
		
		/*이미지 첨부 시작
		 * 1번~3번까지의 이미지 업로드 유무를 판단하고,
		 * 기존 업로드된 이미지가 있을 경우 삭제 후 insert 하고
		 * 아닐 경우는 바로 insert 한다.
		 * */
		
		
		//1번 이미지
		if(request.getFile("img1") != null){ // 해당 변수가 존재하면
			
			MultipartFile file1 = request.getFile("img1"); // 업로드된 원본
			String orgName1 = file1.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
			
			if(orgName1 != ""){ //이미지 파일을 첨부했을 경우
				
				String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
				String fileName = "img_trainingEvent_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
				String fileExt = orgName1.substring(orgName1.lastIndexOf('.'));//서버저장 확장자
				
				File save = new File(file_path+fileName+fileExt); //복사대상 생성 (경로+파일명+확장자)
				file1.transferTo(save);  // 복사본 생성
				
				//이미지파일 상대경로 제작
				String temp = image_path.replace("\\", "/");
				//상대경로 path
				String path = temp+fileName+fileExt; // 상대경로+파일명+확장자
				
				paramClass.setSeq(seq); //최대 시퀀스넘버
				paramClass.setImg1(path); // 이미지경로
				
				//파일 정보 업데이트.
				sqlMapper.update("TrainingEvent.updateImg1", paramClass);
			}
		}
		//.1번 이미지 종료
		
		//2번 이미지
		if(request.getFile("img2") != null){ // 해당 변수가 존재하면
			
			MultipartFile file2 = request.getFile("img2"); // 업로드된 원본
			String orgName2 = file2.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
			
			if(orgName2 != ""){ //이미지 파일을 첨부했을 경우
				String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
				String fileName = "img_trainingEvent_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
				String fileExt = orgName2.substring(orgName2.lastIndexOf('.'));//서버저장 확장자
				
				File save = new File(file_path+fileName+fileExt); //복사대상 생성 (경로+파일명+확장자)
				file2.transferTo(save);  // 복사본 생성
				
				//이미지파일 상대경로 제작
				String temp = image_path.replace("\\", "/");
				//상대경로 path
				String path = temp+fileName+fileExt; // 상대경로+파일명+확장자
				
				paramClass.setSeq(seq); //최대 시퀀스넘버
				paramClass.setImg2(path); // 이미지경로
				
				//파일 정보 업데이트.
				sqlMapper.update("TrainingEvent.updateImg2", paramClass);
			}
		}
		//.2번 이미지 종료
		
		
		//3번 이미지
		if(request.getFile("img3") != null){ // 해당 변수가 존재하면
			
			MultipartFile file3 = request.getFile("img3"); // 업로드된 원본
			String orgName3 = file3.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
			
			if(orgName3 != ""){ //이미지 파일을 첨부했을 경우
			
				String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
				String fileName = "img_trainingEvent_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
				String fileExt = orgName3.substring(orgName3.lastIndexOf('.'));//서버저장 확장자
				
				File save = new File(file_path+fileName+fileExt); //복사대상 생성 (경로+파일명+확장자)
				file3.transferTo(save);  // 복사본 생성
				
				//이미지파일 상대경로 제작
				String temp = image_path.replace("\\", "/");
				//상대경로 path
				String path = temp+fileName+fileExt; // 상대경로+파일명+확장자
				
				paramClass.setSeq(seq); //최대 시퀀스넘버
				paramClass.setImg3(path); // 이미지경로
				
				//파일 정보 업데이트.
				sqlMapper.update("TrainingEvent.updateImg3", paramClass);
			}
		}
		//.3번 이미지 종료
			
		
		
		return "redirect:/trainingEventRepView.do?seq="+seq+"&currentPage="+currentPage+"&searchingNow="+searchingNow; // 호출한 뷰페이지로 리다이렉트
	}
	
	
	
	
}
