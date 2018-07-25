package com.hyjf.am.vo.trade.borrow;


import com.hyjf.am.vo.BaseVO;

/**
 * @package com.hyjf.admin.maintenance.AlllBorrowCustomize;
 * @author GOGTZ-Z
 * @date 2015/07/09 17:00
 * @version V1.0  
 */
public class BorrowCommonCarVO extends BaseVO {

	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 型号
	 */
	private String model;
	/**
	 * 车系
	 */
	private String carseries;
	/**
	 * 颜色
	 */
	private String color;
	/**
	 * 出厂年份
	 */
	private String year;
	/**
	 * 产地
	 */
	private String place;
	/**
	 * 排量
	 */
	private String volume;
	/**
	 * 购买日期
	 */
	private String buytime;
	/**
	 * 有无保险
	 */
	private String isSafe;
	/**
	 * 购买价
	 */
	private String price;
	/**
	 * 评估价
	 */
	private String toprice;
	/**
	 * 车牌号
	 */
	private String number;
	/**
	 * 车辆登记地
	 */
	private String registration;
	/**
	 * 车架号
	 */
	private String vin;
	
	/**
	 * 车辆信息总行数
	 */
	private int carLength = 0;

	/**
	 * brand
	 * 
	 * @return the brand
	 */

	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */

	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * model
	 * 
	 * @return the model
	 */

	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */

	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * carseries
	 * 
	 * @return the carseries
	 */

	public String getCarseries() {
		return carseries;
	}

	/**
	 * @param carseries
	 *            the carseries to set
	 */

	public void setCarseries(String carseries) {
		this.carseries = carseries;
	}

	/**
	 * color
	 * 
	 * @return the color
	 */

	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */

	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * year
	 * 
	 * @return the year
	 */

	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */

	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * place
	 * 
	 * @return the place
	 */

	public String getPlace() {
		return place;
	}

	/**
	 * @param place
	 *            the place to set
	 */

	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * volume
	 * 
	 * @return the volume
	 */

	public String getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 *            the volume to set
	 */

	public void setVolume(String volume) {
		this.volume = volume;
	}

	/**
	 * buytime
	 * 
	 * @return the buytime
	 */

	public String getBuytime() {
		return buytime;
	}

	/**
	 * @param buytime
	 *            the buytime to set
	 */

	public void setBuytime(String buytime) {
		this.buytime = buytime;
	}

	/**
	 * isSafe
	 * 
	 * @return the isSafe
	 */

	public String getIsSafe() {
		return isSafe;
	}

	/**
	 * @param isSafe
	 *            the isSafe to set
	 */

	public void setIsSafe(String isSafe) {
		this.isSafe = isSafe;
	}

	/**
	 * price
	 * 
	 * @return the price
	 */

	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */

	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * toprice
	 * 
	 * @return the toprice
	 */

	public String getToprice() {
		return toprice;
	}

	/**
	 * @param toprice
	 *            the toprice to set
	 */

	public void setToprice(String toprice) {
		this.toprice = toprice;
	}

	/**
	 * carLength
	 * 
	 * @return the carLength
	 */

	public int getCarLength() {
		return carLength;
	}

	/**
	 * @param carLength
	 *            the carLength to set
	 */

	public void setCarLength(int carLength) {
		this.carLength = carLength;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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
