package menu3.improvementCase.controller;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import menu3.improvementCase.dto.ImprovementCaseDTO;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import common.Constants;


@Controller
public class ImprovementCaseDelete {
	
	//삭제될 레코드의 파일삭제를 위한 DTO
	private ImprovementCaseDTO resultClass = new ImprovementCaseDTO();
	
	//삭제될 레코드 이미지의 절대경로
	private String imgPath = Constants.COMMON_FILE_PATH + Constants.MENU3_IMPROVEMENT_FILE_PATH;
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public ImprovementCaseDelete() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	
	
	//급식시설개선사례 삭제폼
	@RequestMapping("/improvementCaseDeleteForm.do")
	public String improvementCaseDeleteForm(HttpServletRequest request) throws SQLException{
		
		//삭제요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pw = Integer.parseInt(request.getParameter("pw"));
		
		
		request.setAttribute("seq", seq);
		request.setAttribute("pw", pw);
		
		return "/view/menu3/improvementCaseDelete.jsp"; // 리스트로 리다이렉트
	}
	
	
	
	//급식시설개선사례 삭제
	@RequestMapping("/improvementCaseDelete.do")
	public String improvementCaseDelete(HttpServletRequest request) throws SQLException{
		
		//삭제요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		
		//파일삭제를 위해 생성 //파일삭제시 기존 업로드된파일의 경로를 얻기위함
		resultClass = (ImprovementCaseDTO)sqlMapper.queryForObject("ImprovementCase.selectImprovementCaseOne", seq);
		
		
		//파일삭제
			if(resultClass.getAttach_path()!=null){
				File deleteFile = new File(resultClass.getAttach_path());
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
			
			if(resultClass.getImg4() != null){
				String imgName = resultClass.getImg4().substring(38); //38 이전은 상대경로
				
				File deleteFile = new File(imgPath+imgName); //파일명 + 경로
				deleteFile.delete();
			}
			
			if(resultClass.getImg5() != null){
				String imgName = resultClass.getImg5().substring(38); //38 이전은 상대경로
				
				File deleteFile = new File(imgPath+imgName); //파일명 + 경로
				deleteFile.delete();
			}
			
			if(resultClass.getImg6() != null){
				String imgName = resultClass.getImg6().substring(38); //38 이전은 상대경로
				
				File deleteFile = new File(imgPath+imgName); //파일명 + 경로
				deleteFile.delete();
			}
		//.이미지삭제 종료
			
			
		//레코드 삭제
		sqlMapper.delete("ImprovementCase.deleteImprovementCase", seq);
		
		
		return "redirect:/improvementCaseList.do"; // 리스트로 리다이렉트
	}
	
}

