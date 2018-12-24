/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.EvaluationCheckConfigVO;
import com.hyjf.am.vo.trade.EvaluationMoneyConfigVO;
import com.hyjf.common.paginator.Paginator;

import java.util.Map;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/20 17:43
 * @param 
 * @return 
 **/
public class EvaluationMoneyResponse extends Response<EvaluationMoneyConfigVO> {
	private int count;

	private Map<String,Object> map;

	private int cuttype;

	private int cnt;

	private EvaluationMoneyConfigVO form;

	Boolean resultBoolean;

	public Boolean getResultBoolean() {
		return resultBoolean;
	}

	public void setResultBoolean(Boolean resultBoolean) {
		this.resultBoolean = resultBoolean;
	}

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

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public int getCuttype() {
		return cuttype;
	}

	public void setCuttype(int cuttype) {
		this.cuttype = cuttype;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public EvaluationMoneyConfigVO getForm() {
		return form;
	}

	public void setForm(EvaluationMoneyConfigVO form) {
		this.form = form;
	}
}
