package menu6.research.dto;

import java.util.Date;

/*
 * 작성자: 류재욱
 * 설  명: 설문조사(문제) DTO
 * 용  도: 
*/

public class ResearchDTO1 {
	private int surq_seq;//문제번호
	private int sur_seq;//설문번호
	private String surq_title;//문제
	private String writer;//작성자
	private String reg_name;//등록자
	private Date reg_date;//등록일
	private String udt_name;//수정자
	private Date udt_date;//수정일
	
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
	public String getSurq_title() {
		return surq_title;
	}
	public void setSurq_title(String surq_title) {
		this.surq_title = surq_title;
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
}
