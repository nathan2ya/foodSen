package menu2.inspectionResult.controller;


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

import menu2.inspectionResult.dto.InspectionResultDTO;

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


@Controller
public class InspectionResultView {
	
	//�������� ���ڵ�
	private InspectionResultDTO resultClass = new InspectionResultDTO();
	
	
	private int currentPage;
	private int searchingNow; // ��ü��, �˻����� �Ǵ��Ͽ� ���� ������ �ǰ����� ����
	
	//�˻����� ��쿡�� �߻��ϴ� ����(it's for uri)
	private String searchType;
	private String userinput;
	
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public InspectionResultView() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	
	//���� ������ �˻��� ��������
	@RequestMapping("/inspectionResultView.do")
	public String inspectionResultView(HttpServletRequest request1, HttpServletResponse response1, HttpSession session) throws SQLException{
		
		int seq = Integer.parseInt(request1.getParameter("seq"));
		if(null == request1.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request1.getParameter("currentPage"));
		}
		searchingNow = Integer.parseInt(request1.getParameter("searchingNow"));
		
		//�˻��� ��� �����ϴ� ���� �ʱ�ȭ
		if(searchingNow==1){
			searchType = request1.getParameter("searchType");
			userinput = request1.getParameter("userinput");
			
			//��� �Ʒ��� ���� 3���� ������ ������
			//����� Ŭ������ �� �ٽ� �˻����� ����Ʈ�� ���ư��� �����̴�. +���� currentPage�� �Բ�!
			request1.setAttribute("searchType", searchType);
			request1.setAttribute("userinput", userinput);
			request1.setAttribute("searchingNow", 1);
		}else{
			
			//�˻����� �ƴѰ�쿡�� �Ʒ��� �������� �������� ������ �ǹ��ϴ� 0�� �ʱ�ȭ ���Ѽ� �ش� jsp �� ������.
			request1.setAttribute("searchType", 0);
			request1.setAttribute("userinput", 0);
			request1.setAttribute("searchingNow", 0);
		}
		
		
		//���������� ������ ���ڵ� 1���� get�Ѵ�.
		resultClass = (InspectionResultDTO)sqlMapper.queryForObject("InspectionResult.selectInspectionResultOne", seq);
		
		
		
		request1.setAttribute("seq", seq);
		request1.setAttribute("currentPage", currentPage);
		request1.setAttribute("searchingNow", searchingNow);
		request1.setAttribute("resultClass", resultClass); //���ڵ�1��
		
		
		return "/view/menu2/inspectionResultView.jsp";
	}
	
}
