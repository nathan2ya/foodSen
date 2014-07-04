<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="footer">
    <h2>하단</h2>
    <address>
    110-781) 서울특별시 종로구 송월길 48(신문로 2-77) 서울특별시학교보건진흥원
    </address>
    <p>COPYRIGHT(C) 2013 <b>Seoul School Health Promotion Center</b> ALL RIGHT RESERVED</p>
    <ul style="margin-top: -15px">
      <li class="bn" style="width: 510px">※최적화 해상도는 1280 Χ 768 이상입니다.<div style="display: inline; float: right;"><span><strong>전체접속자수</strong>: ${sessionScope["totalCount"]}명 /   <strong>오늘의접속자수</strong>: ${sessionScope["todayCount"]}명</span></div></li>
    </ul>
    <ul>
      <li class="bn"><a href="privacy.do">개인정보보호정책</a></li>
      <li><a href="emailCollectRejection.do">이메일 무단수집거부</a></li>
      <li><a href="viewer.do">뷰어프로그램</a></li>
      <li><a href="copyright.do">저작권보호</a></li>
      <li><a href="./down/banner_food.zip">서울학교급식 배너다운로드</a></li>
    </ul>
</div>