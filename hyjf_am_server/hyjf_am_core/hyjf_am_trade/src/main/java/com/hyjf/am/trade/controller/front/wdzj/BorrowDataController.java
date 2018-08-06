package com.hyjf.am.trade.controller.front.wdzj;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.wdzj.BorrowDataResponse;
import com.hyjf.am.response.wdzj.PreapysListResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.wdzj.BorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.wdzj.PreapysListCustomize;
import com.hyjf.am.trade.service.front.wdzj.BorrowDataService;
import com.hyjf.am.vo.wdzj.BorrowListCustomizeVO;
import com.hyjf.am.vo.wdzj.PreapysListCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 网贷之家微服务接口
 * @author hesy
 * @version BorrowDataController, v0.1 2018/7/16 11:59
 */
@RestController
@RequestMapping("/am-trade/wdzj/borrowdata")
public class BorrowDataController extends BaseController {
    @Autowired
    BorrowDataService borrowDataService;

    /**
     * 标的列表数据获取
     * @param requestBean
     * @return
     */
    @RequestMapping("/get_borrowlist")
    public BorrowDataResponse selectBorrowList(@RequestBody Map<String,Object> requestBean){
        BorrowDataResponse response = new BorrowDataResponse();
        List<BorrowListCustomize> listResult = borrowDataService.selectBorrowList(requestBean);
        if(!CollectionUtils.isEmpty(listResult)){
            response.setResultList(CommonUtils.convertBeanList(listResult,BorrowListCustomizeVO.class));
        }
        return response;
    }

    /**
     * 标的列表总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping("/count_borrowlist")
    public Response<Integer> countBorrowList(@RequestBody Map<String, Object> requestBean){
        Integer result = borrowDataService.countBorrowList(requestBean);
        return new Response(result);
    }

    /**
     * 标的列表总金额
     * @param requestBean
     * @return
     */
    @RequestMapping("/sum_borrowamount")
    public Response<String> sumBorrowAmount(@RequestBody Map<String, Object> requestBean){
        String result = borrowDataService.sumBorrowAmount(requestBean);
        return new Response(result);
    }

    /**
     * 提前还款列表
     * @param requestBean
     * @return
     */
    @RequestMapping("/get_preapyslist")
    public PreapysListResponse selectPreapysList(@RequestBody Map<String, Object> requestBean){
        PreapysListResponse response = new PreapysListResponse();
        List<PreapysListCustomize> listResult = borrowDataService.selectPreapysList(requestBean);
        if(!CollectionUtils.isEmpty(listResult)){
            response.setResultList(CommonUtils.convertBeanList(listResult,PreapysListCustomizeVO.class));
        }
        return response;
    }

    /**
     * 提前还款总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping("/count_preapyslist")
    public Response<Integer> countPreapysList (@RequestBody Map<String, Object> requestBean){
        Integer result = borrowDataService.countPreapysList(requestBean);
        return new Response(result);
    }
}
