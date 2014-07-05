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
public class MemberDelete {
	
	private MemberDTO resultClass = new MemberDTO(); // ȸ��Ż�� �� DTO
	private MemberDTO paramClass = new MemberDTO(); // �α��� ��й�ȣ Ȯ�� DTO
	
	private String viewPath; // ���� �� ���
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public MemberDelete() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	
	//ȸ��Ż�� ��
	@RequestMapping("/memberDeleteFrom.do")
	public String memberDeleteFrom(HttpServletRequest request, HttpSession session) throws Exception{
		request.setCharacterEncoding("euc-kr");
		
		//���缼��
		String session_id = (String) session.getAttribute("session_id");
		
		//�ش缼���� ���ڵ� ����
		resultClass = (MemberDTO)sqlMapper.queryForObject("Member.selectMemberOne", session_id);
		
		request.setAttribute("resultClass", resultClass);
		
		return "/view/member/memberDelete.jsp";
	}
	
	
	//ȸ��Ż�� ����
	@RequestMapping("memberDelete.do")
	public String memberDelete(HttpSession session) throws Exception{
		
		//Ż�� ��û�� ����� ����
		String session_id = (String) session.getAttribute("session_id");
		
		//�ش� ���ڵ� delete
		sqlMapper.delete("Member.deleteMember", session_id);
		
		//��������
		session.invalidate();
		
		return "redirect:/main.do";
	}
}
