<%@ page language="java" contentType="image/jpeg; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import = "org.jfree.chart.*" %>
<%@ page import ="chart.*"%>


<%
	int sur_seq = Integer.parseInt(request.getParameter("sur_seq"));
	//int cnt = Integer.parseInt(request.getParameter("cnt"));
	String cnt = request.getParameter("cnt");
	
	String[] title = request.getParameterValues("title");
	String[] i_title1 = request.getParameterValues("i_title1");
	String[] i_title2 = request.getParameterValues("i_title2");
	String[] i_title3 = request.getParameterValues("i_title3");
	String[] i_title4 = request.getParameterValues("i_title4");
	String[] i_title5 = request.getParameterValues("i_title5");

	ServletOutputStream sos = response.getOutputStream();
	BarChartBean bcb = new BarChartBean();
	JFreeChart chart = bcb.getBarChart(sur_seq, cnt, title, i_title1, i_title2, i_title3, i_title4, i_title5);
	ChartUtilities.writeChartAsPNG(sos, chart, 4500, 550); // 450*문제수
%>


	<!-- 
	request.setAttribute("sur_seq", sur_seq);
	request.setAttribute("cnt", cnt); //설문조사 문항개수
	request.setAttribute("title", title); //설문조사(문제배열)
	request.setAttribute("i_title1", i_title1); //설문조사(문항1배열)
	request.setAttribute("i_title2", i_title2); //설문조사(문항2배열)
	request.setAttribute("i_title3", i_title3); //설문조사(문항3배열)
	request.setAttribute("i_title4", i_title4); //설문조사(문항4배열)
	request.setAttribute("i_title5", i_title5); //설문조사(문항5배열)
	
	
	request.setAttribute("resultClass", resultClass);//설문조사(정보)레코드
	request.setAttribute("resultClass1", resultClass1);//설문조사(문제)레코드
	request.setAttribute("resultClass2", resultClass2); //설문조사(문항)
	request.setAttribute("resultClass3", resultClass3);//설문조사(결과)레코드
	request.setAttribute("res_cnt_arr", res_cnt_arr);//설문조사(결과)선택 카운트
	 -->