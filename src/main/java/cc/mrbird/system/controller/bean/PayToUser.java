package cc.mrbird.system.controller.bean;

public class PayToUser {

	private String mch_appid;
	private String mchid;
	private String device_info;
	private String nonce_str;
	private String sign;
	private String partner_trade_no;
	private String openid;
	private String check_name;
	private String re_user_name;
	private String amount;
	private String desc;
	private String spbill_create_ip;
	
	public PayToUser() {
		super();
	}

	public PayToUser(String mch_appid, String mchid, String device_info, String nonce_str,
			String partner_trade_no, String openid, String check_name, String re_user_name, String amount, String desc,
			String spbill_create_ip) {
		super();
		this.mch_appid = mch_appid;
		this.mchid = mchid;
		this.device_info = device_info;
		this.nonce_str = nonce_str;
		this.partner_trade_no = partner_trade_no;
		this.openid = openid;
		this.check_name = check_name;
		this.re_user_name = re_user_name;
		this.amount = amount;
		this.desc = desc;
		this.spbill_create_ip = spbill_create_ip;
	}

	/**
	 * @return the mch_appid
	 */
	public String getMch_appid() {
		return mch_appid;
	}

	/**
	 * @return the mchid
	 */
	public String getMchid() {
		return mchid;
	}

	/**
	 * @return the device_info
	 */
	public String getDevice_info() {
		return device_info;
	}

	/**
	 * @return the nonce_str
	 */
	public String getNonce_str() {
		return nonce_str;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @return the partner_trade_no
	 */
	public String getPartner_trade_no() {
		return partner_trade_no;
	}

	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @return the check_name
	 */
	public String getCheck_name() {
		return check_name;
	}

	/**
	 * @return the re_user_name
	 */
	public String getRe_user_name() {
		return re_user_name;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @return the spbill_create_ip
	 */
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	/**
	 * @param mch_appid the mch_appid to set
	 */
	public void setMch_appid(String mch_appid) {
		this.mch_appid = mch_appid;
	}

	/**
	 * @param mchid the mchid to set
	 */
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	/**
	 * @param device_info the device_info to set
	 */
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	/**
	 * @param nonce_str the nonce_str to set
	 */
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @param partner_trade_no the partner_trade_no to set
	 */
	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}

	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @param check_name the check_name to set
	 */
	public void setCheck_name(String check_name) {
		this.check_name = check_name;
	}

	/**
	 * @param re_user_name the re_user_name to set
	 */
	public void setRe_user_name(String re_user_name) {
		this.re_user_name = re_user_name;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @param spbill_create_ip the spbill_create_ip to set
	 */
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

}
