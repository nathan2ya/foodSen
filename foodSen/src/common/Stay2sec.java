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
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public Stay2sec() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	//������� �����̷�Ʈ2��
	@RequestMapping("/goStay2sec.do")
	public String goImprovementCaseList(HttpServletRequest request){
		
		service = request.getParameter("service");
		
		//ȣ������ ���� �������� ����
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

