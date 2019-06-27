package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.dao.model.auto.Utm;
import com.hyjf.am.user.dao.model.auto.UtmPlat;

/**
 * @author：yinhui
 * @Date: 2018/10/23  16:05
 */
public interface UtmPlatService {

    UtmPlat getUtmPlat(Integer sourceId);

    UtmPlat getUtmByUserId(Integer userId);

    UtmPlat getUtmByUtmId(Integer utmId);

    Utm getUtmBySourceId(Integer sourceId);

    Integer getSourceIdByUtmId(Integer tenderUserUtmId);
}
