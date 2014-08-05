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

/*
 * 작성자: 류재욱
 * 설  명: 학교급식인력풀(구직) delete 클래스
 * 용  도: 학교급식인력풀(구직) 게시글 삭제를 위함.
*/

@Controller
public class ApplicationDelete{
	//삭제될 레코드의 파일삭제를 위한 DTO
	private ApplicationDTO resultClass = new ApplicationDTO();
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public ApplicationDelete() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	
	//학교급식인력풀(구직) 삭제폼
	@RequestMapping("/applicationDeleteFrom.do")
	public String applicationDeleteFrom(HttpServletRequest request) throws SQLException{
		
		//삭제요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pw = Integer.parseInt(request.getParameter("pw"));
		
		
		request.setAttribute("seq", seq);
		request.setAttribute("pw", pw);
		
		return "/view/menu6/recruit_application/applicationDelete.jsp";
	}
	
	
	
	//학교급식인력풀(구직) 삭제
	@RequestMapping("/applicationDelete.do")
	public String applicationDelete(HttpServletRequest request) throws SQLException{
		
		//삭제요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		
		//파일삭제를 위해 생성 //파일삭제시 기존 업로드된파일의 경로를 얻기위함
		resultClass = (ApplicationDTO)sqlMapper.queryForObject("Application.selectApplicationOne", seq);
		
		
		//파일삭제
			if(resultClass.getAttach_path()!=null){
				File deleteFile = new File(resultClass.getAttach_path());
				deleteFile.delete();
			}
		//.파일삭제 완료
			
		//레코드 삭제
		sqlMapper.delete("Application.deleteApplication", seq);
		
		
		return "redirect:/applicationList.do"; // 리스트로 리다이렉트
	}
}

