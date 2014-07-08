<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<html>
<head>
	<title>���� ������ �˻���</title>
	
	<script type="text/javascript">
	

		
	</script>
</head>


<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/safety/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
		<!-- �����޴� -->
		<jsp:include page="/view/include/menu2/inspectionResultLnb.jsp"/>
		<!-- .//�����޴� -->
		
	
		<!-- ���� ������ -->
		<div class="right_box">
			
			<!-- ������� ���� -->
			<p><img src="./images/sub/safety/title_05.gif" alt="���� ������ �˻���" /></p>
			<!-- .//������� ���� -->
			
			
			<!-- ������� ��� ���� -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> �б��޽��������� <img src="./images/sub/history_arrow.gif" alt="����" /> <strong>����.������ �˻���</strong></p>
       		<p class="pt30"></p>
			<!-- .//������� ��� ���� -->
		
			<!-- �Խ��ǿ��� -->
			<div class="tbl_box">

				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="����.������ �˻���">
				
					<caption>����.������ �˻���</caption>
					<colgroup>
						<col width="8%" />
						<col width="*%" />
						<col width="10%" />
						<col width="15%" />
						<col width="15%" />
						<col width="10%" />
					</colgroup>
					<tbody>
						<tr>
							<th>NO</th>
							<th>����</th>
							<th>÷������</th>
							<th>�����</th>
							<th>�����</th>
							<th>��ȸ��</th>
						</tr>
						
						
						<c:if test="${vo eq null || empty vo}">
							<tr>
								<td colspan="6" align="center">�Խñ��� �����ϴ�.</td>
							</tr>
						</c:if>
						
						<c:if test="${vo ne null && not empty vo}">
							<c:forEach var="bw" items="${vo}" varStatus="i">
								<tr>
									<td>${((totalCount - ((cPage-1) * ps))- i.index)}</td>
									<td class="tl"><a class="title"
										href="javascript:goView('${bw.seq }')">${bw.title }</a></td>
									<td><c:if test="${bw.attach_name ne null && not empty bw.attach_name }">
											<img src="./images/sub/btn/btn_down.gif" alt="pdf" />
										</c:if></td>
									<td>${bw.writer}</td>
									<td><fmt:formatDate value="${bw.reg_date}" pattern="yyyy-MM-dd" /></td>
									<td>${bw.hits}</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
					
				</table>



				<!-- ����¡ -->
				<ul class="paging">
					<li>${pageNavi}</li>
				</ul>
				<!-- .//����¡ -->


				<!-- ��� ��� ��ư -->
				<span class="bbs_btn"> <span class="wte_l"><a href="foodNewsList.do" class="wte_r">���</a></span> 
					<c:if test="${loginUser.admin_yn eq 'Y' }">
						<span class="per_l"><a href="foodNewsCreate.do" class="pre_r">���</a></span>
					</c:if>
				</span>
				<!-- .//��� ��� ��ư -->

			</div>
			<!-- .//�Խ��ǿ��� -->
			
			
			
			<!-- �˻� ���� -->
			<div class="search_box">
		        <form name="search" action="foodNewsSearch.do" method="post">
		          <select name="searchType">
		            <option value="title">����</option>
		            <option value="writer">�����</option>
		          </select>
		          <input type="text" id="searchString" name="searchString" />
		          <a href="javascript:goSearch()"><img src="./images/sub/btn/btn_serch.gif" alt="�˻�" /></a> 
		        </form>
	        </div>
			<!-- .//�˻� ���� -->
			
			
		</div>
		<!-- .//���� ������ -->
		
		
		<p class="bottom_bg"></p>
		
	</div>
</div>

<jsp:include page="/view/include/footer.jsp"/>