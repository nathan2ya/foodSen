package header.member.dto;

import java.util.Date;

/*
 * �ۼ���: �����
 * ��  ��: ȸ������ DTO
 * ��  ��: 
*/

public class MemberDTO {
	private String user_id;//����� ���̵�
	private String user_pw;//����� ��й�ȣ
	private int member_seq;//ȸ�� ���̺��� ������
	private String member_name;//ȸ�� �̸�
	private String school_name;//�б���
	private String position;//����
	private String sen_email;//�̸���
	private String phone;//����ȣ
	private String admin_yn;//�����ڿ���
	private String admin_pw;//�����ں�й�ȣ
	private String approve_yn;//���ٱ��� ����
	private String school_type;//�б�����
	private Date reg_date;//��ϳ�¥
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getMember_seq() {
		return member_seq;
	}
	public void setMember_seq(int member_seq) {
		this.member_seq = member_seq;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getSchool_name() {
		return school_name;
	}
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSen_email() {
		return sen_email;
	}
	public void setSen_email(String sen_email) {
		this.sen_email = sen_email;
	}
	public String getAdmin_yn() {
		return admin_yn;
	}
	public void setAdmin_yn(String admin_yn) {
		this.admin_yn = admin_yn;
	}
	public String getAdmin_pw() {
		return admin_pw;
	}
	public void setAdmin_pw(String admin_pw) {
		this.admin_pw = admin_pw;
	}
	public String getApprove_yn() {
		return approve_yn;
	}
	public void setApprove_yn(String approve_yn) {
		this.approve_yn = approve_yn;
	}
	public String getSchool_type() {
		return school_type;
	}
	public void setSchool_type(String school_type) {
		this.school_type = school_type;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
