package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DebtConfig implements Serializable {
    /**
     * 主键ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 服务费率
     *
     * @mbggenerated
     */
    private BigDecimal attornRate;

    /**
     * 折让率上限
     *
     * @mbggenerated
     */
    private BigDecimal concessionRateUp;

    /**
     * 折让率下限
     *
     * @mbggenerated
     */
    private BigDecimal concessionRateDown;

    /**
     * 散标债转开关
     *
     * @mbggenerated
     */
    private Integer toggle;

    /**
     * 关闭提示
     *
     * @mbggenerated
     */
    private String closeDes;

    /**
     * 类型1:债转配置
     *
     * @mbggenerated
     */
    private Integer configType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAttornRate() {
        return attornRate;
    }

    public void setAttornRate(BigDecimal attornRate) {
        this.attornRate = attornRate;
    }

    public BigDecimal getConcessionRateUp() {
        return concessionRateUp;
    }

    public void setConcessionRateUp(BigDecimal concessionRateUp) {
        this.concessionRateUp = concessionRateUp;
    }

    public BigDecimal getConcessionRateDown() {
        return concessionRateDown;
    }

    public void setConcessionRateDown(BigDecimal concessionRateDown) {
        this.concessionRateDown = concessionRateDown;
    }

    public Integer getToggle() {
        return toggle;
    }

    public void setToggle(Integer toggle) {
        this.toggle = toggle;
    }

    public String getCloseDes() {
        return closeDes;
    }

    public void setCloseDes(String closeDes) {
        this.closeDes = closeDes == null ? null : closeDes.trim();
    }

    public Integer getConfigType() {
        return configType;
    }

    public void setConfigType(Integer configType) {
        this.configType = configType;
    }
}