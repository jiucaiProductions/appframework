package test.mybatis;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import test.dao.UserDao;
import test.dao.UserDaoTestContext;

//这个必须使用junit4.9以上才有。
//@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserMbt extends UserDaoTestContext {

    @Autowired
    private UserDao userDaoMbt;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Override
    public UserDao getUserDao() {
        return userDaoMbt;
    }

    @Override
    @Before
    public void setup() {
    }

    // @Test
    // @Transactional
    // public void test() {
    // super.doTest();
    // }

}
