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
	private String image_path = Constants.MENU3_IMPROVEMENT_IMAGE_PATH; //���ε� �̹��� ���� ���
	private String imgPath = Constants.COMMON_FILE_PATH + Constants.MENU3_IMPROVEMENT_FILE_PATH; //������ ���ڵ� �̹����� ������
	
	
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
		
		request.setAttribute("cnt", cnt);
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
		
		
		
		
		/*
		##����÷�� ����##
		���ε��û�� ���� ���ð��
		���� ������ ������ �Ŀ� ���� ���纻�� ������ ������
		*/
		
		MultipartFile file = request.getFile("filename"); // ���ε�� ����
		String orgName = file.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
		
		if(orgName != ""){ //������ ÷������ ���
			
			//�������� ��������
			if(resultClass.getAttach_path() != null){
				File deleteFile = new File(resultClass.getAttach_path()+resultClass.getAttach_name());
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
			
		
			
			
		/*
		##�̹���÷�� ���� �ó�����##
		0. ������ == null �϶� -> ������ == null �̸�, �ƹ��͵����� - ����ڰ� ��� ������ȭ�� ���� �ʾ����� �ǹ�
		1. ������ == null �϶� -> ������ != null �̸�, �������ڵ� ������Ʈ - ����ڰ� �̹����� �߰���������� �ǹ�
		2. ������ != null �϶� -> ������ != null �̸�, �������ڵ� ����(�̹�������), �������ڵ� ������Ʈ  - ����ڰ� �����÷��� �����Ͽ����� �ǹ�
		3. ������ != null �϶� -> ������ == null �̸� -> ��������>��������, �����÷� ����(�̹�������) - ����ڰ� �̹����� ���ҵ�������� �ǹ�
						                    		  -> 0==��������, �ƹ��͵����� - ����ڰ� ��� ������ȭ�� ���� �ʾ����� �ǹ�
		*/	
		
		
		
		//����img���� ����
		int orgCnt = Integer.parseInt(request.getParameter("cnt"));
		
		//����img���� ����
		int uptCnt = 0;
		if(request.getFile("optupload1") != null){
			uptCnt++;
		}
		if(request.getFile("optupload2") != null){
			uptCnt++;
		}
		if(request.getFile("optupload3") != null){
			uptCnt++;
		}
		if(request.getFile("optupload4") != null){
			uptCnt++;
		}
		if(request.getFile("optupload5") != null){
			uptCnt++;
		}
		if(request.getFile("optupload6") != null){
			uptCnt++;
		}
		//����img���� ���� ����
		
		
		
		// 1. ������ == null �϶� -> ������ != null �̸�, �������ڵ� ������Ʈ - ����ڰ� �̹����� �߰���������� �ǹ�
		
		//1�� �̹���
		if(resultClass.getImg1() == null){
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
		}
		//.1�� �̹��� ����
		
		//2�� �̹���
		if(resultClass.getImg2() == null){
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
		}
		//.2�� �̹��� ����
		
		
		//3�� �̹���
		if(resultClass.getImg3() == null){
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
		}
		
		
		//4�� �̹���
		if(resultClass.getImg4() == null){
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
		}
		//.4�� �̹��� ����
		
		
		//5�� �̹���
		if(resultClass.getImg5() == null){
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
		}
		//.5�� �̹��� ����
		
		
		//6�� �̹���
		if(resultClass.getImg6() == null){
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
		}
		//.6�� �̹��� ����

		//.1�� �ó����� ����
		
		
		
		
		//2. ������ != null �϶� -> ������ != null �̸�, �������ڵ� ����(�̹�������), �������ڵ� ������Ʈ  - ����ڰ� �����÷��� �����Ͽ����� �ǹ�
		//3. ������ != null �϶� -> ������ == null �̸� -> ��������>��������, �����÷� ����(�̹�������) - ����ڰ� �̹����� ���ҵ�������� �ǹ�
		//				                    		    -> 0 == ��������, �ƹ��͵����� - ����ڰ� ��� ������ȭ�� ���� �ʾ����� �ǹ�
		
			
		//1�� �̹���
		if(resultClass.getImg1() != null){
			
			//1. ���� ������ �����ϸ�
			if(request.getFile("optupload1") != null){ 
				
				//�����̹��� ����
				if(orgCnt > uptCnt && 0 != uptCnt){
					String imgName = resultClass.getImg1().substring(38); //38 ������ �����
					File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
					deleteFile.delete();
				}
				//�����̹��� ���� ����
				
				//���ο���� ������Ʈ
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
			//.1����
			
			
			//2. ���� ������ ������ -> �̹��� ������ ��ġ�ʰų� or �̹����� �����߰ų�
			if(request.getFile("optupload1") == null){
				
				if(orgCnt > uptCnt && 0 != uptCnt){ //����ڰ� �̹����� ���ҵ�������� �ǹ�
					//�����̹��� ����
					String imgName = resultClass.getImg1().substring(38); //38 ������ �����
					File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
					deleteFile.delete();
					//�����̹��� ���� ����
					
					//�ش� �÷��� �����͸� ����
					paramClass.setSeq(seq); //�ִ� �������ѹ�
					paramClass.setImg1(null); // �̹������
					
					//���� ���� ������Ʈ.
					sqlMapper.update("ImprovementCase.updateImg1", paramClass); // �ش� �÷��� �ʱⰪ�� null�� �ʱ�ȭ
					
				}else if(0 == uptCnt){ //������ ��ġ ���� ���
					//�ƹ��۾�����
				}
			
			}
			//.2����
			
		}
		//.1�� �̹��� ����
		
		//2�� �̹���
		if(resultClass.getImg2() != null){
			
			//1. ���� ������ �����ϸ�
			if(request.getFile("optupload2") != null){
				
				//���������� ����
				if(orgCnt > uptCnt && 0 != uptCnt){
					String imgName = resultClass.getImg2().substring(38); //38 ������ �����
					File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
					deleteFile.delete();
				}
				//���������� ���� ����
				
				//���ο���� ������Ʈ
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
			
			//2. ���� ������ ������ -> �̹��� ������ ��ġ�ʰų� or �̹����� �����߰ų�
			if(request.getFile("optupload2") == null){
				
				if(orgCnt > uptCnt && 0 != uptCnt){ //����ڰ� �̹����� ���ҵ�������� �ǹ�
					//�����̹��� ����
					String imgName = resultClass.getImg2().substring(38); //38 ������ �����
					File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
					deleteFile.delete();
					//�����̹��� ���� ����
					
					//�ش� �÷��� �����͸� ����
					paramClass.setSeq(seq); //�ִ� �������ѹ�
					paramClass.setImg2(null); // �̹������
					
					//���� ���� ������Ʈ.
					sqlMapper.update("ImprovementCase.updateImg2", paramClass); // �ش� �÷��� �ʱⰪ�� null�� �ʱ�ȭ
					
				}else if(0 == uptCnt){ //������ ��ġ ���� ���
					//�ƹ��۾�����
				}
			
			}
			//.2����
		}
		//.2�� �̹��� ����
		
		
		//3�� �̹���
		if(resultClass.getImg3() != null){
			
			//1. ���� ������ �����ϸ�
			if(request.getFile("optupload3") != null){
				
				//���������� ����
				if(orgCnt > uptCnt && 0 != uptCnt){
					String imgName = resultClass.getImg3().substring(38); //38 ������ �����
					File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
					deleteFile.delete();
				}
				//���������� ���� ����
				
				//���ο���� ������Ʈ
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
			
			//2. ���� ������ ������ -> �̹��� ������ ��ġ�ʰų� or �̹����� �����߰ų�
			if(request.getFile("optupload3") == null){
				
				if(orgCnt > uptCnt && 0 != uptCnt){ //����ڰ� �̹����� ���ҵ�������� �ǹ�
					//�����̹��� ����
					String imgName = resultClass.getImg3().substring(38); //38 ������ �����
					File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
					deleteFile.delete();
					//�����̹��� ���� ����
					
					//�ش� �÷��� �����͸� ����
					paramClass.setSeq(seq); //�ִ� �������ѹ�
					paramClass.setImg3(null); // �̹������
					
					//���� ���� ������Ʈ.
					sqlMapper.update("ImprovementCase.updateImg3", paramClass); // �ش� �÷��� �ʱⰪ�� null�� �ʱ�ȭ
					
				}else if(0 == uptCnt){ //������ ��ġ ���� ���
					//�ƹ��۾�����
				}
			
			}
			//.2����
		}
		//.3�� �̹��� ����
		
		
		//4�� �̹���
		if(resultClass.getImg4() != null){
			
			//1. ���� ������ �����ϸ�
			if(request.getFile("optupload4") != null){
	
				//���������� ����
				if(orgCnt > uptCnt && 0 != uptCnt){
					String imgName = resultClass.getImg4().substring(38); //38 ������ �����
					File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
					deleteFile.delete();
				}
				//���������� ���� ����
				
				//���ο���� ������Ʈ
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
			
			//2. ���� ������ ������ -> �̹��� ������ ��ġ�ʰų� or �̹����� �����߰ų�
			if(request.getFile("optupload4") == null){
				
				if(orgCnt > uptCnt && 0 != uptCnt){ //����ڰ� �̹����� ���ҵ�������� �ǹ�
					//�����̹��� ����
					String imgName = resultClass.getImg4().substring(38); //38 ������ �����
					File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
					deleteFile.delete();
					//�����̹��� ���� ����
					
					//�ش� �÷��� �����͸� ����
					paramClass.setSeq(seq); //�ִ� �������ѹ�
					paramClass.setImg4(null); // �̹������
					
					//���� ���� ������Ʈ.
					sqlMapper.update("ImprovementCase.updateImg4", paramClass); // �ش� �÷��� �ʱⰪ�� null�� �ʱ�ȭ
					
				}else if(0 == uptCnt){ //������ ��ġ ���� ���
					//�ƹ��۾�����
				}
			
			}
			//.2����
		}
		//.4�� �̹��� ����
		
		
		//5�� �̹���
		if(resultClass.getImg5() != null){
			
			//1. ���� ������ �����ϸ�
			if(request.getFile("optupload5") != null){
	
				//���������� ����
				if(orgCnt > uptCnt && 0 != uptCnt){
					String imgName = resultClass.getImg5().substring(38); //38 ������ �����
					File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
					deleteFile.delete();
				}
				//���������� ���� ����
				
				//���ο���� ������Ʈ
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
			
			//2. ���� ������ ������ -> �̹��� ������ ��ġ�ʰų� or �̹����� �����߰ų�
			if(request.getFile("optupload5") == null){
				
				if(orgCnt > uptCnt && 0 != uptCnt){ //����ڰ� �̹����� ���ҵ�������� �ǹ�
					//�����̹��� ����
					String imgName = resultClass.getImg5().substring(38); //38 ������ �����
					File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
					deleteFile.delete();
					//�����̹��� ���� ����
					
					//�ش� �÷��� �����͸� ����
					paramClass.setSeq(seq); //�ִ� �������ѹ�
					paramClass.setImg5(null); // �̹������
					
					//���� ���� ������Ʈ.
					sqlMapper.update("ImprovementCase.updateImg5", paramClass); // �ش� �÷��� �ʱⰪ�� null�� �ʱ�ȭ
					
				}else if(0 == uptCnt){ //������ ��ġ ���� ���
					//�ƹ��۾�����
				}
			
			}
			//.2����
		}
		//.5�� �̹��� ����
		
		
		//6�� �̹���
		if(resultClass.getImg6() != null){
			
			//1. ���� ������ �����ϸ�
			if(request.getFile("optupload6") != null){
	
				//���������� ����
				if(orgCnt > uptCnt && 0 != uptCnt){
					String imgName = resultClass.getImg6().substring(38); //38 ������ �����
					File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
					deleteFile.delete();
				}
				//���������� ���� ����
				
				//���ο���� ������Ʈ
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
			
			//2. ���� ������ ������ -> �̹��� ������ ��ġ�ʰų� or �̹����� �����߰ų�
			if(request.getFile("optupload6") == null){
				
				if(orgCnt > uptCnt && 0 != uptCnt){ //����ڰ� �̹����� ���ҵ�������� �ǹ�
					//�����̹��� ����
					String imgName = resultClass.getImg6().substring(38); //38 ������ �����
					File deleteFile = new File(imgPath+imgName); //���ϸ� + ���
					deleteFile.delete();
					//�����̹��� ���� ����
					
					//�ش� �÷��� �����͸� ����
					paramClass.setSeq(seq); //�ִ� �������ѹ�
					paramClass.setImg6(null); // �̹������
					
					//���� ���� ������Ʈ.
					sqlMapper.update("ImprovementCase.updateImg6", paramClass); // �ش� �÷��� �ʱⰪ�� null�� �ʱ�ȭ
					
				}else if(0 == uptCnt){ //������ ��ġ ���� ���
					//�ƹ��۾�����
				}
			
			}
			//.2����
		}
		//.6�� �̹��� ����
		//.2~3�� �ó����� ����
			
		
		//.�̹���÷�� ���� �ó����� ����
		
		
		return "redirect:/improvementCaseView.do?seq="+seq+"&currentPage="+currentPage+"&searchingNow="+searchingNow; // ȣ���� ���������� �����̷�Ʈ
	}
	
}
