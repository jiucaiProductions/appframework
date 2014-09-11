package test.hbn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import test.dao.UserDao;
import test.dao.UserDaoTestContext;

//这个必须使用junit4.9以上才有。  
//@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserDaoHbn extends UserDaoTestContext {

	@Autowired
	private UserDao userDaoHbn;

	@Override
	public UserDao getUserDao() {
		return userDaoHbn;
	}

//	@Test
//	@Transactional("hbnTransactionManager")
//	public void test() {
//		super.doTest();
//	}

}
