package org.jiucai.appframework.base.quartz;

import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.PersistJobDataAfterExecution;

/**
 * quartz StatefulJob 的抽象类
 * @author jiucai
 *
 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public abstract class PersistentJob implements Job {

	protected Logs log = LogUtil.getLog(getClass());
	
}
