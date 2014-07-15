package menu6.recruit.controller;



import header.member.dto.MemberDTO;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import menu2.inspectionResult.dto.InspectionResultDTO;
import menu6.recruit.dto.RecruitDTO;

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
public class RecruitCreate {
	
	//insert
	private RecruitDTO paramClass = new RecruitDTO();
	private RecruitDTO resultClass = new RecruitDTO();
	
	private MemberDTO resultClass1 = new MemberDTO(); // ȸ�� DTO
	
	//�ִ�������ѹ�
	private int seq;
	
	//���ε� ���� ���
	private String file_path = Constants.COMMON_FILE_PATH + Constants.MENU6_RECRUIT_FILE_PATH;
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public RecruitCreate() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	//�б��޽��η�Ǯ(����) �Է���
	@RequestMapping("/recruitCreateForm.do")
	public String recruitCreateForm(){
		return "/view/menu6/recruit_application/recruitCreate.jsp";
	}
	
	
	
	//�б��޽��η�Ǯ(����) �Է�
	@RequestMapping(value="/recruitCreate.do", method=RequestMethod.POST)
	public String recruitCreate(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session) throws Exception{
		request.setCharacterEncoding("euc-kr");
		
		//�ۼ��� �����(���� �α����� ���Ǿ��̵�)
		String session_id = (String) session.getAttribute("session_id");
		
		//�б���, �б������� insert�ϱ����� ������ ���ڵ带 �ҷ���
		resultClass1 = (MemberDTO)sqlMapper.queryForObject("Member.selectMemberOne", session_id);
		
		
		
		//����ڰ� �Է��� ��
		String title = request1.getParameter("title");
		String loc = request1.getParameter("loc");
		String job = request1.getParameter("job");
		String gubun = request1.getParameter("gubun");
		String description = request1.getParameter("description");
		String pw = request1.getParameter("pw");
		Calendar today = Calendar.getInstance();
		
		
		
		//( phone,  writer, )
		
		//DTO Set()
		paramClass.setTitle(title);
		paramClass.setLoc_seq(loc);
		paramClass.setJob(job);
		paramClass.setGubun(gubun);
		paramClass.setDescription(description);
		paramClass.setPw(pw);
		paramClass.setHits(1);
		paramClass.setReg_name(session_id);
		paramClass.setReg_date(today.getTime());
		paramClass.setUdt_name(session_id);
		paramClass.setUdt_date(today.getTime());
		paramClass.setSchool_name(resultClass1.getSchool_name());
		paramClass.setSchool_type(resultClass1.getSchool_type());
		paramClass.setEmail(resultClass1.getSen_email());
		paramClass.setPhone(resultClass1.getPhone());
		paramClass.setWriter(session_id);
		
		//��ϸ��� N
		paramClass.setEnd_yn("N");
		
		//DB�� insert �ϱ� (�� ���)
		sqlMapper.insert("Recruit.insertRecruit", paramClass);
		
		
		
		
		//����÷��
		
		//�ִ�������ѹ� get
		resultClass = (RecruitDTO) sqlMapper.queryForObject("Recruit.selectLastNum");
		seq = (int)(resultClass.getSeq());
				
		MultipartFile file = request.getFile("filename"); // ���ε�� ����
		String orgName = file.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
		
		if(orgName != ""){ //������ ÷������ ���
			
			String randNum = Integer.toString((int)(Math.random() * 99999));//������ȣ
			String fileName = "file_recruit_"+randNum;//�������� ���ϸ�(file_recruit_������ȣ)
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
			sqlMapper.update("Recruit.updateFile", paramClass);
		}
		//.����÷�� ���� 
		
		return "redirect:/recruitList.do"; //����Ʈ�� �����̷�Ʈ
	}
	
}
