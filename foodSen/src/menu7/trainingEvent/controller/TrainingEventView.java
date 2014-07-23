package menu7.trainingEvent.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import menu7.trainingEvent.dto.TrainingEventDTO;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import common.Constants;


@Controller
public class TrainingEventView {
	
	//�������� ���ڵ�
	private TrainingEventDTO paramClass = new TrainingEventDTO();
	private TrainingEventDTO resultClass = new TrainingEventDTO();
	private int currentPage;
	private int searchingNow; // ��ü��, �˻����� �Ǵ��Ͽ� ���� ������ �ǰ����� ����
	private String searchType;
	private String userinput;
	
	//���ϴٿ�ε� ����
	private String file_path = Constants.COMMON_FILE_PATH + Constants.MENU7_TRAININGEVENT_FILE_PATH;
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public TrainingEventView() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	
	//�������(����) ��������
	@RequestMapping("/trainingEventView.do")
	public String inspectionResultView(HttpServletRequest request1, HttpSession session) throws SQLException{
		
		int seq = Integer.parseInt(request1.getParameter("seq"));
		
		if(null == request1.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request1.getParameter("currentPage"));
		}
		searchingNow = Integer.parseInt(request1.getParameter("searchingNow"));
		
		//�˻��� ��� �����ϴ� ���� �ʱ�ȭ
		if(searchingNow==1){
			searchType = request1.getParameter("searchType");
			userinput = request1.getParameter("userinput");
			
			//��� �Ʒ��� ���� 3���� ������ ������
			//����� Ŭ������ �� �ٽ� �˻����� ����Ʈ�� ���ư��� �����̴�. +���� currentPage�� �Բ�!
			request1.setAttribute("searchType", searchType);
			request1.setAttribute("userinput", userinput);
			request1.setAttribute("searchingNow", 1);
		}else{
			
			//�˻����� �ƴѰ�쿡�� �Ʒ��� �������� �������� ������ �ǹ��ϴ� 0�� �ʱ�ȭ ���Ѽ� �ش� jsp �� ������.
			request1.setAttribute("searchType", 0);
			request1.setAttribute("userinput", 0);
			request1.setAttribute("searchingNow", 0);
		}
		
		
		//��ȸ���� ���� get
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		//���������� ��ȸ���� +1 ������Ʈ
		paramClass.setSeq(seq);
		paramClass.setHits(resultClass.getHits()+1);
		sqlMapper.update("TrainingEvent.updateHits", paramClass);
		
		
		//���������� ������ ���ڵ� 1���� get
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		
		request1.setAttribute("seq", seq);
		request1.setAttribute("currentPage", currentPage);
		request1.setAttribute("searchingNow", searchingNow);
		request1.setAttribute("resultClass", resultClass); //���ڵ�1��
		
		
		return "/view/menu7/trainingEvent/trainingEventView.jsp";
	}
	
	
	
	////�������(���) ��������
	/*
		@ create method here @ will be!
	*/
	
	
	//������� ÷������ �ٿ�ε�
	@RequestMapping(value = "/trainingEvent_FileDownload.do")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String uploadPath = file_path;
		String requestedFile = request.getParameter("attach_name");
		//String attach_path = request.getParameter("attach_path"); //���ϰ� ��� ��θ� ������ ����(���� ���� �� �־� �ּ�ó����)

		File uFile = new File(uploadPath, requestedFile); //���,���ϸ����� ���ϰ�ü ����
		int fSize = (int) uFile.length();
		boolean ctrl = uFile.exists(); //������������
		
		if (ctrl){
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(uFile)); //������ �о���� // ���ۿ�

			String mimetype = "text/html";

			response.setBufferSize(fSize); //����ũ�⼳��
			response.setContentType(mimetype); //���������ļ���
			response.setHeader("Content-Disposition", "attachment; filename=\"" + requestedFile + "\"");
			response.setContentLength(fSize); //������ ��ü�� ����

			FileCopyUtils.copy(in, response.getOutputStream());
			in.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}else{ //���������� ȭ�� ����
			//setContentType�� ������Ʈ ȯ�濡 ���߾� ����
			response.setContentType("application/x-msdownload");
			PrintWriter printwriter = response.getWriter();
			printwriter.println("<html>");
			printwriter.println("<br><br><br><h2>���� ������ ã�� �� �����ϴ�. </h2><br> ���ϸ� : " + requestedFile);
			printwriter
			.println("<br><br><br><center><h3>������������ ���ư��÷��� <a href='javascript: history.go(-1)'>����</a>�� Ŭ���ϼ���.</h3></center>");
			printwriter.println("<br><br><br>&copy; webAccess");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
		}
	}
	
	
}
