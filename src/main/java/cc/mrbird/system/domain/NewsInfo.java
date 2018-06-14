package cc.mrbird.system.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name="t_news_info")
public class NewsInfo  implements Serializable{
	
	@Id
	@GeneratedValue(generator = "JDBC")
	private String id;
	
	private Date publishDate;
	
	private String idataId;
	
	private String posterScreenName;
	
	private String url;
	
	private String title;
	
	private String posterId;
	
	private Integer viewCount;
	
	private String content;
	
	private String imgsUrl;
	
	private String catId;
	
	private int flag;
	
	@Transient
	private String catValue;
	
	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public NewsInfo() {
		// TODO Auto-generated constructor stub
	}
	
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getIdataId() {
		return idataId;
	}

	public void setIdataId(String idataId) {
		this.idataId = idataId;
	}

	public String getPosterScreenName() {
		return posterScreenName;
	}

	public void setPosterScreenName(String posterScreenName) {
		this.posterScreenName = posterScreenName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPosterId() {
		return posterId;
	}

	public void setPosterId(String posterId) {
		this.posterId = posterId;
	}

	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getImgsUrl() {
		return imgsUrl;
	}

	public void setImgsUrl(String imgsUrl) {
		this.imgsUrl = imgsUrl;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getCatValue() {
		return catValue;
	}

	public void setCatValue(String catValue) {
		this.catValue = catValue;
	}
	
	
}
