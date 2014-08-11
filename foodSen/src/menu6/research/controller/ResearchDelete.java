package menu6.research.controller;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import menu6.research.dto.ResearchDTO;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/*
 * 작성자: 류재욱
 * 설  명: 설문조사 delete 클래스
 * 용  도: 설문조사 게시글 리스트를 보여주기 위함.
*/

@Controller
public class ResearchDelete {
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public ResearchDelete() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//설문조사 삭제
	@RequestMapping("/researchDelete.do")
	public String researchList(HttpServletRequest request) throws SQLException{
		int sur_seq = Integer.parseInt(request.getParameter("sur_seq"));
		
		//설문조사(정보) 삭제
		sqlMapper.delete("Research.deleteResearch", sur_seq);
		
		//설문조사(문제) 삭제
		sqlMapper.delete("Research.deleteResearch1", sur_seq);
		
		//설문조사(문항) 삭제
		sqlMapper.delete("Research.deleteResearch2", sur_seq);
		
		//설문조사(결과) 삭제
		sqlMapper.delete("Research.deleteResearch3", sur_seq);
		
		return "redirect:/researchList.do"; //리스트로 리다이렉트
	}
	
} //end of class
