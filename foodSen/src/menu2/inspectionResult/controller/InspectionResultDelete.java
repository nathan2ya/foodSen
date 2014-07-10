package menu2.inspectionResult.controller;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import menu2.inspectionResult.dto.InspectionResultDTO;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;




import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import common.Constants;


@Controller
public class InspectionResultDelete {
	
	//������ ���ڵ��� ���ϻ����� ���� DTO
	private InspectionResultDTO resultClass = new InspectionResultDTO();
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public InspectionResultDelete() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	
	
	//����.���� �˻��� ������
	@RequestMapping("/inspectionResultDeleteFrom.do")
	public String inspectionResultDeleteForm(HttpServletRequest request) throws SQLException{
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pw = Integer.parseInt(request.getParameter("pw"));
		
		
		request.setAttribute("seq", seq);
		request.setAttribute("pw", pw);
		
		return "/view/menu2/inspectionResultDelete.jsp"; // ����Ʈ�� �����̷�Ʈ
	}
	
	
	
	//����.���� �˻��� ����
	@RequestMapping("/inspectionResultDelete.do")
	public String inspectionResultDelete(HttpServletRequest request) throws SQLException{
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		
		//���ϻ����� ���� ���� //���ϻ����� ���� ���ε�������� ��θ� �������
		resultClass = (InspectionResultDTO)sqlMapper.queryForObject("InspectionResult.selectInspectionResultOne", seq);
		
		
		//���ϻ���
			if(resultClass.getAttach_path()!=null){
				File deleteFile = new File(resultClass.getAttach_path());
				deleteFile.delete();
			}
		//.���ϻ��� �Ϸ�
			
		//���ڵ� ����
		sqlMapper.delete("InspectionResult.deleteInspectionResult", seq);
		
		
		return "redirect:/inspectionResultList.do"; // ����Ʈ�� �����̷�Ʈ
	}
	
}

