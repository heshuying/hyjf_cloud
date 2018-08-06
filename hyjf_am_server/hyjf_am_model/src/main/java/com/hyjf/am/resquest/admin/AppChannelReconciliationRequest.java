package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.admin.promotion.channel.AppChannelReconciliationCustomizeVO;
import com.hyjf.am.vo.user.UtmPlatVO;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/24 16:40
 * @Description: AppChannelReconciliationRequest
 */
public class AppChannelReconciliationRequest extends AppChannelReconciliationCustomizeVO implements Serializable {
    /**
     * serialVersionUID
     */

    private static final long serialVersionUID = 387630498860089653L;
    /**
     * 用户名查询
     */
    private String userNameSrch;
    /**
     * 订单号查询
     */
    private String orderCodeSrch;
    /**
     * 标的编号查询
     */
    private String borrowNidSrch;
    /**
     * app渠道查询
     */
    private String utmNameSrch;

    /**
     * app渠道列表
     */
    private List<UtmPlatVO> utmtTypeList;

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getOrderCodeSrch() {
        return orderCodeSrch;
    }

    public void setOrderCodeSrch(String orderCodeSrch) {
        this.orderCodeSrch = orderCodeSrch;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getUtmNameSrch() {
        return utmNameSrch;
    }

    public void setUtmNameSrch(String utmNameSrch) {
        this.utmNameSrch = utmNameSrch;
    }

    public List<UtmPlatVO> getUtmtTypeList() {
        return utmtTypeList;
    }

    public void setUtmtTypeList(List<UtmPlatVO> utmtTypeList) {
        this.utmtTypeList = utmtTypeList;
    }
}
