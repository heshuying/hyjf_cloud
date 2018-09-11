package com.hyjf.am.resquest.config;
import java.util.List;
import com.hyjf.am.vo.config.AdminMenuVO;
import com.hyjf.am.vo.config.AdminSystemVO;

/**
 * @author dongzeshan
 * @version AccountListResponse, v0.1 2018/6/20 10:52
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
public class AdminMenuRequest extends AdminMenuVO {

	 /**
     * serialVersionUID
     */

    private static final long serialVersionUID = 387630498860089653L;

    private List<String> ids;

    private String selectedNode;

    private String[] permissionsUuid;

    private List<AdminSystemVO> recordList;
    private int adminId;
    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<AdminSystemVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<AdminSystemVO> recordList) {
        this.recordList = recordList;
    }

    public String getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(String selectedNode) {
        this.selectedNode = selectedNode;
    }

    public String[] getPermissionsUuid() {
        return permissionsUuid;
    }

    public void setPermissionsUuid(String[] permissionsUuid) {
        this.permissionsUuid = permissionsUuid;
    }

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	
	
}
