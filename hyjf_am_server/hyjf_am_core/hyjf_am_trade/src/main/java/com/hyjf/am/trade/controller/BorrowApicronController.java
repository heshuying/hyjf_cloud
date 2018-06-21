/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.service.BorrowApicronService;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author ${yaoy}
 * @version BorrowApicronController, v0.1 2018/6/14 16:44
 */
@RestController
@RequestMapping("/am-trade/borrowApicron")
public class BorrowApicronController {

	@Autowired
	private BorrowApicronService borrowApicronService;

	@GetMapping("/getBorrowApicronList/{extraYieldRepayStatus}/{apiType}")
	public BorrowApicronResponse getBorrowApicronList(Integer extraYieldRepayStatus, Integer apiType) {
		BorrowApicronResponse response = new BorrowApicronResponse();
		List<BorrowApicron> borrowApicronList = borrowApicronService.getBorrowApicronList(extraYieldRepayStatus,
				apiType);
		if (!CollectionUtils.isEmpty(borrowApicronList)) {
			response.setResultList(CommonUtils.convertBeanList(borrowApicronList, BorrowApicronVO.class));
		}
		return response;
	}

	@PostMapping("/updateBorrowApicron")
	public int updateBorrowApicron(@RequestBody Integer id, Integer status, String data) {
		int updateBorrowApicronFlag = borrowApicronService.updateBorrowApicron(id, status, data);
		return updateBorrowApicronFlag;
	}

	@PostMapping("/updateBorrowApicron2")
	public int updateBorrowApicron2(@RequestBody Integer id, Integer status) {
		int updateBorrowApicronFlag = borrowApicronService.updateBorrowApicron(id, status);
		return updateBorrowApicronFlag;
	}

	@GetMapping("/getBorrowApicronListWithRepayStatus/{status}/{apiType}")
	public BorrowApicronResponse getBorrowApicronListWithRepayStatus(Integer status, Integer apiType) {
		BorrowApicronResponse response = new BorrowApicronResponse();
		List<BorrowApicron> borrowApicronList = borrowApicronService.getBorrowApicronListWithRepayStatus(status,
				apiType);
		if (!CollectionUtils.isEmpty(borrowApicronList)) {
			List<BorrowApicronVO> borrowApicronVoList = CommonUtils.convertBeanList(borrowApicronList,
                    BorrowApicronVO.class);
			response.setResultList(borrowApicronVoList);
		}
		return response;
	}

	@PostMapping("/updateBorrowApicronOfRepayStatus")
	public int updateBorrowApicronOfRepayStatus(@RequestBody Integer id, Integer status) {
		int updateBorrowApicronFlag = borrowApicronService.updateBorrowApicronOfRepayStatus(id, status);
		return updateBorrowApicronFlag;
	}
}