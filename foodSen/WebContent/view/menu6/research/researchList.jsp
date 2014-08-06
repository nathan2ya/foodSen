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
			            <col width="*%"/>
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
							
								<!-- ���������� ���� ������Ƽ -->
								<c:url var="url" value="/recruitView.do"> 
									<c:param name="seq" value="${list.seq}"/>
									<c:param name="currentPage" value="${currentPage}"/>
									<c:param name="searchingNow" value="${searchingNow}"/>
									
									<!-- �˻����� ��� �Ʒ��� uri�� �߰��� �����ؼ� �� �������� ���� -->
									<c:if test="${searchingNow == 1}">
										<!-- Ÿ������ -->
										<c:param name="searchType" value="${searchType}"/>
										<!-- .//Ÿ������ -->
										
										<!-- ����Ÿ������ -->
										<c:if test="${searchType eq 'job'}">
											<c:param name="job" value="${job}"/>
										</c:if>
										<c:if test="${searchType eq 'gubun'}">
											<c:param name="gubun" value="${gubun}"/>
										</c:if>
										<c:if test="${searchType eq 'loc_seq'}">
											<c:param name="loc_seq" value="${loc_seq}"/>
										</c:if>
										<c:if test="${searchType eq 'school_type'}">
											<c:param name="school_type" value="${school_type}"/>
										</c:if>
										<!-- .//����Ÿ������ -->
									</c:if>
									<!-- .//�˻����� ��� �Ʒ��� uri�� �߰��� �����ؼ� �� �������� ���� -->
									
								</c:url>
								<!-- .//���������� ���� ������Ƽ -->
								 
								 
								<tr>
									<td align="center">
										<c:out value="${number}" />
										<c:set var="number" value="${number-1}"/>
									</td>
									<td align="left">
										<a href=${url}>${list.title}</a>
									</td>
									<td align="left">
										<c:if test="${list.job == 01}">
											���米��
										</c:if>
										<c:if test="${list.job == 02}">
											�����
										</c:if>
										<c:if test="${list.job == 03}">
											������
										</c:if>
										<c:if test="${list.job == 04}">
											������
										</c:if>
										<c:if test="${list.job == 05}">
											��ĵ����
										</c:if>
									</td>
									<td align="left">
										<c:if test="${list.gubun == 01}">
											������
										</c:if>
										<c:if test="${list.gubun == 02}">
											�ð���
										</c:if>
									</td>
									<td align="left">
										<c:if test="${list.loc_seq == 01}">
											������
										</c:if>
										<c:if test="${list.loc_seq == 02}">
											������
										</c:if>
										<c:if test="${list.loc_seq == 03}">
											���ϱ�
										</c:if>
										<c:if test="${list.loc_seq == 04}">
											������
										</c:if>
										<c:if test="${list.loc_seq == 05}">
											���Ǳ�
										</c:if>
										<c:if test="${list.loc_seq == 06}">
											������
										</c:if>
										<c:if test="${list.loc_seq == 07}">
											���α�
										</c:if>
										<c:if test="${list.loc_seq == 08}">
											��õ��
										</c:if>
										<c:if test="${list.loc_seq == 09}">
											�����
										</c:if>
										<c:if test="${list.loc_seq == 10}">
											������
										</c:if>
										<c:if test="${list.loc_seq == 11}">
											���빮��
										</c:if>
										<c:if test="${list.loc_seq == 12}">
											���۱�
										</c:if>
										<c:if test="${list.loc_seq == 13}">
											������
										</c:if>
										<c:if test="${list.loc_seq == 14}">
											���빮��
										</c:if>
										<c:if test="${list.loc_seq == 15}">
											���ʱ�
										</c:if>
										<c:if test="${list.loc_seq == 16}">
											������
										</c:if>
										<c:if test="${list.loc_seq == 17}">
											���ϱ�
										</c:if>
										<c:if test="${list.loc_seq == 18}">
											���ı�
										</c:if>
										<c:if test="${list.loc_seq == 19}">
											��õ��
										</c:if>
										<c:if test="${list.loc_seq == 20}">
											��������
										</c:if>
										<c:if test="${list.loc_seq == 21}">
											������
										</c:if>
										<c:if test="${list.loc_seq == 22}">
											��걸
										</c:if>
										<c:if test="${list.loc_seq == 23}">
											���α�
										</c:if>
										<c:if test="${list.loc_seq == 24}">
											�߱�
										</c:if>
										<c:if test="${list.loc_seq == 25}">
											�߶���
										</c:if>
									</td>
									<td align="left">
										${list.end_yn}
									</td>
									<td align="left">
										<c:if test="${list.school_type == 1}">
											${list.school_name}��
										</c:if>
										<c:if test="${list.school_type == 2}">
											${list.school_name}��
										</c:if>
										<c:if test="${list.school_type == 3}">
											${list.school_name}��
										</c:if>
										<c:if test="${list.school_type == 4}">
											${list.school_name}Ư��
										</c:if>
										<c:if test="${list.school_type == 5}">
											${list.school_name}����
										</c:if>
									</td>
									<td align="left">
										<fmt:formatDate value="${list.reg_date}"  pattern="YY-MM-dd" />
									</td>
									<td align="left">
										${list.hits}
									</td>
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
						<a href="/foodSen/recruitList.do" class="wte_r">���</a>
					</span>
					
					<!-- �������� ��츸 ��Ϲ�ư ���� -->
					<c:if test="${sessionScope.session_position == 3}">
						<span class="per_l">
							<a href="/foodSen/recruitCreateForm.do" class="pre_r">���</a>
						</span>
					</c:if>
					<!-- .//�������� ��츸 ��Ϲ�ư ���� -->
					
				</span>
				<!-- .//��� ��� ��ư -->

			</div>
			<!-- .//�Խ��ǿ��� -->
			
			
			
			<!-- �˻� ���� -->
			<div class="search_box">
		        <form name="search" action="/foodSen/trainingEventSearch.do" method="post">
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