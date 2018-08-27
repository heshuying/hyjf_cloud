package com.hyjf.pay.lib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaySystemConfig {

    @Value("${hyjf.pay.bank.url}")
    public String bankUrl;

    @Value("${hyjf.pay.url}")
    public String payUrl;

    @Value("${hyjf.chinapnr.return.url}")
    public String chinapnrReturnUril;

    @Value("${hyjf.chinapnr.bindreturn.url}")
    public String chinapnrBindreturnUrl;

    @Value("${hyjf.chinapnr.callback.url}")
    public String chinapnrCallBack;

    /** 商户客户号 **/
    @Value("${hyjf.chinapnr.merid}")
    public String chinapnrMerId;

    /** 商户公钥文件地址 **/
    @Value("${hyjf.chinapnr.mer.pubkey.path}")
    public String chinapnrPubkey;

    /** 商户私钥文件地址 **/
    @Value("${hyjf.chinapnr.mer.prikey.path}")
    public String chinapnrPrikey;

    /** 版本号 */
    @Value("${hyjf.chinapnr.version}")
    private String version;

    /** 商户客户号 */
    @Value("${hyjf.chinapnr.mercustid}")
    private String merCustId;

    /** 商户子账户号 */
    @Value("${hyjf.chinapnr.meracctId}")
    private String merAcctId;

    /** 商户后台回调地址 */
    @Value("${hyjf.chinapnr.callback.url}")
    private String bgRetUrl;

    /** 汇付天下地址 */
    @Value("${hyjf.chinapnr.url}")
    private String chinapnrUrl;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMerCustId() {
        return merCustId;
    }

    public void setMerCustId(String merCustId) {
        this.merCustId = merCustId;
    }

    public String getMerAcctId() {
        return merAcctId;
    }

    public void setMerAcctId(String merAcctId) {
        this.merAcctId = merAcctId;
    }

    public String getBgRetUrl() {
        return bgRetUrl;
    }

    public void setBgRetUrl(String bgRetUrl) {
        this.bgRetUrl = bgRetUrl;
    }

    public String getChinapnrUrl() {
        return chinapnrUrl;
    }

    public void setChinapnrUrl(String chinapnrUrl) {
        this.chinapnrUrl = chinapnrUrl;
    }

    public String getChinapnrMerId() {
        return chinapnrMerId;
    }

    public void setChinapnrMerId(String chinapnrMerId) {
        this.chinapnrMerId = chinapnrMerId;
    }

    public String getChinapnrPubkey() {
        return chinapnrPubkey;
    }

    public void setChinapnrPubkey(String chinapnrPubkey) {
        this.chinapnrPubkey = chinapnrPubkey;
    }

    public String getChinapnrPrikey() {
        return chinapnrPrikey;
    }

    public void setChinapnrPrikey(String chinapnrPrikey) {
        this.chinapnrPrikey = chinapnrPrikey;
    }

    public String getChinapnrCallBack() {
        return chinapnrCallBack;
    }

    public void setChinapnrCallBack(String chinapnrCallBack) {
        this.chinapnrCallBack = chinapnrCallBack;
    }

    public String getChinapnrBindreturnUrl() {
        return chinapnrBindreturnUrl;
    }

    public void setChinapnrBindreturnUrl(String chinapnrBindreturnUrl) {
        this.chinapnrBindreturnUrl = chinapnrBindreturnUrl;
    }

    public String getChinapnrReturnUril() {
        return chinapnrReturnUril;
    }

    public void setChinapnrReturnUril(String chinapnrReturnUril) {
        this.chinapnrReturnUril = chinapnrReturnUril;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }

}
