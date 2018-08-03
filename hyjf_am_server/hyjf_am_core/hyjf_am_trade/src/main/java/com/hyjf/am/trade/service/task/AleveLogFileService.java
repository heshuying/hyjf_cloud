/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.am.trade.dao.model.auto.AleveErrorLog;
import com.hyjf.am.trade.dao.model.auto.AleveLog;
import com.hyjf.am.trade.dao.model.auto.EveLog;
import com.hyjf.am.trade.dao.model.customize.trade.AleveLogCustomize;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * @author wangjun
 * @version AleveLogFileService, v0.1 2018/6/25 10:09
 */
public interface AleveLogFileService extends BaseService {
    /**
     * 存款业务红包流水全明细数据文件下载
     */
    void downloadFiles();

    /**
     * aleveLog表数据插入
     * @param list
     */
    void insertAleveLog(List<AleveLog> list);

    /**
     * eveLog表数据插入
     * @param list
     */
    void insertEveLog(List<EveLog> list);

    /**
     * aleveErrorLog表数据插入
     * @param aleveErrorLogs
     */
    void insertAleveErrorLog(List<AleveErrorLog> aleveErrorLogs);

    /**
     * 检查导入的ALEVE数据中是否含有利息相关记录
     * @param tranStype
     * @return
     */
    List<AleveLogCustomize> selectAleveReverseList(List<String> tranStype);

    /**
     * 检索手动冲正数量
     * @param aleveLogCustomize
     * @return
     */
    int countManualReverse(AleveLogCustomize aleveLogCustomize);

    /**
     * 自动冲正
     * @param aleveLogCustomize
     * @return
     */
    boolean updateAutoCorretion(AleveLogCustomize aleveLogCustomize);

    /**
     * 同步冲正后更新处理flg
     * @param aleveLogCustomize
     * @return
     */
    boolean updateAleveLog(AleveLogCustomize aleveLogCustomize);
}
