package menu7.trainingEvent.controller;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import menu2.inspectionResult.dto.InspectionResultDTO;
import menu7.trainingEvent.dto.TrainingEventDTO;

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
public class TrainingEventCreate {
	
	//insert
	private TrainingEventDTO paramClass = new TrainingEventDTO();
	private TrainingEventDTO resultClass = new TrainingEventDTO();
	
	//최대시퀀스넘버
	private int seq;
	
	//업로드 파일 경로
	String file_path = Constants.COMMON_FILE_PATH + Constants.MENU7_TRAININGEVENT_FILE_PATH;
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public TrainingEventCreate() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	//연수행사 새글 입력폼
	@RequestMapping("/trainingEventCreateFrom.do")
	public String trainingEventCreateFrom(){
		return "/view/menu7/trainingEvent/trainingEventCreate.jsp";
	}
	
	//연수행사 새글 입력
	@RequestMapping(value="/trainingEventCreate.do", method=RequestMethod.POST)
	public String trainingEventCreate(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session) throws Exception{
		request.setCharacterEncoding("euc-kr");
		
		//작성한 사용자(현재 로그인한 세션아이디)
		String session_id = (String) session.getAttribute("session_id");
		
		//사용자가 입력한 값
		String title = request1.getParameter("title");
		String str_date = request1.getParameter("str_date");
		String end_date = request1.getParameter("end_date");
		String description = request1.getParameter("description");
		String pw = request1.getParameter("pw");
		Calendar today = Calendar.getInstance();
		
		//DTO Set()
		paramClass.setGubun("0"); // 0은 새글, 1은 답글
		paramClass.setTitle(title);
		paramClass.setStr_date(str_date);
		paramClass.setEnd_date(end_date);
		paramClass.setDescription(description);
		paramClass.setPw(pw);
		paramClass.setHits(1);
		paramClass.setWriter(session_id);
		paramClass.setReg_name(session_id);
		paramClass.setReg_date(today.getTime());
		paramClass.setUdt_name(session_id);
		paramClass.setUdt_date(today.getTime());
		paramClass.setTurn("0");
		
		Integer count = (Integer) sqlMapper.queryForObject("TrainingEvent.selectCount", session_id);
		if(count==0){
			paramClass.setUp_seq(1);
		}else{
			resultClass = (TrainingEventDTO) sqlMapper.queryForObject("TrainingEvent.selectLastNum");
			seq = (int)(resultClass.getSeq());
			paramClass.setUp_seq(seq+1); // 앞으로 만들어질 시퀀스넘버라 +1 함
		}
		
		//DB에 insert 하기 (글 등록)
		sqlMapper.insert("TrainingEvent.insertTrainingEvent", paramClass);
		
		
		//최대시퀀스넘버 get
		resultClass = (TrainingEventDTO) sqlMapper.queryForObject("TrainingEvent.selectLastNum");
		seq = (int)(resultClass.getSeq());
		
		
		//파일첨부
		MultipartFile file = request.getFile("filename"); // 업로드된 원본
		String orgName = file.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
		
		if(orgName != ""){ //파일을 첨부했을 경우
			
			String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
			String fileName = "file_trainingEvent_"+randNum;//서버저장 파일명(file_inspctionResult_랜덤번호)
			String fileExt = orgName.substring(orgName.lastIndexOf('.'));//서버저장 확장자
			
			File save = new File(file_path+fileName+fileExt); //복사대상 생성 (경로+파일명+확장자)
			file.transferTo(save);  // 복사본 생성
			
			//DB 파일 경로 저장용
			//상대경로 path
			//String path = save.getPath().replace("\\", "/").substring(42); // 42전까지가 절대경로
			//절대경로 path
			//String path = file_path+fileName+fileExt;
			
			paramClass.setSeq(seq); //최대 시퀀스넘버
			paramClass.setAttach_name(fileName+fileExt); //파일명
			paramClass.setAttach_path(file_path.replace("\\", "/")); //파일경로
			
			//파일 정보 업데이트.
			sqlMapper.update("TrainingEvent.updateFile", paramClass);
		}
		//.파일첨부
		
		return "redirect:/TrainingEventList.do"; //리스트로 리다이렉트
	}
	
	
	
	
	
	//연수행사 답글 입력폼
	@RequestMapping("/trainingEventResCreateFrom.do")
	public String trainingEventResCreateFrom(HttpServletRequest request){
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		
		
		request.setAttribute("seq", seq);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("searchingNow", searchingNow);
		
		return "/view/menu7/trainingEvent/trainingEventResCreate.jsp";
	}
		
		
}
