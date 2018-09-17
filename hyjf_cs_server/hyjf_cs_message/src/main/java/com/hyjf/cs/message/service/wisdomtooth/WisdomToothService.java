package com.hyjf.cs.message.service.wisdomtooth;

import com.hyjf.am.vo.admin.ContentHelpCustomizeVO;
import com.hyjf.am.vo.admin.ContentHelpVO;

import java.util.List;

public interface WisdomToothService {

	/**
	 * 查询问题列表
	 *
	 * @param
	 * @return
	 */
	List<ContentHelpCustomizeVO> queryContentCustomize();

	/**
	 * 根据ID查询问题
	 *
	 * @param id
	 * @return
	 */
	 ContentHelpVO queryContentById(int id);

}
