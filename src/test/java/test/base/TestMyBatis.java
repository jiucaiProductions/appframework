package test.base;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service("testMyBatis")
public class TestMyBatis {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String[] configFiles = { "classpath:spring/applicationContext.xml",
                "classpath:spring/applicationDatabase.xml" };

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(configFiles);

        // int count = ctx.getBeanDefinitionCount();
        // System.out.println("getBeanDefinitionCount: " + count);
        //
        // String[] beans = ctx.getBeanDefinitionNames();
        //
        // for (int i = 0; i < beans.length; i++) {
        // System.out.println("bean[" + i + "]: " + beans[i]);
        // }

        TestMyBatis t = ctx.getBean("testMyBatis", TestMyBatis.class);
        t.test2();

        ctx.close();

    }

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    public void test1() {

        Configuration cfg = sqlSessionTemplate.getConfiguration();
        SqlSourceBuilder b = new SqlSourceBuilder(cfg);

        File f = new File(
                getClass().getClassLoader().getResource("mybatis/sql/test.xml").getFile());

        String sqlContent = "";
        try {
            sqlContent = FileUtils.readFileToString(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SqlSource ss = b.parse(sqlContent, List.class, null);

        String sql = ss.getBoundSql(new HashMap<String, Object>()).getSql();

        System.out.println(sql);
    }

    public void test2() {
        String dynamicSql = ""; // TestSqlBuilder.selectByPro();

        System.out.println("dynamicSql:\n" + dynamicSql);
        // List<String> list = Lists.newArrayList();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", "%test%");

        BoundSql sql = TestSqlBuilder.getSql(dynamicSql, paramMap,
                sqlSessionTemplate.getConfiguration());

        System.out.println(sql.getSql());
    }

}
