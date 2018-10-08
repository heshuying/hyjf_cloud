package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DebtConfig implements Serializable {
    private Integer id;

    private BigDecimal attornRate;

    private BigDecimal concessionRateUp;

    private BigDecimal concessionRateDown;

    private Integer toggle;

    private String closeDes;

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