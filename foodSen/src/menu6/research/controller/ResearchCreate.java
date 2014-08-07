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
			
			paramClass2.setSurq_seq(surq_seq);
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
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			//사용자가 입력한 값(문항)
			item12 = request.getParameter("item12");
			item22 = request.getParameter("item22");
			item32 = request.getParameter("item32");
			item42 = request.getParameter("item42");
			item52 = request.getParameter("item52");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item12);
			paramClass2.setSuri_title2(item22);
			paramClass2.setSuri_title3(item32);
			paramClass2.setSuri_title4(item42);
			paramClass2.setSuri_title5(item52);
			paramClass2.setSuri_num1(12);
			paramClass2.setSuri_num2(22);
			paramClass2.setSuri_num3(32);
			paramClass2.setSuri_num4(42);
			paramClass2.setSuri_num5(52);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(3 <= que_cnt_size){
			surq_title3 = request.getParameter("surq_title3");
			paramClass1.setSurq_title(surq_title3);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			//사용자가 입력한 값(문항)
			item13 = request.getParameter("item13");
			item23 = request.getParameter("item23");
			item33 = request.getParameter("item33");
			item43 = request.getParameter("item43");
			item53 = request.getParameter("item53");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item13);
			paramClass2.setSuri_title2(item23);
			paramClass2.setSuri_title3(item33);
			paramClass2.setSuri_title4(item43);
			paramClass2.setSuri_title5(item53);
			paramClass2.setSuri_num1(13);
			paramClass2.setSuri_num2(23);
			paramClass2.setSuri_num3(33);
			paramClass2.setSuri_num4(43);
			paramClass2.setSuri_num5(53);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(4 <= que_cnt_size){
			surq_title4 = request.getParameter("surq_title4");
			paramClass1.setSurq_title(surq_title4);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			//사용자가 입력한 값(문항)
			item14 = request.getParameter("item14");
			item24 = request.getParameter("item24");
			item34 = request.getParameter("item34");
			item44 = request.getParameter("item44");
			item54 = request.getParameter("item54");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item14);
			paramClass2.setSuri_title2(item24);
			paramClass2.setSuri_title3(item34);
			paramClass2.setSuri_title4(item44);
			paramClass2.setSuri_title5(item54);
			paramClass2.setSuri_num1(14);
			paramClass2.setSuri_num2(24);
			paramClass2.setSuri_num3(34);
			paramClass2.setSuri_num4(44);
			paramClass2.setSuri_num5(54);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(5 <= que_cnt_size){
			surq_title5 = request.getParameter("surq_title5");
			paramClass1.setSurq_title(surq_title5);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			
			//사용자가 입력한 값(문항)
			item15 = request.getParameter("item15");
			item25 = request.getParameter("item25");
			item35 = request.getParameter("item35");
			item45 = request.getParameter("item45");
			item55 = request.getParameter("item55");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item15);
			paramClass2.setSuri_title2(item25);
			paramClass2.setSuri_title3(item35);
			paramClass2.setSuri_title4(item45);
			paramClass2.setSuri_title5(item55);
			paramClass2.setSuri_num1(15);
			paramClass2.setSuri_num2(25);
			paramClass2.setSuri_num3(35);
			paramClass2.setSuri_num4(45);
			paramClass2.setSuri_num5(55);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(6 <= que_cnt_size){
			surq_title6 = request.getParameter("surq_title6");
			paramClass1.setSurq_title(surq_title6);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			
			//사용자가 입력한 값(문항)
			item16 = request.getParameter("item16");
			item26 = request.getParameter("item26");
			item36 = request.getParameter("item36");
			item46 = request.getParameter("item46");
			item56 = request.getParameter("item56");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item16);
			paramClass2.setSuri_title2(item26);
			paramClass2.setSuri_title3(item36);
			paramClass2.setSuri_title4(item46);
			paramClass2.setSuri_title5(item56);
			paramClass2.setSuri_num1(16);
			paramClass2.setSuri_num2(26);
			paramClass2.setSuri_num3(36);
			paramClass2.setSuri_num4(46);
			paramClass2.setSuri_num5(56);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(7 <= que_cnt_size){
			surq_title7 = request.getParameter("surq_title7");
			paramClass1.setSurq_title(surq_title7);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			
			//사용자가 입력한 값(문항)
			item17 = request.getParameter("item17");
			item27 = request.getParameter("item27");
			item37 = request.getParameter("item37");
			item47 = request.getParameter("item47");
			item57 = request.getParameter("item57");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item17);
			paramClass2.setSuri_title2(item27);
			paramClass2.setSuri_title3(item37);
			paramClass2.setSuri_title4(item47);
			paramClass2.setSuri_title5(item57);
			paramClass2.setSuri_num1(17);
			paramClass2.setSuri_num2(27);
			paramClass2.setSuri_num3(37);
			paramClass2.setSuri_num4(47);
			paramClass2.setSuri_num5(57);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(8 <= que_cnt_size){
			surq_title8 = request.getParameter("surq_title8");
			paramClass1.setSurq_title(surq_title8);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());

			//사용자가 입력한 값(문항)
			item18 = request.getParameter("item18");
			item28 = request.getParameter("item28");
			item38 = request.getParameter("item38");
			item48 = request.getParameter("item48");
			item58 = request.getParameter("item58");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item18);
			paramClass2.setSuri_title2(item28);
			paramClass2.setSuri_title3(item38);
			paramClass2.setSuri_title4(item48);
			paramClass2.setSuri_title5(item58);
			paramClass2.setSuri_num1(18);
			paramClass2.setSuri_num2(28);
			paramClass2.setSuri_num3(38);
			paramClass2.setSuri_num4(48);
			paramClass2.setSuri_num5(58);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(9 <= que_cnt_size){
			surq_title9 = request.getParameter("surq_title9");
			paramClass1.setSurq_title(surq_title9);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			//사용자가 입력한 값(문항)
			item19 = request.getParameter("item19");
			item29 = request.getParameter("item29");
			item39 = request.getParameter("item39");
			item49 = request.getParameter("item49");
			item59 = request.getParameter("item59");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item19);
			paramClass2.setSuri_title2(item29);
			paramClass2.setSuri_title3(item39);
			paramClass2.setSuri_title4(item49);
			paramClass2.setSuri_title5(item59);
			paramClass2.setSuri_num1(19);
			paramClass2.setSuri_num2(29);
			paramClass2.setSuri_num3(39);
			paramClass2.setSuri_num4(49);
			paramClass2.setSuri_num5(59);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(10 <= que_cnt_size){
			surq_title10 = request.getParameter("surq_title10");
			paramClass1.setSurq_title(surq_title10);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			//사용자가 입력한 값(문항)
			item110 = request.getParameter("item110");
			item210 = request.getParameter("item210");
			item310 = request.getParameter("item310");
			item410 = request.getParameter("item410");
			item510 = request.getParameter("item510");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item110);
			paramClass2.setSuri_title2(item210);
			paramClass2.setSuri_title3(item310);
			paramClass2.setSuri_title4(item410);
			paramClass2.setSuri_title5(item510);
			paramClass2.setSuri_num1(110);
			paramClass2.setSuri_num2(210);
			paramClass2.setSuri_num3(310);
			paramClass2.setSuri_num4(410);
			paramClass2.setSuri_num5(510);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(11 <= que_cnt_size){
			surq_title11 = request.getParameter("surq_title11");
			paramClass1.setSurq_title(surq_title11);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			//사용자가 입력한 값(문항)
			item111 = request.getParameter("item111");
			item211 = request.getParameter("item211");
			item311 = request.getParameter("item311");
			item411 = request.getParameter("item411");
			item511 = request.getParameter("item511");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item111);
			paramClass2.setSuri_title2(item211);
			paramClass2.setSuri_title3(item311);
			paramClass2.setSuri_title4(item411);
			paramClass2.setSuri_title5(item511);
			paramClass2.setSuri_num1(111);
			paramClass2.setSuri_num2(211);
			paramClass2.setSuri_num3(311);
			paramClass2.setSuri_num4(411);
			paramClass2.setSuri_num5(511);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(12 <= que_cnt_size){
			surq_title12 = request.getParameter("surq_title12");
			paramClass1.setSurq_title(surq_title12);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			//사용자가 입력한 값(문항)
			item112 = request.getParameter("item112");
			item212 = request.getParameter("item212");
			item312 = request.getParameter("item312");
			item412 = request.getParameter("item412");
			item512 = request.getParameter("item512");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item112);
			paramClass2.setSuri_title2(item212);
			paramClass2.setSuri_title3(item312);
			paramClass2.setSuri_title4(item412);
			paramClass2.setSuri_title5(item512);
			paramClass2.setSuri_num1(112);
			paramClass2.setSuri_num2(212);
			paramClass2.setSuri_num3(312);
			paramClass2.setSuri_num4(412);
			paramClass2.setSuri_num5(512);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(13 <= que_cnt_size){
			surq_title13 = request.getParameter("surq_title13");
			paramClass1.setSurq_title(surq_title13);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			//사용자가 입력한 값(문항)
			item113 = request.getParameter("item113");
			item213 = request.getParameter("item213");
			item313 = request.getParameter("item313");
			item413 = request.getParameter("item413");
			item513 = request.getParameter("item513");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item113);
			paramClass2.setSuri_title2(item213);
			paramClass2.setSuri_title3(item313);
			paramClass2.setSuri_title4(item413);
			paramClass2.setSuri_title5(item513);
			paramClass2.setSuri_num1(113);
			paramClass2.setSuri_num2(213);
			paramClass2.setSuri_num3(313);
			paramClass2.setSuri_num4(413);
			paramClass2.setSuri_num5(513);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(14 <= que_cnt_size){
			surq_title14 = request.getParameter("surq_title14");
			paramClass1.setSurq_title(surq_title14);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			//사용자가 입력한 값(문항)
			item114 = request.getParameter("item114");
			item214 = request.getParameter("item214");
			item314 = request.getParameter("item314");
			item414 = request.getParameter("item414");
			item514 = request.getParameter("item514");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item114);
			paramClass2.setSuri_title2(item214);
			paramClass2.setSuri_title3(item314);
			paramClass2.setSuri_title4(item414);
			paramClass2.setSuri_title5(item514);
			paramClass2.setSuri_num1(114);
			paramClass2.setSuri_num2(214);
			paramClass2.setSuri_num3(314);
			paramClass2.setSuri_num4(414);
			paramClass2.setSuri_num5(514);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(15 <= que_cnt_size){
			surq_title15 = request.getParameter("surq_title15");
			paramClass1.setSurq_title(surq_title15);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			//사용자가 입력한 값(문항)
			item115 = request.getParameter("item115");
			item215 = request.getParameter("item215");
			item315 = request.getParameter("item315");
			item415 = request.getParameter("item415");
			item515 = request.getParameter("item515");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item115);
			paramClass2.setSuri_title2(item215);
			paramClass2.setSuri_title3(item315);
			paramClass2.setSuri_title4(item415);
			paramClass2.setSuri_title5(item515);
			paramClass2.setSuri_num1(115);
			paramClass2.setSuri_num2(215);
			paramClass2.setSuri_num3(315);
			paramClass2.setSuri_num4(415);
			paramClass2.setSuri_num5(515);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		if(16 <= que_cnt_size){
			surq_title16 = request.getParameter("surq_title16");
			paramClass1.setSurq_title(surq_title16);
			//설문조사 문제 insert
			sqlMapper.insert("Research.insertResearch1", paramClass1);

			//설문조사 문제 최대시퀀스
			resultClass1 = (ResearchDTO1) sqlMapper.queryForObject("Research.selectLastNum1");
			int surq_seq = (int)(resultClass1.getSurq_seq());
			
			//사용자가 입력한 값(문항)
			item116 = request.getParameter("item116");
			item216 = request.getParameter("item216");
			item316 = request.getParameter("item316");
			item416 = request.getParameter("item416");
			item516 = request.getParameter("item516");
			
			paramClass2.setSurq_seq(surq_seq);
			paramClass2.setSuri_title1(item116);
			paramClass2.setSuri_title2(item216);
			paramClass2.setSuri_title3(item316);
			paramClass2.setSuri_title4(item416);
			paramClass2.setSuri_title5(item516);
			paramClass2.setSuri_num1(116);
			paramClass2.setSuri_num2(216);
			paramClass2.setSuri_num3(316);
			paramClass2.setSuri_num4(416);
			paramClass2.setSuri_num5(516);
			sqlMapper.insert("Research.insertResearch2", paramClass2);
		}
		
		return "redirect:/researchList.do"; //리스트로 리다이렉트
	}
} //end of class
