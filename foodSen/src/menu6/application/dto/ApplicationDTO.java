package menu6.application.dto;

import java.util.Date;

/*
 * �ۼ���: �����
 * ��  ��: �޽��η�Ǯ(����) DTO
 * ��  ��: 
*/

public class ApplicationDTO {
    private int seq;//������
    private String title;//����
    private String pw;//��й�ȣ
    private String loc_seq;//����
    private String sex;//����
    private String job;//���米��/�����/������/������/��ĵ����
    private String gubun;//������/�ð���
    private String email;//�̸����ּ�
    private String phone;//����ȣ
    private String description;//����
    private String attach_name;//�����̸�
    private String attach_path;//���ϰ��
    private int hits;//��ȸ��
    private String writer;//�ۼ���
    private String reg_name;//�����
    private Date reg_date;//�����
    private String udt_name;//������
    private Date udt_date;//������
    private String age;//����
    private String end_yn;//��������
    
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
	public String getLoc_seq() {
		return loc_seq;
	}
	public void setLoc_seq(String loc_seq) {
		this.loc_seq = loc_seq;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getEnd_yn() {
		return end_yn;
	}
	public void setEnd_yn(String end_yn) {
		this.end_yn = end_yn;
	}
}
