package org.jiucai.appframework.common.util;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;

/**
 * 日志包装类
 * 
 * @author zhaidw
 * 
 */
public class Logs {

	private Log log;

	private Logger logger;

	public Logs(Log log) {
		this.log = log;
	}

	public Logs(Logger logger) {
		this.logger = logger;
	}

	public boolean isDebugEnabled() {
		if (null != log) {
			return log.isDebugEnabled();
		} else if (null != logger) {
			return logger.isDebugEnabled();
		} else {
			return false;
		}

	}

	public boolean isErrorEnabled() {
		if (null != log) {
			return log.isErrorEnabled();
		} else if (null != logger) {
			return logger.isErrorEnabled();
		} else {
			return false;
		}
	}

	public boolean isFatalEnabled() {
		if (null != log) {
			return log.isFatalEnabled();
		} else if (null != logger) {
			return logger.isErrorEnabled();
		} else {
			return false;
		}
	}

	public boolean isInfoEnabled() {
		if (null != log) {
			return log.isInfoEnabled();
		} else if (null != logger) {
			return logger.isInfoEnabled();
		} else {
			return false;
		}
	}

	public boolean isTraceEnabled() {

		if (null != log) {
			return log.isTraceEnabled();
		} else if (null != logger) {
			return logger.isTraceEnabled();
		} else {
			return false;
		}
	}

	public boolean isWarnEnabled() {
		if (null != log) {
			return log.isWarnEnabled();
		} else if (null != logger) {
			return logger.isWarnEnabled();
		} else {
			return false;
		}
	}

	// -------------------------------------------------------- Logging Methods

	private String obj2Str(Object message) {

		if (message instanceof String) {
			return (String) message;
		} else {
			if (null != message) {
				return String.valueOf(message);
			} else {
				return null;
			}
		}

	}

	public void trace(Object message) {
		if (null != log) {
			log.trace(message);
		} else if (null != logger) {
			logger.trace(obj2Str(message));
		}
	}

	public void trace(Object message, Throwable t) {
		if (null != log) {
			log.trace(message, t);
		} else if (null != logger) {
			logger.trace(obj2Str(message), t);
		}
	}

	public void debug(Object message) {
		if (null != log) {
			log.debug(message);
		} else if (null != logger) {
			logger.debug(obj2Str(message));
		}
	}

	public void debug(Object message, Throwable t) {
		if (null != log) {
			log.debug(message, t);
		} else if (null != logger) {
			logger.debug(obj2Str(message), t);
		}
	}

	public void info(Object message) {
		if (null != log) {
			log.info(message);
		} else if (null != logger) {
			logger.info(obj2Str(message));
		}
	}

	public void info(Object message, Throwable t) {
		if (null != log) {
			log.info(message, t);
		} else if (null != logger) {
			logger.info(obj2Str(message), t);
		}
	}

	public void warn(Object message) {
		if (null != log) {
			log.warn(message);
		} else if (null != logger) {
			logger.warn(obj2Str(message));
		}
	}

	public void warn(Object message, Throwable t) {
		if (null != log) {
			log.warn(message, t);
		} else if (null != logger) {
			logger.warn(obj2Str(message), t);
		}
	}

	public void error(Object message) {
		if (null != log) {
			log.error(message);
		} else if (null != logger) {
			logger.error(obj2Str(message));
		}
	}

	public void error(Object message, Throwable t) {
		if (null != log) {
			log.error(message, t);
		} else if (null != logger) {
			logger.error(obj2Str(message), t);
		}
	}

	public void fatal(Object message) {
		if (null != log) {
			log.fatal(message);
		} else if (null != logger) {
			logger.error(obj2Str(message));
		}
	}

	public void fatal(Object message, Throwable t) {
		if (null != log) {
			log.fatal(message, t);
		} else if (null != logger) {
			logger.error(obj2Str(message), t);
		}
	}

}
