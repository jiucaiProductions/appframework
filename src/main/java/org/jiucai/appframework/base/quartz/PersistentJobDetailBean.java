package org.jiucai.appframework.base.quartz;

import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;

/**
 * 可持久化的 JobDetail
 * <pre>
 * 可以代替 org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean
 * job 实现类需要继承 {@link org.jiucai.appframework.base.quartz.PersistentJob }
 * </pre>
 * @see PersistentJob
 * @author jiucai
 *
 */
public class PersistentJobDetailBean extends JobDetailImpl implements
		BeanNameAware, InitializingBean {
	
	private static final long serialVersionUID = 3715614559479047815L;

	private String beanName;

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void afterPropertiesSet() {
		if (getName() == null) {
			setName(this.beanName);
		}
		if (getGroup() == null) {
			setGroup(getGroup());
		}
	}

}
