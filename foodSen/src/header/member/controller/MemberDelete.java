package header.member.controller;

import header.member.dto.MemberDTO;
import java.io.IOException;
import java.io.Reader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/*
 * �ۼ���: �����
 * ��  ��: ȸ��Ż�� Ŭ����
 * 			1. ȸ������ delete�޼ҵ�
 * 			2. delete �� ȸ������(��й�ȣ) ��Ȯ��
*/

@Controller
public class MemberDelete {
	
	//ȸ��Ż�� delete DTO
	private MemberDTO resultClass = new MemberDTO(); // ȸ��Ż�� form DTO
	
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

	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//ȸ��Ż�� form
	@RequestMapping("/memberDeleteFrom.do")
	public String memberDeleteFrom(HttpServletRequest request, HttpSession session) throws Exception{
		
		//���ڵ�����
		request.setCharacterEncoding("euc-kr");
		
		//���缼�� (���� �α��ε� ����ڰ� ȸ��Ż�� ��û)
		String session_id = (String) session.getAttribute("session_id");
		
		//���� �α��ε� ������� ���ڵ带 ����(��й�ȣ ��Ȯ���� ����)
		resultClass = (MemberDTO)sqlMapper.queryForObject("Member.selectMemberOne", session_id); //�ش缼���� ���ڵ� get
		
		request.setAttribute("resultClass", resultClass);
		return "/view/member/memberDelete.jsp";
	}
	
	//ȸ��Ż�� DB delete
	@RequestMapping("memberDelete.do")
	public String memberDelete(HttpSession session) throws Exception{
		
		//Ż�� ��û�� ����� ����
		String session_id = (String) session.getAttribute("session_id");
		
		//�ش� ���ڵ� delete
		sqlMapper.delete("Member.deleteMember", session_id);
		
		//�������� (ȸ���������, �α׾ƿ��� ���� ��������)
		session.invalidate();
		
		return "redirect:/main.do";
	}
	
} //end of class
