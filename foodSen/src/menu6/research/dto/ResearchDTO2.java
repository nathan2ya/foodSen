package menu6.research.dto;

import java.util.Date;

/*
 * 작성자: 류재욱
 * 설  명: 설문조사(문항) DTO
 * 용  도: 
*/

public class ResearchDTO2 {
	private int suri_seq;//문항번호
	private int surq_seq;//설문번호
	private int sur_seq;//문제번호
	private String suri_title1;//문항제목1
	private String suri_title2;//문항제목2
	private String suri_title3;//문항제목3
	private String suri_title4;//문항제목4
	private String suri_title5;//문항제목5
	private String writer;//작성자
	private String reg_name;//등록자
	private Date reg_date;//등록일
	private String udt_name;//수정자
	private Date udt_date;//수정일
	private int suri_num1;//문항1
	private int suri_num2;//문항2
	private int suri_num3;//문항3
	private int suri_num4;//문항4
	private int suri_num5;//문항5
	
	public int getSuri_seq() {
		return suri_seq;
	}
	public void setSuri_seq(int suri_seq) {
		this.suri_seq = suri_seq;
	}
	public int getSurq_seq() {
		return surq_seq;
	}
	public void setSurq_seq(int surq_seq) {
		this.surq_seq = surq_seq;
	}
	public int getSur_seq() {
		return sur_seq;
	}
	public void setSur_seq(int sur_seq) {
		this.sur_seq = sur_seq;
	}
	public String getSuri_title1() {
		return suri_title1;
	}
	public void setSuri_title1(String suri_title1) {
		this.suri_title1 = suri_title1;
	}
	public String getSuri_title2() {
		return suri_title2;
	}
	public void setSuri_title2(String suri_title2) {
		this.suri_title2 = suri_title2;
	}
	public String getSuri_title3() {
		return suri_title3;
	}
	public void setSuri_title3(String suri_title3) {
		this.suri_title3 = suri_title3;
	}
	public String getSuri_title4() {
		return suri_title4;
	}
	public void setSuri_title4(String suri_title4) {
		this.suri_title4 = suri_title4;
	}
	public String getSuri_title5() {
		return suri_title5;
	}
	public void setSuri_title5(String suri_title5) {
		this.suri_title5 = suri_title5;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getReg_name() {
		return reg_name;
	}
	public void setReg_name(String reg_name) {
		this.reg_name = reg_name;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getUdt_name() {
		return udt_name;
	}
	public void setUdt_name(String udt_name) {
		this.udt_name = udt_name;
	}
	public Date getUdt_date() {
		return udt_date;
	}
	public void setUdt_date(Date udt_date) {
		this.udt_date = udt_date;
	}
	public int getSuri_num1() {
		return suri_num1;
	}
	public void setSuri_num1(int suri_num1) {
		this.suri_num1 = suri_num1;
	}
	public int getSuri_num2() {
		return suri_num2;
	}
	public void setSuri_num2(int suri_num2) {
		this.suri_num2 = suri_num2;
	}
	public int getSuri_num3() {
		return suri_num3;
	}
	public void setSuri_num3(int suri_num3) {
		this.suri_num3 = suri_num3;
	}
	public int getSuri_num4() {
		return suri_num4;
	}
	public void setSuri_num4(int suri_num4) {
		this.suri_num4 = suri_num4;
	}
	public int getSuri_num5() {
		return suri_num5;
	}
	public void setSuri_num5(int suri_num5) {
		this.suri_num5 = suri_num5;
	}
}
