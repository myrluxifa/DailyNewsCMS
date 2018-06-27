package cc.mrbird.system.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="t_balance_log")
public class BalanceLog implements Serializable {
	@Id
	@GeneratedValue(generator = "UUID")
	private String id;
	
	private String userId;
	
	private String type;
	
	private String num;
	
	private String oldNum;
	
	private String newNum;
	
	private String createUser;
	
	private Date createTime;
	
	private String remark;
	
	private Date updateTime;
	
	private String triggerUserId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getOldNum() {
		return oldNum;
	}

	public void setOldNum(String oldNum) {
		this.oldNum = oldNum;
	}

	public String getNewNum() {
		return newNum;
	}

	public void setNewNum(String newNum) {
		this.newNum = newNum;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTriggerUserId() {
		return triggerUserId;
	}

	public void setTriggerUserId(String triggerUserId) {
		this.triggerUserId = triggerUserId;
	}
	
	
}
