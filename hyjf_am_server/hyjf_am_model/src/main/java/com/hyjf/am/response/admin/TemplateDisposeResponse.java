package com.hyjf.am.response.admin;

import java.util.List;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.vo.admin.TemplateDisposeVO;
import com.hyjf.am.vo.user.TemplateConfigVO;
import com.hyjf.am.vo.user.UtmPlatVO;

public class TemplateDisposeResponse extends AdminResponse<TemplateDisposeVO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private List<UtmPlatVO> userRoles;
	
	private List<TemplateConfigVO>	templateConfigList;

	public List<UtmPlatVO> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UtmPlatVO> userRoles) {
		this.userRoles = userRoles;
	}

	public List<TemplateConfigVO> getTemplateConfigList() {
		return templateConfigList;
	}

	public void setTemplateConfigList(List<TemplateConfigVO> templateConfigList) {
		this.templateConfigList = templateConfigList;
	}

	
}
