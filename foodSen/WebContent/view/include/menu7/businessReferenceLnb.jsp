<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<ul class="lnb">
  <li><img src="./images/sub/notice/sub_title_01.gif" alt="��������" /></li>
  <li><a href="foodNewsList.do"><img src="./images/sub/notice/sub_stitle_01Off.gif" alt="�޽ļҽ�" /></a></li>
  <li><a href="trainingEvent.do"><img src="./images/sub/notice/sub_stitle_02Off.gif" alt="���������" /></a></li>
  <li><a href="businessReferenceList.do"><img src="./images/sub/notice/sub_stitle_03On.gif" alt="�����ڷ��" /></a></li>
  <li><a href="relatedLaws.do"><img src="./images/sub/notice/sub_stitle_04Off.gif" alt="���ù���" /></a></li>
  <li><a href="
  			<c:if test="${loginUser.admin_yn eq null || loginUser.admin_yn eq 'N' }"> faqList.do </c:if>
            <c:if test="${loginUser.admin_yn eq 'Y' }"> faqSBMList.do </c:if>
	  "><img src="./images/sub/notice/sub_stitle_05Off.gif" alt="FAQ" /></a></li>
</ul>
