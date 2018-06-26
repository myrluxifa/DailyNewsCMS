package cc.mrbird.system.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name="t_advert_img")
public class AdvertImg implements Serializable {
	
	
	@Id
	@GeneratedValue(generator = "UUID")
	private String id;
	
	private String img;
	
	private String advertId;
	
	
	public AdvertImg() {
		// TODO Auto-generated constructor stub
	}
	
	public AdvertImg(String img,String advertId) {
		// TODO Auto-generated constructor stub
		this.img=img;
		this.advertId=advertId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getAdvertId() {
		return advertId;
	}

	public void setAdvertId(String advertId) {
		this.advertId = advertId;
	}
	
	
}
