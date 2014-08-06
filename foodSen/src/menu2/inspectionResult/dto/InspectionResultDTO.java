package menu2.inspectionResult.dto;

import java.util.Date;

/*
 * 작성자: 류재욱
 * 설  명: 위생안전성검사결과 DTO
 * 용  도: 
*/

public class InspectionResultDTO {
	private int seq;//시퀀스
	private String title;//제목
	private String attach_name;//파일이름
	private String attach_path;//파일경로
	private String description;//내용
	private String pw;//비밀번호
	private int hits;//조회수
	private String wirte;//작성자
	private String reg_name;//등록자
	private Date reg_date;//등록일
	private String udt_name;//수정자
	private Date udt_date;//수정일
	private int rownum;//rownum(정렬)
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAttach_name() {
		return attach_name;
	}
	public void setAttach_name(String attach_name) {
		this.attach_name = attach_name;
	}
	public String getAttach_path() {
		return attach_path;
	}
	public void setAttach_path(String attach_path) {
		this.attach_path = attach_path;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getWirte() {
		return wirte;
	}
	public void setWirte(String wirte) {
		this.wirte = wirte;
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
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
}
