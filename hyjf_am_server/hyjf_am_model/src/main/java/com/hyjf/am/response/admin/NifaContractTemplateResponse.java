package com.hyjf.am.response.admin;


import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.NifaContractTemplateVO;

/**
 * @author nxl
 * 合同模版约定条款表Response
 */
public class NifaContractTemplateResponse extends Response<NifaContractTemplateVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
