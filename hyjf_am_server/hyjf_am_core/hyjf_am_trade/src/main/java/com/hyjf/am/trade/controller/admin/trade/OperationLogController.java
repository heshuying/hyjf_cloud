package com.hyjf.am.trade.controller.admin.trade;

import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.resquest.admin.AdminOperationLogRequest;
import com.hyjf.am.trade.service.admin.OperationLogService;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/17.
 */
@RestController
@RequestMapping("/am-trade/config/operationlog")
public class OperationLogController {
    @Autowired
    private OperationLogService operationLogService;


    /**
     * 产品类型
     * @return
     */
    @RequestMapping("/getHjhAssetType")
    public AdminOperationLogResponse getHjhAssetType() {
        AdminOperationLogResponse response = new AdminOperationLogResponse();
        //查询版本配置列表条数
        List<HjhAssetTypeVO> list = this.operationLogService.getHjhAssetType();
        if(!CollectionUtils.isEmpty(list)){
            response.setHjhAssetTypes(list);
            return response;
        }
        return null;
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
