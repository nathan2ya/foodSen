<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>



<jsp:include page="/view/include/top.jsp"/>
<link href="/css/base.css" rel="stylesheet" type="text/css" />
<link href="/css/common.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">

	function goEdit(){
		var seq = "${seq}"; // �������� �������ѹ�
		var currentPage = "${currentPage}"; // �������� ����������
		var searchingNow = "${searchingNow}"; // �������� �˻����� ����
		
		location.href='/foodSen/recruitEditFrom.do?seq='+seq+'&currentPage='+currentPage+'&searchingNow='+searchingNow;
	}

	function goDelete(){
		var seq = "${seq}"; // �������� �������ѹ�
		var pw = "${resultClass.pw}"; // �������� ����������
		
		//url
		url="/foodSen/recruitDeleteFrom.do?seq="+seq+"&pw="+pw;
		// ���ο� �����츦 ���ϴ�.
		open(url,"confirm","toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width=500,height=220");
	}
	
	//���� �κ� ���丵ũ
	$(document).ready(function(){
		autolink(document.getElementById("description"));
	});
	
	function autolink(id) {
	    var container = id;
	    var doc = container.innerHTML;
	    
	    //�ּ��ڵ���ũ
	    var regURL = new RegExp("(http|https|ftp|telnet|news|irc)://([-/.a-zA-Z0-9_~#%$?&=:200-377()]+)","gi");
	    
	    //�̸����ڵ���ũ
	    var regEmail = new RegExp("([xA1-xFEa-z0-9_-]+@[xA1-xFEa-z0-9-]+\.[a-z0-9-]+\.[a-z0-9-]+)","gi");
	
	    container.innerHTML = doc.replace(regURL,"<a href='$1://$2' target='_blank'>$1://$2</a>").replace(regEmail,"<a href='mailto:$1'>$1</a>");
	}
	//.���� �κ� ���丵ũ ����
	
</script>



<div id="container">
	<div id="contents">
	
		<p><img src="./images/sub/safety/sub_vimg_01.jpg" alt="�ǰ��� �޽� �ູ�� �б�" /></p>
		
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
			
			
			<!-- ����/���� �� -->
			<ul class="pool_tab">
				<li><a href="/foodSen/recruitList.do" class="pool_tab01_on">����</a></li>
				<li><a href="/foodSen/applicationList.do" class="pool_tab02">����</a></li>
			</ul>
			<!-- .//����/���� �� -->
			
			
			<!-- �Խ��ǿ��� -->
			<div class="tbl_box">
					
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="�б��޽��η�Ǯ(����)">
					<caption>�б��޽��η�Ǯ(����)</caption>
					<colgroup>
						<col width="15%"/>
						<col width="20%"/>
						<col width="15%"/>
						<col width="20%"/>
						<col width="15%"/>
						<col width="%"/>
					</colgroup>
					<tbody>
					
						<!-- 1��°�� -->
						<tr>
							<th>����</th>
							<td colspan="5" class="tl">
								${resultClass.title}
							</td>
						</tr>
						<!-- .//1��°�� -->
						
						
						<!-- 2��°�� -->
						<tr>
							<th>�����</th>
							<td colspan="1" class="tl">
								${resultClass.reg_name}
							</td>
							<th>�����</th>
							<td colspan="1" class="tl">
								<fmt:formatDate value="${resultClass.reg_date}" pattern="yyyy-MM-dd" />
							</td>
							<th>��������</th>
							<td colspan="1" class="tl">
								${resultClass.end_yn}
							</td>
						</tr>
						<!-- .//2��°�� -->
						
						
						<!-- 3��°�� -->
						<tr>
							<th>�б���</th>
							<td colspan="1" class="tl">
								<c:if test="${resultClass.school_type == 1}">
									�ʵ��б�
								</c:if>
								<c:if test="${resultClass.school_type == 2}">
									���б�
								</c:if>
								<c:if test="${resultClass.school_type == 3}">
									����б�
								</c:if>
								<c:if test="${resultClass.school_type == 4}">
									Ư���б�
								</c:if>
								<c:if test="${resultClass.school_type == 5}">
									����
								</c:if>
							</td>
							<th>�б���</th>
							<td colspan="1" class="tl">
								${resultClass.school_name}
							</td>
							<th>����</th>
							<td colspan="1" class="tl">
								<c:if test="${resultClass.loc_seq == 01}">
									������
								</c:if>
								<c:if test="${resultClass.loc_seq == 02}">
									������
								</c:if>
								<c:if test="${resultClass.loc_seq == 03}">
									���ϱ�
								</c:if>
								<c:if test="${resultClass.loc_seq == 04}">
									������
								</c:if>
								<c:if test="${resultClass.loc_seq == 05}">
									���Ǳ�
								</c:if>
								<c:if test="${resultClass.loc_seq == 06}">
									������
								</c:if>
								<c:if test="${resultClass.loc_seq == 07}">
									���α�
								</c:if>
								<c:if test="${resultClass.loc_seq == 08}">
									��õ��
								</c:if>
								<c:if test="${resultClass.loc_seq == 09}">
									�����
								</c:if>
								<c:if test="${resultClass.loc_seq == 10}">
									������
								</c:if>
								<c:if test="${resultClass.loc_seq == 11}">
									���빮��
								</c:if>
								<c:if test="${resultClass.loc_seq == 12}">
									���۱�
								</c:if>
								<c:if test="${resultClass.loc_seq == 13}">
									������
								</c:if>
								<c:if test="${resultClass.loc_seq == 14}">
									���빮��
								</c:if>
								<c:if test="${resultClass.loc_seq == 15}">
									���ʱ�
								</c:if>
								<c:if test="${resultClass.loc_seq == 16}">
									������
								</c:if>
								<c:if test="${resultClass.loc_seq == 17}">
									���ϱ�
								</c:if>
								<c:if test="${resultClass.loc_seq == 18}">
									���ı�
								</c:if>
								<c:if test="${resultClass.loc_seq == 19}">
									��õ��
								</c:if>
								<c:if test="${resultClass.loc_seq == 20}">
									��������
								</c:if>
								<c:if test="${resultClass.loc_seq == 21}">
									������
								</c:if>
								<c:if test="${resultClass.loc_seq == 22}">
									��걸
								</c:if>
								<c:if test="${resultClass.loc_seq == 23}">
									���α�
								</c:if>
								<c:if test="${resultClass.loc_seq == 24}">
									�߱�
								</c:if>
								<c:if test="${resultClass.loc_seq == 25}">
									�߶���
								</c:if>
							</td>
						</tr>
						<!-- .//3��°�� -->
						
						
						<!-- 4��°�� -->
						<tr>
							<th>����</th>
							<td colspan="1" class="tl">
								<c:if test="${resultClass.job == 01}">
									���米��
								</c:if>
								<c:if test="${resultClass.job == 02}">
									�����
								</c:if>
								<c:if test="${resultClass.job == 03}">
									������
								</c:if>
								<c:if test="${resultClass.job == 04}">
									������
								</c:if>
								<c:if test="${resultClass.job == 05}">
									��ĵ����
								</c:if>
							</td>
							<th>�ٹ�����</th>
							<td colspan="3" class="tl">
								<c:if test="${resultClass.gubun == 01}">
									������
								</c:if>
								<c:if test="${resultClass.gubun == 02}">
									�ð���
								</c:if>
							</td>
						</tr>
						<!-- .//4��°�� -->
						
						
						<!-- 5��°�� -->
						<tr>
							<th>������</th>
							<td colspan="1" class="tl">
								${resultClass.phone}
							</td>
							<th>�̸���</th>
							<td colspan="3" class="tl">
								${resultClass.email}
							</td>
						</tr>
						<!-- .//5��°�� -->
						
						<!-- 6��°�� -->
						<tr>
							<th>
								����<br/>(2000�� �̳�)
							</th>
							<td id="description" colspan="5" class="tl">
								<pre>${resultClass.description}</pre>
							</td>
						</tr>
						<!-- .//6��°�� -->
						
						
						<!-- 7��°�� -->
						<tr>
							<th>÷������</th>
							<td colspan="5" class="tl">
								<c:if test="${resultClass.attach_name != null}">
									<a href="/foodSen/recruit_FileDownload.do?attach_name=${resultClass.attach_name}">
										${resultClass.attach_name}
										<img src="./images/sub/btn/btn_down.gif" alt="pdf" />
									</a>
								</c:if>
							</td>
						</tr>
						<!-- .//7��°�� -->
						
					</tbody>
				</table>
					
				<p class="pt40"></p>
				

				<!-- ��ư -->
				<span class="bbs_btn"> 
					
					<!-- ��ü�� ��� -->
					<c:if test="${searchingNow == 0}">
						<span class="wte_l"><a href="/foodSen/recruitList.do?currentPage=${currentPage}" class="wte_r">���</a></span>
					</c:if>
					<!-- .//��ü�� ��� -->
					
					
					<!-- �˻����� ��� -->
					<c:if test="${searchingNow == 1}">
						<c:if test="${searchType eq 'job'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&job=${resultClass.job}&currentPage=${currentPage}" class="wte_r">���</a></span>
						</c:if>
						<c:if test="${searchType eq 'gubun'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&gubun=${resultClass.gubun}&currentPage=${currentPage}" class="wte_r">���</a></span>
						</c:if>
						<c:if test="${searchType eq 'loc_seq'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&loc_seq=${resultClass.loc_seq}&currentPage=${currentPage}" class="wte_r">���</a></span>
						</c:if>
						<c:if test="${searchType eq 'school_type'}">
							<span class="wte_l"><a href="/foodSen/recruitSearch.do?searchType=${searchType}&school_type=${resultClass.school_type}&currentPage=${currentPage}" class="wte_r">���</a></span>
						</c:if>
					</c:if>
					<!-- .//�˻����� ��� -->
					
					
					<!-- �� �ۼ����� ��츸 ��ư ���� -->
					<c:if test="${sessionScope.session_id == resultClass.writer}">
						<span class="per_l"><a href="javascript:goEdit()" class="pre_r">����</a></span>
						<span class="wte_l"><a href="javascript:goDelete()" class="wte_r">����</a></span>
					</c:if>
					<!-- .//�� �ۼ����� ��츸 ��ư ���� -->
					
				</span>
				<!-- //��ư -->


			</div>
			<!-- .//�Խ��ǿ��� -->
			
			
			
		</div>
		<!-- .//���� ������ -->
		
		<p class="bottom_bg"></p>
		
	</div>
</div>

<jsp:include page="/view/include/footer.jsp"/>
