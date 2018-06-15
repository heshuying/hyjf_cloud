package com.hyjf.cs.user.result;

import java.util.HashMap;
import java.util.Map;

import com.hyjf.am.vo.user.UsersContactVO;

/**
 * 紧急联系人设置返回bean
 * @author hesy
 *
 */
public class ContractSetResultBean extends ApiResult<UsersContactVO>{
	private String checkRelationName = "";
	private String checkRelationId = "";
	
	Map<String, String> relationMap = new HashMap<String, String>();
	
	public String getCheckRelationName() {
		return checkRelationName;
	}
	public void setCheckRelationName(String checkRelationName) {
		this.checkRelationName = checkRelationName;
	}
	public String getCheckRelationId() {
		return checkRelationId;
	}
	public void setCheckRelationId(String checkRelationId) {
		this.checkRelationId = checkRelationId;
	}
	public Map<String, String> getRelationMap() {
		return relationMap;
	}
	public void setRelationMap(Map<String, String> relationMap) {
		this.relationMap = relationMap;
	}
	
	
	
}
