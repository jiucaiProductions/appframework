package org.jiucai.appframework.base.quartz;

import org.jiucai.appframework.base.service.BaseService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

/**
 * Quartz Job 基类
 * 
 * @author zhaidangwei
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public abstract class QuartzJob extends BaseService implements Job {

    public abstract void execute();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        execute();
    }
}
