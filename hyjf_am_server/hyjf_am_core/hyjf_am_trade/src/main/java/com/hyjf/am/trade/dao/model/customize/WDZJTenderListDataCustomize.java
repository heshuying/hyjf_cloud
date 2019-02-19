package com.hyjf.am.trade.dao.model.customize;

import com.hyjf.common.util.IdCard15To18;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class WDZJTenderListDataCustomize implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private String subscribeUserName;
    private String amount;
    private String validAmount;
    private String addDate;
    private String status;
    private String type;
    
	public String getSubscribeUserName() {
		return subscribeUserName;
//		return encryptTenderUser(subscribeUserName);
	}
	public void setSubscribeUserName(String subscribeUserName) {
		this.subscribeUserName = subscribeUserName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getValidAmount() {
		return validAmount;
	}
	public void setValidAmount(String validAmount) {
		this.validAmount = validAmount;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 出借人用户编号加密
	 * 出借人ID加密规则：
		1. 基础数据为出借人身份证号数据
		2. 字母部分：
		转为数字，X转为9
		3. 数字部分：
		每隔三位，将首位与第三位互换；不足三位，首位后移
	 * @param original
	 * @return
	 */
	private String encryptTenderUser(String original){
		String result = "";
		if(original == null || StringUtils.isBlank(original)){
			return "";
		}
		
		//身份证号长度小于18位的转换成18位
		if (original.length() < 18) {
			try {
				original = IdCard15To18.getEighteenIDCard(original);
			} catch (Exception e) {
				System.out.println("================获取身份证异常===============");
			}
		}
		
		original = original.replace("X", "9");
		
		if(original.length() < 18){
			return original;
		}
		
		char[] arr = original.toCharArray();
		result = result + arr[2] + arr[1] + arr[0] + arr[5] + arr[4] + arr[3] + arr[8] + arr[7] + arr[6] + arr[11] + arr[10]+ arr[9]
				+ arr[14] + arr[13] + arr[12] + arr[17] + arr[16] + arr[15];
		
		return result;
	}
    
    
}