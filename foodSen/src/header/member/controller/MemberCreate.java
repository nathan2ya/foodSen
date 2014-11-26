package header.member.controller;

import header.member.dto.MemberDTO;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/*
 * �ۼ���: �����
 * ��  ��: ȸ������ Ŭ����.
 * 			1. ȸ������ isnert�޼ҵ�
 * 			2. insert ���� ȣ��Ǵ� ���̵��ߺ�üũ �޼ҵ�
*/

@Controller
public class MemberCreate {
	
	//�ּ��׽�Ʈ
	private String city;
	private String gun;
	
	//���̵��ߺ�üũ
	private String viewPath; //return���
	private int notFound; //ID�ߺ����θ� �Ǵ��ϴ� ����
	
	//ȸ������ insert DTO
	private MemberDTO paramClass = new MemberDTO();
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public MemberCreate() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//ȸ������ form
	@RequestMapping("/memberCreateFrom.do")
	public String memberCreateFrom() throws Exception{
		
		return "/view/member/memberCreate.jsp";
	}
	
	//ȸ������ DB insert
	@RequestMapping("/memberCreate.do")
	public String memberCreate(HttpServletRequest request) throws Exception{
		
		//���ڵ�����
		request.setCharacterEncoding("euc-kr");
		
		//����ڰ� �Է��� ����
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String member_name = request.getParameter("member_name");
		String school_name = request.getParameter("school_name");
		String school_type = request.getParameter("school_type");
		String position = request.getParameter("position");
		String sen_email = request.getParameter("sen_email");
		String phone = request.getParameter("phone");
		Calendar today = Calendar.getInstance(); //��¥
		//�ּ��׽�Ʈ
		String post1 = request.getParameter("post1");
		String post2 = request.getParameter("post2");
		String addr = request.getParameter("addr");
		String addr2 = request.getParameter("addr2");
		
		//��,�� �� ��,�� �з� ����
		int space = addr.indexOf(" ");
		city = addr.substring(0, space);//��,��
		String tempAddr = addr.substring(space+1);
		space = tempAddr.indexOf(" ");
		gun = tempAddr.substring(0, space);//��,��
		//��,�� �� ��,�� �з� ����
		
		System.out.println("�����ȣ : "+post1+"-"+post2);
		System.out.println("�ּ� : "+addr);
		System.out.println("���ּ� : "+addr2);
		System.out.println("#######################################");
		System.out.println("��,�� : "+city);
		System.out.println("��,�� : "+gun);
		//.�ּ��׽�Ʈ ����
		
		//����� ���� DB insert
		paramClass.setUser_id(user_id);
		paramClass.setUser_pw(user_pw);
		paramClass.setMember_name(member_name);
		paramClass.setSchool_name(school_name);
		paramClass.setPosition(position);
		paramClass.setSen_email(sen_email);
		paramClass.setPhone(phone);
		paramClass.setAdmin_yn("n");
		paramClass.setApprove_yn("n");
		paramClass.setSchool_type(school_type);
		paramClass.setReg_date(today.getTime());
		sqlMapper.insert("Member.insertMember",paramClass);
		
		return "redirect:/main.do";
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//���̵��ߺ�üũ form
	@RequestMapping("/checkUser_idFrom.do")
	public String checkUser_idFrom(HttpServletRequest request) throws Exception{
		
		//����ڰ� �Է��� ����
		String user_id = request.getParameter("user_id");
		
		/*
		 * ���̵��ߺ�üũ ������ ������ notFound �� ������ �����Ѵ�.
		 * notFound ��������
		 *  0 : �ߺ��ƴ�
		 *  1 : �ߺ�
		 *  2 : ��������
		*/
		if(null == request.getParameter("notFound")){ //notFound ���� ���� ���� ��� : ���� ���Խ�
			notFound = 2;
		}else{ //�ߺ��ƴ� 0, �ߺ� 1 �� �ʱ�ȭ.
			notFound = Integer.parseInt(request.getParameter("notFound"));
		}
		//.notFound ���� ����
		
		request.setAttribute("user_id", user_id);
		request.setAttribute("notFound", notFound);
		return "/view/member/checkUser_idFrom.jsp";
	}
	
	//���̵��ߺ�üũ ����
	@RequestMapping("/checkUser_id.do")
	public String checkUser_id(HttpServletRequest request) throws Exception{
		
		//����ڰ� �Է��� ����
		String user_id = request.getParameter("user_id");
		
		/*
		 * ���̵��ߺ�üũ ������ ������ notFound �� ������ �����Ѵ�.
		 *  1 : �ߺ�
		 *  2 : ��������
		*/
		Integer count = (Integer) sqlMapper.queryForObject("Member.selectUser_id", user_id); //�ش� ���̵� �ִ��� ������ �Ǵ�
		
		if(count == 0){ //����ڰ� �Է��� ������ ���� ���ڵ尡 ����, �ߺ��ȵ�
			viewPath = "redirect:/checkUser_idFrom.do?user_id="+user_id+"&notFound=0"; //notFound 0���� �ʱ�ȭ
		}else{ //����ڰ� �Է��� ������ ���� ���ڵ尡 ����, �ߺ���
			viewPath = "redirect:/checkUser_idFrom.do?user_id="+user_id+"&notFound=1"; //notFound 1�� �ʱ�ȭ
		}
		
		return viewPath;
	}
	
} //end of class
