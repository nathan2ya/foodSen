package menu7.trainingEvent.controller;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import menu7.trainingEvent.dto.TrainingEventDTO;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import common.Constants;

/*
 * 작성자: 류재욱
 * 설  명: 연수행사 delete 클래스
 * 용  도: 연수행사 게시글 삭제를 위함.
*/

@Controller
public class TrainingEventDelete {
	
	//삭제될 레코드의 파일삭제를 위한 DTO
	private TrainingEventDTO resultClass = new TrainingEventDTO();
	
	//삭제될 레코드 이미지의 절대경로
	private String imgPath = Constants.COMMON_FILE_PATH + Constants.MENU7_TRAININGEVENT_IMAGE_PATH;
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	
	//DB커넥트 생성자
	public TrainingEventDelete() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	//-------------------------------------------------------------------------------------------------------------------------//
	
	
	//연수행사(새글) 삭제폼
	@RequestMapping("/trainingEventDeleteForm.do")
	public String trainingEventDeleteForm(HttpServletRequest request) throws SQLException{
		
		//삭제요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pw = Integer.parseInt(request.getParameter("pw"));
		
		
		request.setAttribute("seq", seq);
		request.setAttribute("pw", pw);
		
		return "/view/menu7/trainingEvent/trainingEventDelete.jsp"; // 리스트로 리다이렉트
	}
	
	//연수행사(새글) 삭제
	@RequestMapping("/trainingEventDelete.do")
	public String trainingEventDelete(HttpServletRequest request) throws SQLException{
		
		//삭제요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		
		//파일삭제를 위해 생성 //파일삭제시 기존 업로드된파일의 경로를 얻기위함
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		
		//파일삭제
			if(resultClass.getAttach_path()!=null){
				File deleteFile = new File(resultClass.getAttach_path()+resultClass.getAttach_name());
				deleteFile.delete();
			}
		//.파일삭제 완료
			
			
		//레코드 삭제
		sqlMapper.delete("TrainingEvent.deleteTrainingEvent", seq);
		
		
		return "redirect:/TrainingEventList.do"; // 리스트로 리다이렉트
	}
	
	
	
	
	//-------------------------------------------------------------------------------------------------------------------------//
	
	
	
	
	//연수행사(답글) 삭제폼
	@RequestMapping("/trainingEventRepDeleteForm.do")
	public String trainingEventRepDeleteForm(HttpServletRequest request) throws SQLException{
		
		//삭제요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pw = Integer.parseInt(request.getParameter("pw"));
		
		
		request.setAttribute("seq", seq);
		request.setAttribute("pw", pw);
		
		return "/view/menu7/trainingEvent/trainingEventRepDelete.jsp";
	}
	
	
	//연수행사(답글) 삭제
	@RequestMapping("/trainingEventRepDelete.do")
	public String trainingEventRepDelete(HttpServletRequest request) throws SQLException{
		
		//삭제요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		
		//파일삭제를 위해 생성 //파일삭제시 기존 업로드된파일의 경로를 얻기위함
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		//부모의 turn 값을 다시 0으로 update 시킨다.
		int up_seq = resultClass.getUp_seq(); // 이 답글의 up_seq 는 부모의 seq와 같으므로 얻었다.
		sqlMapper.update("TrainingEvent.updateParentTurnDown", up_seq); // 해당 시퀀스 넘버의 레코드의 turn 값을 0으로 업데이트 한다.
		
		
		//파일삭제
			if(resultClass.getAttach_path()!=null){
				File deleteFile = new File(resultClass.getAttach_path()+resultClass.getAttach_name());
				deleteFile.delete();
			}
		//.파일삭제 완료
		
		
		//이미지 삭제
			if(resultClass.getImg1() != null){
				String imgName = resultClass.getImg1().substring(38); //38 이전은 상대경로
				
				File deleteFile = new File(imgPath+imgName); //파일명 + 경로
				deleteFile.delete();
			}
			
			if(resultClass.getImg2() != null){
				String imgName = resultClass.getImg2().substring(38); //38 이전은 상대경로
				
				File deleteFile = new File(imgPath+imgName); //파일명 + 경로
				deleteFile.delete();
			}
			
			if(resultClass.getImg3() != null){
				String imgName = resultClass.getImg3().substring(38); //38 이전은 상대경로
				
				File deleteFile = new File(imgPath+imgName); //파일명 + 경로
				deleteFile.delete();
			}
		//.이미지삭제 종료
			
			
		
		//레코드 삭제
		sqlMapper.delete("TrainingEvent.deleteTrainingEvent", seq);
		
		
		return "redirect:/TrainingEventList.do"; // 리스트로 리다이렉트
	}
	
	
}

