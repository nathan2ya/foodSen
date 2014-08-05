package menu7.trainingEvent.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import menu7.trainingEvent.dto.TrainingEventDTO;
import net.sf.json.JSONObject;

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
 * �ۼ���: �����
 * ��  ��: ������� ����Ʈ1 Ŭ����
 * ��  ��: ������� �޷¿� ����Ǵ� ������ ����ϱ� ����.
*/

@Controller
public class TrainingEvent {
	
	private List<TrainingEventDTO> list = new ArrayList<TrainingEventDTO>();
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public TrainingEvent() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	
	//������� �޷� ����Ʈ
	@RequestMapping("/TrainingEvent.do")
	public String TrainingEvent(HttpServletRequest request) throws SQLException{
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String currentTime = sdf.format(cal.getTime());
		
		Integer count = (Integer) sqlMapper.queryForObject("TrainingEvent.trainingEventCount", currentTime);
		list = sqlMapper.queryForList("TrainingEvent.selectTrainingEvent", currentTime);
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		
		return "/view/menu7/trainingEvent/trainingEvent.jsp";
	}
	
	//���� �޷� ����Ʈ//���� �޷� ����Ʈ
	@RequestMapping("/trainingEventMonth.do")
	public void trainingEventMonth(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException{
		response.setCharacterEncoding("utf-8");
		PrintWriter write = response.getWriter();
		
		String year = request.getParameter("year");
		String month =  request.getParameter("month");
		
		if(Integer.parseInt(month) <10){
			month = "0"+month;
		}
		
		String currentTime = year+month;
		
		Integer count = (Integer) sqlMapper.queryForObject("TrainingEvent.trainingEventCount", currentTime);
		list = sqlMapper.queryForList("TrainingEvent.selectTrainingEvent", currentTime);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", list);
		

		JSONObject json = new JSONObject();
		json = JSONObject.fromObject(map);

		write.print(json);
		write.close();
		
	}
	
}
