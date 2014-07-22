package menu7.trainingEvent.controller;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import menu2.inspectionResult.dto.InspectionResultDTO;
import menu7.trainingEvent.dto.TrainingEventDTO;

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
public class TrainingEventCreate {
	
	//insert
	private TrainingEventDTO paramClass = new TrainingEventDTO();
	private TrainingEventDTO resultClass = new TrainingEventDTO();
	
	//�ִ�������ѹ�
	private int seq;
	
	//���ε� ���� ���
	String file_path = Constants.COMMON_FILE_PATH + Constants.MENU7_TRAININGEVENT_FILE_PATH;
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public TrainingEventCreate() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	//������� ���� �Է���
	@RequestMapping("/trainingEventCreateFrom.do")
	public String trainingEventCreateFrom(){
		return "/view/menu7/trainingEvent/trainingEventCreate.jsp";
	}
	
	//������� ���� �Է�
	@RequestMapping(value="/trainingEventCreate.do", method=RequestMethod.POST)
	public String trainingEventCreate(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session) throws Exception{
		request.setCharacterEncoding("euc-kr");
		
		//�ۼ��� �����(���� �α����� ���Ǿ��̵�)
		String session_id = (String) session.getAttribute("session_id");
		
		//����ڰ� �Է��� ��
		String title = request1.getParameter("title");
		String str_date = request1.getParameter("str_date");
		String end_date = request1.getParameter("end_date");
		String description = request1.getParameter("description");
		String pw = request1.getParameter("pw");
		Calendar today = Calendar.getInstance();
		
		//DTO Set()
		paramClass.setGubun("0"); // 0�� ����, 1�� ���
		paramClass.setTitle(title);
		paramClass.setStr_date(str_date);
		paramClass.setEnd_date(end_date);
		paramClass.setDescription(description);
		paramClass.setPw(pw);
		paramClass.setHits(1);
		paramClass.setWriter(session_id);
		paramClass.setReg_name(session_id);
		paramClass.setReg_date(today.getTime());
		paramClass.setUdt_name(session_id);
		paramClass.setUdt_date(today.getTime());
		paramClass.setTurn("0");
		
		Integer count = (Integer) sqlMapper.queryForObject("TrainingEvent.selectCount", session_id);
		if(count==0){
			paramClass.setUp_seq(1);
		}else{
			resultClass = (TrainingEventDTO) sqlMapper.queryForObject("TrainingEvent.selectLastNum");
			seq = (int)(resultClass.getSeq());
			paramClass.setUp_seq(seq+1); // ������ ������� �������ѹ��� +1 ��
		}
		
		//DB�� insert �ϱ� (�� ���)
		sqlMapper.insert("TrainingEvent.insertTrainingEvent", paramClass);
		
		
		//�ִ�������ѹ� get
		resultClass = (TrainingEventDTO) sqlMapper.queryForObject("TrainingEvent.selectLastNum");
		seq = (int)(resultClass.getSeq());
		
		
		//����÷��
		MultipartFile file = request.getFile("filename"); // ���ε�� ����
		String orgName = file.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
		
		if(orgName != ""){ //������ ÷������ ���
			
			String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
			String fileName = "file_trainingEvent_"+randNum;//�������� ���ϸ�(file_inspctionResult_������ȣ)
			String fileExt = orgName.substring(orgName.lastIndexOf('.'));//�������� Ȯ����
			
			File save = new File(file_path+fileName+fileExt); //������ ���� (���+���ϸ�+Ȯ����)
			file.transferTo(save);  // ���纻 ����
			
			//DB ���� ��� �����
			//����� path
			//String path = save.getPath().replace("\\", "/").substring(42); // 42�������� ������
			//������ path
			//String path = file_path+fileName+fileExt;
			
			paramClass.setSeq(seq); //�ִ� �������ѹ�
			paramClass.setAttach_name(fileName+fileExt); //���ϸ�
			paramClass.setAttach_path(file_path.replace("\\", "/")); //���ϰ��
			
			//���� ���� ������Ʈ.
			sqlMapper.update("TrainingEvent.updateFile", paramClass);
		}
		//.����÷��
		
		return "redirect:/TrainingEventList.do"; //����Ʈ�� �����̷�Ʈ
	}
	
	
	
	
	
	//������� ��� �Է���
	@RequestMapping("/trainingEventResCreateFrom.do")
	public String trainingEventResCreateFrom(HttpServletRequest request){
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		
		
		request.setAttribute("seq", seq);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("searchingNow", searchingNow);
		
		return "/view/menu7/trainingEvent/trainingEventResCreate.jsp";
	}
		
		
}
