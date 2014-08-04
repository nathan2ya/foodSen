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

/*
 * 작성자: 류재욱
 * 설  명: foodSen 프로젝트에서 2초대기 페이지로 return 하는 클래스.
 * 용  도: foodSen 프로젝트에서 DB실행 및 완료 보다 페이지이동이 더 빠른 경우 2초간 대기시키기 위함.
*/

@Controller
public class Stay2sec {
	
	//호출측과 리턴경로 정의
	private String service; // 호출측 uri
	private String stay2sec_path; // return 경로
	
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
	
	
	//리다이렉트2초 페이지
	@RequestMapping("/goStay2sec.do")
	public String goImprovementCaseList(HttpServletRequest request){
		
		//호출 uri 초기화
		service = request.getParameter("service");
		
		/*
		 * 호출측의 페이지 마다 리턴경로가 다름.
		 * jsp측 리턴경로는 해당메뉴의 list 로 정의됨.
		*/
		if(service.equals("inspectionResult")){
			stay2sec_path = "/view/menu2/stay2sec.jsp";
		}
		if(service.equals("improvement")){
			stay2sec_path = "/view/menu3/stay2sec.jsp";
		}
		if(service.equals("recruit")){
			stay2sec_path = "/view/menu6/recruit_application/stay2sec_r.jsp";
		}
		if(service.equals("application")){
			stay2sec_path = "/view/menu6/recruit_application/stay2sec_a.jsp";
		}
		if(service.equals("trainingEvent")){
			stay2sec_path = "/view/menu7/trainingEvent/stay2sec.jsp";
		}
		//.호출측에 따른 뷰페이지 정의 종료
		
		return stay2sec_path;
	}
}


