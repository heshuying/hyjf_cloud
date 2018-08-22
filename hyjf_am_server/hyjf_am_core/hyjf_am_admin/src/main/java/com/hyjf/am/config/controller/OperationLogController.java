package com.hyjf.am.config.controller;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.dao.model.auto.FeerateModifyLog;
import com.hyjf.am.config.service.OperationLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/17.
 */
@RestController
@RequestMapping("/am-config/config/operationlog")
public class OperationLogController extends BaseConfigController {

    @Autowired
    private OperationLogService operationLogService;


    /**
     * 分页查询配置中心操作日志配置
     * @return
     */
    @RequestMapping("/list")
    public AdminOperationLogResponse selectOperationLogList(@RequestBody  Map<String, Object> map) {
        logger.info("操作日志列表..." + JSONObject.toJSON(map));
        AdminOperationLogResponse  response =new AdminOperationLogResponse();
        //查询版本配置列表条数
        int recordTotal = this.operationLogService.selectOperationLogCountByPage(map);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator((int)map.get("paginatorPage"), recordTotal);
            //查询记录  todo
            List<FeerateModifyLog> recordList =operationLogService.selectOperationLogListByPage(map,paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<FeerateModifyLogVO> feerateModifyLogVOList = CommonUtils.convertBeanList(recordList, FeerateModifyLogVO.class);
                for (int i = 0; i < feerateModifyLogVOList.size(); i++) {
                    feerateModifyLogVOList.get(i).setModifyTypeSrch(accountEsbStates(recordList.get(i).getModifyType()));
                    feerateModifyLogVOList.get(i).setStatusName(nameStates(recordList.get(i).getStatus()));
                }
                response.setResultList(feerateModifyLogVOList);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
            return response;
        }
        return null;
    }
//
//    /**
//     * 修改类型
//     * @return list
//     */
//    public List<Map<String,String>> updateTypeList(){
//
//        List list=new ArrayList();
//        Integer i = 0;
//        for (; i < 4; i++) {
//            Map<String,String> map=new HashMap();
//            switch (i) {
//                case 0:
////				map.put("typeId", i.toString());
////				map.put("name", "全部");
//                    break;
//                case 1:
//                    map.put("typeId", i.toString());
//                    map.put("name", "增加");
//                    break;
//                case 2:
//                    map.put("typeId", i.toString());
//                    map.put("name", "修改");
//                    break;
//                case 3:
//                    map.put("typeId", i.toString());
//                    map.put("name", "删除");
//                    break;
//                default:
//                    break;
//            }
//            list.add(map);
//        }
//        return list;
//
//
//
//    }
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
