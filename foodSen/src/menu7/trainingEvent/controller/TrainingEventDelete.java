package menu7.trainingEvent.controller;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import menu7.trainingEvent.dto.TrainingEventDTO;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import common.Constants;

/*
 * �ۼ���: �����
 * ��  ��: ������� delete Ŭ����
 * ��  ��: ������� �Խñ� ������ ����.
*/

@Controller
public class TrainingEventDelete {
	
	//������ ���ڵ��� ���ϻ����� ���� DTO
	private TrainingEventDTO resultClass = new TrainingEventDTO();
	
	//������ ���ڵ� �̹����� ������
	private String imgPath = Constants.COMMON_FILE_PATH + Constants.MENU7_TRAININGEVENT_IMAGE_PATH;
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	
	//DBĿ��Ʈ ������
	public TrainingEventDelete() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	//-------------------------------------------------------------------------------------------------------------------------//
	
	
	//�������(����) ������
	@RequestMapping("/trainingEventDeleteForm.do")
	public String trainingEventDeleteForm(HttpServletRequest request) throws SQLException{
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pw = Integer.parseInt(request.getParameter("pw"));
		
		
		request.setAttribute("seq", seq);
		request.setAttribute("pw", pw);
		
		return "/view/menu7/trainingEvent/trainingEventDelete.jsp"; // ����Ʈ�� �����̷�Ʈ
	}
	
	//�������(����) ����
	@RequestMapping("/trainingEventDelete.do")
	public String trainingEventDelete(HttpServletRequest request) throws SQLException{
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		
		//���ϻ����� ���� ���� //���ϻ����� ���� ���ε�������� ��θ� �������
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		
		//���ϻ���
			if(resultClass.getAttach_path()!=null){
				File deleteFile = new File(resultClass.getAttach_path()+resultClass.getAttach_name());
				deleteFile.delete();
			}
		//.���ϻ��� �Ϸ�
			
			
		//���ڵ� ����
		sqlMapper.delete("TrainingEvent.deleteTrainingEvent", seq);
		
		
		return "redirect:/TrainingEventList.do"; // ����Ʈ�� �����̷�Ʈ
	}
	
	
	
	
	//-------------------------------------------------------------------------------------------------------------------------//
	
	
	
	
	//�������(���) ������
	@RequestMapping("/trainingEventRepDeleteForm.do")
	public String trainingEventRepDeleteForm(HttpServletRequest request) throws SQLException{
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		int pw = Integer.parseInt(request.getParameter("pw"));
		
		
		request.setAttribute("seq", seq);
		request.setAttribute("pw", pw);
		
		return "/view/menu7/trainingEvent/trainingEventRepDelete.jsp";
	}
	
	
	//�������(���) ����
	@RequestMapping("/trainingEventRepDelete.do")
	public String trainingEventRepDelete(HttpServletRequest request) throws SQLException{
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		
		//���ϻ����� ���� ���� //���ϻ����� ���� ���ε�������� ��θ� �������
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		//�θ��� turn ���� �ٽ� 0���� update ��Ų��.
		int up_seq = resultClass.getUp_seq(); // �� ����� up_seq �� �θ��� seq�� �����Ƿ� �����.
		sqlMapper.update("TrainingEvent.updateParentTurnDown", up_seq); // �ش� ������ �ѹ��� ���ڵ��� turn ���� 0���� ������Ʈ �Ѵ�.
		
		
		//���ϻ���
			if(resultClass.getAttach_path()!=null){
				File deleteFile = new File(resultClass.getAttach_path()+resultClass.getAttach_name());
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
		//.�̹������� ����
			
			
		
		//���ڵ� ����
		sqlMapper.delete("TrainingEvent.deleteTrainingEvent", seq);
		
		
		return "redirect:/TrainingEventList.do"; // ����Ʈ�� �����̷�Ʈ
	}
	
	
}

