package com.hyjf.cs.market.controller;

/**
 * @author xiasq
 * @version AbstractController, v0.1 2019/5/5 16:49
 */
public abstract class AbstractController {

    /**
     * 成功返回码
     * @return
     */
    protected abstract String getSuccessStatus();

    /**
     * 失败返回码
     * @return
     */
    protected abstract String getFailStatus();
}
