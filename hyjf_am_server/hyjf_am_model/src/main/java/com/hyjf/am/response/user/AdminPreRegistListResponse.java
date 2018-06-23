/**
 * Description:用户预注册注册记录
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 朱晓东
 * @version: 1.0
 * Created at: 2016年06月23日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.AdminPreRegistListVO;

/**
 * @author dongzeshan
 */

public class AdminPreRegistListResponse extends Response<AdminPreRegistListVO>{

	private int recordTotal;

	public int getRecordTotal() {
		return recordTotal;
	}

	public void setRecordTotal(int recordTotal) {
		this.recordTotal = recordTotal;
	}
    

}
