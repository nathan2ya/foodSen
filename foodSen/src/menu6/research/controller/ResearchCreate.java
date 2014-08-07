package menu6.research.controller;


import header.member.dto.MemberDTO;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import menu2.inspectionResult.dto.InspectionResultDTO;
import menu6.research.dto.ResearchDTO;
import menu6.research.dto.ResearchDTO1;
import menu6.research.dto.ResearchDTO2;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import common.Constants;

/*
 * 작성자: 류재욱
 * 설  명: 설문조사 insert 클래스
 * 용  도: 설문조사 게시글 등록을 위함.
*/

@Controller
public class ResearchCreate {
	
	//설문조사
	private ResearchDTO paramClass = new ResearchDTO();//설문조사 정보
	private ResearchDTO resultClass = new ResearchDTO();//설문조사 정보(최대시퀀스get)
	
	private ResearchDTO1 paramClass1 = new ResearchDTO1();//설문조사 문제
	private ResearchDTO1 resultClass1 = new ResearchDTO1(); //설문조사 문자(최대시퀀스 get)
	
	private ResearchDTO2 paramClass2 = new ResearchDTO2();//설문조사 문항
	
	//최대시퀀스넘버
	private int seq;//설문조사 정보
	private int surq_seq;//설문조사 문제
	
	//설문조사 문제
	private String surq_title1;
	private String surq_title2;
	private String surq_title3;
	private String surq_title4;
	private String surq_title5;
	private String surq_title6;
	private String surq_title7;
	private String surq_title8;
	private String surq_title9;
	private String surq_title10;
	private String surq_title11;
	private String surq_title12;
	private String surq_title13;
	private String surq_title14;
	private String surq_title15;
	private String surq_title16;
	
	//설문조사 문항
	private String item11;
	private String item21;
	private String item31;
	private String item41;
	private String item51;
	
	private String item12;
	private String item22;
	private String item32;
	private String item42;
	private String item52;
	
	private String item13;
	private String item23;
	private String item33;
	private String item43;
	private String item53;
	
	private String item14;
	private String item24;
	private String item34;
	private String item44;
	private String item54;
	
	private String item15;
	private String item25;
	private String item35;
	private String item45;
	private String item55;
	
	private String item16;
	private String item26;
	private String item36;
	private String item46;
	private String item56;
	
	private String item17;
	private String item27;
	private String item37;
	private String item47;
	private String item57;
	
	private String item18;
	private String item28;
	private String item38;
	private String item48;
	private String item58;
	
	private String item19;
	private String item29;
	private String item39;
	private String item49;
	private String item59;
	
	private String item110;
	private String item210;
	private String item310;
	private String item410;
	private String item510;
	
	private String item111;
	private String item211;
	private String item311;
	private String item411;
	private String item511;
	
	private String item112;
	private String item212;
	private String item312;
	private String item412;
	private String item512;
	
	private String item113;
	private String item213;
	private String item313;
	private String item413;
	private String item513;
	
	private String item114;
	private String item214;
	private String item314;
	private String item414;
	private String item514;
	
	private String item115;
	private String item215;
	private String item315;
	private String item415;
	private String item515;
	
	private String item116;
	private String item216;
	private String item316;
	private String item416;
	private String item516;
	
	//DB커넥트 인스턴스 변수
	SqlMapClientTemplate ibatis = null;
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	//DB커넥트 생성자
	public ResearchCreate() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		reader.close();
	}
	//.DB커넥트 생성자 버전 끝
	
	
	//설문조사 입력폼
	@RequestMapping("/researchCreateForm.do")
	public String researchCreateForm(){
		return "/view/menu6/research/researchCreate.jsp";
	}
	
	//설문조사 DB insert
	@RequestMapping(value="/researchCreate.do", method=RequestMethod.POST)
	public String researchCreate(HttpServletRequest request, HttpSession session) throws Exception{
		
		//인코딩
		request.setCharacterEncoding("euc-kr");
		//날짜
		Calendar today = Calendar.getInstance();
		
		//작성한 사용자(현재 로그인한 세션아이디)
		String session_id = (String) session.getAttribute("session_id");
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		/*
		 * 설문조사 정보 insert
		 * 설문조사 게시글 데이터
		 * PK : sur_seq
		*/
		//사용자가 입력한 값(정보)
		String sur_title = request.getParameter("sur_title");
		String sur_sat_date = request.getParameter("sur_sat_date");
		String sur_end_date = request.getParameter("sur_end_date");
		String que_cnt = request.getParameter("que_cnt");
		
		//DTO Set()
		paramClass.setSur_title(sur_title);
		paramClass.setSur_sat_date(sur_sat_date);
		paramClass.setSur_end_date(sur_end_date);
		paramClass.setQue_cnt(que_cnt);
		paramClass.setHits(0);
		paramClass.setWriter(session_id);
		paramClass.setReg_name(session_id);
		paramClass.setReg_date(today.getTime());
		paramClass.setUdt_name(session_id);
		paramClass.setUdt_date(today.getTime());
		sqlMapper.insert("Research.insertResearch", paramClass);
		
		//-----------------------------------------------------------------------------------------------------------------------------------------//
		
		/*
		 * 설문조사 문제 insert
		 * 설문조사 정보에 포함된 문제
		 * PK : surq_seq
		 * FK : sur_seq
		 * 
		 * 최대 16개의 문제가 들어갈 수 있으며,
		 * 16개는 레코드 단위로 들어간다.
		*/
		
		//설문조사(정보)의 시퀀스넘버 get
		resultClass = (ResearchDTO) sqlMapper.queryForObject("Research.selectLastNum");
		int sur_seq = (int)(resultClass.getSur_seq());
		
		//설문조사 개수
		int que_cnt_size = Integer.parseInt(que_cnt);
		
		//설문조사 문제 공통으로 들어가는 레코드.
		paramClass1.setSur_seq(sur_seq);
		paramClass1.setWriter(session_id);
		paramClass1.setReg_name(session_id);
		paramClass1.setReg_date(today.getTime());
		paramClass1.setUdt_name(session_id);
		paramClass1.setUdt_date(today.getTime());
		
		//설문조사 문항 공통으로 들어가는 레코드
		paramClass2.setSur_seq(sur_seq);
		paramClass2.setWriter(session_id);
		paramClass2.setReg_name(session_id);
		paramClass2.setReg_date(today.getTime());
		paramClass2.setUdt_name(session_id);
		paramClass2.setUdt_date(today.getTime());
		
		//설문조사 문제 insert(최대16번 insert)
		//각 문제에 포함된 문항 insert

		
		/*
		 * 사용자가 정한 문항개수만큼 문제, 문항에 대해 insert를 실시한다.
		 * 설문조사 문제 insert (1번~최대16번 실행)
		 *    각 문제에 포함된 문항5개 insert (1번~최대16번 실행)
		*/
		if(1 <= que_cnt_size){
			surq_title1 = request.getParameter("surq_title1");
			paramClass1.setSurq_title(surq_title1);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);
			
			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			//사용자가 입력한 값(문항)
			item11 = request.getParameter("item11");
			item21 = request.getParameter("item21");
			item31 = request.getParameter("item31");
			item41 = request.getParameter("item41");
			item51 = request.getParameter("item51");
			
			paramClass2.setSurq_seq(surq_seq);//1번문제의 시퀀스
			paramClass2.setSuri_title1(item11);
			paramClass2.setSuri_title2(item21);
			paramClass2.setSuri_title3(item31);
			paramClass2.setSuri_title4(item41);
			paramClass2.setSuri_title5(item51);
			paramClass2.setSuri_num1(11);
			paramClass2.setSuri_num2(21);
			paramClass2.setSuri_num3(31);
			paramClass2.setSuri_num4(41);
			paramClass2.setSuri_num5(51);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(2 <= que_cnt_size){
			surq_title2 = request.getParameter("surq_title2");
			paramClass1.setSurq_title(surq_title2);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(3 <= que_cnt_size){
			surq_title3 = request.getParameter("surq_title3");
			paramClass1.setSurq_title(surq_title3);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(4 <= que_cnt_size){
			surq_title4 = request.getParameter("surq_title4");
			paramClass1.setSurq_title(surq_title4);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(5 <= que_cnt_size){
			surq_title5 = request.getParameter("surq_title5");
			paramClass1.setSurq_title(surq_title5);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(6 <= que_cnt_size){
			surq_title6 = request.getParameter("surq_title6");
			paramClass1.setSurq_title(surq_title6);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(7 <= que_cnt_size){
			surq_title7 = request.getParameter("surq_title7");
			paramClass1.setSurq_title(surq_title7);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(8 <= que_cnt_size){
			surq_title8 = request.getParameter("surq_title8");
			paramClass1.setSurq_title(surq_title8);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(9 <= que_cnt_size){
			surq_title9 = request.getParameter("surq_title9");
			paramClass1.setSurq_title(surq_title9);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(10 <= que_cnt_size){
			surq_title10 = request.getParameter("surq_title10");
			paramClass1.setSurq_title(surq_title10);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(11 <= que_cnt_size){
			surq_title11 = request.getParameter("surq_title11");
			paramClass1.setSurq_title(surq_title11);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(12 <= que_cnt_size){
			surq_title12 = request.getParameter("surq_title12");
			paramClass1.setSurq_title(surq_title12);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(13 <= que_cnt_size){
			surq_title13 = request.getParameter("surq_title13");
			paramClass1.setSurq_title(surq_title13);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(14 <= que_cnt_size){
			surq_title14 = request.getParameter("surq_title14");
			paramClass1.setSurq_title(surq_title14);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(15 <= que_cnt_size){
			surq_title15 = request.getParameter("surq_title15");
			paramClass1.setSurq_title(surq_title15);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		if(16 <= que_cnt_size){
			surq_title16 = request.getParameter("surq_title16");
			paramClass1.setSurq_title(surq_title16);
			sqlMapper.insert("Research.insertResearch1", paramClass1);
		}
		
		
		return "redirect:/researchList.do"; //리스트로 리다이렉트
	}
} //end of class
