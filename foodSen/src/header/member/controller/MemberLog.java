package header.member.controller;

import header.member.dto.MemberDTO;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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


@Controller
public class MemberLog {
	
	private MemberDTO paramClass = new MemberDTO(); // �α��� ��й�ȣ Ȯ�� DTO
	private MemberDTO resultClass = new MemberDTO(); // ����� ���̵��� ���ڵ� ���� �������� DTO
	
	private String viewPath; // ���� �� ���
	private int notFound; // �α��� ���� or ���п� ���� ����
	private String user_id; // �α��� ���п� ���� id ���庯��
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public MemberLog() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	
	
	//log ��
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request){
		
		//�α��� ���н� else�������� �ʱ�ȭ��
		if(null == request.getParameter("notFound") && null == request.getParameter("user_id")){ //�α��μ���
			notFound = 0;
			user_id = "";
		}else{ //�α��� ����
			notFound = Integer.parseInt(request.getParameter("notFound"));
			user_id = request.getParameter("user_id");
		}
		
		request.setAttribute("notFound", notFound);
		request.setAttribute("user_id", user_id);
		
		return "/view/member/memberLoginFrom.jsp";
	}
	
	
	//logIN ����
	@RequestMapping("/loginPro.do")
	public String loginPro(HttpServletRequest request, HttpSession session) throws Exception{
		
		//����ڰ� �α��� ��û�� �Է°�
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		
		
		//���̵� ��й�ȣ ��ġ�ϴ� ���ڵ尡 �ִ��� �Ǵ�
		paramClass.setUser_id(user_id);
		paramClass.setUser_pw(user_pw);
		Integer count = (Integer) sqlMapper.queryForObject("Member.selectCountForLogin", paramClass);
		
		
		if(count==1){ // ���̵�, ��й�ȣ ��ġ
			//�ش� ���̵��� ���ڵ带 �ҷ���
			resultClass = (MemberDTO)sqlMapper.queryForObject("Member.selectMemberOne", user_id);
			
			session.setAttribute("session_id", resultClass.getUser_id()); // id ���ǻ���
			session.setAttribute("session_position", resultClass.getPosition()); //type ���ǻ���
			session.setAttribute("session_admin_yn", resultClass.getAdmin_yn()); // ������ Yes or No
			session.setAttribute("session_resultClass", resultClass); // ����� ���� ����
			
			viewPath = "redirect:/main.do";
		}else{ // ���̵�, ��й�ȣ ����ġ
			viewPath = "redirect:/login.do?notFound=1&user_id="+user_id; //notFound ���� 1�� �ʱ�ȭ, ����� �Է� ���̵� �α��������� �����̷�Ʈ
		}
		
		return viewPath;
	}
	
	
	//logOUT ����
	@RequestMapping("/logoutPro.do")
	public String logoutPro(HttpServletRequest request, HttpSession session) throws Exception{
		
		//��������
		session.invalidate();
		
		return "redirect:/login.do";
	}
	
}
