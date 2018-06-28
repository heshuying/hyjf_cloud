package com.hyjf.am.trade.controller.demo;

import com.hyjf.am.trade.dao.mapper.auto.ProducerTransactionMessageMapper;
import com.hyjf.am.trade.dao.model.auto.ProducerTransactionMessage;
import com.hyjf.am.trade.dao.model.auto.ProducerTransactionMessageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author xiasq
 * @version ProducerTransactionMessageServiceImpl, v0.1 2018/6/28 15:18
 */
@Service
public class ProducerTransactionMessageServiceImpl implements ProducerTransactionMessageService {

	@Autowired
	ProducerTransactionMessageMapper producerTransactionMessageMapper;

	/**
	 * 保存
	 * 
	 * @param message
	 */
	@Override
	@Transactional
	public void save(ProducerTransactionMessage message) {
		if (message.getId() != null && message.getId() > 0) {
			producerTransactionMessageMapper.updateByPrimaryKey(message);
		}
		producerTransactionMessageMapper.insertSelective(message);
	}

	/**
	 * 根据特定条件查询
	 * 
	 * @param topic
	 * @param keys
	 * @param tag
	 * @return
	 */
	@Override
	public ProducerTransactionMessage findByCondition(String topic, String keys, String tag) {
		ProducerTransactionMessageExample example = new ProducerTransactionMessageExample();
		ProducerTransactionMessageExample.Criteria criteria = example.createCriteria();
		criteria.andTopicEqualTo(topic).andTagsEqualTo(tag).andKeysEqualTo(keys);

		List<ProducerTransactionMessage> list = producerTransactionMessageMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list))
			return null;

		if (list.size() > 1)
			throw new RuntimeException("消息不唯一...");

		return list.get(0);
	}
}
