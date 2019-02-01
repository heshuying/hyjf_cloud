package com.hyjf.am.response.admin;


import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.NifaFieldDefinitionVO;

/**
 * @author nxl
 * 互金字段定义表Response
 */
public class NifaFieldDefinitionResponse extends Response<NifaFieldDefinitionVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
