/*
 * @Copyright: 2005-2017 www.2345.com. All rights reserved.
 */
package com.hyjf.batch.frame.service;

import com.hyjf.batch.frame.exception.ServiceException;
import com.hyjf.batch.frame.pojo.TaskInfo;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


/**
 * @author xiasq
 * @version TaskManageController, v0.1 2018/1/20 22:12
 */
@Service
public class TaskService {
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

	@Autowired
	private Scheduler scheduler;

	/**
	 * 所有任务列表
	 * 
	 * @return
	 */
	public List<TaskInfo> list() {
		List<TaskInfo> list = new ArrayList<>();
		String df = "yyyy-MM-dd HH:mm:ss";

		try {
			for (String groupJob : scheduler.getJobGroupNames()) {
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.<JobKey> groupEquals(groupJob))) {
					List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
					for (Trigger trigger : triggers) {
						Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
						try {
							JobDetail jobDetail = null;
							try {
								jobDetail = scheduler.getJobDetail(jobKey);
							} catch (Exception e) {
								logger.error("===> getJobDetail fail jobKey:{}", jobKey, e);
							}

							String cronExpression = "", createTime = "";

							if (trigger instanceof CronTrigger) {
								CronTrigger cronTrigger = (CronTrigger) trigger;
								cronExpression = cronTrigger.getCronExpression();
								createTime = cronTrigger.getDescription();
							}

							TaskInfo info = new TaskInfo();
							info.setJobName(jobKey.getName());
							info.setJobGroup(jobKey.getGroup());
							if (jobDetail != null) {
								info.setJobDescription(jobDetail.getDescription());
							}
							info.setJobStatus(triggerState.name());
							info.setCronExpression(cronExpression);
							info.setCreateTime(createTime);
							//下次触发时间加入判空处理
							if (trigger.getNextFireTime()!=null){
								info.setNextFireTime(GetDate.formatDate(trigger.getNextFireTime(),df));
							}else {
								info.setNextFireTime("--");
							}
                            //上次触发时间加入判空处理
							if (trigger.getPreviousFireTime()!=null){
								info.setPrevFireTime(GetDate.formatDate(trigger.getPreviousFireTime(),df));
							}else {
								info.setPrevFireTime("--");
							}

							list.add(info);
						} catch (Exception e) {
							logger.error("===> getTaskInfo fail jobKey:{}", jobKey, e);
						}
					}
				}
			}
		} catch (SchedulerException e) {
			logger.error(e.getMessage());
		}

		return list;
	}

	/**
	 * 保存定时任务
	 * 
	 * @param info
	 */
	@SuppressWarnings("unchecked")
	public void addJob(TaskInfo info) {
		String jobName = info.getJobName(), jobGroup = info.getJobGroup(), cronExpression = info.getCronExpression(),
				jobDescription = info.getJobDescription(),
				createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		try {
			if (checkExists(jobName, jobGroup)) {
				logger.info("===> AddJob fail, job already exist, jobGroup:{}, jobName:{}", jobGroup, jobName);
				throw new ServiceException(String.format("Job已经存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
			}

			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
			JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

			CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
					.withMisfireHandlingInstructionDoNothing();
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime)
					.withSchedule(schedBuilder).build();

			Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(jobName);
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).withDescription(jobDescription).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException | ClassNotFoundException e) {
			throw new ServiceException("类名不存在或执行表达式错误");
		}
	}

	/**
	 * 修改定时任务
	 * 
	 * @param info
	 */
	public void edit(TaskInfo info) {

		String jobName = info.getJobName(), jobGroup = info.getJobGroup(), cronExpression = info.getCronExpression(),
				jobDescription = info.getJobDescription(),
				createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		try {
			if (!checkExists(jobName, jobGroup)) {
				throw new ServiceException(String.format("Job不存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
			}
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
			JobKey jobKey = new JobKey(jobName, jobGroup);
			CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
					.withMisfireHandlingInstructionDoNothing();
			CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime)
					.withSchedule(cronScheduleBuilder).build();
			JobDetail jobDetail = scheduler.getJobDetail(jobKey).getJobBuilder().withDescription(jobDescription)
					.build();
			HashSet<Trigger> triggerSet = new HashSet<>();
			triggerSet.add(cronTrigger);
			scheduler.scheduleJob(jobDetail, triggerSet, true);
		} catch (SchedulerException e) {
			throw new ServiceException("类名不存在或执行表达式错误");
		}
	}

	/**
	 * 删除定时任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 */
	public void delete(String jobName, String jobGroup) {
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
		try {
			scheduler.pauseTrigger(triggerKey);
			scheduler.unscheduleJob(triggerKey);
			logger.info("===> delete, triggerKey:{}", triggerKey);
		} catch (SchedulerException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 暂停定时任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 */
	public void pause(String jobName, String jobGroup) {
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
		try {
			if (checkExists(jobName, jobGroup)) {
				scheduler.pauseTrigger(triggerKey);
				logger.info("===> Pause success, triggerKey:{}", triggerKey);
			}
		} catch (SchedulerException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 重新开始任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 * 
	 */
	public void resume(String jobName, String jobGroup) {
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);

		try {
			if (checkExists(jobName, jobGroup)) {
				scheduler.resumeTrigger(triggerKey);
				logger.info("===> Resume success, triggerKey:{}", triggerKey);
			}
		} catch (SchedulerException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 运行一次任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 */
	public void runOnce(String jobName, String jobGroup) {
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		try {
			if (checkExists(jobName, jobGroup)) {
				scheduler.triggerJob(jobKey);
				logger.info("===> runOnce success, JobKey:{}", jobKey);
			}
		} catch (SchedulerException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 验证是否存在
	 * 
	 * @param jobName
	 * @param jobGroup
	 * @throws SchedulerException
	 */
	private boolean checkExists(String jobName, String jobGroup) throws SchedulerException {
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
		return scheduler.checkExists(triggerKey);
	}
}
