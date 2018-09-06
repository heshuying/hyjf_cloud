package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.admin.ProtocolTemplateCommonVO;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/8  15:49
 */
public class AdminProtocolRequest  extends BasePage implements Serializable {

    private static final long serialVersionUID = 1L;

    //协议模板
    @ApiModelProperty(value = "协议模板VO")
    private ProtocolTemplateVO protocolTemplateVO;

    @ApiModelProperty(value = "修改查询主键id")
    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    private List<ProtocolTemplateCommonVO> recordList;

    private String   pageStatus;

    @ApiModelProperty(value = "校验使用-协议模板名称")
    private String protocolName ;//协议模板名称
    @ApiModelProperty(value = "校验使用-协议类别")
    private String protocolType ;//协议类别
    @ApiModelProperty(value = "校验使用-版本号")
    private String versionNumber ;//版本号
    @ApiModelProperty(value = "校验使用-前台展示名称")
    private String displayName ;//前台展示名称
    @ApiModelProperty(value = "校验使用-协议上传路径")
    private String protocolUrl ;//协议上传路径
    @ApiModelProperty(value = "校验使用-原前台展示名称")
    private String oldDisplayName ;//原前台展示名称
    @ApiModelProperty(value = "校验使用-0=添加校验,1=修改校验")
    private String flag ;

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    public int limitStart = -1;

    public int limitEnd = -1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private com.hyjf.common.paginator.Paginator paginator;

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public List<ProtocolTemplateCommonVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<ProtocolTemplateCommonVO> recordList) {
        this.recordList = recordList;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public com.hyjf.common.paginator.Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(com.hyjf.common.paginator.Paginator paginator) {
        this.paginator = paginator;
    }


    public String getPageStatus() {
        return pageStatus;
    }

    public void setPageStatus(String pageStatus) {
        this.pageStatus = pageStatus;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public ProtocolTemplateVO getProtocolTemplateVO() {
        return protocolTemplateVO;
    }

    public void setProtocolTemplateVO(ProtocolTemplateVO protocolTemplateVO) {
        this.protocolTemplateVO = protocolTemplateVO;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProtocolUrl() {
        return protocolUrl;
    }

    public void setProtocolUrl(String protocolUrl) {
        this.protocolUrl = protocolUrl;
    }

    public String getOldDisplayName() {
        return oldDisplayName;
    }

    public void setOldDisplayName(String oldDisplayName) {
        this.oldDisplayName = oldDisplayName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
