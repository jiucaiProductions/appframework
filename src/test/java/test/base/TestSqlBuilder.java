package test.base;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.Configuration;

public class TestSqlBuilder extends SQL {

	public String getSelect() {
		
		SELECT("p.id,p.username,p.password");
		SELECT("p.createDate,p.modifyDate");
		FROM("person p");
		FROM("account a");
		INNER_JOIN("dept d on d.id=p.id");
		INNER_JOIN("company c on c.id=d.id");
		WHERE("p.id=a.id");
		WHERE("p.name like #{name}  ");
		OR();
		WHERE("p.sex = '1'");
		GROUP_BY("p.id");
		HAVING("p.age > #{age}");
		ORDER_BY("p.id");
		ORDER_BY("p.name");

		return getSelf().toString();
	}
	
	public static BoundSql getSql(String dynamicSql, Map keysMap,Configuration cfg) {
		TextSqlNode node =new TextSqlNode(dynamicSql);     
        DynamicSqlSource s=new DynamicSqlSource(cfg,node);    
        
        //此外对于静态SQL，ibatis还提供了StaticSqlSource  
        BoundSql sql = s.getBoundSql(keysMap); 
        
        return sql;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestSqlBuilder t = new TestSqlBuilder();
		System.out.println( t.getSelect() );

	}

}
