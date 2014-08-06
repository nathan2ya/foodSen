package menu6.recruit.dto;

import java.util.Date;

/*
 * 작성자: 류재욱
 * 설  명: 급식인력풀(구인) DTO
 * 용  도: 
*/

public class RecruitDTO {
    private int seq;//시퀀스
    private String title;//제목
    private String pw;//비밀번호
    private String school_name;//학교이름
    private String school_type;//초/중/고/특수/각종
    private String loc_seq;//지역
    private String job;//영양교사/영양사/조리사/조리원/배식도우미
    private String gubun;//전일제/시간제
    private String email;//이메일주소
    private String phone;//폰번호
    private String description;//내용
    private String attach_name;//파일이름
    private String attach_path;//파일경로
    private int hits;//조회수
    private String writer;//작성자
    private String reg_name;//등록자
    private Date reg_date;//등록일
	private String udt_name;//수정자
	private Date udt_date;//수정일
	private String end_yn;//마감여부
	private int rownum;//로우넘(정렬)
	
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
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getSchool_name() {
		return school_name;
	}
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	public String getSchool_type() {
		return school_type;
	}
	public void setSchool_type(String school_type) {
		this.school_type = school_type;
	}
	public String getLoc_seq() {
		return loc_seq;
	}
	public void setLoc_seq(String loc_seq) {
		this.loc_seq = loc_seq;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getEnd_yn() {
		return end_yn;
	}
	public void setEnd_yn(String end_yn) {
		this.end_yn = end_yn;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
}
