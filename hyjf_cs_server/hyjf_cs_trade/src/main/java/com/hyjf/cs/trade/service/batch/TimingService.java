package com.hyjf.cs.trade.service.batch;

/**
 * @Author: yinhui
 * @Date: 2018/12/18 16:21
 * @Version 1.0
 */
public interface TimingService {

    void noneSplitBorrow();

    void hjhBorrow();

    void splitBorrow();

    void couponExpired();

    void dataInfo();

    void downloadFile();

    void holidays();

}
