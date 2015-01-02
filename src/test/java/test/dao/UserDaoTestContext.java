package test.dao;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.jiucai.appframework.base.util.DateTimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import test.TestContext;
import test.domain.User;

public abstract class UserDaoTestContext extends TestContext  {

	/**
	 * 子类负责返回 UserDao 示例
	 */
	public abstract UserDao getUserDao();
	
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
	}
	
	
	
	public void doTest() {
		testListUser();
		testInsertUser();
		testUpdateUser(1L);
		testDeleteUser(2L);
		testListUser();
		testselectUserById(1L);
	}
	
	
	protected String getTime() {
		return DateTimeUtil.getCurrentTime();
	}

	public void testDeleteUser(Long userId) {
		User u = new User();
		u.setId(userId);
		try {
			getUserDao().deleteUser(u);
			log.info("user deleted: " + u);
		} catch (Throwable e) {
			log.error("deleteUser error:", e);
			org.junit.Assert.fail("deleteUser error:"
					+ ExceptionUtils.getRootCauseMessage(e));
		}

	}

	public void testUpdateUser(Long userId) {
		User u = new User();
		u.setId(userId);
		String className = getUserDao().getClass().getName();
		u.setUsername(className + " 更新用户 Test-" + getTime());
		u.setPassword("新密码: " + getTime());
		try {

			getUserDao().updateUser(u);
			log.info("user updated: " + u);
		} catch (Throwable e) {
			log.error("updateUser error:", e);
			org.junit.Assert.fail("updateUser error:"
					+ ExceptionUtils.getRootCauseMessage(e));
		}

	}

//	@Transactional
	public void testInsertUser() {
		try {

			for (int i = 0; i < 4; i++) {

				String className = getUserDao().getClass().getName();
				User u = new User();
				u.setUsername(className + " 用户 " + (i+1)+ " Test-" + getTime());
				u.setPassword(getTime());
				
				getUserDao().insertUser(u);
				
//				if (i < 2) {
//					getUserDao().insertUser(u);
//				} else {
//					throw new RuntimeException("more than 2 is not supported.");
//				}
				
				log.info("user inserted: " + u);
			}

		} catch (Throwable e) {
			log.error("insertUser error:", e);
			org.junit.Assert.fail("insertUser error:"
					+ ExceptionUtils.getRootCauseMessage(e));
		}

	}

	public void testListUser() {

		User u = new User();
		u.setUsername("用户");
		try {

			List<User> list = getUserDao().listUser(u);
			log.info("user list: " + list);
		} catch (Throwable e) {
			log.error("listUser error:", e);
			org.junit.Assert.fail("listUser error:"
					+ ExceptionUtils.getRootCauseMessage(e));
		}

	}
	
	
	public void testselectUserById(Long userId) {
		try {
			User user = getUserDao().selectUserById(userId);
			log.info("selectUserById: " + user);
		} catch (Throwable e) {
			log.error("selectUserById error:", e);
			org.junit.Assert.fail("selectUserById error:"
					+ ExceptionUtils.getRootCauseMessage(e));
		}

	}
	
}
