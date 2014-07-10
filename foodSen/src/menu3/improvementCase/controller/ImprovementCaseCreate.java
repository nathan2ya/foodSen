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


@Controller
public class ImprovementCaseCreate {
	
	//insert
	private ImprovementCaseDTO paramClass = new ImprovementCaseDTO();
	private ImprovementCaseDTO resultClass = new ImprovementCaseDTO();
	
	//�ִ�������ѹ�
	private int seq;
	
	//���ε� ���� ���
	String file_path = Constants.COMMON_FILE_PATH + Constants.MENU3_IMPROVEMENT_FILE_PATH;
	
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
			String path = file_path+fileName+fileExt;
			
			paramClass.setSeq(seq); //�ִ� �������ѹ�
			paramClass.setAttach_name(fileName+fileExt); //���ϸ�
			paramClass.setAttach_path(path); // ���ϰ��(img src ��θ� �ǹ���)
			
			//���� ���� ������Ʈ.
			sqlMapper.update("ImprovementCase.updateFile", paramClass);
		}
		//.����÷��
		
		return "redirect:/improvementCaseList.do"; //����Ʈ�� �����̷�Ʈ
	}
	
}
