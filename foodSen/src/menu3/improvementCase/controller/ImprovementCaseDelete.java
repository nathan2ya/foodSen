package menu3.improvementCase.controller;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import menu3.improvementCase.dto.ImprovementCaseDTO;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import common.Constants;


@Controller
public class ImprovementCaseDelete {
	
	//������ ���ڵ��� ���ϻ����� ���� DTO
	private ImprovementCaseDTO resultClass = new ImprovementCaseDTO();
	
	//������ ���ڵ� �̹����� ������
	private String imgPath = Constants.COMMON_FILE_PATH + Constants.MENU3_IMPROVEMENT_FILE_PATH;
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public ImprovementCaseDelete() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	
	
	//�޽Ľü�������� ������
	@RequestMapping("/improvementCaseDeleteForm.do")
	public String improvementCaseDeleteForm(HttpServletRequest request) throws SQLException{
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pw = Integer.parseInt(request.getParameter("pw"));
		
		
		request.setAttribute("seq", seq);
		request.setAttribute("pw", pw);
		
		return "/view/menu3/improvementCaseDelete.jsp"; // ����Ʈ�� �����̷�Ʈ
	}
	
	
	
	//�޽Ľü�������� ����
	@RequestMapping("/improvementCaseDelete.do")
	public String improvementCaseDelete(HttpServletRequest request) throws SQLException{
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		
		//���ϻ����� ���� ���� //���ϻ����� ���� ���ε�������� ��θ� �������
		resultClass = (ImprovementCaseDTO)sqlMapper.queryForObject("ImprovementCase.selectImprovementCaseOne", seq);
		
		
		//���ϻ���
			if(resultClass.getAttach_path()!=null){
				File deleteFile = new File(resultClass.getAttach_path());
				deleteFile.delete();
			}
		//.���ϻ��� �Ϸ�
			
			
		//�̹��� ����
			if(resultClass.getImg1() != null){
				String imgName = resultClass.getImg1().substring(38); //38 ������ �����
				
				File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
				deleteFile.delete();
			}
			
			if(resultClass.getImg2() != null){
				String imgName = resultClass.getImg2().substring(38); //38 ������ �����
				
				File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
				deleteFile.delete();
			}
			
			if(resultClass.getImg3() != null){
				String imgName = resultClass.getImg3().substring(38); //38 ������ �����
				
				File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
				deleteFile.delete();
			}
			
			if(resultClass.getImg4() != null){
				String imgName = resultClass.getImg4().substring(38); //38 ������ �����
				
				File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
				deleteFile.delete();
			}
			
			if(resultClass.getImg5() != null){
				String imgName = resultClass.getImg5().substring(38); //38 ������ �����
				
				File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
				deleteFile.delete();
			}
			
			if(resultClass.getImg6() != null){
				String imgName = resultClass.getImg6().substring(38); //38 ������ �����
				
				File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
				deleteFile.delete();
			}
		//.�̹������� ����
			
			
		//���ڵ� ����
		sqlMapper.delete("ImprovementCase.deleteImprovementCase", seq);
		
		
		return "redirect:/improvementCaseList.do"; // ����Ʈ�� �����̷�Ʈ
	}
	
}

