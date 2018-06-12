package com.hyjf.callcenter.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hyjf.callcenter.result.BaseResultBean;

/**
 * 呼叫中心用接口返回多条数据的通用类
 * @author libin
 * @version hyjf 1.0
 * @since hyjf 1.0 2018年06月6日 
 */
public class ResultListBean extends BaseResultBean implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 返回多条数据list
     */
    private List<Object> dataList;
    
    //实例化list
    {
    	dataList = new ArrayList<Object>();
    }

	public List<Object> getDataList() {
		return dataList;
	}

	public void setDataList(List<Object> dataList) {
		this.dataList = dataList;
	}

}
