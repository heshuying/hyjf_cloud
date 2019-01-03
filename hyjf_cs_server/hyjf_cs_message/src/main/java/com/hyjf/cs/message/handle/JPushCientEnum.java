package com.hyjf.cs.message.handle;

import org.apache.commons.lang3.StringUtils;

import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.message.jpush.*;

import cn.jpush.api.JPushClient;

/**
 * @author xiasq
 * @version JPushCientEnum, v0.1 2019/1/2 14:17
 */
public enum JPushCientEnum {
    JPUSH_39(CustomConstants.MSG_PUSH_PACKAGE_CODE_39, JPushPro.getClientInstance(), "msgJpushProId"),
    JPUSH_YXB(CustomConstants.MSG_PUSH_PACKAGE_CODE_YXB, JPushYXB.getClientInstance(), "msgJpushYxbId"),
    JPUSH_ZNB(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZNB, JPushZNB.getClientInstance(), "msgJpushZnbId"),
    JPUSH_ZYB(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZYB, JPushZYB.getClientInstance(), "msgJpushZybId"),
    JPUSH_ZZB(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZZB, JPushZZB.getClientInstance(), "msgJpushZzbId"),
    JPUSH_TEST(CustomConstants.MSG_PUSH_PACKAGE_CODE_TEST, JPushTEST.getClientInstance(), "msgJpushTestId"),
    JPUSH(null, JPush.getClientInstance(), "msgJpushId")
    ;

    private String packageCode;
    private JPushClient pushClient;
    private String fieldName;

    JPushCientEnum(String packageCode, JPushClient pushClient, String fieldName){
        this.packageCode = packageCode;
        this.pushClient = pushClient;
        this.fieldName = fieldName;
    }

	public static JPushClient getJPushClient(String packageCode) {
		if (StringUtils.isNotBlank(packageCode)) {
			JPushCientEnum[] list = JPushCientEnum.values();
			for (JPushCientEnum jPushCientEnum : list) {
				if (packageCode.equals(jPushCientEnum.getPushClient())) {
					return jPushCientEnum.getPushClient();
				}
			}
		}
		return JPUSH_TEST.getPushClient();
	}

    /**
     *
     * @param packageCode
     * @return
     */
	public static String getFieldName(String packageCode) {
		if (StringUtils.isNotBlank(packageCode)) {
			JPushCientEnum[] list = JPushCientEnum.values();
			for (JPushCientEnum jPushCientEnum : list) {
				if (packageCode.equals(jPushCientEnum.getPushClient())) {
					return jPushCientEnum.getFieldName();
				}
			}
		}
		return JPUSH_TEST.getFieldName();
	}


    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public JPushClient getPushClient() {
        return pushClient;
    }

    public void setPushClient(JPushClient pushClient) {
        this.pushClient = pushClient;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
