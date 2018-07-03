package cc.mrbird.system.controller.bean;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class PayToUserRes {

	private String return_code;
	private String return_msg;
	private String mch_appid;
	private String mchid;
	private String device_info;
	private String nonce_str;
	private String result_code;
	private String err_code;
	private String err_code_des;
	private String partner_trade_no;
	private String payment_no;
	private String payment_time;

	/**
	 * @return the return_code
	 */
	public String getReturn_code() {
		return return_code;
	}

	/**
	 * @return the return_msg
	 */
	public String getReturn_msg() {
		return return_msg;
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
	 * @return the result_code
	 */
	public String getResult_code() {
		return result_code;
	}

	/**
	 * @return the err_code
	 */
	public String getErr_code() {
		return err_code;
	}

	/**
	 * @return the err_code_des
	 */
	public String getErr_code_des() {
		return err_code_des;
	}

	/**
	 * @return the partner_trade_no
	 */
	public String getPartner_trade_no() {
		return partner_trade_no;
	}

	/**
	 * @return the payment_no
	 */
	public String getPayment_no() {
		return payment_no;
	}

	/**
	 * @return the payment_time
	 */
	public String getPayment_time() {
		return payment_time;
	}

	/**
	 * @param return_code the return_code to set
	 */
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	/**
	 * @param return_msg the return_msg to set
	 */
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
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
	 * @param result_code the result_code to set
	 */
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	/**
	 * @param err_code the err_code to set
	 */
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	/**
	 * @param err_code_des the err_code_des to set
	 */
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	/**
	 * @param partner_trade_no the partner_trade_no to set
	 */
	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}

	/**
	 * @param payment_no the payment_no to set
	 */
	public void setPayment_no(String payment_no) {
		this.payment_no = payment_no;
	}

	/**
	 * @param payment_time the payment_time to set
	 */
	public void setPayment_time(String payment_time) {
		this.payment_time = payment_time;
	}

	public static PayToUserRes fromXml(String xml) {
		XStream xstream = new XStream() {
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {
					@Override
					public boolean shouldSerializeMember(@SuppressWarnings("rawtypes") Class definedIn,
							String fieldName) {
						if (definedIn == Object.class) {
							return false;
						}
						return super.shouldSerializeMember(definedIn, fieldName);
					}
				};
			}
		};
		xstream.alias("xml", PayToUserRes.class);
		return (PayToUserRes) xstream.fromXML(xml);
	}
}
