/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.trade;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.PushMoney;
import com.hyjf.am.trade.service.admin.PushMoneyService;
import com.hyjf.am.vo.trade.PushMoneyVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fuqiang
 * @version PushMoneyController, v0.1 2018/7/10 19:20
 */
@RestController
@RequestMapping("/am-trade/pushmoney")
public class PushMoneyController extends BaseController {
	@Autowired
	private PushMoneyService pushMoneyService;

	/**
	 * 获取提成列表
	 *
	 * @return
	 */
	@RequestMapping("/getrecordlist")
	public PushMoneyResponse getRecordList(PushMoneyRequest requestBean) {
		PushMoneyResponse response = new PushMoneyResponse();
		List<PushMoney> list = pushMoneyService.getRecordList();
		response.setCount(list.size());
		if (list != null) {
			Paginator paginator = new Paginator(requestBean.getCurrPage(), list.size(), requestBean.getPageSize() == 0 ? 10 : requestBean.getPageSize());
			List<PushMoney> pushMoneyList = this.pushMoneyService.getRecordList(paginator.getOffset(),paginator.getLimit());
			if (!CollectionUtils.isEmpty(pushMoneyList)) {
				List<PushMoneyVO> voList = CommonUtils.convertBeanList(pushMoneyList, PushMoneyVO.class);
				response.setPaginator(paginator);
				response.setResultList(voList);
			}
		}
		return response;
	}

	/**
	 * 添加提成配置
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertpushmoney")
	public PushMoneyResponse insertPushMoney(@RequestBody PushMoneyRequest request) {
		PushMoneyResponse response = new PushMoneyResponse();
		pushMoneyService.insertPushMoney(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 修改提成配置
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatepushmoney")
	public PushMoneyResponse updatePushMoney(@RequestBody PushMoneyRequest request) {
		PushMoneyResponse response = new PushMoneyResponse();
		pushMoneyService.updatePushMoney(request);
		response.setRtn(AdminResponse.SUCCESS);
		return response;
	}

	/**
	 * 画面迁移(含有id更新，不含有id添加)
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/get_info_action/{id}")
	public PushMoneyResponse getInfoAction(@PathVariable Integer id) {
		PushMoneyResponse response = new PushMoneyResponse();
		if(id != null){
			PushMoney pushMoney = this.pushMoneyService.getRecordById(id);
			if (pushMoney != null){
				PushMoneyVO voList = CommonUtils.convertBean(pushMoney, PushMoneyVO.class);
				response.setResult(voList);
			}
		}
		return response;
	}

    /**
     * 删除配置信息
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete_record")
    public PushMoneyResponse deleteRecord(@RequestBody List<Integer> ids) {
        PushMoneyResponse response = new PushMoneyResponse();
        if(!CollectionUtils.isEmpty(ids)){
            this.pushMoneyService.deleteRecord(ids);
        }
        return response;
    }

}
