package com.hyjf.am.user.dao.mapper.customize.callcenter;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.callcenter.CallCenterUserInfoRequest;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.user.dao.model.customize.callcenter.CallcenterAccountHuifuCustomize;
import com.hyjf.am.user.dao.model.customize.callcenter.CallcenterUserBaseCustomize;

/**
 * @author libin
 * @version CallCenterCustomizeMapper, v0.1 2018/5/8 14:14
 */
public interface CallCenterAccountHuifuMapper {
    /**
     * @param mobiles
     * @return
     */
    List<CallcenterAccountHuifuCustomize> findHuifuTiedcardInfo(CallcenterAccountHuifuRequest callcenterAccountHuifuRequest);
    
/*    周二来了新作一个XM了文件参考  原 CallcenterAccountHuifuCustomizeMapper.xml
    com.hyjf.am.user.dao.mapper.customize.callcenter.CallcenterAccountHuifuCustomize
    com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest*/
    
    
			/*    SELECT			
				hydu.user_id,
				hydu.username AS user_name,
				hydbc. NAME AS bank,
				CASE
			WHEN hyduab.card_type = 0	
			OR hyduab.card_type = 1 THEN '普通提现卡'	
			ELSE	
				'快捷支付卡'
			END AS card_property,	
			CASE	
			WHEN hyduab.card_type = 1	
			OR hyduab.card_type = 2 THEN '是'	
			ELSE	
				'否'
			END AS card_type,	
			hyduab.account AS account,	
			 from_unixtime(	
				hyduab.addtime,
				'%Y-%m-%d %H:%i:%s'
			) AS add_time	
			FROM	
				huiyingdai_users hydu
			INNER JOIN huiyingdai_account_bank hyduab ON hydu.user_id = hyduab.user_id
			LEFT JOIN huiyingdai_bank_config hydbc ON hydbc. CODE = hyduab.bank	
			<include refid="Where_Clause" />	
			ORDER BY hyduab.addtime DESC	
			<if test="limitStart != null and limitEnd !=null" >	
			  LIMIT #{limitStart,jdbcType=INTEGER} , #{limitEnd,jdbcType=INTEGER}		
			</if>*/
}
