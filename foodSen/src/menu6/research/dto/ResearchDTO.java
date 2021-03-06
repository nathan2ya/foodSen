package menu6.research.dto;

import java.util.Date;

/*
 * 작성자: 류재욱
 * 설  명: 설문조사(정보) DTO
 * 용  도: 
*/

public class ResearchDTO {
	private int sur_seq;//설문번호
	private String sur_title;//제목
	private String que_cnt;//문제수
	private String sur_sat_date;//설문시작일자
	private String sur_end_date;//설문종료일자
	private int hits;//조회수
	private String writer;//작성자
	private String reg_name;//등록자
	private Date reg_date;//등록일
	private String udt_name;//수정자
	private Date udt_date;//수정일
	
	public int getSur_seq() {
		return sur_seq;
	}
	public void setSur_seq(int sur_seq) {
		this.sur_seq = sur_seq;
	}
	public String getSur_title() {
		return sur_title;
	}
	public void setSur_title(String sur_title) {
		this.sur_title = sur_title;
	}
	public String getQue_cnt() {
		return que_cnt;
	}
	public void setQue_cnt(String que_cnt) {
		this.que_cnt = que_cnt;
	}
	public String getSur_sat_date() {
		return sur_sat_date;
	}
	public void setSur_sat_date(String sur_sat_date) {
		this.sur_sat_date = sur_sat_date;
	}
	public String getSur_end_date() {
		return sur_end_date;
	}
	public void setSur_end_date(String sur_end_date) {
		this.sur_end_date = sur_end_date;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
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
