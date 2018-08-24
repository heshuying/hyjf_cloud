package com.hyjf.am.trade.controller.admin.trade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.resquest.admin.AdminOperationLogRequest;
import com.hyjf.am.trade.service.admin.OperationLogService;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;

/**
 * @author by xiehuili on 2018/7/17.
 */

@RequestMapping("/am-trade/config/operationlog")
public class TradeOperationLogController {
    @Autowired
    private OperationLogService operationLogService;


    /**
     * 产品类型
     * @return
     */
    @RequestMapping("/getHjhAssetType")
    public List<HjhAssetTypeVO> getHjhAssetType() {
        //查询版本配置列表条数
        return this.operationLogService.getHjhAssetType();
    }
    /**
     * 查询 资产来源 instCode 和 assetType的值AdminOperationLogResponse
     * @return
     */
    @RequestMapping("/selectInstAndAssertType")
    public AdminOperationLogResponse selectInstAndAssertType(@RequestBody AdminOperationLogRequest adminRequest) {
        //查询 资产来源
        List<FeerateModifyLogVO> feerateModifyLogVOList = this.operationLogService.selectInstAndAssertType(adminRequest);
        AdminOperationLogResponse response = new AdminOperationLogResponse();
        response.setResultList(feerateModifyLogVOList);
        return response;
    }


}
