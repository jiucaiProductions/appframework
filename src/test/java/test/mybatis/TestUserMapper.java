package test.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import test.dao.UserDao;
import test.dao.UserDaoTestContext;
import test.dao.UserMapper;

//这个必须使用junit4.9以上才有。  
//@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserMapper extends UserDaoTestContext {

	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDao getUserDao() {
		return userMapper;
	}
	
//	@Test
//	@Transactional
//	public void test() {
//		super.doTest();
//	}



}
