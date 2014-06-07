package org.jiucai.appframework.base.spring.quartz;

import org.jiucai.appframework.common.util.LogUtil;
import org.jiucai.appframework.common.util.Logs;
import org.quartz.StatefulJob;

/**
 * quartz StatefulJob 的抽象类
 * @author jiucai
 *
 */
@SuppressWarnings("deprecation")
public abstract class PersistentJob implements StatefulJob {

	protected Logs log = LogUtil.getLog(getClass());
	
}
