/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.EvaluationMoneyConfigVO;
import com.hyjf.am.vo.admin.EvaluationMoneyLogConfigVO;
import com.hyjf.common.paginator.Paginator;

import java.util.Map;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/20 17:43
 * @param 
 * @return 
 **/
public class EvaluationMoneyLogResponse extends Response<EvaluationMoneyLogConfigVO> {
	private int count;

	private EvaluationMoneyConfigVO form;

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private Paginator paginator;

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public EvaluationMoneyConfigVO getForm() {
		return form;
	}

	public void setForm(EvaluationMoneyConfigVO form) {
		this.form = form;
	}
}
