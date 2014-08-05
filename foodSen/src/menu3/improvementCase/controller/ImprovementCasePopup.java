package menu3.improvementCase.controller;


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

import common.PagingAction;

/*
 * 작성자: 류재욱
 * 설  명: 급식시설 개선사례 popup 클래스
 * 용  도: 급식시설 개선사례 게시글 팝업을 위함.
*/

@Controller
public class ImprovementCasePopup {
	
	//이미지경로를 불러오기 위한 DTO
	private ImprovementCaseDTO resultClass = new ImprovementCaseDTO();
	
	//이미지 개수
	private int cnt;
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public ImprovementCasePopup() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	
	//이미지 팝업창 띄우기
	@RequestMapping("/improvementCasePrevImg.do")
	public String improvementCasePrevImg(HttpServletRequest request) throws SQLException{
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		//이미지경로와 개수를 파악하기 위해서 get함
		resultClass = (ImprovementCaseDTO)sqlMapper.queryForObject("ImprovementCase.selectImprovementCaseOne", seq);
		
		cnt = 0; //재호출 될때 다시 초기화 시켜 카운트 하기 위함.
		
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
		
		
		System.out.println("Popup에서 호출 // 이미지개수 : "+cnt);
		
		
		request.setAttribute("cnt", cnt); // 전체,검색글을 판단함
		request.setAttribute("resultClass", resultClass); //이미지경로 정보
		
		return "/view/menu3/improvementCasePopup.jsp";
	}
	
	
}
