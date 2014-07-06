package header.member.controller;

import header.member.dto.MemberDTO;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;


@Controller
public class MemberCreate {
	//ȸ������ ���̵�,�̸��� �ߺ�Ȯ�� List
	private List<MemberDTO> list = new ArrayList<MemberDTO>();
	private String viewPath; //���� �� ���
	private int notFound; //id�ߺ� ������ �Ǵ��ϴ� ���� // 0 ���ߺ�, 1 �ߺ�
	
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
	
	
	
	//ȸ������ ��
	@RequestMapping("/memberCreateFrom.do")
	public String memberCreateFrom() throws Exception{
		return "/view/member/memberCreate.jsp";
	}
	
	
	//ȸ������ db insert
	@RequestMapping("/memberCreate.do")
	public String memberCreate(HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("euc-kr");
		
		//����ڰ� �Է��� ����
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String member_name = request.getParameter("member_name");
		String school_name = request.getParameter("school_name");
		String school_type = request.getParameter("school_type");
		String position = request.getParameter("position");
		String sen_email = request.getParameter("sen_email");
		Calendar today = Calendar.getInstance(); //��¥

		
		//����� ���� DB insert
		paramClass.setUser_id(user_id);
		paramClass.setUser_pw(user_pw);
		paramClass.setMember_name(member_name);
		paramClass.setSchool_name(school_name);
		paramClass.setPosition(position);
		paramClass.setSen_email(sen_email);
		paramClass.setAdmin_yn("n");
		paramClass.setApprove_yn("n");
		paramClass.setSchool_type(school_type);
		paramClass.setReg_date(today.getTime());
		
		sqlMapper.insert("Member.insertMember",paramClass);
		
		return "redirect:/main.do";
	}
	
	
	
	
	
	//���̵��ߺ�üũ ��
	@RequestMapping("/checkUser_idFrom.do")
	public String checkUser_idFrom(HttpServletRequest request) throws Exception{
		//����ڰ� �Է��� ����
		String user_id = request.getParameter("user_id");
		
		
		if(null == request.getParameter("notFound")){
			notFound = 2;
		}else{
			notFound = Integer.parseInt(request.getParameter("notFound"));
		}
		
		
		request.setAttribute("user_id", user_id);
		request.setAttribute("notFound", notFound);
		
		return "/view/member/checkUser_idFrom.jsp";
	}
	
	
	//���̵��ߺ�üũ
	@RequestMapping("/checkUser_id.do")
	public String checkUser_id(HttpServletRequest request) throws Exception{
		//����ڰ� �Է��� ����
		String user_id = request.getParameter("user_id");
		
		
		//�ش� ���̵� �ִ��� ������ �Ǵ�
		Integer count = (Integer) sqlMapper.queryForObject("Member.selectUser_id", user_id);
		
		if(count == 0){ //id�ߺ��� �ƴ� ���
			viewPath = "redirect:/checkUser_idFrom.do?user_id="+user_id+"&notFound=0";
		}else{ //id�ߺ��� ���
			viewPath = "redirect:/checkUser_idFrom.do?user_id="+user_id+"&notFound=1";
		}
		
		
		return viewPath;
	}
	
	
	
}
