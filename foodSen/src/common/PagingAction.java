package common;

public class PagingAction {

	//생성자1 매개변수
	private String serviceName; //호출 URI 정의
	
	//게시물 count
    private int totalCount; // 전체 게시물 수
    private int totalPage; // 전체 페이지 수
    
    //페이지 단위
    private int blockCount; // 한 페이지의 게시물의 수
    private int blockPage; // 한 화면에 보여줄 페이지 수
    
    //현재페이지 산출
    private int startCount; // 한 페이지에서 보여줄 게시글의 시작 번호
    private int endCount; // 한 페이지에서 보여줄 게시글의 끝 번호
    private int startPage; // 시작 페이지
    private int endPage; // 마지막 페이지
    
    //다양한 URI (오버라이딩된 생성자에 따라 초기화)
    private int currentPage; // 현재페이지 // URI
    private int board_seq_num; // 소댓글의 호출페이지 // URI
    private int r_currentPage; // 소댓글 현재페이지 // URI
    private String searchType; // 검색타입 //URI
    private String userinput; // 검색어 //URI
    private String subType; // 서브검색종류 //URI
    private String subValue; // 서브검색값 //URI
    
    //최종 페이지 완성 결과 반환변수
    private StringBuffer pagingHtml; //결과반환
    
    
    // 생성자1. 전체글 동작시에 호출
    public PagingAction(String serviceName, int currentPage, int totalCount, int blockCount, int blockPage) {
    	
    	//요구된 파라미터 초기화
    	this.serviceName = serviceName;
		this.blockCount = blockCount;
		this.blockPage = blockPage;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		//.요구된 파라미터 초기화
		
		
		// 전체 페이지 수
		totalPage = (int) Math.ceil((double) totalCount / blockCount); // 나눈뒤 반올림
		if (totalPage == 0){ // 레코드 유무과 무관하게 최초 1페이지 초기화
			totalPage = 1;
		}
		

		// 방어코드_ 사용자가 주소창에 현재페이지를 전체페이지 보다 크게 입력할경우 전체페이지수로 초기화 시켜버림
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		
		// 현재페이지에 첫레코드, 마지막레코드로 쓰일 list 인덱스 번호를 산출한다.
		startCount = (currentPage - 1) * blockCount;
		endCount = startCount + blockCount - 1;
		
		
		// 시작페이지, 마지막페이지는 레코드개수에 의해 정의된다.
		startPage = (int) ((currentPage - 1) / blockPage) * blockPage + 1;
		endPage = startPage + blockPage - 1;
		
		
		// 마지막페이지 재정의_ 마지막페이지가 전체페이지 수보다 클경우 전체페이지로 재정의
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		
		
		//처음으로(항상 노출 시킨다.)
		pagingHtml = new StringBuffer();
		pagingHtml.append("<a href=" + serviceName + ".do?currentPage=1><img src='./images/sub/btn/pre_01.gif' alt='처음으로' /></a>");
		
		
		// 이전 block 페이지(최초 구역을 초과할 경우에만 노출)
		if (currentPage > blockPage) {
			pagingHtml.append("<a href=" + serviceName + ".do?currentPage=" + (startPage - 1) + ">");
			pagingHtml.append(" <img src='./images/sub/btn/pre.gif' alt='이전 페이지로 가기' /> ");
			pagingHtml.append("</a>");
		}

		// 페이지 번호
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == currentPage) {
				pagingHtml.append("<a><font color='red'> ");
				pagingHtml.append(i); //현재 페이지
				pagingHtml.append(" </font></a>");
			} else {
				pagingHtml.append("<a href=" + serviceName
						+ ".do?currentPage=");
				pagingHtml.append(i); //그 외 다른 페이지 링크
				pagingHtml.append("> ");
				pagingHtml.append(i);
				pagingHtml.append(" </a>");
			}
		}

		// 다음 block 페이지(최후 구역이 아닐 경우에만 노출)
		if (totalPage - startPage >= blockPage) {
			pagingHtml.append("<a href=" + serviceName+ ".do?currentPage=" + (endPage + 1) + ">");
			pagingHtml.append(" <img src='./images/sub/btn/next.gif' alt='다음 페이지' /> ");
			pagingHtml.append("</a>");
		}
		
		//마지막으로
		pagingHtml.append("<a href=" + serviceName + ".do?currentPage="+totalPage+"><img src='./images/sub/btn/next_01.gif' alt='마지막페이지' /></a>");
		
	}
    // .생성자1
    
    

    // 생성자1. 검색글 동작시에 호출
    public PagingAction(String serviceName, int currentPage, int totalCount, int blockCount, int blockPage, String searchType, String userinput) {
    	
    	//요구된 파라미터 초기화
    	this.serviceName = serviceName;
		this.blockCount = blockCount;
		this.blockPage = blockPage;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.searchType = searchType;
		this.userinput = userinput;
		//.요구된 파라미터 초기화
		
		
		// 전체 페이지 수
		totalPage = (int) Math.ceil((double) totalCount / blockCount); // 나눈뒤 반올림
		if (totalPage == 0) {
			totalPage = 1;
		}

		
		// 현재 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		
		// 현재 페이지의 처음과 마지막 글의 번호 가져오기
		startCount = (currentPage - 1) * blockCount;
		endCount = startCount + blockCount - 1;

		
		// 시작 페이지와 마지막 페이지 값 구하기
		startPage = (int) ((currentPage - 1) / blockPage) * blockPage + 1;
		endPage = startPage + blockPage - 1;

		
		// 마지막 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		
		// 이전 block 페이지
		pagingHtml = new StringBuffer();
		pagingHtml.append("<a href=" + serviceName + ".do?searchType="+searchType+"&userinput="+userinput+"&currentPage=1><img src='./images/sub/btn/pre_01.gif'  alt='처음으로' /></a>");
		
		if (currentPage > blockPage) {
			pagingHtml.append("<a href=" + serviceName + ".do?searchType="+searchType+"&userinput="+userinput+"&currentPage=" + (startPage - 1) + ">");
			pagingHtml.append("<img src='./images/sub/btn/pre.gif' alt='이전 페이지로 가기' />");
			pagingHtml.append("</a>");
		}

		
		// 페이지 번호. 현재 페이지는 파란색으로 강조하고 링크를 제거
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == currentPage) {
				pagingHtml.append("<a> <font color='red'>");
				pagingHtml.append(i);
				pagingHtml.append(" </font></a>");
			} else {
				pagingHtml.append("<a href=" + serviceName
						+ ".do?searchType="+searchType+"&userinput="+userinput+"&currentPage=");
				pagingHtml.append(i);
				pagingHtml.append(">");
				pagingHtml.append(i);
				pagingHtml.append("</a>");
			}
		}

		// 다음 block 페이지
		if (totalPage - startPage >= blockPage) {
			pagingHtml.append("<a href=" + serviceName
					+ ".do?searchType="+searchType+"&userinput="+userinput+"&currentPage=" + (endPage + 1) + ">");
			pagingHtml.append("<img src='./images/sub/btn/next.gif' alt='다음 페이지' />");
			pagingHtml.append("</a>");
		}
		
		//마지막
		pagingHtml.append("<a href=" + serviceName + ".do?searchType="+searchType+"&userinput="+userinput+"&currentPage="+totalPage+"> <img src='./images/sub/btn/next_01.gif' alt='마지막페이지' /> </a>");
	}
    // .생성자2
    
    
    
    // 생성자3. 학교급식인력풀(구인) 검색글 페이징 동작시
    public PagingAction(String serviceName, int currentPage, int totalCount, int blockCount, int blockPage, String searchType, String subType, String subValue) {
    	
    	//요구된 파라미터 초기화
    	this.serviceName = serviceName;
		this.blockCount = blockCount;
		this.blockPage = blockPage;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.searchType = searchType;
		this.subType = subType;
		this.subValue = subValue;
		//.요구된 파라미터 초기화
		
		
		// 전체 페이지 수
		totalPage = (int) Math.ceil((double) totalCount / blockCount);
		if (totalPage == 0) {
			totalPage = 1;
		}

		
		// 현재 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		
		// 현재 페이지의 처음과 마지막 글의 번호 가져오기
		startCount = (currentPage - 1) * blockCount;
		endCount = startCount + blockCount - 1;

		
		// 시작 페이지와 마지막 페이지 값 구하기
		startPage = (int) ((currentPage - 1) / blockPage) * blockPage + 1;
		endPage = startPage + blockPage - 1;

		
		// 마지막 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		
		// 이전 block 페이지
		pagingHtml = new StringBuffer();
		pagingHtml.append("<a href=" + serviceName + ".do?searchType="+searchType+"&"+subType+"="+subValue+"&currentPage=1><img src='./images/sub/btn/pre_01.gif'  alt='처음으로' /></a>");
		
		if (currentPage > blockPage) {
			pagingHtml.append("<a href=" + serviceName + ".do?searchType="+searchType+"&"+subType+"="+subValue+"&currentPage=" + (startPage - 1) + ">");
			pagingHtml.append("<img src='./images/sub/btn/pre.gif' alt='이전 페이지로 가기' />");
			pagingHtml.append("</a>");
		}

		
		// 페이지 번호. 현재 페이지는 파란색으로 강조하고 링크를 제거
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == currentPage) {
				pagingHtml.append("<a> <font color='red'>");
				pagingHtml.append(i);
				pagingHtml.append(" </font></a>");
			} else {
				pagingHtml.append("<a href=" + serviceName
						+ ".do?searchType="+searchType+"&"+subType+"="+subValue+"&currentPage=");
				pagingHtml.append(i);
				pagingHtml.append(">");
				pagingHtml.append(i);
				pagingHtml.append("</a>");
			}
		}

		// 다음 block 페이지
		if (totalPage - startPage >= blockPage) {
			pagingHtml.append("<a href=" + serviceName
					+ ".do?searchType="+searchType+"&"+subType+"="+subValue+"&currentPage=" + (endPage + 1) + ">");
			pagingHtml.append("<img src='./images/sub/btn/next.gif' alt='다음 페이지' />");
			pagingHtml.append("</a>");
		}
		
		//마지막
		pagingHtml.append("<a href=" + serviceName + ".do?searchType="+searchType+"&"+subType+"="+subValue+"&currentPage="+totalPage+"> <img src='./images/sub/btn/next_01.gif' alt='마지막페이지' /></a>");
		
	}
    // .생성자3
    
    
    
    
    /*
    
    //현재 미사용 - 향후 사용할 가능성 있으므로 주석처리
    // 생성자4: String serviceName 파라미터 // 소댓글 페이징 동작시
    public PagingAction(String serviceName, int currentPage, int totalCount, int blockCount, int blockPage, int board_seq_num, int r_currentPage) {
    	//파라미터 초기화
    	this.serviceName = serviceName;
		this.blockCount = blockCount;
		this.blockPage = blockPage;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.board_seq_num = board_seq_num;
		this.r_currentPage = r_currentPage;

		// 전체 페이지 수
		totalPage = (int) Math.ceil((double) totalCount / blockCount);
		//리스트size() / 한페이지에 보여줄 게시글 수 = 페이지수(반올림)
		if (totalPage == 0) {
			totalPage = 1;
		}

		// 특수한경우 // 현재 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (r_currentPage > totalPage) {
			r_currentPage = totalPage;
		}

		// 현재 페이지의 처음과 마지막 글의 번호 가져오기
		startCount = (r_currentPage - 1) * blockCount;
		endCount = startCount + blockCount - 1;

		// 시작 페이지와 마지막 페이지 값 구하기
		startPage = (int) ((r_currentPage - 1) / blockPage) * blockPage + 1;
		endPage = startPage + blockPage - 1;
		
		// 마지막 페이지가 전체 페이지 수보다 크면 전체 페이지 수로 설정
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		
		// 이전 block 페이지
		pagingHtml = new StringBuffer();
		if (r_currentPage > blockPage) {
			pagingHtml.append("<a href=" + serviceName + ".do?board_seq_num="+board_seq_num+"&currentPage="+currentPage+"&r_currentPage=1><font size=2 color='#050099'> 처음으로 </font></a>");
			pagingHtml.append("<a href=" + serviceName + ".do?board_seq_num="+board_seq_num+"&currentPage="+currentPage+"&r_currentPage="+(startPage - 1)+">");
			pagingHtml.append("<b> 이전 </b>");
			pagingHtml.append("</a>");
		}

		// 페이지 번호. 
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == r_currentPage) {
				pagingHtml.append("<a><font color='red'> ");
				pagingHtml.append(i); //현재 페이지
				pagingHtml.append(" </font></a>");
			} else {
				pagingHtml.append("<a href=" + serviceName
						+ ".do?board_seq_num="+board_seq_num+"&currentPage="+currentPage+"&r_currentPage=");
				pagingHtml.append(i); //그 외 다른 페이지 링크
				pagingHtml.append("> ");
				pagingHtml.append(i);
				pagingHtml.append(" </a>");
			}
		}

		// 다음 block 페이지
		if (totalPage - startPage >= blockPage) {
			pagingHtml.append("<a href=" + serviceName
					+ ".do?board_seq_num="+board_seq_num+"&currentPage="+currentPage+"&r_currentPage=" + (endPage + 1) + ">");
			pagingHtml.append("<b> 다음 </b>");
			pagingHtml.append("</a>");
			pagingHtml.append("<a href=" + serviceName + ".do?board_seq_num="+board_seq_num+"&currentPage="+currentPage+"&r_currentPage="+totalPage+"> <font size=2 color='#050099'> 마지막으로 </font></a>");
		}
	}
    // .생성자4
    
    */
    
    
    
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getBlockCount() {
        return blockCount;
    }
    public void setBlockCount(int blockCount) {
        this.blockCount = blockCount;
    }
    public int getBlockPage() {
        return blockPage;
    }
    public void setBlockPage(int blockPage) {
        this.blockPage = blockPage;
    }
    public int getStartCount() {
        return startCount;
    }
    public void setStartCount(int startCount) {
        this.startCount = startCount;
    }
    public int getEndCount() {
        return endCount;
    }
    public void setEndCount(int endCount) {
        this.endCount = endCount;
    }
    public int getStartPage() {
        return startPage;
    }
    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }
    public int getEndPage() {
        return endPage;
    }
    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }
    public StringBuffer getPagingHtml() {
        return pagingHtml;
    }
    public void setPagingHtml(StringBuffer pagingHtml) {
        this.pagingHtml = pagingHtml;
    }

}
