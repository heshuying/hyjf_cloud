package com.hyjf.am.resquest.config;

import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.am.vo.config.SubmissionsCustomizeVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lisheng
 * @version SubmissionsRequest, v0.1 2018/7/13 16:23
 */

public class SubmissionsRequest extends SubmissionsCustomizeVO {

    /**
     * serialVersionUID
     */

    private static final long serialVersionUID = 387630498860089653L;

    /**
     * 画面一览列表
     */
    private List<SubmissionsCustomizeVO> submissionsList = new ArrayList<SubmissionsCustomizeVO>();

    /************************* 以下字段画面检索用 ************************/


    @ApiModelProperty(value = "被选中的意见反馈")
    private String selectedSubId;
    @ApiModelProperty(value = "开始时间")
    private String addTimeStart;
    @ApiModelProperty(value = "结束时间")
    private String addTimeEnd;
    @ApiModelProperty(value = "处理状态")
    private List<ParamName> subStateList = new ArrayList<ParamName>();
    @ApiModelProperty(value = "系统类别")
    private List<ParamName> sysTypeList = new ArrayList<ParamName>();

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;
    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    public List<SubmissionsCustomizeVO> getSubmissionsList() {
        return submissionsList;
    }

    public void setSubmissionsList(List<SubmissionsCustomizeVO> submissionsList) {
        this.submissionsList = submissionsList;
    }

    public String getAddTimeStart() {
        return addTimeStart;
    }

    public void setAddTimeStart(String addTimeStart) {
        this.addTimeStart = addTimeStart;
    }

    public String getAddTimeEnd() {
        return addTimeEnd;
    }

    public void setAddTimeEnd(String addTimeEnd) {
        this.addTimeEnd = addTimeEnd;
    }

    public List<ParamName> getSubStateList() {
        return subStateList;
    }

    public void setSubStateList(List<ParamName> subStateList) {
        this.subStateList = subStateList;
    }

    public List<ParamName> getSysTypeList() {
        return sysTypeList;
    }

    public void setSysTypeList(List<ParamName> sysTypeList) {
        this.sysTypeList = sysTypeList;
    }

    public int getPaginatorPage() {
        if (this.paginatorPage == 0) {
            this.paginatorPage = 1;
        }
        return paginatorPage;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public String getSelectedSubId() {
        return selectedSubId;
    }

    public void setSelectedSubId(String selectedSubId) {
        this.selectedSubId = selectedSubId;
    }

}
