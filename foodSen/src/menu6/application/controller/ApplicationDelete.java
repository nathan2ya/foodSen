package menu6.application.controller;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import menu6.application.dto.ApplicationDTO;

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

/*
 * �ۼ���: �����
 * ��  ��: �б��޽��η�Ǯ(����) delete Ŭ����
 * ��  ��: �б��޽��η�Ǯ(����) �Խñ� ������ ����.
*/

@Controller
public class ApplicationDelete{
	//������ ���ڵ��� ���ϻ����� ���� DTO
	private ApplicationDTO resultClass = new ApplicationDTO();
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public ApplicationDelete() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	
	//�б��޽��η�Ǯ(����) ������
	@RequestMapping("/applicationDeleteFrom.do")
	public String applicationDeleteFrom(HttpServletRequest request) throws SQLException{
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pw = Integer.parseInt(request.getParameter("pw"));
		
		
		request.setAttribute("seq", seq);
		request.setAttribute("pw", pw);
		
		return "/view/menu6/recruit_application/applicationDelete.jsp";
	}
	
	
	
	//�б��޽��η�Ǯ(����) ����
	@RequestMapping("/applicationDelete.do")
	public String applicationDelete(HttpServletRequest request) throws SQLException{
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		
		//���ϻ����� ���� ���� //���ϻ����� ���� ���ε�������� ��θ� �������
		resultClass = (ApplicationDTO)sqlMapper.queryForObject("Application.selectApplicationOne", seq);
		
		
		//���ϻ���
			if(resultClass.getAttach_path()!=null){
				File deleteFile = new File(resultClass.getAttach_path());
				deleteFile.delete();
			}
		//.���ϻ��� �Ϸ�
			
		//���ڵ� ����
		sqlMapper.delete("Application.deleteApplication", seq);
		
		
		return "redirect:/applicationList.do"; // ����Ʈ�� �����̷�Ʈ
	}
}

