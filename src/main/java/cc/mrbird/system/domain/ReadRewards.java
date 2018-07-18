package cc.mrbird.system.domain;

import javax.persistence.Table;

@Table(name="t_sys_gold_rewards")
public class ReadRewards {

	private String id;
	
	private int dailyCnt;
	
	private int gold;
	
	private String explain;
	
	private int hour;
	
	private int horCnt;
	
	private String horMoney;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDailyCnt() {
		return dailyCnt;
	}

	public void setDailyCnt(int dailyCnt) {
		this.dailyCnt = dailyCnt;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getHorCnt() {
		return horCnt;
	}

	public void setHorCnt(int horCnt) {
		this.horCnt = horCnt;
	}

	public String getHorMoney() {
		return horMoney;
	}

	public void setHorMoney(String horMoney) {
		this.horMoney = horMoney;
	}

	
	
	
}
