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

/*
 * �ۼ���: �����
 * ��  ��: �������� delete Ŭ����
 * ��  ��: �������� �Խñ� ����Ʈ�� �����ֱ� ����.
*/

@Controller
public class ResearchDelete {
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public ResearchDelete() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	//-----------------------------------------------------------------------------------------------------------------------------------------//
	
	//�������� ����
	@RequestMapping("/researchDelete.do")
	public String researchList(HttpServletRequest request) throws SQLException{
		int sur_seq = Integer.parseInt(request.getParameter("sur_seq"));
		
		//��������(����) ����
		sqlMapper.delete("Research.deleteResearch", sur_seq);
		
		//��������(����) ����
		sqlMapper.delete("Research.deleteResearch1", sur_seq);
		
		//��������(����) ����
		sqlMapper.delete("Research.deleteResearch2", sur_seq);
		
		//��������(���) ����
		sqlMapper.delete("Research.deleteResearch3", sur_seq);
		
		return "redirect:/researchList.do"; //����Ʈ�� �����̷�Ʈ
	}
	
} //end of class
