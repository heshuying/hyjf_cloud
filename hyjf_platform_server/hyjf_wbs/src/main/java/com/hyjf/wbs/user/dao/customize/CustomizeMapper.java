package com.hyjf.wbs.user.dao.customize;

import com.hyjf.wbs.user.dao.auto.AutoMapper;
import com.hyjf.wbs.user.dao.mapper.customize.BankOpenRecordCustomizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("amUserCustomizeMapper")
public class CustomizeMapper extends AutoMapper {
    @Autowired
    protected BankOpenRecordCustomizeMapper bankOpenRecordCustomizeMapper;
}
