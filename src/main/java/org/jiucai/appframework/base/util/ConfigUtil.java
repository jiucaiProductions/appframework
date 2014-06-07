package org.jiucai.appframework.base.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * properties 属性文件加载器，支持动态加载
 * 
 * <pre>
 * 1.properties 编码 UTF-8 方可支持中文;
 * 2.默认加载的配置文件支持热加载，修改后即时生效;
 * 3.加载其他配置文件的方式：
 * 		a. 配置 config.properties 中的include 项，Config第一次初始化时将加载其中的配置文件;
 * 		b. 调用 Config.addConfig(配置文件名)，然后使用返回值的方法获取配置项数据;
 * </pre>
 * 
 * @author zhaidw at 2010-11-9
 * 
 */
public class ConfigUtil {

	protected static Log log = LogFactory.getLog(ConfigUtil.class);
	protected static CompositeConfiguration config;

	private static String FILE_ENCODING = "UTF-8";
	private static String SUFFIX_PERPERTIES = ".properties";
	// 配置项列表
	private static Map<String, Configuration> cfgMap;

	static {
		init();
	}

	private synchronized static void init() {
		try {
			config = new CompositeConfiguration();
			config.setLogger(log);

			// 1 SystemConfiguration
			SystemConfiguration configSys = new SystemConfiguration();

			cfgMap = new HashMap<String, Configuration>();

			cfgMap.put("system", configSys);
			config.addConfiguration(configSys);
			log.debug("loading System Properties");

		} catch (Exception e) {
			log.error("load config file failed: "
					+ ExceptionUtils.getFullStackTrace(e));
		}
	}

	/**
	 * 获取最原始的 Configuration
	 * 
	 * @return Configuration
	 */
	public synchronized static Configuration getConfiguration() {

		if (null == config) {
			init();
		}
		return config;
	}

	/**
	 * 设置配置文件编码
	 * 
	 * @param encoding
	 * @return String
	 */
	public synchronized static String setEncoding(final String encoding) {

		FILE_ENCODING = encoding;

		return FILE_ENCODING;
	}

	/**
	 * 设置配置文件名扩展名，包含. 比如 .properties
	 * 
	 * @param suffix
	 * @return String
	 */
	public synchronized static String setSuffix(final String suffix) {

		SUFFIX_PERPERTIES = suffix;

		return SUFFIX_PERPERTIES;
	}

	/**
	 * 每次加载同名配置时，自动移除旧的同名配置
	 * 
	 * @param configFileName
	 * @return Configuration
	 */
	public synchronized static Configuration addConfig(String configFileName) {
		try {

			if (configFileName.toLowerCase().lastIndexOf(SUFFIX_PERPERTIES) == -1) {
				configFileName = configFileName + SUFFIX_PERPERTIES;
			}

			if (null != cfgMap.get(configFileName)) {
				config.removeConfiguration(cfgMap.get(configFileName));
				cfgMap.remove(configFileName);
				log.debug("removing config: " + configFileName);
			}

			PropertiesConfiguration configApp = new PropertiesConfiguration();
			configApp.setEncoding(FILE_ENCODING);
			configApp.load(configFileName);
			configApp.setReloadingStrategy(new FileChangedReloadingStrategy());

			log.debug("adding config file: " + configFileName);
			cfgMap.put(configFileName, configApp);
			config.addConfiguration(configApp);

			log.debug("Config item count: " + cfgMap.size());

		} catch (ConfigurationException e) {
			log.error("load config file failed: "
					+ ExceptionUtils.getFullStackTrace(e));
		}

		return config;
	}

	/**
	 * 获取字符串配置值 无法获取时返回null
	 * 
	 * @param key
	 * @return String
	 */
	public synchronized static String getString(String key) {
		String value = null;
		try {
			value = config.getString(key);
			if (null == value) {
				log.warn("config value of " + key + " is null. ");
			}
		} catch (Exception e) {
			log.error("can not find config key[" + key + "]. ");
			log.error(ExceptionUtils.getFullStackTrace(e));
		}

		return value;
	}

	public static void main(String[] args) {

		String key = "mail.from_name";
		ConfigUtil.addConfig("mail");

		String s = ConfigUtil.getString(key);

		System.out.println("s = " + s);

	}

}
