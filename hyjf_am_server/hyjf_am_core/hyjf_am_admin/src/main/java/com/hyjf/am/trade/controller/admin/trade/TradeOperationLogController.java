package com.hyjf.am.trade.controller.admin.trade;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.service.OperationLogService;
import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.resquest.admin.AdminOperationLogRequest;
import com.hyjf.am.trade.service.admin.TradeOperationLogService;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.common.paginator.Paginator;
import org.apache.commons.collections.CollectionUtils;
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
@RequestMapping("/am-admin/config/operationlog")
public class TradeOperationLogController  extends BaseConfigController {
    @Autowired
    private OperationLogService operationLogService;

   @Autowired
   private TradeOperationLogService tradeOperationLogService;


    /**
     * 产品类型
     * @return
     */
    @RequestMapping("/getHjhAssetType")
    public AdminOperationLogResponse getHjhAssetType() {
        AdminOperationLogResponse response = new AdminOperationLogResponse();
        //查询版本配置列表条数
        List<HjhAssetTypeVO> list = this.tradeOperationLogService.getHjhAssetType();
        if(!CollectionUtils.isEmpty(list)){
            response.setHjhAssetTypes(list);
        }
        return response;
    }
    /**
     * 查询 资产来源 instCode 和 assetType的值AdminOperationLogResponse
     * @return
     */
    @RequestMapping("/selectInstAndAssertType")
    public AdminOperationLogResponse selectInstAndAssertType(@RequestBody AdminOperationLogRequest adminRequest) {
        //查询 资产来源
        List<FeerateModifyLogVO> feerateModifyLogVOList = this.tradeOperationLogService.selectInstAndAssertType(adminRequest);
        AdminOperationLogResponse response = new AdminOperationLogResponse();
        response.setResultList(feerateModifyLogVOList);
        return response;
    }
    /**
     * 分页查询配置中心操作日志配置
     * @return
     */
    @RequestMapping("/list")
    public AdminOperationLogResponse selectOperationLogList(@RequestBody Map<String, Object> map) {
        logger.info("操作日志列表..." + JSONObject.toJSON(map));
        AdminOperationLogResponse  response =new AdminOperationLogResponse();
        //查询版本配置列表条数
        int recordTotal = this.operationLogService.selectOperationLogCountByPage(map);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator((int)map.get("currPage"), recordTotal,(int)map.get("pageSize"));
            //查询记录
            List<FeerateModifyLogVO> recordList =operationLogService.selectOperationLogListByPage(map,paginator.getOffset(), paginator.getLimit());
            if(!org.springframework.util.CollectionUtils.isEmpty(recordList)){
                for (int i = 0; i < recordList.size(); i++) {
                    recordList.get(i).setModifyTypeSrch(accountEsbStates(recordList.get(i).getModifyType()));
                    recordList.get(i).setStatusName(nameStates(recordList.get(i).getStatus()));
                }
                response.setResultList(recordList);
                response.setRecordTotal(recordTotal);
            }
        }
        return response;
    }
    //判断修改类型表
    public String accountEsbStates(Integer string) {
//        if (string==0) {
//            return "全部";
//        }
        if (string==1) {
            return "增加";
        }
        if (string==2) {
            return "修改";
        }
        if (string==3) {
            return "删除";
        }
        return null;

    }
    //判断状态
    public String nameStates(Integer string) {
        if (string==0) {
            return "启用";
        }
        if (string==1) {
            return "禁用";
        }
        return null;

    }


}
