package menu2.inspectionResult.controller;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import menu2.inspectionResult.dto.InspectionResultDTO;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/*
 * �ۼ���: �����
 * ��  ��: ����.�������˻��� delete Ŭ����
 * ��  ��: ����.�������˻��� �Խñ� ������ ����.
*/

@Controller
public class InspectionResultDelete {
	
	//����.�������˻��� delete
	private InspectionResultDTO resultClass = new InspectionResultDTO(); //������ ���ڵ��� ���ϻ����� ���� DTO
	
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
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//����.���� �˻��� ���� form
	@RequestMapping("/inspectionResultDeleteFrom.do")
	public String inspectionResultDeleteForm(HttpServletRequest request) throws SQLException{
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pw = Integer.parseInt(request.getParameter("pw"));
		
		request.setAttribute("seq", seq);
		request.setAttribute("pw", pw);
		return "/view/menu2/inspectionResultDelete.jsp";
	}
	
	//����.���� �˻��� ���� DB delete
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
	
} //end of class
