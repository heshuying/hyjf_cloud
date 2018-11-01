/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.locked;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.locked.LockedUserMgrResponse;
import com.hyjf.am.resquest.admin.locked.LockedeUserListRequest;
import com.hyjf.am.user.dao.model.auto.LockedUserInfo;
import com.hyjf.am.user.service.admin.locked.LockedUserService;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author cui
 * @version LockedUserController, v0.1 2018/9/20 17:39
 */

@Api(value = "登录失败锁定用户管理")
@RestController
@RequestMapping("/am-user/lockeduser")
public class LockedUserController {

	Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	private LockedUserService lockedUserService;

	@RequestMapping("/frontlist")
	public LockedUserMgrResponse frontList(@RequestBody LockedeUserListRequest lockedeUserListRequest) {
		LockedUserMgrResponse response = new LockedUserMgrResponse();
		createPage(response, lockedeUserListRequest, true);
		return response;
	}

	@RequestMapping("/adminlist")
	public LockedUserMgrResponse adminList(@RequestBody LockedeUserListRequest lockedeUserListRequest) {
		LockedUserMgrResponse response = new LockedUserMgrResponse();
		createPage(response, lockedeUserListRequest, false);
		return response;
	}

	private void createPage(LockedUserMgrResponse response, LockedeUserListRequest qo, boolean isFront) {
		Map<String, Object> parameterMap = Maps.newHashMap();
		parameterMap.put("lockTimeStartStr", qo.getLockTimeStartStr());
		parameterMap.put("lockTimeEndStr", qo.getLockTimeEndStr());
		parameterMap.put("username", qo.getUsername());
		parameterMap.put("mobile", qo.getMobile());
		parameterMap.put("isFront", isFront ? 1 : 0);

		int recordTotal = lockedUserService.countRecordTotal(parameterMap);
		if (recordTotal > 0) {
			response.setCount(recordTotal);
			Paginator paginator = new Paginator(qo.getCurrPage(), recordTotal,qo.getPageSize());

			parameterMap.put("limitStart", paginator.getOffset());
			parameterMap.put("limitEnd", paginator.getLimit());

			List<LockedUserInfo> recordList = lockedUserService.getRecordList(parameterMap);
			List<LockedUserInfoVO> lstRecord = parseRecord(recordList, isFront);
			response.getResultList().addAll(lstRecord);
		}
	}

	private List<LockedUserInfoVO> parseRecord(List<LockedUserInfo> recordList, boolean isFront) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return Lists.transform(recordList, new Function<LockedUserInfo, LockedUserInfoVO>() {
			@Nullable
			@Override
			public LockedUserInfoVO apply(@Nullable LockedUserInfo loginErrorLockUser) {
				LockedUserInfoVO record = new LockedUserInfoVO();
				BeanUtils.copyProperties(loginErrorLockUser, record);
				if (loginErrorLockUser.getLockTime() != null) {
					String lockTimeString = sdf.format(loginErrorLockUser.getLockTime());
					record.setLockTimeStr(lockTimeString);
				}

				// 是否被锁定
				String redisKeyPrefix = isFront ? RedisConstants.PASSWORD_ERR_COUNT_ALL
						: RedisConstants.PASSWORD_ERR_COUNT_ADMIN;

				String key = record.getUsername();

				if (isFront) {
					key = String.valueOf(lockedUserService.getLockedUserId(record.getUsername()));
				}

				boolean isLocked = RedisUtils.exists(redisKeyPrefix + key);

				record.setUnlocked(isLocked ? 0 : 1);

				return record;
			}
		});
	}


	@RequestMapping(value = "/frontunlock")
	public BooleanResponse frontUnlock(@RequestBody LockedUserInfoVO vo) {
		boolean flag =lockedUserService.unlock(vo,true);
		return new BooleanResponse(flag);
	}

	@RequestMapping(value = "/adminunlock")
	public BooleanResponse adminUnlock(@RequestBody LockedUserInfoVO vo) {
		boolean flag =lockedUserService.unlock(vo,false);
		return new BooleanResponse(flag);
	}
}
