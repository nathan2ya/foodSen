package menu2.inspectionResult.controller;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import menu2.inspectionResult.dto.InspectionResultDTO;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/*
 * 작성자: 류재욱
 * 설  명: 위생.안전성검사결과 delete 클래스
 * 용  도: 위생.안전성검사결과 게시글 삭제를 위함.
*/

@Controller
public class InspectionResultDelete {
	
	//위생.안전성검사결과 delete
	private InspectionResultDTO resultClass = new InspectionResultDTO(); //삭제될 레코드의 파일삭제를 위한 DTO
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public InspectionResultDelete() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//위생.안전 검사결과 삭제 form
	@RequestMapping("/inspectionResultDeleteFrom.do")
	public String inspectionResultDeleteForm(HttpServletRequest request) throws SQLException{
		
		//삭제요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pw = Integer.parseInt(request.getParameter("pw"));
		
		request.setAttribute("seq", seq);
		request.setAttribute("pw", pw);
		return "/view/menu2/inspectionResultDelete.jsp";
	}
	
	//위생.안전 검사결과 삭제 DB delete
	@RequestMapping("/inspectionResultDelete.do")
	public String inspectionResultDelete(HttpServletRequest request) throws SQLException{
		
		//삭제요청한 뷰 정보
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		//파일삭제를 위해 생성 //파일삭제시 기존 업로드된파일의 경로를 얻기위함
		resultClass = (InspectionResultDTO)sqlMapper.queryForObject("InspectionResult.selectInspectionResultOne", seq);
		
		//파일삭제
			if(resultClass.getAttach_path()!=null){
				File deleteFile = new File(resultClass.getAttach_path());
				deleteFile.delete();
			}
		//.파일삭제 완료
			
		//레코드 삭제
		sqlMapper.delete("InspectionResult.deleteInspectionResult", seq);
		
		return "redirect:/inspectionResultList.do"; // 리스트로 리다이렉트
	}
	
} //end of class
