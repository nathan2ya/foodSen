package common;


/*
 * �ۼ��� : �����
 * ��  �� : foodSen ������Ʈ���� �������� ���� ������������ ������ �������̵� ����.
 * ��  �� : foodSen ������Ʈ���� �������� �� ��� �� Ŭ������ ��ü�� �����Ͽ� StringBuffer Ÿ���� ����� ���.
 * 
 * ������1 - ��ü�� ���۽� ȣ��
 * ������2 - �˻���(����1��) ���۽� ȣ��
 * ������3 - �˻���(����2��) ���۽� ȣ�� (ex. �б��޽��η�Ǯ(����))
*/


public class PagingAction {

	//ȣ�� URI mapping �ּ�
	private String serviceName; //ȣ�� URI ����
	
	//�Խù� count
    private int totalCount; // ��ü �Խù� ��
    private int totalPage; // ��ü ������ ��
    
    //������ ����
    private int blockCount; // �� �������� �Խù��� ��
    private int blockPage; // �� ȭ�鿡 ������ ������ ��
    
    /*
     * ����������(��������)���� 
     * ��� ���ڵ�, ��� �������� ������� �������� ����.
    */
    private int startCount; // �� ���������� ������ �Խñ��� ���� ��ȣ
    private int endCount; // �� ���������� ������ �Խñ��� �� ��ȣ
    private int startPage; // ���� ������
    private int endPage; // ������ ������
    
    /*
     * ȣ������������ �䱸�� ������ ��� �ִ� ����.
     * �Ʒ��� ������ ������ ��� ������� �޶���.
    */
    private int currentPage; // ���������� // URI
    private int board_seq_num; // �Ҵ���� ȣ�������� // URI
    private int r_currentPage; // �Ҵ�� ���������� // URI
    private String searchType; // �˻�Ÿ�� //URI
    private String userinput; // �˻��� //URI
    private String subType; // ����˻����� //URI
    private String subValue; // ����˻��� //URI
    
    //����������� ���� ����
    private StringBuffer pagingHtml; //�����ȯ
    
    
    
    // ������1. ��ü�� ���۽ÿ� ȣ��
    public PagingAction(String serviceName, int currentPage, int totalCount, int blockCount, int blockPage) {
    	
    	//ȣ������������ �䱸�� �Ķ���� �ʱ�ȭ
    	this.serviceName = serviceName;
		this.blockCount = blockCount;
		this.blockPage = blockPage;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		//.ȣ������������ �䱸�� �Ķ���� �ʱ�ȭ
		
		
		/*
		 * ��ü��������
		 * �ѷ��ڵ尳��/�����������ڵ尳�� -> �ݿø� -> int ����ȯ
		 * 1�������� 1���� �����ϹǷ� ceil�� �̿��� �ݿø�
		*/
		totalPage = (int) Math.ceil((double) totalCount / blockCount);
		
		
		//totalPage �� ���� 0�� ���, ���ڵ尡 ���°ɷ� �����ϰ� ���� 1�������� �ʱ�ȭ
		if (totalPage == 0){ 
			totalPage = 1;
		}
		

		// ����ڵ�_ ����ڰ� �ּ�â�� ������������ ��ü������ ���� ũ�� �Է��Ұ�� ��ü���������� �ʱ�ȭ ���ѹ���
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		
		/*
		 * ������������ ù ���ڵ�, ���������ڵ�� ���� list �ε��� ��ȣ�� ����.
		 * ���� startCount, endCount ���� subList�� index value�� ���.
		*/
		startCount = (currentPage - 1) * blockCount;
		endCount = startCount + blockCount - 1;
		
		/*
		 * ����������, �������������� ������������ ���� ����.
		 * (����-1)  : -1 ������ blockPage�� ������ ��츦 ���ϱ� ���� && blockPage �� ������ ����ȯ �������� ����
		 *      +1   : +1 ������ �տ��� -1 �����ϱ�.
		 * 
		*/
		startPage = (int) ((currentPage - 1) / blockPage) * blockPage + 1;
		endPage = startPage + blockPage - 1;
		
		
		// ������������ ������_ �������������� ��ü������ ������ Ŭ��� ��ü�������� ������
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		
		
		//��� ���庯��
		pagingHtml = new StringBuffer();
		
		//ó������
		pagingHtml.append("<a href=" + serviceName + ".do?currentPage=1><img src='./images/sub/btn/pre_01.gif' alt='ó������' /></a>");
		
		
		// ���� block ������(���� ������ �ʰ��� ��쿡�� ����)
		if (currentPage > blockPage) {
			pagingHtml.append("<a href=" + serviceName + ".do?currentPage=" + (startPage - 1) + ">");
			pagingHtml.append(" <img src='./images/sub/btn/pre.gif' alt='���� �������� ����' /> ");
			pagingHtml.append("</a>");
		}

		// ������ ��ȣ
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == currentPage) {
				pagingHtml.append("<a><font color='red'> ");
				pagingHtml.append(i); //���� ������
				pagingHtml.append(" </font></a>");
			} else {
				pagingHtml.append("<a href=" + serviceName
						+ ".do?currentPage=");
				pagingHtml.append(i); //�� �� �ٸ� ������ ��ũ
				pagingHtml.append("> ");
				pagingHtml.append(i);
				pagingHtml.append(" </a>");
			}
		}

		// ���� block ������(���� ������ �ƴ� ��쿡�� ����)
		if (totalPage - startPage >= blockPage) {
			pagingHtml.append("<a href=" + serviceName+ ".do?currentPage=" + (endPage + 1) + ">");
			pagingHtml.append(" <img src='./images/sub/btn/next.gif' alt='���� ������' /> ");
			pagingHtml.append("</a>");
		}
		
		//����������
		pagingHtml.append("<a href=" + serviceName + ".do?currentPage="+totalPage+"><img src='./images/sub/btn/next_01.gif' alt='������������' /></a>");
		
	}
    //.������1
    
    

    // ������2. �˻���(����1��) ���۽ÿ� ȣ��
    public PagingAction(String serviceName, int currentPage, int totalCount, int blockCount, int blockPage, String searchType, String userinput) {
    	
    	//ȣ������������ �䱸�� �Ķ���� �ʱ�ȭ
    	this.serviceName = serviceName;
		this.blockCount = blockCount;
		this.blockPage = blockPage;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.searchType = searchType;
		this.userinput = userinput;
		//.ȣ������������ �䱸�� �Ķ���� �ʱ�ȭ
		
		
		/*
		 * ��ü��������
		 * �ѷ��ڵ尳��/�����������ڵ尳�� -> �ݿø� -> int ����ȯ
		 * 1�������� 1���� �����ϹǷ� ceil�� �̿��� �ݿø�
		*/
		totalPage = (int) Math.ceil((double) totalCount / blockCount);
		
		//totalPage �� ���� 0�� ���, ���ڵ尡 ���°ɷ� �����ϰ� ���� 1�������� �ʱ�ȭ
		if (totalPage == 0) {
			totalPage = 1;
		}

		
		// ���� �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		
		/*
		 * ������������ ù ���ڵ�, ���������ڵ�� ���� list �ε��� ��ȣ�� ����.
		 * ���� startCount, endCount ���� subList�� index value�� ���.
		*/
		startCount = (currentPage - 1) * blockCount;
		endCount = startCount + blockCount - 1;

		
		/*
		 * ����������, �������������� ������������ ���� ����.
		 * (����-1)  : -1 ������ blockPage�� ������ ��츦 ���ϱ� ���� && blockPage �� ������ ����ȯ �������� ����
		 *      +1   : +1 ������ �տ��� -1 �����ϱ�.
		 * 
		*/
		startPage = (int) ((currentPage - 1) / blockPage) * blockPage + 1;
		endPage = startPage + blockPage - 1;

		
		// ������ �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		
		// ���� block ������
		pagingHtml = new StringBuffer();
		pagingHtml.append("<a href=" + serviceName + ".do?searchType="+searchType+"&userinput="+userinput+"&currentPage=1><img src='./images/sub/btn/pre_01.gif'  alt='ó������' /></a>");
		
		if (currentPage > blockPage) {
			pagingHtml.append("<a href=" + serviceName + ".do?searchType="+searchType+"&userinput="+userinput+"&currentPage=" + (startPage - 1) + ">");
			pagingHtml.append("<img src='./images/sub/btn/pre.gif' alt='���� �������� ����' />");
			pagingHtml.append("</a>");
		}

		
		// ������ ��ȣ. ���� �������� �Ķ������� �����ϰ� ��ũ�� ����
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

		// ���� block ������
		if (totalPage - startPage >= blockPage) {
			pagingHtml.append("<a href=" + serviceName
					+ ".do?searchType="+searchType+"&userinput="+userinput+"&currentPage=" + (endPage + 1) + ">");
			pagingHtml.append("<img src='./images/sub/btn/next.gif' alt='���� ������' />");
			pagingHtml.append("</a>");
		}
		
		//������
		pagingHtml.append("<a href=" + serviceName + ".do?searchType="+searchType+"&userinput="+userinput+"&currentPage="+totalPage+"> <img src='./images/sub/btn/next_01.gif' alt='������������' /> </a>");
	}
    // .������2
    
    
    
    // ������3. �˻���(����2��) ���۽ÿ� ȣ��. //�б��޽��η�Ǯ(����) �˻��� ����¡ ���۽�
    public PagingAction(String serviceName, int currentPage, int totalCount, int blockCount, int blockPage, String searchType, String subType, String subValue) {
    	
    	//ȣ������������ �䱸�� �Ķ���� �ʱ�ȭ
    	this.serviceName = serviceName;
		this.blockCount = blockCount;
		this.blockPage = blockPage;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.searchType = searchType;
		this.subType = subType;
		this.subValue = subValue;
		//.ȣ������������ �䱸�� �Ķ���� �ʱ�ȭ
		
		
		// ��ü ������ ��
		totalPage = (int) Math.ceil((double) totalCount / blockCount);
		if (totalPage == 0) {
			totalPage = 1;
		}

		
		// ����ڵ�_ ����ڰ� �ּ�â�� ������������ ��ü������ ���� ũ�� �Է��Ұ�� ��ü���������� �ʱ�ȭ ���ѹ���
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		
		// ���� �������� ó���� ������ ���� ��ȣ ��������
		startCount = (currentPage - 1) * blockCount;
		endCount = startCount + blockCount - 1;

		
		// ���� �������� ������ ������ �� ���ϱ�
		startPage = (int) ((currentPage - 1) / blockPage) * blockPage + 1;
		endPage = startPage + blockPage - 1;

		
		// ������ �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		
		// ���� block ������
		pagingHtml = new StringBuffer();
		pagingHtml.append("<a href=" + serviceName + ".do?searchType="+searchType+"&"+subType+"="+subValue+"&currentPage=1><img src='./images/sub/btn/pre_01.gif'  alt='ó������' /></a>");
		
		if (currentPage > blockPage) {
			pagingHtml.append("<a href=" + serviceName + ".do?searchType="+searchType+"&"+subType+"="+subValue+"&currentPage=" + (startPage - 1) + ">");
			pagingHtml.append("<img src='./images/sub/btn/pre.gif' alt='���� �������� ����' />");
			pagingHtml.append("</a>");
		}

		
		// ������ ��ȣ. ���� �������� �Ķ������� �����ϰ� ��ũ�� ����
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

		// ���� block ������
		if (totalPage - startPage >= blockPage) {
			pagingHtml.append("<a href=" + serviceName
					+ ".do?searchType="+searchType+"&"+subType+"="+subValue+"&currentPage=" + (endPage + 1) + ">");
			pagingHtml.append("<img src='./images/sub/btn/next.gif' alt='���� ������' />");
			pagingHtml.append("</a>");
		}
		
		//������
		pagingHtml.append("<a href=" + serviceName + ".do?searchType="+searchType+"&"+subType+"="+subValue+"&currentPage="+totalPage+"> <img src='./images/sub/btn/next_01.gif' alt='������������' /></a>");
		
	}
    // .������3
    
    
    
    
    /*
    
    //���� �̻�� - ���� ����� ���ɼ� �����Ƿ� �ּ�ó��
    // ������4: String serviceName �Ķ���� // �Ҵ�� ����¡ ���۽�
    public PagingAction(String serviceName, int currentPage, int totalCount, int blockCount, int blockPage, int board_seq_num, int r_currentPage) {
    	//�Ķ���� �ʱ�ȭ
    	this.serviceName = serviceName;
		this.blockCount = blockCount;
		this.blockPage = blockPage;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.board_seq_num = board_seq_num;
		this.r_currentPage = r_currentPage;

		// ��ü ������ ��
		totalPage = (int) Math.ceil((double) totalCount / blockCount);
		//����Ʈsize() / ���������� ������ �Խñ� �� = ��������(�ݿø�)
		if (totalPage == 0) {
			totalPage = 1;
		}

		// Ư���Ѱ�� // ���� �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (r_currentPage > totalPage) {
			r_currentPage = totalPage;
		}

		// ���� �������� ó���� ������ ���� ��ȣ ��������
		startCount = (r_currentPage - 1) * blockCount;
		endCount = startCount + blockCount - 1;

		// ���� �������� ������ ������ �� ���ϱ�
		startPage = (int) ((r_currentPage - 1) / blockPage) * blockPage + 1;
		endPage = startPage + blockPage - 1;
		
		// ������ �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		
		// ���� block ������
		pagingHtml = new StringBuffer();
		if (r_currentPage > blockPage) {
			pagingHtml.append("<a href=" + serviceName + ".do?board_seq_num="+board_seq_num+"&currentPage="+currentPage+"&r_currentPage=1><font size=2 color='#050099'> ó������ </font></a>");
			pagingHtml.append("<a href=" + serviceName + ".do?board_seq_num="+board_seq_num+"&currentPage="+currentPage+"&r_currentPage="+(startPage - 1)+">");
			pagingHtml.append("<b> ���� </b>");
			pagingHtml.append("</a>");
		}

		// ������ ��ȣ. 
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == r_currentPage) {
				pagingHtml.append("<a><font color='red'> ");
				pagingHtml.append(i); //���� ������
				pagingHtml.append(" </font></a>");
			} else {
				pagingHtml.append("<a href=" + serviceName
						+ ".do?board_seq_num="+board_seq_num+"&currentPage="+currentPage+"&r_currentPage=");
				pagingHtml.append(i); //�� �� �ٸ� ������ ��ũ
				pagingHtml.append("> ");
				pagingHtml.append(i);
				pagingHtml.append(" </a>");
			}
		}

		// ���� block ������
		if (totalPage - startPage >= blockPage) {
			pagingHtml.append("<a href=" + serviceName
					+ ".do?board_seq_num="+board_seq_num+"&currentPage="+currentPage+"&r_currentPage=" + (endPage + 1) + ">");
			pagingHtml.append("<b> ���� </b>");
			pagingHtml.append("</a>");
			pagingHtml.append("<a href=" + serviceName + ".do?board_seq_num="+board_seq_num+"&currentPage="+currentPage+"&r_currentPage="+totalPage+"> <font size=2 color='#050099'> ���������� </font></a>");
		}
	}
    // .������4
    
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
