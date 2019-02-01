package com.hyjf.am.response.admin;


import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.NifaReportLogVO;

/**
 * @author nxl
 * 互金协会报送日志表Response
 */
public class NifaReportLogResponse extends Response<NifaReportLogVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
