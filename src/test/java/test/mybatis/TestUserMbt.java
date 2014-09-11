package test.mybatis;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import test.dao.UserDao;
import test.dao.UserDaoTestContext;

//这个必须使用junit4.9以上才有。  
//@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserMbt extends UserDaoTestContext {

	@Autowired
	private UserDao UserDaoMbt;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
	}


	@Override
	public UserDao getUserDao() {
		return UserDaoMbt;
	}
	
	
//	@Test
//	@Transactional
//	public void test() {
//		super.doTest();
//	}

}
