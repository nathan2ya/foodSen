package menu7.trainingEvent.controller;

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

import menu7.trainingEvent.dto.TrainingEventDTO;

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


@Controller
public class TrainingEvent {
	
	private List<TrainingEventDTO> list = new ArrayList<TrainingEventDTO>();
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public TrainingEvent() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	
	//연수행사 달력 리스트
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
	
	
}
