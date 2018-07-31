package com.hyjf.am.config.controller.admin.content;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.Category;
import com.hyjf.am.config.dao.model.auto.ContentHelp;
import com.hyjf.am.config.dao.model.auto.ContentHelpExample;
import com.hyjf.am.config.dao.model.customize.IdCardCustomize;
import com.hyjf.am.config.service.CategoryService;
import com.hyjf.am.config.service.IdCardService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CategoryResponse;
import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.response.trade.OperationReportJobResponse;
import com.hyjf.am.resquest.admin.CategoryBeanRequest;
import com.hyjf.am.resquest.admin.ContentHelpBeanRequest;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.am.resquest.trade.OperationReportJobRequest;
import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.admin.ContentHelpCustomizeVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: walter.tanyy
 * @Date: 2018/7/31 16:28
 * @Description: IdCardController
 */
@RestController
@RequestMapping("/am-config/content/idcard")
public class IdCardController extends BaseConfigController {
    private static final Logger logger = LoggerFactory.getLogger(IdCardController.class);

    @Autowired
    private IdCardService idCardService;

    /**
     * @Author walter.tanyy
     * @user walter.tanyy
     * @Description  查询身份证对应的地区配置表
     * @Date 14:59 2018/7/24
     * @Param idCardCustomize
     * @return
     */
    @RequestMapping("/idcarddetail")
    public IdCardCustomize idCardDetail(@RequestBody IdCardCustomize idCardCustomize) {
        logger.info("查询身份证对应的地区配置表......");
       return idCardService.getIdCardCustomize(idCardCustomize);
    }
    /**
     * @Author walter.tanyy
     * @user walter.tanyy
     * @Description  查询分组信息
     * @Date 14:59 2018/7/24
     * @Param request
     * @return
     */
    @RequestMapping("/tendercitygroupby")
    public OperationReportJobResponse tenderCityGroupBy(@RequestBody OperationReportJobRequest request) {
        logger.info("查询分组信息......");
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list = idCardService.getTenderCityGroupBy(request.getOperationReportJobVOList());
        response.setResultList(list);
        return response;
    }

}
