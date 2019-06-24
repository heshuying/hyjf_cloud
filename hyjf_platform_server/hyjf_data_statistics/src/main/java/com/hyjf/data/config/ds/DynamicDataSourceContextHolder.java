package com.hyjf.data.config.ds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DynamicDataSourceContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);


    // 列举数据源的key
    public enum DbType {
        WRITETRADE, WRITEUSER, WRITECONFIG, WRITEMARKET, READTRADE, READUSER, READCONFIG, READMARKET
    }

    /**
     * 用于在切换数据源时保证不会被其他线程修改
     */
    private static Lock lock = new ReentrantLock();

    /**
     * 用于轮循的计数器
     */
    private static int counter = 0;

    /**
     * Maintain variable for every thread, to avoid effect other thread
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return DbType.WRITETRADE.name();
        }
    };

    /**
     * All DataSource List
     */
    public static List<String> dataSourceKeys = new ArrayList<>();

    /**
     * The constant slaveDataSourceKeys.
     */
    public static List<String> slaveDataSourceKeys = new ArrayList<>();

    /**
     * To switch DataSource
     *
     * @param key the key
     */
    public static void setDataSourceKey(String key) {
        CONTEXT_HOLDER.set(key);
    }

    /**
     * Use master data source.
     */
//    public static void useMasterDataSource() {
//        CONTEXT_HOLDER.set(DbType.WRITETRADE.name());
//    }
    public static void useMasterTradeDataSource() {
        CONTEXT_HOLDER.set(DbType.WRITETRADE.name());
    }
    public static void useMasterUserDataSource() {
        CONTEXT_HOLDER.set(DbType.WRITEUSER.name());
    }
    public static void useMasterConfigDataSource() {
        CONTEXT_HOLDER.set(DbType.WRITECONFIG.name());
    }
    public static void useMasterMarketDataSource() {
        CONTEXT_HOLDER.set(DbType.WRITEMARKET.name());
    }
    /**
     * 当使用只读数据源时通过轮循方式选择要使用的数据源
     */
    public static void useSlaveDataSource() {
        lock.lock();

        try {
            int datasourceKeyIndex = counter % slaveDataSourceKeys.size();
            CONTEXT_HOLDER.set(slaveDataSourceKeys.get(datasourceKeyIndex));
            if(counter < 0) {
            	counter = 0;
            }else {
                counter++;
            }
        } catch (Exception e) {
            logger.error("Switch slave datasource failed, error message is {}", e.getMessage());
            useMasterTradeDataSource();
            logger.error(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public static void useSlaveTradeDataSource(){
        CONTEXT_HOLDER.set(DbType.READTRADE.name());
    }

    public static void useSlaveUserDataSource(){
        CONTEXT_HOLDER.set(DbType.READUSER.name());
    }

    public static void useSlaveConfigDataSource(){
        CONTEXT_HOLDER.set(DbType.READCONFIG.name());
    }

    public static void useSlaveMarketDataSource(){
        CONTEXT_HOLDER.set(DbType.READMARKET.name());
    }

    /**
     * Get current DataSource
     *
     * @return data source key
     */
    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * To set DataSource as default
     */
    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }

}
