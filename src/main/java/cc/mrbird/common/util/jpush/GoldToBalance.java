package cc.mrbird.common.util.jpush;

public class GoldToBalance {
	private String body;
	
	private String createTime;

	
	public GoldToBalance() {
		// TODO Auto-generated constructor stub
	}
	
	public GoldToBalance(String body,String createTime) {
		// TODO Auto-generated constructor stub
		this.body=body;
		this.createTime=createTime;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
