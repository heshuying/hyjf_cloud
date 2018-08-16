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

public class AppVehiclePledgeCustomize implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = -7584267305755745655L;
	/* 车辆品牌 vehicleBrand */
	private String vehicleBrand;
	/* 型号 vehicleModel */
	private String vehicleModel;
	/* 评估价（元）evaluationPrice*/
	private String evaluationPrice;
	/* place */
	private String place;

	public AppVehiclePledgeCustomize() {
		super();
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getEvaluationPrice() {
		return evaluationPrice;
	}

	public void setEvaluationPrice(String evaluationPrice) {
		this.evaluationPrice = evaluationPrice;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}



}
