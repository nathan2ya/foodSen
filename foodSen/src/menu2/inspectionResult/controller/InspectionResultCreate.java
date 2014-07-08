package menu2.inspectionResult.controller;


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
public class InspectionResultCreate {
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public InspectionResultCreate() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	//����.���� �˻��� �Է���
	@RequestMapping("/inspectionResultCreateFrom.do")
	public String inspectionResultCreateFrom(){
		return "/view/menu2/inspectionResultCreate.jsp";
	}
	
	
	
	
	/*
	
	//����.���� �˻��� �Է�
	@RequestMapping(value="/inspectionResultCreate.do",method=RequestMethod.POST)
	public String inspectionResultCreate(MultipartHttpServletRequest request, HttpServletRequest request1, HttpServletResponse response1, HttpSession session, @ModelAttribute("SpringDTO") SpringDTO dto) throws Exception{
													//���Ͼ��ε��	request						//�Ķ���� request1				//�Ķ���� response1					 //���ǿ�							//�Ķ���� DTO�� �ڵ� set(), get()

		//.jsp���� ������Ʈ�� �ް� ������
		String message = request1.getParameter("message");
		//.jsp�� ������Ʈ�� ������ ������
		request1.setAttribute("message", message);

		
		
		//���� �����ϰ������
		session.setAttribute("session_id", result.getBuyer_id()); // (���Ǿ��̵� ����,�޾ƿ°���)
		
		
		
		//���� ���� ���� ���� ������ (���� ������ sessionID�� ������ �����Ǿ� �ִ� ���) 
		String sessionID = (String) session.getAttribute("sessionID");
		
		
		
		//���� ��¥,�ð� insert�ϰ� ������ - ����1
		Calendar today = Calendar.getInstance();
		today.getTime(); // ��ȯ���� Date��.
		
		
		//���� ��¥,�ð� �����ѻ��·� ��Ʈ������ �ޱ� - ����2
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_hhmmss");
		sdf.format(gc.getTime()); // ��ȯ���� String��
		
		
		
		//dto���� get�ϰ� ������
		System.out.println("����ڰ� �Է��� �޼��� : "+dto.getMessage());
		
		
		
		
		//���Ͼ��ε��Ҷ�
		MultipartFile file = request.getFile("upload"); // ���ε�� ����
		String orgName = file.getOriginalFilename(); // ����ڰ� ���ε��� ���� ���� �̸�
		
		String nowTime = sdf.format(gc.getTime());//����ð�
		String randNum = Integer.toString((int)(Math.random() * 999));//������ȣ
		String fileName = "img_spring_"+nowTime+"_"+randNum;//�������� ���ϸ�(img_spring_���糯¥_������ȣ)
		String fileExt = orgName.substring(orgName.lastIndexOf('.'));//�������� Ȯ����
		
		File save = new File(SPRING_TEST_FILE_PATH+fileName+fileExt); //������ ���� (���+���ϸ�+Ȯ����)
		file.transferTo(save);  // ���纻 ����
		
		//DB ���� ��� �����
		String temp = save.getPath().replace("\\", "/");
		String path = "/springEx"+temp.substring((temp.indexOf("save"))-1);
		
		
		//  dto�� set���ص� jsp���� �� �� �ִ�. (63�� @ModelAttribute�� ���� �ڵ� get()�Ǳ� ������)
		dto.setDestfile(path); 

		
		
		
		//DB�� insert �ϱ� (�۾���)
		sqlMapper.insert("insertSpring",dto);
		sqlMapper.update("insertSpring",dto);
		sqlMapper.delete("insertSpring",dto);
		
		//DB���� select �ϱ� (����Ʈ)
		list = sqlMapper.queryForList("selectSpring");
		
		
		//select 1����
		//private BoardDTO resultClass = new BoardDTO();
		resultClass = (BoardDTO)sqlMapper.queryForObject("Board.selectBoardOne", board_seq_num);
		
		
		request1.setAttribute("list", list);
		
		
		
		
		//���� ���� �������� (setAttribute ������ ����)
		return "/spring/formPro.jsp";
		//�����̷�Ʈ ��Ű��
		return "redirect:/listBoard.do";
	}
	
	*/
}
