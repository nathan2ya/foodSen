<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	function goView(seq){
		view.sur_seq.value=seq;
		view.submit();
	}
	
	function goSearch(){
		search.submit();
	}
	
	$(document).ready(function(){
		var i=40;
		$(".title").each(function(index, item){		
			if(getStrByte($(item).text())>25){
				$(item).html($(item).text().cut(25));
			}
		});
	});
	
	function getStrByte(str) {
		var p, len = 0;
		for(p=0; p<str.length; p++) {
			(str.charCodeAt(p) > 255) ? len+=2 : len++; // charCodeAt(���ڿ�) - ���ڿ��� �����ڵ尪���� ��ȯ�Ͽ� 255���� ũ�� �ѱ�.
		}
		return len;
	} // ���ڿ��� byte���� ���ϴ� �Լ� - �ѱ��̶�� ���ڴ� 2bytes, �׿ܿ��� 1byte�� ����Ѵ�.

	String.prototype.cut = function(len) {
        var str = this;
        var l = 0;
        for (var i=0; i<str.length; i++) {
                l += (str.charCodeAt(i) > 255) ? 2 : 1;
                if (l > len) return str.substring(0,i) + "...";
        }
        return str;
	}; // ���ڿ��� �߶��ִ� �Լ� - ���ϴ� byte����ŭ �߶��ְ� '...'�� �ٿ��ش�
	
	function goResult(sur_seq) {
		var frm = document.result;    //�˾��� �ѱ� �θ�â�� ��
	
		frm.sur_seq.value = sur_seq;  //���� ������ �����Ѵ�.  
	
		//���������� �˾�â�� �켱 �ϳ� ����.
		window.open('', 'popup_post', 'width=550, height=545, resizable=yes');
	
		//�θ�â�� Ÿ���� ���������� ��� �˾�â�� �̸����� �Ѵ�
		frm.target = 'popup_post';   
	
		//�ѱ� ���� action�� �˾��� ��Ÿ�� �������� �Ѵ�.
		frm.action = 'researchResult.do';
	
		//�˾����� �ѱ� ���� �������ִ� ���� submit �Ѵ�.
		frm.submit();
	}
</script>



<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/particiation/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
		<!-- �����޴� -->
		<jsp:include page="/view/include/menu6/researchLnb.jsp"/>
		<!-- .//�����޴� -->
		
	
		<!-- ���� ������ -->
		<div class="right_box">
			
			<!-- ������� ���� -->
			<h3><img src="./images/sub/particiation/title_05.gif" alt="��������" /></h3>
			<!-- .//������� ���� -->
			
			
			<!-- ������� ��� ���� -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> �������� <img src="./images/sub/history_arrow.gif" alt="����" /> <strong>��������</strong></p>
       		<p class="pt30"></p>
			<!-- .//������� ��� ���� -->
			
			<!-- �Խ��ǿ��� -->
			<div class="tbl_box">
			
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="��������">
				
					<caption>��������</caption>
					<colgroup>
						<col width="8%"/>
			            <col width="45%"/>
			            <col width="15%"/>
			            <col width="15%"/>
			            <col width="10%"/>
			            <col width="8%"/>
					</colgroup>
					
					<tbody>
						<tr>
							<th>NO</th>
			                <th>����</th>
			                <th>������</th>
			                <th>������</th>
			                <th>�ϷῩ��</th>
			                <th>��ȸ��</th>
						</tr>
			
						<c:if test="${list eq '[]'}">
							<tr>
								<td colspan="9" align="center">�Խñ��� �����ϴ�.</td>
							</tr>
						</c:if>
						
						<c:if test="${list != null}">
							
							<c:forEach var="list" items="${list}">
							
								<c:url var="url" value="/researchView.do"> 
									<c:param name="sur_seq" value="${list.sur_seq}"/>
									<c:param name="currentPage" value="${currentPage}"/>
									<c:param name="searchingNow" value="${searchingNow}"/>
									<c:if test="${searchingNow == 1}">
										<c:param name="searchType" value="${searchType}"/>
										<c:param name="userinput" value="${userinput}"/>
									</c:if>
								</c:url>
								
								<tr>
									<td align="center">
										<c:out value="${number}" />
										<c:set var="number" value="${number-1}"/>
									</td>
									<td align="left">
										<a href="${url}">${list.sur_title}</a>
									</td>
									<td align="center">${list.sur_sat_date}</td>
									<td align="center">${list.sur_end_date}</td>
									<td align="center">
										<c:if test="${list.sur_end_date < currentTime}">
											�Ϸ�
										</c:if>
										<c:if test="${list.sur_end_date > currentTime}">
											������
										</c:if>
									</td>
									<td align="center">${list.hits}</td>
								</tr>
							</c:forEach>
							
							
						</c:if>
					</tbody>
					
				</table>


				<!-- ����¡ -->
				<ul class="paging">
					<li>${pagingHtml}</li>
				</ul>
				<!-- .//����¡ -->


				<!-- ��� . ��� ��ư -->
				<span class="bbs_btn"> 
					<span class="wte_l">
						<a href="/foodSen/researchList.do" class="wte_r">���</a>
					</span>
					
					<!-- �������� ��츸 ��Ϲ�ư ���� -->
					<c:if test="${sessionScope.session_admin_yn == 'y'}">
						<span class="per_l">
							<a href="/foodSen/researchCreateForm.do" class="pre_r">���</a>
						</span>
					</c:if>
					<!-- .//�������� ��츸 ��Ϲ�ư ���� -->
					
				</span>
				<!-- .//��� ��� ��ư -->

			</div>
			<!-- .//�Խ��ǿ��� -->
			
			
			
			<!-- �˻� ���� -->
			<div class="search_box">
		        <form name="search" action="/foodSen/researchSearch.do" method="post">
		          <select name="searchType">
		            <option value="title">����</option>
		          </select>
		          <input type="text" id="userinput" name="userinput" />
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