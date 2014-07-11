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
	private String image_path = Constants.MENU3_IMPROVEMENT_IMAGE_PATH; //업로드 이미지 파일 경로
	private String imgPath = Constants.COMMON_FILE_PATH + Constants.MENU3_IMPROVEMENT_FILE_PATH; //삭제될 레코드 이미지의 절대경로
	
	
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
		
		request.setAttribute("cnt", cnt);
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
		
		
		
		
		/*
		##파일첨부 시작##
		업로드요청이 새로 들어올경우
		기존 파일을 삭제한 후에 새로 복사본을 서버에 저장함
		*/
		
		MultipartFile file = request.getFile("filename"); // 업로드된 원본
		String orgName = file.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
		
		if(orgName != ""){ //파일을 첨부했을 경우
			
			//기존파일 삭제시작
			if(resultClass.getAttach_path() != null){
				File deleteFile = new File(resultClass.getAttach_path()+resultClass.getAttach_name());
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
			
		
			
			
		/*
		##이미지첨부 수정 시나리오##
		0. 기존값 == null 일때 -> 수정값 == null 이면, 아무것도안함 - 사용자가 어떠한 수정변화도 주지 않았음을 의미
		1. 기존값 == null 일때 -> 수정값 != null 이면, 수정레코드 업데이트 - 사용자가 이미지를 추가등록했음을 의미
		2. 기존값 != null 일때 -> 수정값 != null 이면, 기존레코드 삭제(이미지포함), 수정레코드 업데이트  - 사용자가 동일컬럼을 수정하였음을 의미
		3. 기존값 != null 일때 -> 수정값 == null 이면 -> 기존개수>수정개수, 기존컬럼 삭제(이미지포함) - 사용자가 이미지를 감소등록했음을 의미
						                    		  -> 0==수정개수, 아무것도안함 - 사용자가 어떠한 수정변화도 주지 않았음을 의미
		*/	
		
		
		
		//기존img개수 산출
		int orgCnt = Integer.parseInt(request.getParameter("cnt"));
		
		//수정img개수 산출
		int uptCnt = 0;
		if(request.getFile("optupload1") != null){
			uptCnt++;
		}
		if(request.getFile("optupload2") != null){
			uptCnt++;
		}
		if(request.getFile("optupload3") != null){
			uptCnt++;
		}
		if(request.getFile("optupload4") != null){
			uptCnt++;
		}
		if(request.getFile("optupload5") != null){
			uptCnt++;
		}
		if(request.getFile("optupload6") != null){
			uptCnt++;
		}
		//수정img개수 산출 종료
		
		
		
		// 1. 기존값 == null 일때 -> 수정값 != null 이면, 수정레코드 업데이트 - 사용자가 이미지를 추가등록했음을 의미
		
		//1번 이미지
		if(resultClass.getImg1() == null){
			//1번 이미지
			if(request.getFile("optupload1") != null){ // 해당 변수가 존재하면
				
				MultipartFile file1 = request.getFile("optupload1"); // 업로드된 원본
				String orgName1 = file1.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
				
				if(orgName1 != ""){ //이미지 파일을 첨부했을 경우
					
					String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
					String fileName = "img_improvementCase_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
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
					sqlMapper.update("ImprovementCase.updateImg1", paramClass);
				}
			}
		}
		//.1번 이미지 종료
		
		//2번 이미지
		if(resultClass.getImg2() == null){
			if(request.getFile("optupload2") != null){ // 해당 변수가 존재하면
				
				MultipartFile file2 = request.getFile("optupload2"); // 업로드된 원본
				String orgName2 = file2.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
				
				if(orgName2 != ""){ //이미지 파일을 첨부했을 경우
					String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
					String fileName = "img_improvementCase_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
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
					sqlMapper.update("ImprovementCase.updateImg2", paramClass);
				}
			}
		}
		//.2번 이미지 종료
		
		
		//3번 이미지
		if(resultClass.getImg3() == null){
			if(request.getFile("optupload3") != null){ // 해당 변수가 존재하면
				
				MultipartFile file3 = request.getFile("optupload3"); // 업로드된 원본
				String orgName3 = file3.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
				
				if(orgName3 != ""){ //이미지 파일을 첨부했을 경우
				
					String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
					String fileName = "img_improvementCase_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
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
					sqlMapper.update("ImprovementCase.updateImg3", paramClass);
				}
			}
			//.3번 이미지 종료
		}
		
		
		//4번 이미지
		if(resultClass.getImg4() == null){
			if(request.getFile("optupload4") != null){ // 해당 변수가 존재하면
				
				MultipartFile file4 = request.getFile("optupload4"); // 업로드된 원본
				String orgName4 = file4.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
				
				if(orgName4 != ""){ //이미지 파일을 첨부했을 경우
				
					String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
					String fileName = "img_improvementCase_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
					String fileExt = orgName4.substring(orgName4.lastIndexOf('.'));//서버저장 확장자
					
					File save = new File(file_path+fileName+fileExt); //복사대상 생성 (경로+파일명+확장자)
					file4.transferTo(save);  // 복사본 생성
					
					//이미지파일 상대경로 제작
					String temp = image_path.replace("\\", "/");
					//상대경로 path
					String path = temp+fileName+fileExt; // 상대경로+파일명+확장자
					
					paramClass.setSeq(seq); //최대 시퀀스넘버
					paramClass.setImg4(path); // 이미지경로
					
					//파일 정보 업데이트.
					sqlMapper.update("ImprovementCase.updateImg4", paramClass);
				}
			}
		}
		//.4번 이미지 종료
		
		
		//5번 이미지
		if(resultClass.getImg5() == null){
			if(request.getFile("optupload5") != null){ // 해당 변수가 존재하면
				
				MultipartFile file5 = request.getFile("optupload5"); // 업로드된 원본
				String orgName5 = file5.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
				
				if(orgName5 != ""){ //이미지 파일을 첨부했을 경우
				
					String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
					String fileName = "img_improvementCase_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
					String fileExt = orgName5.substring(orgName5.lastIndexOf('.'));//서버저장 확장자
					
					File save = new File(file_path+fileName+fileExt); //복사대상 생성 (경로+파일명+확장자)
					file5.transferTo(save);  // 복사본 생성
					
					//이미지파일 상대경로 제작
					String temp = image_path.replace("\\", "/");
					//상대경로 path
					String path = temp+fileName+fileExt; // 상대경로+파일명+확장자
					
					paramClass.setSeq(seq); //최대 시퀀스넘버
					paramClass.setImg5(path); // 이미지경로
					
					//파일 정보 업데이트.
					sqlMapper.update("ImprovementCase.updateImg5", paramClass);
				}
			}
		}
		//.5번 이미지 종료
		
		
		//6번 이미지
		if(resultClass.getImg6() == null){
			if(request.getFile("optupload6") != null){ // 해당 변수가 존재하면
				
				MultipartFile file6 = request.getFile("optupload6"); // 업로드된 원본
				String orgName6 = file6.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
				
				if(orgName6 != ""){ //이미지 파일을 첨부했을 경우
				
					String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
					String fileName = "img_improvementCase_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
					String fileExt = orgName6.substring(orgName6.lastIndexOf('.'));//서버저장 확장자
					
					File save = new File(file_path+fileName+fileExt); //복사대상 생성 (경로+파일명+확장자)
					
					file6.transferTo(save);  // 복사본 생성
					
					//이미지파일 상대경로 제작
					String temp = image_path.replace("\\", "/");
					//상대경로 path
					String path = temp+fileName+fileExt; // 상대경로+파일명+확장자
					
					paramClass.setSeq(seq); //최대 시퀀스넘버
					paramClass.setImg6(path); // 이미지경로
					
					//파일 정보 업데이트.
					sqlMapper.update("ImprovementCase.updateImg6", paramClass);
				}
			}
		}
		//.6번 이미지 종료

		//.1번 시나리오 종료
		
		
		
		
		//2. 기존값 != null 일때 -> 수정값 != null 이면, 기존레코드 삭제(이미지포함), 수정레코드 업데이트  - 사용자가 동일컬럼을 수정하였음을 의미
		//3. 기존값 != null 일때 -> 수정값 == null 이면 -> 기존개수>수정개수, 기존컬럼 삭제(이미지포함) - 사용자가 이미지를 감소등록했음을 의미
		//				                    		    -> 0 == 수정개수, 아무것도안함 - 사용자가 어떠한 수정변화도 주지 않았음을 의미
		
			
		//1번 이미지
		if(resultClass.getImg1() != null){
			
			//1. 수정 변수가 존재하면
			if(request.getFile("optupload1") != null){ 
				
				//기존이미지 삭제
				if(orgCnt > uptCnt && 0 != uptCnt){
					String imgName = resultClass.getImg1().substring(38); //38 이전은 상대경로
					File deleteFile = new File(imgPath+imgName); //파일명 + 경로
					deleteFile.delete();
				}
				//기존이미지 삭제 종료
				
				//새로운데이터 업데이트
				MultipartFile file1 = request.getFile("optupload1"); // 업로드된 원본
				String orgName1 = file1.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
				
				if(orgName1 != ""){ //이미지 파일을 첨부했을 경우
					
					String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
					String fileName = "img_improvementCase_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
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
					sqlMapper.update("ImprovementCase.updateImg1", paramClass);
				}
			}
			//.1종료
			
			
			//2. 수정 변수가 없으면 -> 이미지 수정을 원치않거나 or 이미지를 감소했거나
			if(request.getFile("optupload1") == null){
				
				if(orgCnt > uptCnt && 0 != uptCnt){ //사용자가 이미지를 감소등록했음을 의미
					//기존이미지 삭제
					String imgName = resultClass.getImg1().substring(38); //38 이전은 상대경로
					File deleteFile = new File(imgPath+imgName); //파일명 + 경로
					deleteFile.delete();
					//기존이미지 삭제 종료
					
					//해당 컬럼의 데이터만 제거
					paramClass.setSeq(seq); //최대 시퀀스넘버
					paramClass.setImg1(null); // 이미지경로
					
					//파일 정보 업데이트.
					sqlMapper.update("ImprovementCase.updateImg1", paramClass); // 해당 컬럼을 초기값인 null로 초기화
					
				}else if(0 == uptCnt){ //수정을 원치 않은 경우
					//아무작업안함
				}
			
			}
			//.2종료
			
		}
		//.1번 이미지 종료
		
		//2번 이미지
		if(resultClass.getImg2() != null){
			
			//1. 수정 변수가 존재하면
			if(request.getFile("optupload2") != null){
				
				//기존데이터 삭제
				if(orgCnt > uptCnt && 0 != uptCnt){
					String imgName = resultClass.getImg2().substring(38); //38 이전은 상대경로
					File deleteFile = new File(imgPath+imgName); //파일명 + 경로
					deleteFile.delete();
				}
				//기존데이터 삭제 종료
				
				//새로운데이터 업데이트
				MultipartFile file2 = request.getFile("optupload2"); // 업로드된 원본
				String orgName2 = file2.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
				
				if(orgName2 != ""){ //이미지 파일을 첨부했을 경우
					String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
					String fileName = "img_improvementCase_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
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
					sqlMapper.update("ImprovementCase.updateImg2", paramClass);
				}
			}
			
			//2. 수정 변수가 없으면 -> 이미지 수정을 원치않거나 or 이미지를 감소했거나
			if(request.getFile("optupload2") == null){
				
				if(orgCnt > uptCnt && 0 != uptCnt){ //사용자가 이미지를 감소등록했음을 의미
					//기존이미지 삭제
					String imgName = resultClass.getImg2().substring(38); //38 이전은 상대경로
					File deleteFile = new File(imgPath+imgName); //파일명 + 경로
					deleteFile.delete();
					//기존이미지 삭제 종료
					
					//해당 컬럼의 데이터만 제거
					paramClass.setSeq(seq); //최대 시퀀스넘버
					paramClass.setImg2(null); // 이미지경로
					
					//파일 정보 업데이트.
					sqlMapper.update("ImprovementCase.updateImg2", paramClass); // 해당 컬럼을 초기값인 null로 초기화
					
				}else if(0 == uptCnt){ //수정을 원치 않은 경우
					//아무작업안함
				}
			
			}
			//.2종료
		}
		//.2번 이미지 종료
		
		
		//3번 이미지
		if(resultClass.getImg3() != null){
			
			//1. 수정 변수가 존재하면
			if(request.getFile("optupload3") != null){
				
				//기존데이터 삭제
				if(orgCnt > uptCnt && 0 != uptCnt){
					String imgName = resultClass.getImg3().substring(38); //38 이전은 상대경로
					File deleteFile = new File(imgPath+imgName); //파일명 + 경로
					deleteFile.delete();
				}
				//기존데이터 삭제 종료
				
				//새로운데이터 업데이트
				MultipartFile file3 = request.getFile("optupload3"); // 업로드된 원본
				String orgName3 = file3.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
				
				if(orgName3 != ""){ //이미지 파일을 첨부했을 경우
				
					String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
					String fileName = "img_improvementCase_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
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
					sqlMapper.update("ImprovementCase.updateImg3", paramClass);
				}
			}
			
			//2. 수정 변수가 없으면 -> 이미지 수정을 원치않거나 or 이미지를 감소했거나
			if(request.getFile("optupload3") == null){
				
				if(orgCnt > uptCnt && 0 != uptCnt){ //사용자가 이미지를 감소등록했음을 의미
					//기존이미지 삭제
					String imgName = resultClass.getImg3().substring(38); //38 이전은 상대경로
					File deleteFile = new File(imgPath+imgName); //파일명 + 경로
					deleteFile.delete();
					//기존이미지 삭제 종료
					
					//해당 컬럼의 데이터만 제거
					paramClass.setSeq(seq); //최대 시퀀스넘버
					paramClass.setImg3(null); // 이미지경로
					
					//파일 정보 업데이트.
					sqlMapper.update("ImprovementCase.updateImg3", paramClass); // 해당 컬럼을 초기값인 null로 초기화
					
				}else if(0 == uptCnt){ //수정을 원치 않은 경우
					//아무작업안함
				}
			
			}
			//.2종료
		}
		//.3번 이미지 종료
		
		
		//4번 이미지
		if(resultClass.getImg4() != null){
			
			//1. 수정 변수가 존재하면
			if(request.getFile("optupload4") != null){
	
				//기존데이터 삭제
				if(orgCnt > uptCnt && 0 != uptCnt){
					String imgName = resultClass.getImg4().substring(38); //38 이전은 상대경로
					File deleteFile = new File(imgPath+imgName); //파일명 + 경로
					deleteFile.delete();
				}
				//기존데이터 삭제 종료
				
				//새로운데이터 업데이트
				MultipartFile file4 = request.getFile("optupload4"); // 업로드된 원본
				String orgName4 = file4.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
				
				if(orgName4 != ""){ //이미지 파일을 첨부했을 경우
				
					String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
					String fileName = "img_improvementCase_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
					String fileExt = orgName4.substring(orgName4.lastIndexOf('.'));//서버저장 확장자
					
					File save = new File(file_path+fileName+fileExt); //복사대상 생성 (경로+파일명+확장자)
					file4.transferTo(save);  // 복사본 생성
					
					//이미지파일 상대경로 제작
					String temp = image_path.replace("\\", "/");
					//상대경로 path
					String path = temp+fileName+fileExt; // 상대경로+파일명+확장자
					
					paramClass.setSeq(seq); //최대 시퀀스넘버
					paramClass.setImg4(path); // 이미지경로
					
					//파일 정보 업데이트.
					sqlMapper.update("ImprovementCase.updateImg4", paramClass);
				}
			}
			
			//2. 수정 변수가 없으면 -> 이미지 수정을 원치않거나 or 이미지를 감소했거나
			if(request.getFile("optupload4") == null){
				
				if(orgCnt > uptCnt && 0 != uptCnt){ //사용자가 이미지를 감소등록했음을 의미
					//기존이미지 삭제
					String imgName = resultClass.getImg4().substring(38); //38 이전은 상대경로
					File deleteFile = new File(imgPath+imgName); //파일명 + 경로
					deleteFile.delete();
					//기존이미지 삭제 종료
					
					//해당 컬럼의 데이터만 제거
					paramClass.setSeq(seq); //최대 시퀀스넘버
					paramClass.setImg4(null); // 이미지경로
					
					//파일 정보 업데이트.
					sqlMapper.update("ImprovementCase.updateImg4", paramClass); // 해당 컬럼을 초기값인 null로 초기화
					
				}else if(0 == uptCnt){ //수정을 원치 않은 경우
					//아무작업안함
				}
			
			}
			//.2종료
		}
		//.4번 이미지 종료
		
		
		//5번 이미지
		if(resultClass.getImg5() != null){
			
			//1. 수정 변수가 존재하면
			if(request.getFile("optupload5") != null){
	
				//기존데이터 삭제
				if(orgCnt > uptCnt && 0 != uptCnt){
					String imgName = resultClass.getImg5().substring(38); //38 이전은 상대경로
					File deleteFile = new File(imgPath+imgName); //파일명 + 경로
					deleteFile.delete();
				}
				//기존데이터 삭제 종료
				
				//새로운데이터 업데이트
				MultipartFile file5 = request.getFile("optupload5"); // 업로드된 원본
				String orgName5 = file5.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
				
				if(orgName5 != ""){ //이미지 파일을 첨부했을 경우
				
					String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
					String fileName = "img_improvementCase_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
					String fileExt = orgName5.substring(orgName5.lastIndexOf('.'));//서버저장 확장자
					
					File save = new File(file_path+fileName+fileExt); //복사대상 생성 (경로+파일명+확장자)
					file5.transferTo(save);  // 복사본 생성
					
					//이미지파일 상대경로 제작
					String temp = image_path.replace("\\", "/");
					//상대경로 path
					String path = temp+fileName+fileExt; // 상대경로+파일명+확장자
					
					paramClass.setSeq(seq); //최대 시퀀스넘버
					paramClass.setImg5(path); // 이미지경로
					
					//파일 정보 업데이트.
					sqlMapper.update("ImprovementCase.updateImg5", paramClass);
				}
			}
			
			//2. 수정 변수가 없으면 -> 이미지 수정을 원치않거나 or 이미지를 감소했거나
			if(request.getFile("optupload5") == null){
				
				if(orgCnt > uptCnt && 0 != uptCnt){ //사용자가 이미지를 감소등록했음을 의미
					//기존이미지 삭제
					String imgName = resultClass.getImg5().substring(38); //38 이전은 상대경로
					File deleteFile = new File(imgPath+imgName); //파일명 + 경로
					deleteFile.delete();
					//기존이미지 삭제 종료
					
					//해당 컬럼의 데이터만 제거
					paramClass.setSeq(seq); //최대 시퀀스넘버
					paramClass.setImg5(null); // 이미지경로
					
					//파일 정보 업데이트.
					sqlMapper.update("ImprovementCase.updateImg5", paramClass); // 해당 컬럼을 초기값인 null로 초기화
					
				}else if(0 == uptCnt){ //수정을 원치 않은 경우
					//아무작업안함
				}
			
			}
			//.2종료
		}
		//.5번 이미지 종료
		
		
		//6번 이미지
		if(resultClass.getImg6() != null){
			
			//1. 수정 변수가 존재하면
			if(request.getFile("optupload6") != null){
	
				//기존데이터 삭제
				if(orgCnt > uptCnt && 0 != uptCnt){
					String imgName = resultClass.getImg6().substring(38); //38 이전은 상대경로
					File deleteFile = new File(imgPath+imgName); //파일명 + 경로
					deleteFile.delete();
				}
				//기존데이터 삭제 종료
				
				//새로운데이터 업데이트
				MultipartFile file6 = request.getFile("optupload6"); // 업로드된 원본
				String orgName6 = file6.getOriginalFilename(); // 사용자가 업로드한 실제 파일 이름
				
				if(orgName6 != ""){ //이미지 파일을 첨부했을 경우
				
					String randNum = Integer.toString((int)(Math.random() * 99999));//랜덤번호
					String fileName = "img_improvementCase_"+randNum;//서버저장 파일명(img_improvementCase_랜덤번호)
					String fileExt = orgName6.substring(orgName6.lastIndexOf('.'));//서버저장 확장자
					
					File save = new File(file_path+fileName+fileExt); //복사대상 생성 (경로+파일명+확장자)
					
					file6.transferTo(save);  // 복사본 생성
					
					//이미지파일 상대경로 제작
					String temp = image_path.replace("\\", "/");
					//상대경로 path
					String path = temp+fileName+fileExt; // 상대경로+파일명+확장자
					
					paramClass.setSeq(seq); //최대 시퀀스넘버
					paramClass.setImg6(path); // 이미지경로
					
					//파일 정보 업데이트.
					sqlMapper.update("ImprovementCase.updateImg6", paramClass);
				}
			}
			
			//2. 수정 변수가 없으면 -> 이미지 수정을 원치않거나 or 이미지를 감소했거나
			if(request.getFile("optupload6") == null){
				
				if(orgCnt > uptCnt && 0 != uptCnt){ //사용자가 이미지를 감소등록했음을 의미
					//기존이미지 삭제
					String imgName = resultClass.getImg6().substring(38); //38 이전은 상대경로
					File deleteFile = new File(imgPath+imgName); //파일명 + 경로
					deleteFile.delete();
					//기존이미지 삭제 종료
					
					//해당 컬럼의 데이터만 제거
					paramClass.setSeq(seq); //최대 시퀀스넘버
					paramClass.setImg6(null); // 이미지경로
					
					//파일 정보 업데이트.
					sqlMapper.update("ImprovementCase.updateImg6", paramClass); // 해당 컬럼을 초기값인 null로 초기화
					
				}else if(0 == uptCnt){ //수정을 원치 않은 경우
					//아무작업안함
				}
			
			}
			//.2종료
		}
		//.6번 이미지 종료
		//.2~3번 시나리오 종료
			
		
		//.이미지첨부 수정 시나리오 종료
		
		
		return "redirect:/improvementCaseView.do?seq="+seq+"&currentPage="+currentPage+"&searchingNow="+searchingNow; // 호출한 뷰페이지로 리다이렉트
	}
	
}
