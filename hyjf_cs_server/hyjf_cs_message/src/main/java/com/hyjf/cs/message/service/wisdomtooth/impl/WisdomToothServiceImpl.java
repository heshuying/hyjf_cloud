package com.hyjf.cs.message.service.wisdomtooth.impl;

import com.hyjf.am.vo.admin.ContentHelpCustomizeVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.service.wisdomtooth.WisdomToothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WisdomToothServiceImpl  implements WisdomToothService {
	@Autowired
	private AmConfigClient amConfigClient;
	@Override
	public List<ContentHelpCustomizeVO> queryContentCustomize() {
		return amConfigClient.queryContentCustomize();
	}
	@Override
	public ContentHelpVO queryContentById(int id) {
		return amConfigClient.help(id);
	}
}
