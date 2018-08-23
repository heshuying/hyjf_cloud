package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author xiasq
 * @version UserVO, v0.1 2018/1/21 22:39
 */
public class UserInfoVO extends BaseVO implements Serializable {
	private Integer id;

	private Integer userId;

	private Integer roleId;

	private Integer isStaff;

	private Integer departmentId;

	private Integer parentId;

	private Integer is51;

	private String nickname;

	private String birthday;

	private Integer sex;

	private String truename;

	private String idcard;

	private Integer truenameIsapprove;

	private Integer mobileIsapprove;

	private Integer emailIsapprove;

	private String education;

	private String province;

	private String city;

	private String area;

	private String address;

	private String intro;

	private String interest;

	private Integer updateTime;

	private Integer attribute;

	private Integer promoteway;

	private Integer isContact;

	private Integer vipId;

	private Integer vipValue;

	private Integer vipExpDate;

	private Integer vipPlatform;

	private Integer borrowerType;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getIsStaff() {
		return isStaff;
	}

	public void setIsStaff(Integer isStaff) {
		this.isStaff = isStaff;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getIs51() {
		return is51;
	}

	public void setIs51(Integer is51) {
		this.is51 = is51;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday == null ? null : birthday.trim();
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename == null ? null : truename.trim();
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard == null ? null : idcard.trim();
	}

	public Integer getTruenameIsapprove() {
		return truenameIsapprove;
	}

	public void setTruenameIsapprove(Integer truenameIsapprove) {
		this.truenameIsapprove = truenameIsapprove;
	}

	public Integer getMobileIsapprove() {
		return mobileIsapprove;
	}

	public void setMobileIsapprove(Integer mobileIsapprove) {
		this.mobileIsapprove = mobileIsapprove;
	}

	public Integer getEmailIsapprove() {
		return emailIsapprove;
	}

	public void setEmailIsapprove(Integer emailIsapprove) {
		this.emailIsapprove = emailIsapprove;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education == null ? null : education.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area == null ? null : area.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro == null ? null : intro.trim();
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest == null ? null : interest.trim();
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getAttribute() {
		return attribute;
	}

	public void setAttribute(Integer attribute) {
		this.attribute = attribute;
	}

	public Integer getPromoteway() {
		return promoteway;
	}

	public void setPromoteway(Integer promoteway) {
		this.promoteway = promoteway;
	}

	public Integer getIsContact() {
		return isContact;
	}

	public void setIsContact(Integer isContact) {
		this.isContact = isContact;
	}

	public Integer getVipId() {
		return vipId;
	}

	public void setVipId(Integer vipId) {
		this.vipId = vipId;
	}

	public Integer getVipValue() {
		return vipValue;
	}

	public void setVipValue(Integer vipValue) {
		this.vipValue = vipValue;
	}

	public Integer getVipExpDate() {
		return vipExpDate;
	}

	public void setVipExpDate(Integer vipExpDate) {
		this.vipExpDate = vipExpDate;
	}

	public Integer getVipPlatform() {
		return vipPlatform;
	}

	public void setVipPlatform(Integer vipPlatform) {
		this.vipPlatform = vipPlatform;
	}

	public Integer getBorrowerType() {
		return borrowerType;
	}

	public void setBorrowerType(Integer borrowerType) {
		this.borrowerType = borrowerType;
	}

}
