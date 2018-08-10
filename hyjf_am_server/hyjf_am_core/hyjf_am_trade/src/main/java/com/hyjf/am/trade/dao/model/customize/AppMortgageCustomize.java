/**
 * Description:项目详情查询所用vo
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */
package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

public class AppMortgageCustomize implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = 1373170486997860939L;
	/* 房产类型 propertyType */
	private String propertyType;
	/* 建筑面积 grossArea */
	private String grossArea;
	/* 抵押价值 guarantyValue*/
	private String guarantyValue;
	/** 资产数量*/
	private String housesCnt;
	/** 资产所属*/
	private String housesBelong;

	public AppMortgageCustomize() {
		super();
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getGrossArea() {
		return grossArea;
	}

	public void setGrossArea(String grossArea) {
		this.grossArea = grossArea;
	}

	public String getGuarantyValue() {
		return guarantyValue;
	}

	public void setGuarantyValue(String guarantyValue) {
		this.guarantyValue = guarantyValue;
	}

    public String getHousesCnt() {
        return housesCnt;
    }

    public void setHousesCnt(String housesCnt) {
        this.housesCnt = housesCnt;
    }

    public String getHousesBelong() {
        return housesBelong;
    }

    public void setHousesBelong(String housesBelong) {
        this.housesBelong = housesBelong;
    }


}
