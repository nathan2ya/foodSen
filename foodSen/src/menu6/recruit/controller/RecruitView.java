package menu6.recruit.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
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

import menu6.recruit.dto.RecruitDTO;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
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
public class RecruitView {

	//�������� ���ڵ�
	private RecruitDTO paramClass = new RecruitDTO();
	private RecruitDTO resultClass = new RecruitDTO();
	
	
	private int currentPage;
	private int searchingNow; // ��ü��, �˻����� �Ǵ��Ͽ� ���� ������ �ǰ����� ����
	
	
	//�˻����� ��쿡�� �߻��ϴ� ����(it's for uri)
	private String searchType;
	private String subType;
	private String subValue;
	
	//���ϴٿ�ε� ����
	private String file_path = Constants.COMMON_FILE_PATH + Constants.MENU6_RECRUIT_FILE_PATH;
	
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public RecruitView() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	//�б��޽��η�Ǯ(����) ��������
	@RequestMapping("/recruitView.do")
	public String inspectionResultView(HttpServletRequest request1, HttpSession session) throws SQLException{
		
		
		//�信�� ���� �� �ʱ�ȭ
			int seq = Integer.parseInt(request1.getParameter("seq"));
			if(null == request1.getParameter("currentPage")){
				currentPage = 1;
			}else{
				currentPage = Integer.parseInt(request1.getParameter("currentPage"));
			}
			searchingNow = Integer.parseInt(request1.getParameter("searchingNow"));
			
			
			//�˻��� ������Ƽ �ʱ�ȭ
			if(searchingNow==1){
				searchType = request1.getParameter("searchType");
				
				if(searchType.equals("job")){
					subType = "job";
					subValue = request1.getParameter("job");
					request1.setAttribute("job", subValue);
				}else if(searchType.equals("gubun")){
					subType = "gubun";
					subValue = request1.getParameter("gubun");
					request1.setAttribute("gubun", subValue);
				}else if(searchType.equals("loc_seq")){
					subType = "loc_seq";
					subValue = request1.getParameter("loc_seq");
					request1.setAttribute("loc_seq", subValue);
				}else{
					subType = "school_type";
					subValue = request1.getParameter("school_type");
					request1.setAttribute("school_type", subValue);
				}
				
				//��� �Ʒ��� ���� 3���� ������ ������
				//����� Ŭ������ �� �ٽ� �˻����� ����Ʈ�� ���ư��� �����̴�. +���� currentPage�� �Բ�!
				request1.setAttribute("searchType", searchType);
				request1.setAttribute("searchingNow", 1);
			}else{
				
				//�˻����� �ƴѰ�쿡�� �Ʒ��� �������� �������� ������ �ǹ��ϴ� 0�� �ʱ�ȭ ���Ѽ� �ش� jsp �� ������.
				request1.setAttribute("searchType", 0);
				request1.setAttribute("searchingNow", 0);
			}
			//.�˻��� ������Ƽ �ʱ�ȭ ����
			
		//.�信�� ���� �� �ʱ�ȭ ����
		
		
		
		
		//��ȸ���� ���� get
		resultClass = (RecruitDTO)sqlMapper.queryForObject("Recruit.selectRecruitOne", seq);
		
		//���������� ��ȸ���� +1 ������Ʈ
		paramClass.setSeq(seq);
		paramClass.setHits(resultClass.getHits()+1);
		sqlMapper.update("Recruit.updateHits", paramClass);
		//���������� ��ȸ���� +1 ������Ʈ ����
		
		
		//���������� ������ ���ڵ� 1���� get
		resultClass = (RecruitDTO)sqlMapper.queryForObject("Recruit.selectRecruitOne", seq);
		
		
		request1.setAttribute("seq", seq);
		request1.setAttribute("currentPage", currentPage);
		request1.setAttribute("searchingNow", searchingNow);
		request1.setAttribute("resultClass", resultClass); //���ڵ�1��
		
		
		return "/view/menu6/recruit_application/recruitView.jsp";
	}
	
	
	
	//�б��޽��η�Ǯ(����) ÷������ �ٿ�ε�
	@RequestMapping(value = "/recruit_FileDownload.do")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String uploadPath = file_path;
		String requestedFile = request.getParameter("attach_name");
		//String attach_path = request.getParameter("attach_path"); //���ϰ� ��� ��θ� ������ ����(���� ���� �� ����)

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
			printwriter.println("<br><br><br><h2>Could not get file name:<br>" + requestedFile + "</h2>");
			printwriter
			.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
			printwriter.println("<br><br><br>&copy; webAccess");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
		}
	}
	
	
}
