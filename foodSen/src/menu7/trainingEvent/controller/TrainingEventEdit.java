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

import common.Constants;

/*
 * �ۼ���: �����
 * ��  ��: ������� update Ŭ����
 * ��  ��: ������� �Խñ� ������ ����.
*/

@Controller
public class TrainingEventEdit {
	
	//������ ���ڵ� �������� DTO
	private TrainingEventDTO resultClass = new TrainingEventDTO();
	//���� ���� ���ڵ�
	private TrainingEventDTO paramClass = new TrainingEventDTO();
	
	//���ε� ���� ���
	String file_path = Constants.COMMON_FILE_PATH + Constants.MENU7_TRAININGEVENT_FILE_PATH;
	
	//���ε� �̹��� ���� ���
	private String image_path = Constants.MENU7_TRAININGEVENT_IMAGE_PATH;
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public TrainingEventEdit() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	
	//�������(����) ������
	@RequestMapping("/trainingEventEditFrom.do")
	public String trainingEventEditFrom(HttpServletRequest request) throws SQLException{
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		int permit = Integer.parseInt(request.getParameter("permit"));
		
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		//���糯¥
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentTime = sdf.format(cal.getTime());
		//.���糯¥
		
		request.setAttribute("currentTime", currentTime);
		request.setAttribute("seq", seq);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("searchingNow", searchingNow);
		request.setAttribute("resultClass", resultClass);
		request.setAttribute("permit", permit);
		
		return "/view/menu7/trainingEvent/trainingEventEdit.jsp";
	}
	
	
	//�������(����) ����
	@RequestMapping(value="/trainingEventEdit.do",method=RequestMethod.POST)
	public String trainingEventEdit(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session) throws Exception{
		
		//�ۼ��� �����(���� �α����� ���Ǿ��̵�)
		String session_id = (String) session.getAttribute("session_id");
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		String permit = request.getParameter("permit");
				
		//����ڰ� �Է��� ��
		String description = request1.getParameter("description");
		Calendar today = Calendar.getInstance();
		
		//DTO Set()
		paramClass.setSeq(seq);
		paramClass.setDescription(description); //��������
		paramClass.setUdt_name(session_id); //������
		paramClass.setUdt_date(today.getTime()); //������
		
		
		if(permit.equals("0")){ //����,�������� ��������
			String str_date = request1.getParameter("str_date");
			String end_date = request1.getParameter("end_date");
			
			paramClass.setStr_date(str_date); //������
			paramClass.setEnd_date(end_date); //������
			//DB�� update �ϱ� (�� ����)//����,�������� ��������
			sqlMapper.update("TrainingEvent.updateTrainingEvent", paramClass);
		}
		
		//DB�� update �ϱ� (�� ����)//����,�������� ��������
		sqlMapper.update("TrainingEvent.updateTrainingEvent1", paramClass);
		
		
		//���ϻ����� ���� ���� //���ϻ����� ���� ���ε�������� ��θ� �������
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		
		//����÷��
		MultipartFile file = request.getFile("filename"); // ���ε�� ����
		String orgName = file.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
		
		if(orgName != ""){ //������ ÷������ ���
			
			//�������� ��������
			if(resultClass.getAttach_path() != null){
				File deleteFile = new File(resultClass.getAttach_path());
				deleteFile.delete();
			}
			//.�������� ��������
			
			
			//���ο����� ��������
				String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
				String fileName = "file_inspectionResult_"+randNum;//�������� ���ϸ�(file_inspctionResult_������ȣ)
				String fileExt = orgName.substring(orgName.lastIndexOf('.'));//�������� Ȯ����
				
				File save = new File(file_path+fileName+fileExt); //������ ���� (���+���ϸ�+Ȯ����)
				file.transferTo(save);  // ���纻 ����
				
				//DB ���� ��� �����
				
				//����� path
				//String path = save.getPath().replace("\\", "/").substring(42); // 42�������� ������
				
				//������ path
				String path = file_path+fileName+fileExt;
				
				paramClass.setSeq(seq); //�������ѹ�
				paramClass.setAttach_name(fileName+fileExt); //���ϸ�
				paramClass.setAttach_path(path); // ���ϰ��(img src ��θ� �ǹ���)
				
				//���� ���� ������Ʈ.
				sqlMapper.update("TrainingEvent.updateFile", paramClass);
			///���ο����� ��������
		}
		//.����÷��
		
		return "redirect:/trainingEventView.do?seq="+seq+"&currentPage="+currentPage+"&searchingNow="+searchingNow; // ȣ���� ���������� �����̷�Ʈ
	}
	
	
	
	
	//----------------------------------------------------------------------------------------------------------------------------------------------//
	
	
	
	
	//������� (���) ������
	@RequestMapping("/trainingEventRepEditFrom.do")
	public String trainingEventRepEditFrom(HttpServletRequest request) throws SQLException{
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		request.setAttribute("seq", seq);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("searchingNow", searchingNow);
		request.setAttribute("resultClass", resultClass);
		
		return "/view/menu7/trainingEvent/trainingEventResEdit.jsp";
	}
	
	
	//������� (���) ����
	@RequestMapping(value="/trainingEventRepEdit.do",method=RequestMethod.POST)
	public String trainingEventRepEdit(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session) throws Exception{
		
		//�ۼ��� �����(���� �α����� ���Ǿ��̵�)
		String session_id = (String) session.getAttribute("session_id");
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
				
		//����ڰ� �Է��� ��
		String description = request1.getParameter("description");
		String str_date = request1.getParameter("str_date");
		String end_date = request1.getParameter("end_date");
		
		Calendar today = Calendar.getInstance();
		

		//DTO Set()
		paramClass.setSeq(seq);
		paramClass.setStr_date(str_date); //������
		paramClass.setEnd_date(end_date); //������
		paramClass.setDescription(description); //��������
		paramClass.setUdt_name(session_id); //������
		paramClass.setUdt_date(today.getTime()); //������
		
		
		//DB�� update �ϱ� (�� ����)
		sqlMapper.update("TrainingEvent.updateTrainingEvent", paramClass);
		
		
		//���ϻ����� ���� ���� //���ϻ����� ���� ���ε�������� ��θ� �������
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		
		//����÷��
		MultipartFile file = request.getFile("filename"); // ���ε�� ����
		String orgName = file.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
		
		if(orgName != ""){ //������ ÷������ ���
			
			//�������� ��������
			if(resultClass.getAttach_path() != null){
				File deleteFile = new File(resultClass.getAttach_path());
				deleteFile.delete();
			}
			//.�������� ��������
			
			
			//���ο����� ��������
				String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
				String fileName = "file_inspectionResult_"+randNum;//�������� ���ϸ�(file_inspctionResult_������ȣ)
				String fileExt = orgName.substring(orgName.lastIndexOf('.'));//�������� Ȯ����
				
				File save = new File(file_path+fileName+fileExt); //������ ���� (���+���ϸ�+Ȯ����)
				file.transferTo(save);  // ���纻 ����
				
				//DB ���� ��� �����
				
				//����� path
				//String path = save.getPath().replace("\\", "/").substring(42); // 42�������� ������
				
				//������ path
				String path = file_path+fileName+fileExt;
				
				paramClass.setSeq(seq); //�������ѹ�
				paramClass.setAttach_name(fileName+fileExt); //���ϸ�
				paramClass.setAttach_path(path); // ���ϰ��(img src ��θ� �ǹ���)
				
				//���� ���� ������Ʈ.
				sqlMapper.update("TrainingEvent.updateFile", paramClass);
			///���ο����� ��������
		}
		//.����÷��
			
		
		
		/*�̹��� ÷�� ����
		 * 1��~3�������� �̹��� ���ε� ������ �Ǵ��ϰ�,
		 * ���� ���ε�� �̹����� ���� ��� ���� �� insert �ϰ�
		 * �ƴ� ���� �ٷ� insert �Ѵ�.
		 * */
		
		
		//1�� �̹���
		if(request.getFile("img1") != null){ // �ش� ������ �����ϸ�
			
			MultipartFile file1 = request.getFile("img1"); // ���ε�� ����
			String orgName1 = file1.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
			
			if(orgName1 != ""){ //�̹��� ������ ÷������ ���
				
				String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
				String fileName = "img_trainingEvent_"+randNum;//�������� ���ϸ�(img_improvementCase_������ȣ)
				String fileExt = orgName1.substring(orgName1.lastIndexOf('.'));//�������� Ȯ����
				
				File save = new File(file_path+fileName+fileExt); //������ ���� (���+���ϸ�+Ȯ����)
				file1.transferTo(save);  // ���纻 ����
				
				//�̹������� ����� ����
				String temp = image_path.replace("\\", "/");
				//����� path
				String path = temp+fileName+fileExt; // �����+���ϸ�+Ȯ����
				
				paramClass.setSeq(seq); //�ִ� �������ѹ�
				paramClass.setImg1(path); // �̹������
				
				//���� ���� ������Ʈ.
				sqlMapper.update("TrainingEvent.updateImg1", paramClass);
			}
		}
		//.1�� �̹��� ����
		
		//2�� �̹���
		if(request.getFile("img2") != null){ // �ش� ������ �����ϸ�
			
			MultipartFile file2 = request.getFile("img2"); // ���ε�� ����
			String orgName2 = file2.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
			
			if(orgName2 != ""){ //�̹��� ������ ÷������ ���
				String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
				String fileName = "img_trainingEvent_"+randNum;//�������� ���ϸ�(img_improvementCase_������ȣ)
				String fileExt = orgName2.substring(orgName2.lastIndexOf('.'));//�������� Ȯ����
				
				File save = new File(file_path+fileName+fileExt); //������ ���� (���+���ϸ�+Ȯ����)
				file2.transferTo(save);  // ���纻 ����
				
				//�̹������� ����� ����
				String temp = image_path.replace("\\", "/");
				//����� path
				String path = temp+fileName+fileExt; // �����+���ϸ�+Ȯ����
				
				paramClass.setSeq(seq); //�ִ� �������ѹ�
				paramClass.setImg2(path); // �̹������
				
				//���� ���� ������Ʈ.
				sqlMapper.update("TrainingEvent.updateImg2", paramClass);
			}
		}
		//.2�� �̹��� ����
		
		
		//3�� �̹���
		if(request.getFile("img3") != null){ // �ش� ������ �����ϸ�
			
			MultipartFile file3 = request.getFile("img3"); // ���ε�� ����
			String orgName3 = file3.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
			
			if(orgName3 != ""){ //�̹��� ������ ÷������ ���
			
				String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
				String fileName = "img_trainingEvent_"+randNum;//�������� ���ϸ�(img_improvementCase_������ȣ)
				String fileExt = orgName3.substring(orgName3.lastIndexOf('.'));//�������� Ȯ����
				
				File save = new File(file_path+fileName+fileExt); //������ ���� (���+���ϸ�+Ȯ����)
				file3.transferTo(save);  // ���纻 ����
				
				//�̹������� ����� ����
				String temp = image_path.replace("\\", "/");
				//����� path
				String path = temp+fileName+fileExt; // �����+���ϸ�+Ȯ����
				
				paramClass.setSeq(seq); //�ִ� �������ѹ�
				paramClass.setImg3(path); // �̹������
				
				//���� ���� ������Ʈ.
				sqlMapper.update("TrainingEvent.updateImg3", paramClass);
			}
		}
		//.3�� �̹��� ����
			
		
		
		return "redirect:/trainingEventRepView.do?seq="+seq+"&currentPage="+currentPage+"&searchingNow="+searchingNow; // ȣ���� ���������� �����̷�Ʈ
	}
	
	
	
	
}
