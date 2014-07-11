package menu3.improvementCase.controller;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import menu3.improvementCase.dto.ImprovementCaseDTO;

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


@Controller
public class ImprovementCaseEdit {
	
	private ImprovementCaseDTO resultClass = new ImprovementCaseDTO(); //������ ���ڵ� �������� DTO
	private ImprovementCaseDTO paramClass = new ImprovementCaseDTO(); //���� ���� ���ڵ�
	
	String file_path = Constants.COMMON_FILE_PATH + Constants.MENU3_IMPROVEMENT_FILE_PATH; //���ε� ���� ���
	
	
	private int cnt; //�̹��� ����
	
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public ImprovementCaseEdit() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	
	//����.���� �˻��� ������
	@RequestMapping("/improvementCaseEditFrom.do")
	public String improvementCaseEditFrom(HttpServletRequest request) throws SQLException{
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
		
		
		resultClass = (ImprovementCaseDTO)sqlMapper.queryForObject("ImprovementCase.selectImprovementCaseOne", seq);
		
		
		
		//������ ���� �̹����� �����ϱ� ���� �۾� ����
			cnt = 0;
			
			//�̹��� ������ �ľ�
			if(resultClass.getImg1() != null){
				cnt++;
			}
			if(resultClass.getImg2() != null){
				cnt++;
			}
			if(resultClass.getImg3() != null){
				cnt++;
			}
			if(resultClass.getImg4() != null){
				cnt++;
			}
			if(resultClass.getImg5() != null){
				cnt++;
			}
			if(resultClass.getImg6() != null){
				cnt++;
			}
			//.�̹��� ������ �ľ� ����
			
			//�̹������� �����ϱ� ���� �迭
			String[] imgNames = new String[cnt];
		
			//�̹����� ���� ����
			if(resultClass.getImg1() != null){
				imgNames[0] = new String(resultClass.getImg1().substring(38));
			}
			if(resultClass.getImg2() != null){
				imgNames[1] = new String(resultClass.getImg2().substring(38));
			}
			if(resultClass.getImg3() != null){
				imgNames[2] = new String(resultClass.getImg3().substring(38));
			}
			if(resultClass.getImg4() != null){
				imgNames[3] = new String(resultClass.getImg4().substring(38));
			}
			if(resultClass.getImg5() != null){
				imgNames[4] = new String(resultClass.getImg5().substring(38));
			}
			if(resultClass.getImg6() != null){
				imgNames[5] = new String(resultClass.getImg6().substring(38));
			}
			//.�̹����� ���� ����
		
		//.������ ���� �̹����� �����ϱ� ���� �۾� ����
		
			
		//�׽�Ʈ	
		System.out.println("-----------------------------");
		
		System.out.println("�̹������� : "+cnt);
		for(int i=0; i<imgNames.length; i++) 
              System.out.println(imgNames[i]);
		
		System.out.println("-----------------------------");
		//�׽�Ʈ
		
		
		request.setAttribute("seq", seq);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("searchingNow", searchingNow);
		request.setAttribute("resultClass", resultClass);
		request.setAttribute("imgNames", imgNames);
		
		return "/view/menu3/improvementCaseEdit.jsp";
	}
	
	
	//����.���� �˻��� ����
	@RequestMapping(value="/improvementCaseEdit.do",method=RequestMethod.POST)
	public String improvementCaseEdit(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session) throws Exception{
		
		//�ۼ��� �����(���� �α����� ���Ǿ��̵�)
		String session_id = (String) session.getAttribute("session_id");
		
		//������û�� �� ����
		int seq = Integer.parseInt(request.getParameter("seq"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int searchingNow = Integer.parseInt(request.getParameter("searchingNow"));
				
		//����ڰ� �Է��� ��
		String description = request1.getParameter("description");
		Calendar today = Calendar.getInstance();
		

		//DTO Set()
		paramClass.setSeq(seq);
		paramClass.setDescription(description); //��������
		paramClass.setUdt_name(session_id); //������
		paramClass.setUdt_date(today.getTime()); //������
		
		
		//DB�� update �ϱ� (�� ����)
		sqlMapper.update("ImprovementCase.updateImprovementCase", paramClass);
		
		
		//1. ���ϻ����� ���� ���� - ���ϻ����� ���� ���ε�������� ��θ� �������
		//2. �̹��� ���� ������ ���� ���� - ����ڰ� ������ �̹��� ���δ��� �߰��ߴ��� �����ߴ��� �Ǵ��ϰ� ������Ʈ�� �ϱ� ����
		resultClass = (ImprovementCaseDTO)sqlMapper.queryForObject("ImprovementCase.selectImprovementCaseOne", seq);
		
		
		//����÷�� ����
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
					sqlMapper.update("ImprovementCase.updateFile", paramClass);
				///���ο����� ��������
			}
		//.����÷�� ����
			
			
			
		//�̹���÷�� ����
			if(resultClass.getImg1() == null){
				
			}
		
		//.�̹���÷�� ����
		
		
		return "redirect:/improvementCaseView.do?seq="+seq+"&currentPage="+currentPage+"&searchingNow="+searchingNow; // ȣ���� ���������� �����̷�Ʈ
	}
	
	
}
