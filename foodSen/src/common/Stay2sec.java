package common;


import java.io.IOException;
import java.io.Reader;

import javax.servlet.http.HttpServletRequest;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;


@Controller
public class Stay2sec {
	
	private String service;
	private String stay2sec_path;
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public Stay2sec() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	//삭제대기 리다이렉트2초
	@RequestMapping("/goStay2sec.do")
	public String goImprovementCaseList(HttpServletRequest request){
		
		service = request.getParameter("service");
		
		//호출측에 따른 뷰페이지 정의
		if(service.equals("inspectionResult")){
			stay2sec_path = "/view/menu2/stay2sec.jsp";
		}
		
		if(service.equals("improvement")){
			stay2sec_path = "/view/menu3/stay2sec.jsp";
		}
		
		if(service.equals("recruit")){
			stay2sec_path = "/view/menu6/recruit_application/stay2sec_r.jsp";
		}
		
		
		
		return stay2sec_path;
	}
}


