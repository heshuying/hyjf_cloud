package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hyjf.common.validator.Validator;

public class WDZJBorrowListDataCustomize implements Serializable {
	private static final long serialVersionUID = 1L;

    private String projectId;
    private String userName;
    private String title;
    private String amount;
    private String type;
    private String schedule;
    private String interestRate;
    private String deadline;
    private String deadlineUnit;
    private String repaymentType;
    private String loanUrl;
    private String successTime;
    private String reward;
    
    private List<WDZJTenderListDataCustomize> subscribes;

	public String getProjectId() {
		return encryptBorrowNid(projectId);
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getDeadlineUnit() {
		return deadlineUnit;
	}

	public void setDeadlineUnit(String deadlineUnit) {
		this.deadlineUnit = deadlineUnit;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getLoanUrl() {
		return loanUrl;
	}

	public void setLoanUrl(String loanUrl) {
		this.loanUrl = loanUrl;
	}

	public String getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}

	public List<WDZJTenderListDataCustomize> getSubscribes() {
		return subscribes;
	}

	public void setSubscribes(List<WDZJTenderListDataCustomize> subscribes) {
		this.subscribes = subscribes;
	}

	public String getUserName() {
		return encryptSendBorrow(userName);
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	/**
	 * 敏感字段加密
	 * 发标人ID加密规则：
		1. 基础数据为项目编号数据
		2. 字母部分：
		转为数字，A开始，对应数字11开始；依次类推。B对应12，C对应13，...
		3. 日期部分：
		A. 年，每两位+20,如：2017，对应数字4037
		B. 月，每两位+8，如：12，对应数字82
		3. 其余位数：
		A. 为5位时，只取前三位
		B. 为4位时，每两位前后互换
		C. 为7位时，前两位往后挪两位，最后一位挪到第一位
		D. 为8位时，前两位往后挪三位
	 * @param original
	 * @return
	 */
	private static String encryptSendBorrow(String original){
		String result = "";
		if(original == null || StringUtils.isBlank(original)){
			return "";
		}
		
		char[] originalArr = original.toCharArray();
		int i=0;
		for(; i<originalArr.length; i++){
			if(Validator.isChar(originalArr[i])){
				result = result + charToNum(originalArr[i]);
			}else{
				break;
			}
		}
		
		Integer temp = Integer.parseInt(original.substring(i, i+2));
		result = result + (temp + 20);
		temp = Integer.parseInt(original.substring(i+2, i+4));
		result = result + (temp + 8);
		
		String tempS = original.substring(i+4);
		if(tempS.length() == 4){
			char[] arr = tempS.toCharArray();
			result = result + arr[1] + arr[0] + arr[3] + arr[2];
		}else if(tempS.length() == 5){
			char[] arr = tempS.toCharArray();
			result = result + arr[0] + arr[1] + arr[2];
		}else if(tempS.length() == 7){
			char[] arr = tempS.toCharArray();
			result = result + arr[6] + arr[2] + arr[3] + arr[0] + arr[1] + arr[4] + arr[5];
		}else if(tempS.length() == 8){
			char[] arr = tempS.toCharArray();
			result = result + arr[2] + arr[3] + arr[4] + arr[0] + arr[1] + arr[5] + arr[6] + arr[7];
		}else{
			result = result + tempS;
		}
		
		return result;
	}
	
	/**
	 * 标的编号加密
	 * 项目编号加密规则：
		1. 基础数据为项目编号
		2. 字母部分：
		转为数字，A开始，对应数字11开始；依次类推。B对应12，C对应13，...
		4. 数字部分：
		包含字母转换为数字的部分，每隔三位，将首位与第三位互换；不足三位，首位后移
	 * @param original
	 * @return
	 */
	 static String encryptBorrowNid(String original){
		String result = "";
		String tempS = "";
		if(original == null || StringUtils.isBlank(original)){
			return "";
		}
		
		char[] originalArr = original.toCharArray();
		int i=0;
		for(; i<originalArr.length; i++){
			if(Validator.isChar(originalArr[i])){
				result = result + charToNum(originalArr[i]);
			}else{
				tempS = tempS + original.substring(i);
				break;
			}
		}
		
		tempS = result + tempS;
		result = "";
		
		while(tempS.length() >=1){
			if(tempS.length()>=3){
				char[] arr = tempS.toCharArray();
				result = result + arr[2] + arr[1] + arr[0];
				tempS = tempS.substring(3);
			}else if(tempS.length() >=2){
				char[] arr = tempS.toCharArray();
				result = result + arr[1] + arr[0];
				tempS = tempS.substring(2);
			}else if(tempS.length() ==1){
				result = result + tempS;
				tempS = "";
			}
		}
		
		return result;
	}
	 
	public static void main(String[] args) {
		String result = encryptSendBorrow("HJD171100000376");
		System.out.println(result);
		
	}
	
	private static String charToNum(char orginal) {
		String result = "";
		switch (orginal) {
		case 'A':
			result = "11";
			break;
		case 'B':
			result = "12";
			break;
		case 'C':
			result = "13";
			break;
		case 'D':
			result = "14";
			break;
		case 'E':
			result = "15";
			break;
		case 'F':
			result = "16";
			break;
		case 'G':
			result = "17";
			break;
		case 'H':
			result = "18";
			break;
		case 'I':
			result = "19";
			break;
		case 'J':
			result = "20";
			break;
		case 'K':
			result = "21";
			break;
		case 'L':
			result = "22";
			break;
		case 'M':
			result = "23";
			break;
		case 'N':
			result = "24";
			break;
		case 'O':
			result = "25";
			break;
		case 'P':
			result = "26";
			break;
		case 'Q':
			result = "27";
			break;
		case 'R':
			result = "28";
			break;
		case 'S':
			result = "29";
			break;
		case 'T':
			result = "30";
			break;
		case 'U':
			result = "31";
			break;
		case 'V':
			result = "32";
			break;
		case 'W':
			result = "33";
			break;
		case 'X':
			result = "34";
			break;
		case 'Y':
			result = "35";
			break;
		case 'Z':
			result = "36";
			break;
		default:
			result = String.valueOf(orginal);
			break;
		}

		return result;
	}
	
    
}