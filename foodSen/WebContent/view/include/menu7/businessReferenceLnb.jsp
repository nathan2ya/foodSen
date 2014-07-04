<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<ul class="lnb">
  <li><img src="./images/sub/notice/sub_title_01.gif" alt="정보마당" /></li>
  <li><a href="foodNewsList.do"><img src="./images/sub/notice/sub_stitle_01Off.gif" alt="급식소식" /></a></li>
  <li><a href="trainingEvent.do"><img src="./images/sub/notice/sub_stitle_02Off.gif" alt="연수·행사" /></a></li>
  <li><a href="businessReferenceList.do"><img src="./images/sub/notice/sub_stitle_03On.gif" alt="업무자료실" /></a></li>
  <li><a href="relatedLaws.do"><img src="./images/sub/notice/sub_stitle_04Off.gif" alt="관련법규" /></a></li>
  <li><a href="
  			<c:if test="${loginUser.admin_yn eq null || loginUser.admin_yn eq 'N' }"> faqList.do </c:if>
            <c:if test="${loginUser.admin_yn eq 'Y' }"> faqSBMList.do </c:if>
	  "><img src="./images/sub/notice/sub_stitle_05Off.gif" alt="FAQ" /></a></li>
</ul>
