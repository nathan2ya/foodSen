package menu6.research.controller;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import menu6.research.dto.ResearchDTO;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import common.PagingAction;

/*
 * �ۼ���: �����
 * ��  ��: �������� list Ŭ����
 * ��  ��: �������� �Խñ� ����Ʈ�� �����ֱ� ����.
*/

@Controller
public class ResearchList {
	
	//�������� list
	private List<ResearchDTO> list = new ArrayList<ResearchDTO>();
	
	//�˻���
	private String tempInput;
	private int searchingNow; // ��ü��, �˻����� �Ǵ��Ͽ� ���� ������ �ǰ����� ���� //0 == ��ü��//1 == �˻���
	
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
	public ResearchList() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//�������� (��ü��) ����Ʈ
	@RequestMapping("/researchList.do")
	public String researchList(HttpServletRequest request) throws SQLException{
		
		//��ü��, �˻��� �Ǵܰ�.
		searchingNow = 0; //0 ��ü��//1 �˻���
		
		//��ü�� list
		list = sqlMapper.queryForList("Research.selectAll"); //��ü��
		int numberCount = list.size(); // ��ü ���ڵ� ���� (���� �۹�ȣ�� �����ϱ� ����)
		
		/*
		 * PagingAction Ŭ������ �̿��Ͽ� ����������
		 * ����Խ����� ������������ ���ڵ尳���� �����Ͽ� �Ķ���ͷ� ȣ��
		*/
		//PagingAction �Ķ���� ����
		blockCount = 10;// �� �������� �Խù��� ��
		blockPage = 5;// �� ȭ�鿡 ������ ������ ��
		serviceName = "researchList";// ȣ�� URI ����
		totalCount = list.size(); // ��ü �� ����
		//currentPage �ʱ�ȭ
		if(null == request.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		//PagingAction ȣ�� �� �����ȯ
		page = new PagingAction(serviceName, currentPage, totalCount, blockCount, blockPage); // pagingAction ��ü ����.
		pagingHtml = page.getPagingHtml().toString(); // ������ HTML ����(String ����ȯ)
		
		
		/*
		 * ���������� list���� startCound ~ endCound ��ŭ�� ���ڵ带 �߶󳻴� �����̴�.
		 * ���� : startCound, endCound �� ������������ ���� ���ǵǴ� �����̴�.
		*/
		int lastCount = totalCount; //���� ��ü���ڵ� ����
		if(page.getEndCount() < totalCount){ //���������� endCound�� / ���������� ������ totalCount ���� ������� +1 ����.(������ -1�߱⶧��..)
			lastCount = page.getEndCount() + 1; //+1
		}
		list = list.subList(page.getStartCount(), lastCount); // ��ü ����Ʈ���� ���� ��������ŭ�� ����Ʈ�� �����´�.
		
		
		//������������ ����
		lastPage = (int) Math.ceil((double) totalCount / blockCount); // ��ü���ڵ尳�� / ���������� ������ ���ڵ尳�� = �ݿø�(1�̶� ������ ������ 1���� �߰��ǹǷ�)
		
		//���ڵ� �������, �Ǵ� 1������ ���� ��� �������������� 1�� ����
		if (lastPage == 0) {
			lastPage = 1;
		}
		
		//����ڵ�_ ������������ ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (currentPage > lastPage) {
			currentPage = lastPage;
		}
		//.������ ����
		
		/*
		 * ���� 18���� ������ ����
		 * ���� list���� ������ 18���� �̻��� �͸� get �� ��,
		 * �������� �ڿ� �ٽ� list�� set �ϰ� �ִ�. (list�� �ε����� set�ϴ� ������ : DB������ �ٲ��� �ʱ� ����)
		*/
		String first; //18��
		String resultSubject; //���
		
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getSur_title().length() > 18){ //������ 18���� �̻��̸�
				first = list.get(i).getSur_title().substring(0, 18); //18���� �߶�
				resultSubject = first + "..."; //18���� + "..."
				list.get(i).setSur_title(resultSubject); //���� for�� list�� �ε���.setTitle(�����ǵ� ����);
			}
		}
		//.���� 15���� ������ ���� ����
				
		//���� ������ �ѹ�
		int number = numberCount-(page.getCurrentPage()-1)*blockCount;
		
		//���糯¥
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentTime = sdf.format(cal.getTime());
		//.���糯¥
		
		request.setAttribute("currentTime", currentTime);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pagingHtml", pagingHtml);
		request.setAttribute("list", list);
		request.setAttribute("number", number); //�۳ѹ� - �������� �����Ǵ� �Խñ��� ����
		request.setAttribute("searchingNow", searchingNow); // ��ü,�˻����� �Ǵ���
		return "/view/menu6/research/researchList.jsp";
	}
	
	/*
	//�������� (�˻���) ����Ʈ
	@RequestMapping("/researchSearch.do")
	public String researchSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// ��ü��, �˻��� �Ǵܰ�.
		searchingNow = 1; //0 == ��ü��//1 == �˻���//
		
		//���ڵ� ����
		request.setCharacterEncoding("euc-kr");
		
		//����ڰ� �Է��� ��
		String searchType = request.getParameter("searchType"); //�˻� ���� // ����,����,�ۼ���
		String userinput = request.getParameter("userinput"); //�˻���
		
		//�˻�����&�˻�� �����ϴ� ���ڵ� �˻�
		if(searchType.equals("title")){
			list = sqlMapper.queryForList("InspectionResult.selectWithTitle", userinput); //���� �˻�
		}else if(searchType.equals("writer")){
			list = sqlMapper.queryForList("InspectionResult.selectWithWriter", userinput); //�ۼ��� �˻�
		}
			
		
		 * PagingAction Ŭ������ �̿��Ͽ� ����������
		 * ����Խ����� ������������ ���ڵ尳���� �����Ͽ� �Ķ���ͷ� ȣ��
		
		//PagingAction �Ķ���� ����
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
		
		//PagingAction ȣ�� �� �����ȯ
		page = new PagingAction(serviceName, currentPage, totalCount, blockCount, blockPage, searchType, userinput); // pagingAction ��ü ����.
		pagingHtml = page.getPagingHtml().toString(); // ������ HTML ����.
		
		
		 * ���������� list���� startCound ~ endCound ��ŭ�� ���ڵ带 �߶󳻴� �����̴�.
		 * ���� : startCound, endCound �� ������������ ���� ���ǵǴ� �����̴�.
		
		int lastCount = totalCount; // ������ ���ڵ� = ����
		if (page.getEndCount() < totalCount) //���������� endCound�� / ���������� ������ totalCount ���� ������� +1 ����.(������ -1�߱⶧��..)
			lastCount = page.getEndCount() + 1; //+1
		
		// ��ü ����Ʈ���� ���� ��������ŭ�� ����Ʈ�� �����´�.
		list = list.subList(page.getStartCount(), lastCount);
		//.������ó�� ����
		
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
} //end of class
