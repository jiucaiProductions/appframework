package org.jiucai.appframework.base.dao.support;

import org.hibernate.Session;



public interface HibernateDao {
	  /**
	   * Users should use this method to get a Session to call its statement methods
	   * This is Session is managed by spring.
	   *
	   * @return Spring managed hibernate session
	   */
	public Session getSession();
	
}
