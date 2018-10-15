package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.vo.user.UserPortraitVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface UserPortraitManagerMapper {
	/**
	 * 查询用户用户画像记录数
	 * @param mapParam
	 * @return
	 */
	public Integer countRecordTotal(Map<String,Object> mapParam) ;

	/**
	 * 查询用户画像列表
	 * @param mapParam
	 * @return
	 */
	List<UserPortraitVO> selectUserPortraitList(Map<String,Object> mapParam) ;

	/**
	 * 根据条件获取用户记录数
	 * @param userPortrait
	 * @return
	 */
    int countRecordList(Map<String, Object> userPortrait);

	/**
	 * 根据性别和年龄查询用户年化投资金额
	 * @param map
	 * @return
	 */
	List<BigDecimal> selectInvest(Map<String, Object> map);
}

	