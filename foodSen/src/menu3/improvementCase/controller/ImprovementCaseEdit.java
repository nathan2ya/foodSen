package menu3.improvementCase.controller;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import menu3.improvementCase.dto.ImprovementCaseDTO;

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


@Controller
public class ImprovementCaseEdit {
	
	private ImprovementCaseDTO resultClass = new ImprovementCaseDTO(); //수정할 레코드 가져오기 DTO
	private ImprovementCaseDTO paramClass = new ImprovementCaseDTO(); //수정 진행 레코드
	
	String file_path = Constants.COMMON_FILE_PATH + Constants.MENU3_IMPROVEMENT_FILE_PATH; //업로드 파일 경로
	
	
	private int cnt; //이미지 개수
	
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public ImprovementCaseEdit() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	
	//위생.안전 검사결과 수정폼
	@RequestMapping("/improvementCaseEditFrom.do")
	public String improvementCaseEditFrom(HttpServletRequest request) throws SQLException{
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		
		
		resultClass = (ImprovementCaseDTO)sqlMapper.queryForObject("ImprovementCase.selectImprovementCaseOne", seq);
		
		
		
		//수정시 기존 이미지명 저장하기 위한 작업 시작
			cnt = 0;
			
			//이미지 개수를 파악
			if(resultClass.getImg1() != null){
				cnt++;
			}
			if(resultClass.getImg2() != null){
				cnt++;
			}
			if(resultClass.getImg3() != null){
				cnt++;
			}
			if(resultClass.getImg4() != null){
				cnt++;
			}
			if(resultClass.getImg5() != null){
				cnt++;
			}
			if(resultClass.getImg6() != null){
				cnt++;
			}
			//.이미지 개수를 파악 종료
			
			//이미지명을 저장하기 위한 배열
			String[] imgNames = new String[cnt];
		
			//이미지명 저장 시작
			if(resultClass.getImg1() != null){
				imgNames[0] = new String(resultClass.getImg1().substring(38));
			}
			if(resultClass.getImg2() != null){
				imgNames[1] = new String(resultClass.getImg2().substring(38));
			}
			if(resultClass.getImg3() != null){
				imgNames[2] = new String(resultClass.getImg3().substring(38));
			}
			if(resultClass.getImg4() != null){
				imgNames[3] = new String(resultClass.getImg4().substring(38));
			}
			if(resultClass.getImg5() != null){
				imgNames[4] = new String(resultClass.getImg5().substring(38));
			}
			if(resultClass.getImg6() != null){
				imgNames[5] = new String(resultClass.getImg6().substring(38));
			}
			//.이미지명 저장 종료
		
		//.수정시 기존 이미지명 저장하기 위한 작업 종료
		
			
		//테스트	
		System.out.println("-----------------------------");
		
		System.out.println("이미지개수 : "+cnt);
		for(int i=0; i<imgNames.length; i++) 
              System.out.println(imgNames[i]);
		
		System.out.println("-----------------------------");
		//테스트
		
		
		request.setAttribute("seq", seq);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("searchingNow", searchingNow);
		request.setAttribute("resultClass", resultClass);
		request.setAttribute("imgNames", imgNames);
		
		return "/view/menu3/improvementCaseEdit.jsp";
	}
	
	
	//위생.안전 검사결과 수정
	@RequestMapping(value="/improvementCaseEdit.do",method=RequestMethod.POST)
	public String improvementCaseEdit(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session) throws Exception{
		
		//작성한 사용자(현재 로그인한 세션아이디)
		String session_id = (String) session.getAttribute("session_id");
		
		//수정요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
				
		//사용자가 입력한 값
		String description = request1.getParameter("description");
		Calendar today = Calendar.getInstance();
		

		//DTO Set()
		paramClass.setSeq(seq);
		paramClass.setDescription(description); //수정내용
		paramClass.setUdt_name(session_id); //수정인
		paramClass.setUdt_date(today.getTime()); //수정일
		
		
		//DB에 update 하기 (글 수정)
		sqlMapper.update("ImprovementCase.updateImprovementCase", paramClass);
		
		
		//1. 파일삭제를 위해 생성 - 파일삭제시 기존 업로드된파일의 경로를 얻기위함
		//2. 이미지 파일 수정을 위해 생성 - 사용자가 수정시 이미지 업로더를 추가했는지 감소했는지 판단하고 업데이트를 하기 위함
		resultClass = (ImprovementCaseDTO)sqlMapper.queryForObject("ImprovementCase.selectImprovementCaseOne", seq);
		
		
		//파일첨부 시작
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
					sqlMapper.update("ImprovementCase.updateFile", paramClass);
				///새로운파일 생성종료
			}
		//.파일첨부 종료
			
			
			
		//이미지첨부 시작
			if(resultClass.getImg1() == null){
				
			}
		
		//.이미지첨부 종료
		
		
		return "redirect:/improvementCaseView.do?seq="+seq+"&currentPage="+currentPage+"&searchingNow="+searchingNow; // 호출한 뷰페이지로 리다이렉트
	}
	
	
}
