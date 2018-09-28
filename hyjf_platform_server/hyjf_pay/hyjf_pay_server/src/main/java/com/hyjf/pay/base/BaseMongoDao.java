package com.hyjf.pay.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * mongodb dao基类,可以继续扩展
 * @author xiaojohn
 *
 * @param <T>
 */
public abstract class BaseMongoDao<T> {

	@Autowired
	protected MongoTemplate mongoTemplate;
	
	/**
	 * 保存
	 * @param t
	 */
	public void insert(T t){
		this.mongoTemplate.insert(t, getTableName());
	}
	
	public void insert(T t, String collectionName){
		this.mongoTemplate.insert(t, collectionName);
	}
	
	public void save(T t){
		this.mongoTemplate.save(t, getTableName());
	}
	
	public void save(T t, String collectionName){
		this.mongoTemplate.save(t, collectionName);
	}
	
	public T findOne(Query query){
		return this.mongoTemplate.findOne(query, getEntityClass(), getTableName());
	}
	
	public T findOne(Query query, String collectionName){
		return this.mongoTemplate.findOne(query, getEntityClass(), collectionName);
	}

	public List<T> find(Query query){
		return this.mongoTemplate.find(query,getEntityClass(),getTableName());
	}

	public void update(Query query, Update update){
		this.mongoTemplate.upsert(query, update, getEntityClass(), getTableName());
	}
	
	public void setMongoTemplate(MongoTemplate mongoTemplate){
		this.mongoTemplate = mongoTemplate;
	}
	
	public void findAndModify(Query query,Update update){
		
//		FindAndModifyOptions fo = new FindAndModifyOptions();
//		fo.isRemove();
		
		this.mongoTemplate.findAndModify(query, update, getEntityClass(), getTableName());
	}
	
	/**
	 * 返回实体实际的类型
	 * @return
	 */
	protected abstract Class<T> getEntityClass();
	
	/**
	 * 默认collection
	 * @return
	 */
	protected abstract String getTableName();
}
