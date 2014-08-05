package menu7.trainingEvent.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

/*
 * �ۼ���: �����
 * ��  ��: ������� view Ŭ����
 * ��  ��: ������� �Խñ� �並 ����.
*/

@Controller
public class TrainingEventView {
	
	//�������� ���ڵ�
	private TrainingEventDTO paramClass = new TrainingEventDTO();
	private TrainingEventDTO resultClass = new TrainingEventDTO();
	private int currentPage;
	private int searchingNow; // ��ü��, �˻����� �Ǵ��Ͽ� ���� ������ �ǰ����� ����
	private String searchType;
	private String userinput;
	private int canWrite; // �������� �� �� �ִ��� ������ �Ǵ��ϴ� ����
	
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
	public String trainingEventView(HttpServletRequest request1, HttpSession session) throws SQLException{
		
		int seq = Integer.parseInt(request1.getParameter("seq"));
		
		if(null == request1.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request1.getParameter("currentPage"));
		}
		
		
		/*
		searchingNow
		�޷� ����Ʈ���� view�� ������ ��� 0
		��ü�� ����Ʈ���� view�� ������ ��� 0
		�˻����� ����Ʈ���� view�� ���� �� ��� 1
		
		�� ������ view ���� ����� Ŭ������ �� 
		�ٽ� ���� ����Ʈ�� ����ϱ� ���� ���ǵ� �����̴�.
		*/
		
		//�� ���� null�� ������ ���� �޷¸���Ʈ���� �������� ���� �ش�. (���� �޷¸���Ʈ������ seq�� ����)
		if(null == request1.getParameter("searchingNow")){ 
			searchingNow = 0;
		}else{
			searchingNow = Integer.parseInt(request1.getParameter("searchingNow"));
		}
		
		
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
		
		
		
		/*
		 * ��絵�߿��� �����Ұ��� �ϵ��� �ϴ� �Ǵ��Լ�
		 * 1. ������(���)�� ����������̰ų� �� �̻�Ǿ�߸� �������� �Ǵ��ϴ� ����
		 * 2. ���� ������ �Ǵ��ϴ� ����
		 * 3. 0�� ��ϺҰ� �� �����Ұ�, 1�� ��ϰ��� �� ��������
		 * str_date - �����۳�¥
		 * end_date - ������ᳯ¥
		 * current_date - ���糯¥
		*/
		
		//��� ���� ��¥(str_date)
		int str_date = Integer.parseInt(resultClass.getStr_date().replace("-", ""));
		//��� ���� ��¥(end_date)
		int end_date = Integer.parseInt(resultClass.getEnd_date().replace("-", ""));
		//���� ��¥(current_date)
		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int current_date = Integer.parseInt(sdf.format(today.getTime()));
		
				// ��簡 �����߰�,		����Ǳ� ���̸�
		if(current_date >= str_date && current_date <= end_date){
			canWrite = 0; // 0�� ��ϺҰ� or �����Ұ�
		}else{
			canWrite = 1; // 1�� ��ϰ��� or ��������
		}
		
		
		
		//���糯¥�� String���� ����
		//��簡 ����ɰ�� ���� ������ �� ���� �ϱ� ���Ͽ�, ����ð��� String Type���� jsp �� ������, end_date ���� �Ǵ��Ѵ�.
		Calendar today1 = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String current_date1 = sdf1.format(today1.getTime());
		
		
		
		request1.setAttribute("current_date", current_date);
		request1.setAttribute("current_date1", current_date1); //������(����߰�)
		request1.setAttribute("seq", seq); //���� �������ѹ�
		request1.setAttribute("currentPage", currentPage); //����������
		request1.setAttribute("searchingNow", searchingNow); //��ü�� or �˻��� ������
		request1.setAttribute("canWrite", canWrite); //������(���) �Է� ���� ������
		request1.setAttribute("resultClass", resultClass); //�信 ��µ� ���ڵ�1�� ��������
		return "/view/menu7/trainingEvent/trainingEventView.jsp";
	}
	
	
	
	//�������(���) ��������
	@RequestMapping("/trainingEventRepView.do")
	public String trainingEventRepView(HttpServletRequest request1, HttpSession session) throws SQLException{
		
		
		//-------------------------------------------------------------------------------------------------------------------//
		/*seq, currentPage, searchingNow (+ searchType, userinput) �� �������� �ʱ�ȭ �Ѵ�.*/
		
		
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
		//.-------------------------------------------------------------------------------------------------------------------//
		
		
		
		
		//-------------------------------------------------------------------------------------------------------------------//
		/*��ȸ�� +1 �ϱ�*/
		
		//��ȸ�� +1 ��ų ���ڵ带 �ҷ��´�.
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		//���������� ��ȸ���� +1 ������Ʈ
		paramClass.setSeq(seq);
		paramClass.setHits(resultClass.getHits()+1);
		sqlMapper.update("TrainingEvent.updateHits", paramClass);
		//.-------------------------------------------------------------------------------------------------------------------//
		
		
		
		
		
		//���������� ������ ���ڵ� 1���� get
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		request1.setAttribute("seq", seq);
		request1.setAttribute("currentPage", currentPage);
		request1.setAttribute("searchingNow", searchingNow);
		request1.setAttribute("resultClass", resultClass); //���ڵ�1��
		
		return "/view/menu7/trainingEvent/trainingEventRepView.jsp";
	}
	
	
	//������� ÷������ �ٿ�ε� (����+��� ��� �� uri�� �̿���)
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
