package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BindCardExceptionRequest;
import com.hyjf.am.vo.admin.BindCardExceptionCustomizeVO;

import java.util.List;

/**
 * 绑卡掉单异常处理CustomizeMapper
 * 
 * @author liuyang
 *
 */
public interface BindCardExceptionCustomizeMapper {

	/**
	 * 检索用户银行卡列表件数
	 * 
	 * @param request
	 * @return
	 */
	public int countBankCardList(BindCardExceptionRequest request);

	/**
	 * 检索用户银行卡列表
	 * 
	 * @param request
	 * @return
	 */
	public List<BindCardExceptionCustomizeVO> selectBankCardList(BindCardExceptionRequest request);

}
