package cc.mrbird.system.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="t_easy_money")
public class EasyMoney {

	
	@Id
	@GeneratedValue(generator = "JDBC")
	private String id;
	
	private String title;
	
	private Date createTime;
	
	private String createUser;
	
	private String textare;
	
	private String img;
	
	private int flag;
	
	@Transient
	private String imgOld;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getTextare() {
		return textare;
	}

	public void setTextare(String textare) {
		this.textare = textare;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getImgOld() {
		return imgOld;
	}

	public void setImgOld(String imgOld) {
		this.imgOld = imgOld;
	}
	
	
	
}
