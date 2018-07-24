package com.hyjf.am.vo.datacollect;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tanyy
 * @version OperationReportVO, v0.1 2018/7/23 16:38
 */
public class OperationReportVO extends BaseVO implements Serializable {

    //运营报告id
    private String ids;
    private String releaseTimeStr;

    private String typeRealName;

    private int sortMonth;
    private int sortDay;

    private String id;

    private String cnName;

    private String enName;

    private String year;

    private Integer operationReportType;

    private BigDecimal allAmount;

    private BigDecimal allProfit;

    private BigDecimal registNum;

    private Integer successDealNum;

    private BigDecimal operationAmount;

    private BigDecimal operationProfit;

    private Integer isRelease;

    private Integer isDelete;

    private Integer releaseTime;

    private Integer updateTime;

    private Integer updateUserId;

    private Integer createTime;

    private Integer createUserId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName == null ? null : cnName.trim();
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName == null ? null : enName.trim();
    }

    public Integer getOperationReportType() {
        return operationReportType;
    }

    public void setOperationReportType(Integer operationReportType) {
        this.operationReportType = operationReportType;
    }

    public BigDecimal getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(BigDecimal allAmount) {
        this.allAmount = allAmount;
    }

    public BigDecimal getAllProfit() {
        return allProfit;
    }

    public void setAllProfit(BigDecimal allProfit) {
        this.allProfit = allProfit;
    }

    public BigDecimal getRegistNum() {
        return registNum;
    }

    public void setRegistNum(BigDecimal registNum) {
        this.registNum = registNum;
    }

    public Integer getSuccessDealNum() {
        return successDealNum;
    }

    public void setSuccessDealNum(Integer successDealNum) {
        this.successDealNum = successDealNum;
    }

    public BigDecimal getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(BigDecimal operationAmount) {
        this.operationAmount = operationAmount;
    }

    public BigDecimal getOperationProfit() {
        return operationProfit;
    }

    public void setOperationProfit(BigDecimal operationProfit) {
        this.operationProfit = operationProfit;
    }

    public Integer getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(Integer isRelease) {
        this.isRelease = isRelease;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Integer releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getReleaseTimeStr() {
        return releaseTimeStr;
    }

    public void setReleaseTimeStr(String releaseTimeStr) {
        this.releaseTimeStr = releaseTimeStr;
    }

    public String getTypeRealName() {
        return typeRealName;
    }

    public void setTypeRealName(String typeRealName) {
        this.typeRealName = typeRealName;
    }

    public int getSortMonth() {
        return sortMonth;
    }

    public void setSortMonth(int sortMonth) {
        this.sortMonth = sortMonth;
    }

    public int getSortDay() {
        return sortDay;
    }

    public void setSortDay(int sortDay) {
        this.sortDay = sortDay;
    }





    /**
     * 展示顺序
     */
    private String imageSort;

    /**
     * 资料名称
     */
    private String imageName;

    /**
     * 资料名称
     */
    private String imageRealName;

    /**
     * 图片路径
     */
    private String imagePath;

    /**
     * 图片路径
     */
    private String imageSrc;

    /**
     * 图片大小
     */
    private String imageSize;

    /**
     * 图片类型
     */
    private String imageType;

    /**
     * 错误消息
     */
    private String errorMessage;

    /**
     * 文件内容
     */
    private byte[] bytes;

    /**
     * bytes
     *
     * @return the bytes
     */

    public byte[] getBytes() {
        return bytes;
    }

    /**
     * @param bytes
     *            the bytes to set
     */

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * errorMessage
     *
     * @return the errorMessage
     */

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage
     *            the errorMessage to set
     */

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * imageType
     *
     * @return the imageType
     */

    public String getImageType() {
        return imageType;
    }

    /**
     * @param imageType
     *            the imageType to set
     */

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    /**
     * imageSize
     *
     * @return the imageSize
     */

    public String getImageSize() {
        return imageSize;
    }

    /**
     * @param imageSize
     *            the imageSize to set
     */

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    /**
     * imageSrc
     *
     * @return the imageSrc
     */

    public String getImageSrc() {
        return imageSrc;
    }

    /**
     * @param imageSrc
     *            the imageSrc to set
     */

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    /**
     * imageRealName
     *
     * @return the imageRealName
     */

    public String getImageRealName() {
        return imageRealName;
    }

    /**
     * @param imageRealName
     *            the imageRealName to set
     */

    public void setImageRealName(String imageRealName) {
        this.imageRealName = imageRealName;
    }

    /**
     * imageSort
     *
     * @return the imageSort
     */

    public String getImageSort() {
        return imageSort;
    }

    /**
     * @param imageSort
     *            the imageSort to set
     */

    public void setImageSort(String imageSort) {
        this.imageSort = imageSort;
    }

    /**
     * imageName
     *
     * @return the imageName
     */

    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName
     *            the imageName to set
     */

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * imagePath
     *
     * @return the imagePath
     */

    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath
     *            the imagePath to set
     */

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
