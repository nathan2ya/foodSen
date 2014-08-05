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
 * �ۼ���: �����
 * ��  ��: �޽Ľü� ������� popup Ŭ����
 * ��  ��: �޽Ľü� ������� �Խñ� �˾��� ����.
*/

@Controller
public class ImprovementCasePopup {
	
	//�̹�����θ� �ҷ����� ���� DTO
	private ImprovementCaseDTO resultClass = new ImprovementCaseDTO();
	
	//�̹��� ����
	private int cnt;
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public ImprovementCasePopup() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	
	//�̹��� �˾�â ����
	@RequestMapping("/improvementCasePrevImg.do")
	public String improvementCasePrevImg(HttpServletRequest request) throws SQLException{
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		//�̹�����ο� ������ �ľ��ϱ� ���ؼ� get��
		resultClass = (ImprovementCaseDTO)sqlMapper.queryForObject("ImprovementCase.selectImprovementCaseOne", seq);
		
		cnt = 0; //��ȣ�� �ɶ� �ٽ� �ʱ�ȭ ���� ī��Ʈ �ϱ� ����.
		
		//�̹��� ������ �ľ�
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
		//.�̹��� ������ �ľ� ����
		
		
		System.out.println("Popup���� ȣ�� // �̹������� : "+cnt);
		
		
		request.setAttribute("cnt", cnt); // ��ü,�˻����� �Ǵ���
		request.setAttribute("resultClass", resultClass); //�̹������ ����
		
		return "/view/menu3/improvementCasePopup.jsp";
	}
	
	
}
