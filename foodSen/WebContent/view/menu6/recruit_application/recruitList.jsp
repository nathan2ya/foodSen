<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript">

	function goSearch(){
		search.submit();
	}

	$(document).ready(function(){
		$(".title").each(function(index, item){		
			if(getStrByte($(item).text())>16){
				$(item).html($(item).text().cut(16));
			}
		});
		
		$('#job').change(function(){
			$('#searchString').val($(this).val());
		});
	
		
		//�˻�Ÿ�Կ� ���� ���� ����Ʈ�޴� ����
		$("#searchType").change(function(){
			var sType = $("#searchType option:selected").text();
			
			if(sType == '����'){
				$('#searchString').val($('#loc').val());
				$('.loc').css('display', 'inline'); //loc�� inline �ϰ� �������� hide!
				$('.job').hide();
				$('.gubun').hide();
				$('.school_type').hide();
				$('#loc').change(function(){
					$('#searchString').val($(this).val());
				});
				
			}else if(sType == '����'){
				
				$('#searchString').val($('#job').val());
				$('.loc').hide();
				$('.job').css('display', 'inline');
				$('.gubun').hide();
				$('.school_type').hide();
				$('#job').change(function(){
					$('#searchString').val($(this).val());
				});
				
			}else if(sType == '�ٹ�����'){
				$('#searchString').val($('#gubun').val());
				$('.loc').hide();
				$('.job').hide();
				$('.gubun').css('display', 'inline');
				$('.school_type').hide();
				$('#gubun').change(function(){
					$('#searchString').val($(this).val());
				});
			}else if(sType == '�б���'){
				$('#searchString').val($('#school_type').val());
				$('.loc').hide();
				$('.job').hide();
				$('.gubun').hide();
				$('.school_type').css('display', 'inline');
				$('#school_type').change(function(){
					$('#searchString').val($(this).val());
				});
			}
		}); //.//�˻�Ÿ�Կ� ���� ���� ����Ʈ�޴� ���� ����
		
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
					<li><a href="/foodSen/recruitList.do" class="pool_tab01_on">����</a></li>
					<li><a href="/foodSen/applicationList.do" class="pool_tab02">����</a></li>
				</ul>
				<!-- .//����/���� �� -->
			 

				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="����">
				
					<caption>����</caption>
					<colgroup>
						<col width="8%"/>
						<col width="*%"/>
						<col width="15%"/>
						<col width="8%"/>
						<col width="10%"/>
						<col width="8%"/>
						<col width="10%"/>
						<col width="15%"/>
						<col width="10%"/>
					</colgroup>
					
					<tbody>
						<tr>
							<th>NO</th>
							<th>����</th>
							<th>����</th>
							<th>�ٹ�<br/>����</th>
							<th>����</th>
							<th>����<br/>����</th>
							<th>�б���</th>
							<th>�����</th>
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
										<fmt:formatDate value="${list.reg_date}"  pattern="yyyy-MM-dd" />
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
					<!-- .//�������ϰ�� ��Ϲ�ư ���� -->
					
				</span>
				<!-- .//��� ��� ��ư -->

			</div>
			<!-- .//�Խ��ǿ��� -->
			
			
			
			<!-- �˻� ���� -->
			<div class="search_box">
				<form name="search" action="/foodSen/recruitSearch.do" method="post">
					<div style="display: inline;">
						<select id="searchType" name="searchType">
							<option value="job">����</option>
							<option value="gubun">�ٹ�����</option>
							<option value="loc_seq">����</option>
							<option value="school_type">�б���</option>
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
					<div class="loc" style="display: none;">
						<select id="loc_seq" name="loc_seq">
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
					<div class="gubun" style="display: none;">
						<select id="gubun" name="gubun">
							<option value="01">������</option>
							<option value="02">�ð���</option>
						</select>
					</div>
					<div class="school_type" style="display: none;">
						<select id="school_type" name="school_type">
							<option value="01">��</option>
							<option value="02">��</option>
							<option value="03">��</option>
							<option value="04">Ư��</option>
							<option value="05">����</option>
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