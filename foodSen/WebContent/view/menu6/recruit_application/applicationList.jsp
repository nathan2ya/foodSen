<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
	
	function goView(seq, pw, stat){
		viewPre.seq.value=seq;
		viewPre.pw.value=pw;
		
		if(stat == '01'){
			viewPre.action="applicationPW.do";	
		}else if(stat == '02'){
			viewPre.action="applicationView.do";
		}
	
		viewPre.submit();
	}
	
	function goSearch(){
		search.submit();
	}
	
	$(document).ready(function(){
		var i=40;
		$(".title").each(function(index, item){		
			if(getStrByte($(item).text())>12){
				$(item).html($(item).text().cut(12));
			}
		});
		
		$('.writer').each(function(index, item){
			var txt=$(item).text();
			var len=txt.length;
			var sung=txt.substring(0,1);
			var name='';
			for(var k=0;k<len-1;k++){
				name+='��';
			}
			$(item).text(sung+name);
		});
		
		$('#job').change(function(){
			$('#searchString').val($(this).val());
		});
		
		$("#searchType").change(function(){
			var sType = $("#searchType option:selected").text();
			
			if(sType == '�������'){
				$('#searchString').val($('#loc').val());
				$('.loc').css('display', 'inline');
				$('.job').hide();
				$('.gubun').hide();
				
				$('#loc').change(function(){
					$('#searchString').val($(this).val());
				});
				
			}else if(sType == '����'){
				$('#searchString').val($('#job').val());
				$('.loc').hide();
				$('.job').css('display', 'inline');
				$('.gubun').hide();
				
				$('#job').change(function(){
					$('#searchString').val($(this).val());
				});
				
			}else if(sType == '�ٹ�����'){
				$('#searchString').val($('#gubun').val());
				$('.loc').hide();
				$('.job').hide();
				$('.gubun').css('display', 'inline');
				
				$('#gubun').change(function(){
					$('#searchString').val($(this).val());
				});
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
	
		<p><img src="./images/sub/particiation/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
		<!-- �����޴� -->
		<jsp:include page="/view/include/menu6/recruitLnb.jsp"/>
		<!-- .//�����޴� -->
		
	
		<!-- ���� ������ -->
		<div class="right_box">
			
			<!-- ������� ���� -->
			<h3><img src="./images/sub/particiation/title_01.gif" alt="����" /></h3>
			<!-- .//������� ���� -->
			
			
			<!-- ������� ��� ���� -->
			<p class="history"><img src="./images/sub/history_home.gif" alt="home" /> �������� <img src="./images/sub/history_arrow.gif" alt="����" /> <strong>����</strong></p>
       		<p class="pt30"></p>
			<!-- .//������� ��� ���� -->
			
			
			<!-- �Խ��ǻ��˸� -->
			<ul class="top_box">
	        	<li>���ε���� ����ñ���û(���ϱ��) �� �б� �������� �ۼ� �����մϴ�. </li>
				<li>���������� ����ñ���û(���ϱ��) �� �б� �������� ���� �����մϴ�.</li>
				<li>���������� ����ñ���û �� �б� �������� ��ȸ�� �� �ֵ��� �Ͽ�����, ��ȯ���� Ư���� ���������� ���� �� ���ظ� ���� �� �����Ƿ� ���ʿ��� ������ ���� ���� ���� ������ �����Ͽ� �ֽñ� �ٶ��ϴ�.(�̷¼� � �ֹε�Ϲ�ȣ�� �ۼ��Ǿ� �ִ� ���, ���� �����մϴ�.)</li>
				<li><a href="http://www.sen.go.kr/web/services/bbs/bbsList.action?bbsBean.bbsCd=99">����ñ���û ����/���� �ٷΰ���</a></li>
        	</ul>
        	<!-- .//�Խ��ǻ��˸� -->
		
		
			<!-- �Խ��ǿ��� -->
			<div class="tbl_box">
			
				<!-- ����/���� �� -->
				<ul class="pool_tab">
					<li><a href="/foodSen/recruitList.do" class="pool_tab01">����</a></li>
					<li><a href="/foodSen/applicationList.do" class="pool_tab02_on">����</a></li>
				</ul>
				<!-- .//����/���� �� -->
			 

				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="����">
				
					<caption>����</caption>
					<colgroup>
						<col width="7%"/>
						<col width="*%"/>
						<col width="8%"/>
						<col width="15%"/>
						<col width="10%"/>
						<col width="10%"/>
						<col width="8%"/>
						<col width="8%"/>
						<col width="10%"/>
						<col width="10%"/>
					</colgroup>
					
					<tbody>
						<tr>
							<th>NO</th>
			                <th>����</th>
			                <th>����</th>
			                <th>����</th>
			                <th>�ٹ�<br />����</th>
			                <th>���<br />����</th>
			                <th>����<br />����</th>
			                <th>�ۼ���</th>
			                <th>�����</th>
			                <th>��ȸ��</th>
						</tr>
			
						
						<c:if test="${list eq '[]'}">
							<tr>
								<td colspan="10" align="center">�Խñ��� �����ϴ�.</td>
							</tr>
						</c:if>
						
						<c:if test="${list != null}">
							
							
							<c:forEach var="list" items="${list}">
							
								<c:url var="url" value="/inspectionResultView.do"> 
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
						<a href="/foodSen/inspectionResultList.do" class="wte_r">���</a>
					</span>
					
					<!-- �������ϰ�� ��Ϲ�ư ���� -->
					<c:if test="${sessionScope.session_admin_yn == 'y'}">
						<span class="per_l">
							<a href="/foodSen/inspectionResultCreateFrom.do" class="pre_r">���</a>
						</span>
					</c:if>
					<!-- .//�������ϰ�� ��Ϲ�ư ���� -->
					
				</span>
				<!-- .//��� ��� ��ư -->

			</div>
			<!-- .//�Խ��ǿ��� -->
			
			
			
			<!-- �˻� ���� -->
			<div class="search_box">
				<form name="search" action="/foodSen/inspectionResultSearch.do" method="post">
					<div style="display: inline;">
						<select id="searchType" name="searchType">
							<option value="job">����</option>
							<option value="gubun">�ٹ�����</option>
							<option value="loc_seq">�������</option>
						</select>
					</div>
					<div class="job" style="display: inline;">
						<select id="job" name="job">
							<option value="01">���米��</option>
							<option value="02">�����</option>
							<option value="03">������</option>
							<option value="04">������</option>
							<option value="05">��ĵ����</option>
						</select>
					</div>
					<div class="gubun" style="display: none;">
						<select id="gubun" name="gubun">
							<option value="01">������</option>
							<option value="02">�ð���</option>
						</select>
					</div>
					<div class="loc" style="display: none;">
						<select id="loc" name="loc">
							<option value="01">������</option>
							<option value="02">������</option>
							<option value="03">���ϱ�</option>
							<option value="04">������</option>
							<option value="05">���Ǳ�</option>
							<option value="06">������</option>
							<option value="07">���α�</option>
							<option value="08">��õ��</option>
							<option value="09">�����</option>
							<option value="10">������</option>
							<option value="11">���빮��</option>
							<option value="12">���۱�</option>
							<option value="13">������</option>
							<option value="14">���빮��</option>
							<option value="15">���ʱ�</option>
							<option value="16">������</option>
							<option value="17">���ϱ�</option>
							<option value="18">���ı�</option>
							<option value="19">��õ��</option>
							<option value="20">��������</option>
							<option value="21">��걸</option>
							<option value="22">����</option>
							<option value="23">���α�</option>
							<option value="24">�߱�</option>
							<option value="25">�߶���</option>
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

<jsp:include page="/view/include/footer.jsp"/>