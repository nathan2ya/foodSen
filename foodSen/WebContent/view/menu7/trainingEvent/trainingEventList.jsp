<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../../include/top.jsp"/>


<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	function goView(type,seq){
		if(type == 1){
			view.action="trainingEventView.do";
		}
		
		if(type == 2){
			view.action="trainingEventResultView.do";
		}
		
		view.seq.value=seq;
		view.submit();
	}
	
	function goSearch(){
		if(validateSQL(search.searchString.value) > -1){
			alert("Ư�����ڴ� �Է��� �� �����ϴ�.");
			search.searchString.focus();
			return;
		}
		search.submit();
	}
	
	function validateSQL(obj){
		var x=obj;
		var pos = 0;
		var pos1 = 0;
		var pos2 = 0;
		var pos3 = 0;
		pos=x.indexOf("'");
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
		$(".title1").each(function(index, item){
			if(getStrByte($(item).text())>28){
				$(item).html($(item).text().cut(28));
			}
		});
		
		$(".title2").each(function(index, item){
			if(getStrByte($(item).text())>22){
				$(item).html($(item).text().cut(22));
			}
		});
	});
	
	
	
	function getStrByte(str) {
		var p, len = 0;
		for(p=0; p<str.length; p++) {
			(str.charCodeAt(p) > 255) ? len+=2 : len++;
		}
		return len;
	}
	
	
	String.prototype.cut = function(len) {
	    var str = this;
	    var l = 0;
	    for (var i=0; i<str.length; i++) {
	            l += (str.charCodeAt(i) > 255) ? 2 : 1;
	            if (l > len) return str.substring(0,i) + "...";
	    }
	    return str;
	};
	
	
</script>





<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/notice/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
		
		<!-- �����޴� -->
		<jsp:include page="/view/include/menu7/trainingEventLnb.jsp"/>
		<!-- .//�����޴� -->
		
	
		<!-- ���� ������ -->
		<div class="right_box">
			
			<!-- ������� ���� -->
			<h3><img src="./images/sub/notice/title_02.gif" alt="����.���" /></h3>
			<!-- .//������� ���� -->
			
			
			<!-- ������� ��� ���� -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> �������� <img src="./images/sub/history_arrow.gif" alt="����" /> <strong>����.���</strong></p>
       		<p class="pt30"></p>
			<!-- .//������� ��� ���� -->		
			
			
			
			<!-- �Խ��ǿ��� -->
			<div class="tbl_box">
          
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="���������">
					<caption>���������</caption>
					
					<colgroup>
						<col width="8%"/>
						<col width="*%"/>
						<col width="15%"/>
						<col width="10%"/>
						<col width="15%"/>
						<col width="15%"/>
						<col width="10%"/>
					</colgroup>
					
					<tbody>
						<tr>
							<th>NO</th>
							<th>����</th>
							<th>���Ⱓ</th>
							<th>÷������</th>
							<th>�����</th>
							<th>�����</th>
							<th>��ȸ��</th>
						</tr>
						
						
						<c:if test="${list eq '[]'}">
							<tr>
								<td colspan="7" align="center">�Խñ��� �����ϴ�.</td>
							</tr>
						</c:if>
						
						<c:if test="${list != null}">
						
							<c:forEach var="list" items="${list}">
								
								
								<c:url var="url" value="/trainingEventView.do"> 
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
									
									
									<!-- �Ϲݱ۰� ����� ������ -->
									<td class="tl">
									
										<c:if test="${list.seq eq list.up_seq }">
											<a href=${url}>${list.title}</a>
										</c:if>
										
										<c:if test="${list.seq ne list.up_seq }">
											&nbsp;&nbsp;&nbsp;&nbsp;<img src="./images/sub/btn/btn_re.gif" alt="���" />
											<a href=${url}>${list.title}</a>
										</c:if>
										
									</td>
									<!-- .//�Ϲݱ۰� ����� ������ -->
									
									
									
									<td>
										${list.str_date } ~<br/>
										${list.end_date }
									</td>
									
									<td>
										<c:if test="${list.attach_name ne null}">
											<img src="./images/sub/btn/btn_down.gif" alt="pdf" />
										</c:if>
									</td>
									
									<td>${list.writer }</td>
									
									<td><fmt:formatDate value="${list.reg_date}" pattern="yyyy-MM-dd"/></td>
									
									<td>${list.hits}</td>
									
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
						<a href="/foodSen/TrainingEventList.do" class="wte_r">���</a>
					</span>
					
					<!-- �������� ��츸 ��Ϲ�ư ���� -->
					<c:if test="${sessionScope.session_admin_yn == 'y'}">
						<span class="per_l">
							<a href="/foodSen/trainingEventCreateFrom.do" class="pre_r">���</a>
						</span>
					</c:if>
					<!-- .//�������� ��츸 ��Ϲ�ư ���� -->
					
				</span>
				<!-- .//��� ��� ��ư -->
				
				
				
				
				<!-- �˻� ���� -->
			<div class="search_box">
				<form name="search" action="/foodSen/applicationSearch.do" method="post">
					<div style="display: inline;">
						<select id="searchType" name="searchType">
							<option value="title">����</option>
							<option value="writer">�����</option>
						</select>
					</div>
					
					<input type="hidden" id="searchString" name="searchString" value="01" /> 
					<a href="javascript:goSearch()"><img src="./images/sub/btn/btn_serch.gif" alt="�˻�" /></a>
				</form>
			</div>
			<!-- .//�˻� ���� -->
			
			
		</div>
		<!-- .//���� ������ -->
		
		
		<p class="bottom_bg"></p>
		
	</div>
</div>

<form name="view" method="post">
	<input type="hidden" id="seq" name="seq">
</form>

<jsp:include page="/view/include/footer.jsp"/>
          
