package test;

import org.jiucai.appframework.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
// 控制测试类事务是否提交
// @TransactionConfiguration(defaultRollback = false)
public abstract class TestContext extends BaseService {

    @Autowired
    protected WebApplicationContext context;

    @AfterTransaction
    public void afterTransaction() {
        log.info("事务结束了");
    }

    @BeforeTransaction
    public void beforeTransaction() {
        log.info("事务开始了");
    }

}
