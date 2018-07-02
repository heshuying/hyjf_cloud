package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.user.MspApplyVO;

public class MspApplytRequest extends MspApplyVO {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 前台时间接收
     */
    private String ids;
    
	private String startCreate;

    private String endCreate;
	/**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;
    private String adminId;

    public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }
    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }
    public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getStartCreate() {
		return startCreate;
	}
	public void setStartCreate(String startCreate) {
		this.startCreate = startCreate;
	}
	public String getEndCreate() {
		return endCreate;
	}
	public void setEndCreate(String endCreate) {
		this.endCreate = endCreate;
	}
}
