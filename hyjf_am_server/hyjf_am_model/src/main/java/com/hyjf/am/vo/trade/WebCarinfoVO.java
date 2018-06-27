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
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

public class WebCarinfoVO extends BaseVO {

	/** 序列化id */
	private static final long serialVersionUID = -7584267305755745655L;
	/* 车辆品牌 vehicleBrand */
	private String vehicleBrand;
	/* 型号 vehicleModel */
	private String vehicleModel;
	/* 评估价（元）evaluationPrice*/
	private String evaluationPrice;
	/* 所属地 */
	private String place;
	/* 车牌号 */
	private String vehicleNumber;
	/* 购买价 */
	private String price;
	/* 车辆登记地  */
	private String registration;
	/* 车架号 */
	private String vin;

	public WebCarinfoVO() {
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

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}


}
