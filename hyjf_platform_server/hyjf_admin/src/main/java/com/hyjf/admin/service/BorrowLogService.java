package com.hyjf.admin.service;

import com.hyjf.admin.beans.BorrowLogBean;
import com.hyjf.am.resquest.admin.BorrowLogRequset;
import com.hyjf.am.vo.admin.BorrowLogCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowLogService, v0.1 2018/7/11 10:20
 */
public interface BorrowLogService {
    /**
     * @Description 
     * @Author pangchengchao
     * @Version v0.1
     * @Date 查询借款列表操作日志 
     */
    BorrowLogBean selectBorrowLogList(BorrowLogRequset request);
    /**
     * @Description 
     * @Author pangchengchao
     * @Version v0.1
     * @Date 查询借款操作日志列表导出
     */
    List<BorrowLogCustomizeVO> exportBorrowLogList(BorrowLogRequset copyForm);
}
