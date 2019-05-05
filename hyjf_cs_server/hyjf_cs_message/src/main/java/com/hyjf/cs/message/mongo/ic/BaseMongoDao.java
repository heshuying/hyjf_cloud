package com.hyjf.cs.message.mongo.ic;

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
		this.mongoTemplate.insert(t);
	}

	public void insert(T t, String collectionName){
		this.mongoTemplate.insert(t, collectionName);
	}

	public void save(T t){
		this.mongoTemplate.save(t);
	}

	public void save(T t, String collectionName){
		this.mongoTemplate.save(t, collectionName);
	}

	public T findOne(Query query){
		return this.mongoTemplate.findOne(query, getEntityClass());
	}

	public T findOne(Query query, String collectionName){
		return this.mongoTemplate.findOne(query, getEntityClass(), collectionName);
	}

	public List<T> find(Query query){
		return this.mongoTemplate.find(query, getEntityClass());
	}

	public List<T> find(Query query, String collectionName){
		return this.mongoTempBaseMongoDaolate.find(query, getEntityClass(), collectionName);
	}

	public void update(Query query, Update update){
		this.mongoTemplate.upsert(query, update, getEntityClass());
	}
	/**
	 * 未查询到数据不创建新数据、批量更新
	 * @Authod liushouyi
	 * @param query
	 * @param update
	 */
	public void updateAll(Query query, Update update){
		this.mongoTemplate.updateMulti(query, update, getEntityClass());
	}

	public void del(Query query){
		this.mongoTemplate.remove(query, getEntityClass());
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate){
		this.mongoTemplate = mongoTemplate;
	}

	protected abstract Class<T> getEntityClass();

	public void deleteBatch(List list){
		for(int i=0;i<list.size();i++){
			this.mongoTemplate.remove(list.get(i));
		}
	}

	public Long count(Query query){
		return this.mongoTemplate.count(query,getEntityClass());
	}

}
