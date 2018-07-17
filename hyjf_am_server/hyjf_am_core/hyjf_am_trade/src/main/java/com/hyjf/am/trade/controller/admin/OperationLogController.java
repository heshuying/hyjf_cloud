package com.hyjf.am.trade.controller.admin;

import com.hyjf.am.trade.service.admin.OperationLogService;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    public List<HjhAssetTypeVO> getHjhAssetType() {
        //查询版本配置列表条数
        return this.operationLogService.getHjhAssetType();
    }




}
