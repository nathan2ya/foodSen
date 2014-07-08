package menu2.inspectionResult.controller;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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


@Controller
public class InspectionResultCreate {
	
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
	
	
	//위생.안전 검사결과 입력폼
	@RequestMapping("/inspectionResultCreateFrom.do")
	public String inspectionResultCreateFrom(){
		return "/view/menu2/inspectionResultCreate.jsp";
	}
	
	
	
	
	/*
	
	//위생.안전 검사결과 입력
	@RequestMapping(value="/inspectionResultCreate.do",method=RequestMethod.POST)
	public String inspectionResultCreate(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session, @ModelAttribute("SpringDTO") SpringDTO dto) throws Exception{
													//파일업로드용	request						//파라미터 request1				//파라미터 response1					 //세션용							//파라미터 DTO로 자동 set(), get()

		//.jsp에서 리퀘스트로 받고 싶을때
		String message = request1.getParameter("message");
		//.jsp로 리퀘스트로 보내고 싶을때
		request1.setAttribute("message", message);

		
		
		//세션 생성하고싶을때
		session.setAttribute("session_id", result.getBuyer_id()); // (세션아이디 정의,받아온값을)
		
		
		
		//현재 서버 세션 쓰고 싶을때 (현재 서버상에 sessionID로 세션이 생성되어 있는 경우) 
		String sessionID = (String) session.getAttribute("sessionID");
		
		
		
		//현재 날짜,시간 insert하고 싶을때 - 버전1
		Calendar today = Calendar.getInstance();
		today.getTime(); // 반환값이 Date임.
		
		
		//현재 날짜,시간 포맷한상태로 스트링으로 받기 - 버전2
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_hhmmss");
		sdf.format(gc.getTime()); // 반환값이 String임
		
		
		
		//dto에서 get하고 싶을때
		System.out.println("사용자가 입력한 메세지 : "+dto.getMessage());
		
		
		
		
		//파일업로드할때
		MultipartFile file = request.getFile("upload"); // 업로드된 원본
		String orgName = file.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
		
		String nowTime = sdf.format(gc.getTime());//현재시간
		String randNum = Integer.toString((int)(Math.random() * 999));//랜덤번호
		String fileName = "img_spring_"+nowTime+"_"+randNum;//서버저장 파일명(img_spring_현재날짜_랜덤번호)
		String fileExt = orgName.substring(orgName.lastIndexOf('.'));//서버저장 확장자
		
		File save = new File(SPRING_TEST_FILE_PATH+fileName+fileExt); //복사대상 생성 (경로+파일명+확장자)
		file.transferTo(save);  // 복사본 생성
		
		//DB 파일 경로 저장용
		String temp = save.getPath().replace("\\", "/");
		String path = "/springEx"+temp.substring((temp.indexOf("save"))-1);
		
		
		//  dto에 set만해도 jsp에서 쓸 수 있다. (63행 @ModelAttribute에 의해 자동 get()되기 때문에)
		dto.setDestfile(path); 

		
		
		
		//DB에 insert 하기 (글쓰기)
		sqlMapper.insert("insertSpring",dto);
		sqlMapper.update("insertSpring",dto);
		sqlMapper.delete("insertSpring",dto);
		
		//DB에서 select 하기 (리스트)
		list = sqlMapper.queryForList("selectSpring");
		
		
		//select 1개만
		//private BoardDTO resultClass = new BoardDTO();
		resultClass = (BoardDTO)sqlMapper.queryForObject("Board.selectBoardOne", board_seq_num);
		
		
		request1.setAttribute("list", list);
		
		
		
		
		//최종 목적 뷰페이지 (setAttribute 가지고 가기)
		return "/spring/formPro.jsp";
		//리다이렉트 시키기
		return "redirect:/listBoard.do";
	}
	
	*/
}
