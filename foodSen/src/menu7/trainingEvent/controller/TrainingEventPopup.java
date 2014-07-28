package menu7.trainingEvent.controller;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import menu7.trainingEvent.dto.TrainingEventDTO;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;


@Controller
public class TrainingEventPopup {
	
	//�̹�����θ� �ҷ����� ���� DTO
	private TrainingEventDTO resultClass = new TrainingEventDTO();
	
	//�̹��� ����
	private int cnt;
	
	//DBĿ��Ʈ �ν��Ͻ� ����
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DBĿ��Ʈ ������
	public TrainingEventPopup() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DBĿ��Ʈ ������ ���� ��
	
	
	
	//�̹��� �˾�â ����
	@RequestMapping("/trainingEventPrevImg.do")
	public String trainingEventPrevImg(HttpServletRequest request) throws SQLException{
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		//�̹�����ο� ������ �ľ��ϱ� ���ؼ� get��
		resultClass = (TrainingEventDTO)sqlMapper.queryForObject("TrainingEvent.selectTrainingEventOne", seq);
		
		cnt = 0; //��ȣ�� �ɶ� �ٽ� �ʱ�ȭ ���� ī��Ʈ �ϱ� ����.
		
		//�̹��� ������ �ľ�
		if(resultClass.getImg1() != null){
			cnt++;
		}
		if(resultClass.getImg2() != null){
			cnt++;
		}
		if(resultClass.getImg3() != null){
			cnt++;
		}
		//.�̹��� ������ �ľ� ����
		
		
		System.out.println("Popup���� ȣ�� // �̹������� : "+cnt);
		
		request.setAttribute("cnt", cnt); // ��ü,�˻����� �Ǵ���
		request.setAttribute("resultClass", resultClass); //�̹������ ����
		
		return "/view/menu7/trainingEvent/trainingEventPopup.jsp";
	}
	
	
}

