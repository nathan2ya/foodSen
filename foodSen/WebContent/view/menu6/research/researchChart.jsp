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
	ChartUtilities.writeChartAsPNG(sos, chart, 4500, 550); // 450*������
%>


	<!-- 
	request.setAttribute("sur_seq", sur_seq);
	request.setAttribute("cnt", cnt); //�������� ���װ���
	request.setAttribute("title", title); //��������(�����迭)
	request.setAttribute("i_title1", i_title1); //��������(����1�迭)
	request.setAttribute("i_title2", i_title2); //��������(����2�迭)
	request.setAttribute("i_title3", i_title3); //��������(����3�迭)
	request.setAttribute("i_title4", i_title4); //��������(����4�迭)
	request.setAttribute("i_title5", i_title5); //��������(����5�迭)
	
	
	request.setAttribute("resultClass", resultClass);//��������(����)���ڵ�
	request.setAttribute("resultClass1", resultClass1);//��������(����)���ڵ�
	request.setAttribute("resultClass2", resultClass2); //��������(����)
	request.setAttribute("resultClass3", resultClass3);//��������(���)���ڵ�
	request.setAttribute("res_cnt_arr", res_cnt_arr);//��������(���)���� ī��Ʈ
	 -->