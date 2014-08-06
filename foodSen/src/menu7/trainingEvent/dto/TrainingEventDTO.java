package menu7.trainingEvent.dto;

import java.util.Date;

/*
 * 작성자: 류재욱
 * 설  명: 연수행사(스케줄) DTO
 * 용  도: 
*/

public class TrainingEventDTO {
	private int seq;//시퀀스
	private String title;//제목
	private String gubun;//새글0, 답글1
	private String str_date;//시작일자
	private String end_date;//종료일자
	private String attach_name;//파일이름
	private String attach_path;//파일경로
	private String pw;//비밀번호
	private String img1;//이미지1경로
	private String img2;//이미지2경로
	private String img3;//이미지3경로
	private String description;//내용
	private int up_seq;//부모의시퀀스
	private int hits;//조회수
	private String writer;//작성자
	private String reg_name;//등록자
	private Date reg_date;//등록일
	private String udt_name;//수정자
	private Date udt_date;//수정일
	private String turn;//답글없음0,답글있음1
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
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getStr_date() {
		return str_date;
	}
	public void setStr_date(String str_date) {
		this.str_date = str_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
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
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getUp_seq() {
		return up_seq;
	}
	public void setUp_seq(int up_seq) {
		this.up_seq = up_seq;
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
	public String getTurn() {
		return turn;
	}
	public void setTurn(String turn) {
		this.turn = turn;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
}
