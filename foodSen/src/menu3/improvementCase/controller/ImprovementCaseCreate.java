package menu3.improvementCase.controller;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import menu3.improvementCase.dto.ImprovementCaseDTO;
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
 * ��  ��: �޽Ľü� ������� insert Ŭ����
 * ��  ��: �޽Ľü� ������� �Խñ� ����� ����.
*/

@Controller
public class ImprovementCaseCreate {
	
	//insert
	private ImprovementCaseDTO paramClass = new ImprovementCaseDTO();
	private ImprovementCaseDTO resultClass = new ImprovementCaseDTO();
	
	//�ִ�������ѹ�
	private int seq;
	
	//���ε� ���� ���
	private String file_path = Constants.COMMON_FILE_PATH + Constants.MENU3_IMPROVEMENT_FILE_PATH;
	
	//���ε� �̹��� ���� ���
	private String image_path = Constants.MENU3_IMPROVEMENT_IMAGE_PATH;
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public ImprovementCaseCreate() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	//�޽Ľü� ������� �Է���
	@RequestMapping("/improvementCaseCreateFrom.do")
	public String improvementCaseCreateFrom(){
		return "/view/menu3/improvementCaseCreate.jsp";
	}
	
	
	
	//�޽Ľü� ������� �Է�
	@RequestMapping(value="/improvementCaseCreate.do", method=RequestMethod.POST)
	public String improvementCaseCreate(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session) throws Exception{
		request.setCharacterEncoding("euc-kr");
		
		//�ۼ��� �����(���� �α����� ���Ǿ��̵�)
		String session_id = (String) session.getAttribute("session_id");
		
		//����ڰ� �Է��� ��
		String title = request1.getParameter("title");
		String description = request1.getParameter("description");
		String pw = request1.getParameter("pw");
		Calendar today = Calendar.getInstance();
		
		//DTO Set()
		paramClass.setTitle(title);
		paramClass.setDescription(description);
		paramClass.setPw(pw);
		paramClass.setHits(1);
		paramClass.setWirte(session_id);
		paramClass.setReg_name(session_id);
		paramClass.setReg_date(today.getTime());
		paramClass.setUdt_name(session_id);
		paramClass.setUdt_date(today.getTime());
		
		//DB�� insert �ϱ� (�� ���)
		sqlMapper.insert("ImprovementCase.insertImprovementCase", paramClass);
		
		
		//�ִ�������ѹ� get
		resultClass = (ImprovementCaseDTO) sqlMapper.queryForObject("ImprovementCase.selectLastNum");
		seq = (int)(resultClass.getSeq());
		
		
		
		//����÷��
		MultipartFile file = request.getFile("filename"); // ���ε�� ����
		String orgName = file.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
		
		if(orgName != ""){ //������ ÷������ ���
			
			String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
			String fileName = "file_improvementCase_"+randNum;//�������� ���ϸ�(file_inspctionResult_������ȣ)
			String fileExt = orgName.substring(orgName.lastIndexOf('.'));//�������� Ȯ����
			
			File save = new File(file_path+fileName+fileExt); //������ ���� (���+���ϸ�+Ȯ����)
			file.transferTo(save);  // ���纻 ����
			
			//DB ���� ��� �����
			//����� path
			//String path = save.getPath().replace("\\", "/").substring(42); // 42�������� ������
			//������ path
			//String path = file_path+fileName+fileExt; //���+���ϸ�
			
			paramClass.setSeq(seq); //�ִ� �������ѹ�
			paramClass.setAttach_name(fileName+fileExt); //���ϸ�
			paramClass.setAttach_path(file_path.replace("\\", "/")); // ���ϰ��(img src ��θ� �ǹ���)
			
			//���� ���� ������Ʈ.
			sqlMapper.update("ImprovementCase.updateFile", paramClass);
		}
		//.����÷��
		
		
		
		
		//�̹��� ÷�� ����
		
			//1�� �̹���
			if(request.getFile("optupload1") != null){ // �ش� ������ �����ϸ�
				
				MultipartFile file1 = request.getFile("optupload1"); // ���ε�� ����
				String orgName1 = file1.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
				
				if(orgName1 != ""){ //�̹��� ������ ÷������ ���
					
					String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
					String fileName = "img_improvementCase_"+randNum;//�������� ���ϸ�(img_improvementCase_������ȣ)
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
					sqlMapper.update("ImprovementCase.updateImg1", paramClass);
				}
			}
			//.1�� �̹��� ����
			
			//2�� �̹���
			if(request.getFile("optupload2") != null){ // �ش� ������ �����ϸ�
				
				MultipartFile file2 = request.getFile("optupload2"); // ���ε�� ����
				String orgName2 = file2.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
				
				if(orgName2 != ""){ //�̹��� ������ ÷������ ���
					String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
					String fileName = "img_improvementCase_"+randNum;//�������� ���ϸ�(img_improvementCase_������ȣ)
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
					sqlMapper.update("ImprovementCase.updateImg2", paramClass);
				}
			}
			//.2�� �̹��� ����
			
			
			//3�� �̹���
			if(request.getFile("optupload3") != null){ // �ش� ������ �����ϸ�
				
				MultipartFile file3 = request.getFile("optupload3"); // ���ε�� ����
				String orgName3 = file3.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
				
				if(orgName3 != ""){ //�̹��� ������ ÷������ ���
				
					String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
					String fileName = "img_improvementCase_"+randNum;//�������� ���ϸ�(img_improvementCase_������ȣ)
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
					sqlMapper.update("ImprovementCase.updateImg3", paramClass);
				}
			}
			//.3�� �̹��� ����
			
			
			//4�� �̹���
			if(request.getFile("optupload4") != null){ // �ش� ������ �����ϸ�
				
				MultipartFile file4 = request.getFile("optupload4"); // ���ε�� ����
				String orgName4 = file4.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
				
				if(orgName4 != ""){ //�̹��� ������ ÷������ ���
				
					String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
					String fileName = "img_improvementCase_"+randNum;//�������� ���ϸ�(img_improvementCase_������ȣ)
					String fileExt = orgName4.substring(orgName4.lastIndexOf('.'));//�������� Ȯ����
					
					File save = new File(file_path+fileName+fileExt); //������ ���� (���+���ϸ�+Ȯ����)
					file4.transferTo(save);  // ���纻 ����
					
					//�̹������� ����� ����
					String temp = image_path.replace("\\", "/");
					//����� path
					String path = temp+fileName+fileExt; // �����+���ϸ�+Ȯ����
					
					paramClass.setSeq(seq); //�ִ� �������ѹ�
					paramClass.setImg4(path); // �̹������
					
					//���� ���� ������Ʈ.
					sqlMapper.update("ImprovementCase.updateImg4", paramClass);
				}
			}
			//.4�� �̹��� ����
			
			
			//5�� �̹���
			if(request.getFile("optupload5") != null){ // �ش� ������ �����ϸ�
				
				MultipartFile file5 = request.getFile("optupload5"); // ���ε�� ����
				String orgName5 = file5.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
				
				if(orgName5 != ""){ //�̹��� ������ ÷������ ���
				
					String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
					String fileName = "img_improvementCase_"+randNum;//�������� ���ϸ�(img_improvementCase_������ȣ)
					String fileExt = orgName5.substring(orgName5.lastIndexOf('.'));//�������� Ȯ����
					
					File save = new File(file_path+fileName+fileExt); //������ ���� (���+���ϸ�+Ȯ����)
					file5.transferTo(save);  // ���纻 ����
					
					//�̹������� ����� ����
					String temp = image_path.replace("\\", "/");
					//����� path
					String path = temp+fileName+fileExt; // �����+���ϸ�+Ȯ����
					
					paramClass.setSeq(seq); //�ִ� �������ѹ�
					paramClass.setImg5(path); // �̹������
					
					//���� ���� ������Ʈ.
					sqlMapper.update("ImprovementCase.updateImg5", paramClass);
				}
			}
			//.5�� �̹��� ����
			
			
			//6�� �̹���
			if(request.getFile("optupload6") != null){ // �ش� ������ �����ϸ�
				
				MultipartFile file6 = request.getFile("optupload6"); // ���ε�� ����
				String orgName6 = file6.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
				
				if(orgName6 != ""){ //�̹��� ������ ÷������ ���
				
					String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
					String fileName = "img_improvementCase_"+randNum;//�������� ���ϸ�(img_improvementCase_������ȣ)
					String fileExt = orgName6.substring(orgName6.lastIndexOf('.'));//�������� Ȯ����
					
					File save = new File(file_path+fileName+fileExt); //������ ���� (���+���ϸ�+Ȯ����)
					
					file6.transferTo(save);  // ���纻 ����
					
					//�̹������� ����� ����
					String temp = image_path.replace("\\", "/");
					//����� path
					String path = temp+fileName+fileExt; // �����+���ϸ�+Ȯ����
					
					paramClass.setSeq(seq); //�ִ� �������ѹ�
					paramClass.setImg6(path); // �̹������
					
					//���� ���� ������Ʈ.
					sqlMapper.update("ImprovementCase.updateImg6", paramClass);
				}
			}
			//.6�� �̹��� ����
			
		
		//.�̹��� ÷�� ����
		
		
		return "redirect:/improvementCaseList.do"; //����Ʈ�� �����̷�Ʈ
	}
	
}
