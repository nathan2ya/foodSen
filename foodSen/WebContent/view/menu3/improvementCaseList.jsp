<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">

	function goSearch(){
		if(validateSQL(search.userinput.value) > -1){
			alert("Ư�����ڴ� �Է��� �� �����ϴ�.");
			search.userinput.focus();
			return;
		}
		search.submit();
	}
	
	function validateSQL(obj){
		// SQLInjection�� ���� ���� ������ ����� �ԷµǴ� ���뿡�� '�Ǵ� "�� ã�� �ٲٰų� �Է����� ���ϰ� �ϴ� ���̴�.	
		var x=obj;
		var pos = 0;
		var pos1 = 0;
		var pos2 = 0;
		var pos3 = 0;
		pos=x.indexOf("'"); // ��üID�� ���뿡�� '�� ã�´�. "�� ã������ pos=x.indexOf("\""); �̷��� ���� �ȴ�.
		pos1=x.indexOf("\"");
		pos2=x.indexOf("<");
		pos3=x.indexOf(">");
		if (pos!=-1){
			return pos;
		}
		if (pos1!=-1){
			return pos1;
		}
		if (pos2!=-1){
			return pos2;
		}
		if (pos3!=-1){
			return pos3;
		}
	}
	
	$(document).ready(function(){
		$(".title").each(function(index, item){
			if(getStrByte($(item).text())>46){
				$(item).html($(item).text().cut(46));
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
	
</script>



<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/factory/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
		<!-- �����޴� -->
		<jsp:include page="/view/include/menu3/improvementCaseLnb.jsp"/>
		<!-- .//�����޴� -->
		
	
		<!-- ���� ������ -->
		<div class="right_box">
			
			<!-- ������� ���� -->
			<p><img src="./images/sub/factory/title_02.gif" alt="�޽Ľü� ���� ���" /></p>
			<!-- .//������� ���� -->
			
			
			<!-- ������� ��� ���� -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> �б��޽� �ü����� <img src="./images/sub/history_arrow.gif" alt="����" /> <strong>�޽Ľü��������</strong></p>
       		<p class="pt30"></p>
			<!-- .//������� ��� ���� -->
		
		
			<!-- �Խ��ǿ��� -->
			<div class="tbl_box">

				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="�޽Ľü��������">
				
					<caption>�޽Ľü��������</caption>
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
						
						
						<c:if test="${list eq '[]'}">
							<tr>
								<td colspan="6" align="center">�Խñ��� �����ϴ�.</td>
							</tr>
						</c:if>
						
						<c:if test="${list != null}">
							
							
							<c:forEach var="list" items="${list}">
							
								<c:url var="url" value="/improvementCaseView.do"> 
									<c:param name="seq" value="${list.seq}"/>
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
										<a href=${url}>${list.title}</a>
									</td>
									<td>
										<c:if test="${list.attach_name != null}">
											<img src="./images/sub/btn/btn_down.gif" alt="pdf" />
										</c:if>
									</td>
									<td align="center">${list.wirte}</td>
									<td align="center"><fmt:formatDate value="${list.reg_date}"  pattern="yyyy-MM-dd" /></td>
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
						<a href="/foodSen/improvementCaseList.do" class="wte_r">���</a>
					</span>
					
					<!-- ȸ���� ��� ��Ϲ�ư ���� -->
					<c:if test="${sessionScope.session_position == 1 || sessionScope.session_position == 2}">
						<span class="per_l">
							<a href="/foodSen/improvementCaseCreateFrom.do" class="pre_r">���</a>
						</span>
					</c:if>
					<!-- .//�������ϰ�� ��Ϲ�ư ���� -->
					
				</span>
				<!-- .//��� ��� ��ư -->

			</div>
			<!-- .//�Խ��ǿ��� -->
			
			
			
			<!-- �˻� ���� -->
			<div class="search_box">
		        <form name="search" action="/foodSen/improvementCaseSearch.do" method="post">
		          <select name="searchType">
		            <option value="title">����</option>
		            <option value="writer">�����</option>
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