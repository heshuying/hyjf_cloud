package com.hyjf.am.market.dao.model.customize.app;

import java.io.Serializable;

public class AppAdsCustomize implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -5941155860135801617L;
	private int id;
	private String url;
	
	private String bannerName;

	private String image;
	
	private String newUserShow;

	private String picUrl;

	private String picH5Url;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPicH5Url() {
		return picH5Url;
	}

	public void setPicH5Url(String picH5Url) {
		this.picH5Url = picH5Url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image == null ? null : image.trim();
	}

	public String getBannerName() {
		return bannerName;
	}

	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}

	public String getNewUserShow() {
		return newUserShow;
	}

	public void setNewUserShow(String newUserShow) {
		this.newUserShow = newUserShow;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}