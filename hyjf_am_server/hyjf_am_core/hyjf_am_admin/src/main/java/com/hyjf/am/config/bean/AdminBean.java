package com.hyjf.am.config.bean;

import java.io.Serializable;
import java.util.List;

import com.hyjf.am.config.dao.model.auto.AdminRole;
import com.hyjf.am.config.dao.model.customize.AdminCustomize;
import com.hyjf.am.trade.dao.model.auto.ROaDepartment;
import com.hyjf.common.paginator.Paginator;


/**
 * @package com.hyjf.admin.maintenance.AdminPermissions;
 * @author GOGTZ-T
 * @date 2015/07/09 17:00
 * @version V1.0  
 */
public class AdminBean extends AdminCustomize implements Serializable {

    /**
     * serialVersionUID
     */

    private static final long serialVersionUID = 387630498860089653L;

    private String ids;
    
    private List<AdminCustomize> recordList;

    private List<ROaDepartment> departmentList;

    private List<AdminRole> adminRoleList;

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
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

    public List<AdminCustomize> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<AdminCustomize> recordList) {
        this.recordList = recordList;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<ROaDepartment> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<ROaDepartment> departmentList) {
        this.departmentList = departmentList;
    }

    public List<AdminRole> getAdminRoleList() {
        return adminRoleList;
    }

    public void setAdminRoleList(List<AdminRole> adminRoleList) {
        this.adminRoleList = adminRoleList;
    }
}
