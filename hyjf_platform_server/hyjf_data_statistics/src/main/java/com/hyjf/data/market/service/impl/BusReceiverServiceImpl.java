///*
// * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
// */
//package com.hyjf.data.dao.market.service.impl;
//
//import com.hyjf.data.bean.BusReceiverEntity;
//import com.hyjf.data.dao.market.service.BusReceiverService;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementSetter;
//import org.springframework.stereotype.Service;
//
//import java.sql.SQLException;
//
///**
// * @author zhangqingqing
// * @version BusReceiverServiceImpl, v0.1 2019/6/19 14:29
// */
//@Service
//public class BusReceiverServiceImpl implements BusReceiverService {
//
//
//   // @Autowired
//    JdbcTemplate hiveJdbcTemplate;
//
//    @Override
//   // @PostConstruct
//    public void createTable() {
//        StringBuffer sql = new StringBuffer("create table IF NOT EXISTS ");
//        sql.append("bus_receiver ");
//        sql.append("(id BIGINT comment '主键ID' " +
//                ",name STRING  comment '姓名' " +
//                ",address STRING comment '地址'" +
//                ",en_name STRING comment '拼音名字'" +
//                ",member_family INT comment '家庭成员'" +
//                ",createDate DATE comment '创建时') ");
//        sql.append(" ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'"); // 定义分隔符
//        sql.append(" STORED AS TEXTFILE"); // 作为文本存储*/
//        hiveJdbcTemplate.execute(sql.toString());
//    }
//
//    @Override
//    public void loadData(String pathFile){
//        String sql = "LOAD DATA INPATH  '"+pathFile+"' INTO TABLE bus_receiver";
//        hiveJdbcTemplate.execute(sql);
//    }
//
//    @Override
//    public void insert(BusReceiverEntity busReceiverEntity) {
//        hiveJdbcTemplate.update("insert into bus_receiver(id,name,address,en_name,member_family) values(?,?,?,?,?)",
//                new PreparedStatementSetter(){
//                    @Override
//                    public void setValues(java.sql.PreparedStatement ps) throws SQLException {
//                        ps.setLong(1, busReceiverEntity.getId());
//                        ps.setString(2,busReceiverEntity.getName());
//                        ps.setString(3,busReceiverEntity.getAddress());
//                        ps.setString(4,busReceiverEntity.getEnName());
//                    }
//                }
//        );
//    }
//
//    @Override
//    public void deleteAll(){
//        String sql = "insert overwrite table bus_receiver select * from bus_receiver where 1=0";
//        hiveJdbcTemplate.execute(sql);
//    }
//
//    @Override
//    public void show() {
//
//    }
//}
