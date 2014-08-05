package menu6.recruit.controller;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import menu6.recruit.dto.RecruitDTO;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import common.PagingAction;

/*
 * �ۼ���: �����
 * ��  ��: �б��޽��η�Ǯ(����) list Ŭ����
 * ��  ��: �б��޽��η�Ǯ(����) �Խñ� ����Ʈ ����� ����.
*/

@Controller
public class RecruitList {
	
	//����Ʈ
	private List<RecruitDTO> list = new ArrayList<RecruitDTO>();
	
	//�˻���
	private String userInput;
	private int searchingNow; // ��ü��, �˻����� �Ǵ��Ͽ� ���� ������ �ǰ����� ����
	private String subType; //�˻�����
	private String subValue; //�˻���
	
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
		
		/*
		 * PagingAction Ŭ������ �̿��Ͽ� ����������
		 * ����Խ����� ������������ ���ڵ尳���� �����Ͽ� �Ķ���ͷ� ȣ��
		*/
		//PagingAction �Ķ���� ����
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
				resultSubject = first + "..."; 
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
	
	//�б��޽��η�Ǯ(����) (�˻���) ����Ʈ
	@RequestMapping("/recruitSearch.do")
	public String recruitSearchList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// ��ü��, �˻��� �Ǵܰ�.
		searchingNow = 1; //0 == ��ü��//1 == �˻���//
		request.setCharacterEncoding("euc-kr");
		
		//����ڰ� �Է��� ��
		String searchType = request.getParameter("searchType"); //�˻� ���� // ����,�ٹ�����,����,�б���
		
		
		//�˻� Ÿ�Կ� ���� �ٸ� �߰��Է� ����Ʈ�� ���� list�� get
		if(searchType.equals("job")){
			subType = "job";
			subValue = request.getParameter("job");
			list = sqlMapper.queryForList("Recruit.selectWithJob", subValue);
			request.setAttribute("job", subValue);
		}else if(searchType.equals("gubun")){
			subType = "gubun";
			subValue = request.getParameter("gubun");
			list = sqlMapper.queryForList("Recruit.selectWithGubun", subValue);
			request.setAttribute("gubun", subValue);
		}else if(searchType.equals("loc_seq")){
			subType = "loc_seq";
			subValue = request.getParameter("loc_seq");
			list = sqlMapper.queryForList("Recruit.selectWithLoc", subValue);
			request.setAttribute("loc_seq", subValue);
		}else{
			subType = "school_type";
			subValue = request.getParameter("school_type");
			list = sqlMapper.queryForList("Recruit.selectWithSchool_type", subValue);
			request.setAttribute("school_type", subValue);
		}
		//.�˻� Ÿ�Կ� ���� �ٸ� �߰��Է� ����Ʈ
		
			
		/*
		 * PagingAction Ŭ������ �̿��Ͽ� ����������
		 * ����Խ����� ������������ ���ڵ尳���� �����Ͽ� �Ķ���ͷ� ȣ��
		*/
		//PagingAction �Ķ���� ����
		blockCount = 10;// �� �������� �Խù��� ��
		blockPage = 5;// �� ȭ�鿡 ������ ������ ��
		serviceName = "recruitSearch";// ȣ�� URI ����
		totalCount = list.size(); // ��ü �� ����
		
		//currentPage �ʱ�ȭ
		if(null == request.getParameter("currentPage")){
			currentPage = 1;
		}else{
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		page = new PagingAction(serviceName, currentPage, totalCount, blockCount, blockPage, searchType, subType, subValue); // pagingAction ��ü ����.
		pagingHtml = page.getPagingHtml().toString(); // ������ HTML ����.
		int lastCount = totalCount; // ������ ���ڵ� = ����
		
		
		// ���� �������� ������ ���� ��ȣ�� ��ü�� ������ �� ��ȣ���� ������ lastCount�� +1 ��ȣ�� ����.
		if (page.getEndCount() < totalCount)
			lastCount = page.getEndCount() + 1;
		
		
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
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("searchingNow", searchingNow);
		return "/view/menu6/recruit_application/recruitList.jsp";
	}
	
} //end of class
