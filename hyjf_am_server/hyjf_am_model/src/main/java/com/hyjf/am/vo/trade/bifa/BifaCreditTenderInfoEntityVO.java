/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.bifa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author jun
 * @version BifaCreditTenderInfoEntityVO, v0.1 2019/1/17 10:12
 */
public class BifaCreditTenderInfoEntityVO extends BaseHgDataReportEntityVO{

    private static final Logger logger = LoggerFactory.getLogger(BifaCreditTenderInfoEntityVO.class);

    private static final long serialVersionUID = 1L;
    /**
     * 产品登记类别
     */
    private String product_reg_type;
    /**
     * 产品名称
     */
    private String product_name;
    /**
     * 产品分类
     */
    private String product_mark;
    /**
     * 转让人性别
     */
    private String transfer_sex;
    /**
     * 产品期限
     */
    private String hold_time;
    /**
     * 剩余时长
     */
    private String overplus_time;
    /**
     * 转让金额
     */
    private String amt;
    /**
     * 转让月利率
     */
    private String transfer_rate;
    /**
     * 转让手续费
     */
    private String transfer_fee;
    /**
     * 备注
     */
    private String remark;
    /**
     * 原产品链接
     */
    private String source_product_url;
    /**
     * 姓名身份证的sha256
     */
    private String transfer_name_idcard_digest;

    /**
     * 1散标转让标示  2智投转让标示
     */
    private Integer flag;

    public String getProduct_reg_type() {
        return product_reg_type;
    }

    public void setProduct_reg_type(String product_reg_type) {
        this.product_reg_type = product_reg_type;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_mark() {
        return product_mark;
    }

    public void setProduct_mark(String product_mark) {
        this.product_mark = product_mark;
    }

    public String getTransfer_sex() {
        return transfer_sex;
    }

    public void setTransfer_sex(String transfer_sex) {
        this.transfer_sex = transfer_sex;
    }

    public String getHold_time() {
        return hold_time;
    }

    public void setHold_time(String hold_time) {
        this.hold_time = hold_time;
    }

    public String getOverplus_time() {
        return overplus_time;
    }

    public void setOverplus_time(String overplus_time) {
        this.overplus_time = overplus_time;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getTransfer_rate() {
        return transfer_rate;
    }

    public void setTransfer_rate(String transfer_rate) {
        this.transfer_rate = transfer_rate;
    }

    public String getTransfer_fee() {
        return transfer_fee;
    }

    public void setTransfer_fee(String transfer_fee) {
        this.transfer_fee = transfer_fee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSource_product_url() {
        return source_product_url;
    }

    public void setSource_product_url(String source_product_url) {
        this.source_product_url = source_product_url;
    }

    public String getTransfer_name_idcard_digest() {
        return transfer_name_idcard_digest;
    }

    public void setTransfer_name_idcard_digest(String transfer_name_idcard_digest) {
        this.transfer_name_idcard_digest = transfer_name_idcard_digest;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /**
     * 深度克隆對象
     * @return
     */
    public BifaCreditTenderInfoEntityVO cloneObj() {
        BifaCreditTenderInfoEntityVO outer = null;
        try { // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            // 将流序列化成对象
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            outer = (BifaCreditTenderInfoEntityVO) ois.readObject();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        return outer;
    }
}
