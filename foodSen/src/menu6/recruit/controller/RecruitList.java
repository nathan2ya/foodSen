package menu6.recruit.controller;


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

import menu6.recruit.dto.RecruitDTO;

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

import common.PagingAction;


@Controller
public class RecruitList {
	
	//����Ʈ
	private List<RecruitDTO> list = new ArrayList<RecruitDTO>();
	
	//�˻���
	private String tempInput;
	private int searchingNow; // ��ü��, �˻����� �Ǵ��Ͽ� ���� ������ �ǰ����� ����
	
	//������
	private int totalCount;// �� �Խù��� ��
	private int blockCount;// �� �������� �Խù��� ��
	private int blockPage;// �� ȭ�鿡 ������ ������ ��
	private String pagingHtml;// ����¡�� ������ HTML
	private PagingAction page;// ����¡ Ŭ����
	private String serviceName;// ȣ��������
	private int currentPage; // ����������
	private int lastPage; //������ ������
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public RecruitList() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	
	//�б��޽��η�Ǯ(����) (��ü��) ����Ʈ
	@RequestMapping("/recruitList.do")
	public String recruitList(HttpServletRequest request) throws SQLException{
		
		// ��ü��, �˻��� �Ǵܰ�.
		searchingNow = 0; //0 == ��ü��//1 == �˻���//
				
		
		list = sqlMapper.queryForList("Recruit.selectAll"); //��ü��
		int numberCount = list.size(); // ��ü ���ڵ� ����
		
		
		//������
		blockCount = 10;// �� �������� �Խù��� ��
		blockPage = 5;// �� ȭ�鿡 ������ ������ ��
		serviceName = "recruitList";// ȣ�� URI ����
		totalCount = list.size(); // ��ü �� ����
		//currentPage �ʱ�ȭ
		if(null == request.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		page = new PagingAction(serviceName, currentPage, totalCount, blockCount, blockPage); // pagingAction ��ü ����.
		pagingHtml = page.getPagingHtml().toString(); // ������ HTML ����.

		int lastCount = totalCount; //��ü���� ����
		
		// ���� �������� ������ ���� ��ȣ�� ��ü�� ������ �� ��ȣ���� ������ lastCount�� +1 ��ȣ�� ����.
		if (page.getEndCount() < totalCount)
			lastCount = page.getEndCount() + 1;
		
		
		// ��ü ����Ʈ���� ���� ��������ŭ�� ����Ʈ�� �����´�.
		list = list.subList(page.getStartCount(), lastCount);
		
		
		//������������ ����
		lastPage = (int) Math.ceil((double) totalCount / blockCount);
		//����Ʈsize() / ���������� ������ �Խñ� �� = ��������(�ݿø�)
		if (lastPage == 0) {
			lastPage = 1;
		}
		// ���� �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (currentPage > lastPage) {
			currentPage = lastPage;
		}
		
		//.������ ����
		
		
		
		
		//���� 15���� ������ ����
		String first;
		//String second;
		String resultSubject;
		
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getTitle().length() > 18){ //������ 18���� �̻��̸�
				//0~19 �߶󳻱�//18�� ������
				first = list.get(i).getTitle().substring(0, 18);
				
				resultSubject = first + "..."; //"<br/>" + second;
				list.get(i).setTitle(resultSubject);
			}
			
		}
		//.���� 15���� ������ ���� ����
				
				
		
		
		//���� ������ �ѹ�
		int number = numberCount-(page.getCurrentPage()-1)*blockCount;
		//.���� ������ �ѹ� ����
		
		
		
		
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pagingHtml", pagingHtml);
		
		request.setAttribute("list", list);
		request.setAttribute("number", number); //�۳ѹ� - �������� �����Ǵ� �Խñ��� ����
		
		request.setAttribute("searchingNow", searchingNow); // ��ü,�˻����� �Ǵ���
		
		
		return "/view/menu6/recruit_application/recruitList.jsp";
	}
	
	
	
	
	/*
	//�б��޽��η�Ǯ(����) (�˻���) ����Ʈ
	@RequestMapping("/inspectionResultSearch.do")
	public String inspectionResultSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// ��ü��, �˻��� �Ǵܰ�.
		searchingNow = 1; //0 == ��ü��//1 == �˻���//
		
		request.setCharacterEncoding("euc-kr");
		
		//����ڰ� �Է��� ��
		String searchType = request.getParameter("searchType"); //�˻� ���� // ����,����,�ۼ���
		String userinput = request.getParameter("userinput"); //�˻���
		
		
		//�˻�����&�˻�� �����ϴ� ���ڵ� �˻�
		if(searchType.equals("title")){
			//���� �˻�
			list = sqlMapper.queryForList("InspectionResult.selectWithTitle", userinput);
		}else if(searchType.equals("writer")){
			//�ۼ��� �˻�
			list = sqlMapper.queryForList("InspectionResult.selectWithWriter", userinput);
		}
		
		
			
		//������ó��
		blockCount = 10;// �� �������� �Խù��� ��
		blockPage = 5;// �� ȭ�鿡 ������ ������ ��
		serviceName = "inspectionResultSearch";// ȣ�� URI ����
		totalCount = list.size(); // ��ü �� ����
		
		//currentPage �ʱ�ȭ
		if(null == request.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		page = new PagingAction(serviceName, currentPage, totalCount, blockCount, blockPage, searchType, userinput); // pagingAction ��ü ����.
		pagingHtml = page.getPagingHtml().toString(); // ������ HTML ����.

		int lastCount = totalCount; // ������ ���ڵ� = ����
		
		
		// ���� �������� ������ ���� ��ȣ�� ��ü�� ������ �� ��ȣ���� ������ lastCount�� +1 ��ȣ�� ����.
		if (page.getEndCount() < totalCount)
			lastCount = page.getEndCount() + 1;
		
		
		// ��ü ����Ʈ���� ���� ��������ŭ�� ����Ʈ�� �����´�.
		list = list.subList(page.getStartCount(), lastCount);
		//.������ó�� ����
		
		
		//�˻��� ����
		if(userinput.length()>8){ // ����� �Է°��� 8�� �̻��� ���
			tempInput = userinput.substring(0, 8) + "..."; // 8���� + ... �߰�
		}else{ // �����ϰ��
			tempInput = userinput;
		}
		
		
		//����Ʈ �۹�ȣ ���� ���
		int number=totalCount-(page.getCurrentPage()-1)*blockCount;
				
		
		
		request.setAttribute("number", number);	
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pagingHtml", pagingHtml);
		request.setAttribute("list", list);
		
		request.setAttribute("searchType", searchType);
		request.setAttribute("userinput", userinput);
		
		request.setAttribute("tempInput", tempInput);
		request.setAttribute("totalCount", totalCount);
		
		request.setAttribute("searchingNow", searchingNow);
		
		return  "/view/menu2/inspectionResultList.jsp";
	}
	*/
	
	
}
