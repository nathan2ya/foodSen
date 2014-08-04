package menu2.inspectionResult.controller;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import menu2.inspectionResult.dto.InspectionResultDTO;
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

/*
 * 작성자: 류재욱
 * 설  명: 위생.안전성검사결과 insert 클래스
 * 용  도: 위생.안전성검사결과 게시글 등록을 위함.
*/

@Controller
public class InspectionResultCreate {
	
	//위생.안전성검사결과 insert
	private InspectionResultDTO paramClass = new InspectionResultDTO();
	private InspectionResultDTO resultClass = new InspectionResultDTO();
	
	//최대시퀀스넘버
	private int seq;
	
	//업로드 파일 경로
	String file_path = Constants.COMMON_FILE_PATH + Constants.MENU2_INSPECTIONRESULT_FILE_PATH;
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public InspectionResultCreate() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//위생.안전 검사결과 등록 form
	@RequestMapping("/inspectionResultCreateFrom.do")
	public String inspectionResultCreateFrom(){
		return "/view/menu2/inspectionResultCreate.jsp";
	}
	
	//위생.안전 검사결과 등록 DB insert
	@RequestMapping(value="/inspectionResultCreate.do", method=RequestMethod.POST)
	public String inspectionResultCreate(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session) throws Exception{
		request.setCharacterEncoding("euc-kr");
		
		//작성한 사용자(현재 로그인한 세션아이디)
		String session_id = (String) session.getAttribute("session_id");
		
		//사용자가 입력한 값
		String title = request1.getParameter("title");
		String description = request1.getParameter("description");
		String pw = request1.getParameter("pw");
		Calendar today = Calendar.getInstance();
		
		//DTO Set()
		paramClass.setTitle(title);
		paramClass.setDescription(description);
		paramClass.setPw(pw);
		paramClass.setHits(1);
		paramClass.setWirte(session_id);
		paramClass.setReg_name(session_id);
		paramClass.setReg_date(today.getTime());
		paramClass.setUdt_name(session_id);
		paramClass.setUdt_date(today.getTime());
		
		//DB에 insert 하기 (글 등록)
		sqlMapper.insert("InspectionResult.insertInspectionResult", paramClass);
		
		//최대시퀀스넘버 get //방금 insert한 레코드에 다음 실행될 파일경로, 파일이름을 해당 시퀀스넘버에 update 하기 위함.
		resultClass = (InspectionResultDTO) sqlMapper.queryForObject("InspectionResult.selectLastNum");
		seq = (int)(resultClass.getSeq());
		
		//파일첨부
		MultipartFile file = request.getFile("filename"); // 업로드된 원본
		String orgName = file.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
		
		if(orgName != ""){ //파일을 첨부했을 경우
			
			String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
			String fileName = "file_inspectionResult_"+randNum;//서버저장 파일명(file_inspctionResult_랜덤번호)
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
			sqlMapper.update("InspectionResult.updateFile", paramClass);
		}
		//.파일첨부
		
		return "redirect:/inspectionResultList.do"; //리스트로 리다이렉트
	}
	
} //end of class
