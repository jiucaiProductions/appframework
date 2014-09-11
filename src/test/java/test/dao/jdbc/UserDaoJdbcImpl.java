package test.dao.jdbc;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import test.TestBaseDao;
import test.dao.UserDao;
import test.domain.User;

@Repository("userDaoJdbc")
public class UserDaoJdbcImpl  extends TestBaseDao implements UserDao {

	@Override
	public Long insertUser(User u) {
		StringBuffer sql = new StringBuffer("");
		sql.append(" insert into user ");
		sql.append(" ( username, password ) ");
		sql.append(" values ( ");
		sql.append("   :username ");
		sql.append(" , :password ");
		sql.append(" ) ");
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(u);
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		int rows = getDao().update(sql.toString(), paramSource, generatedKeyHolder);
		
		if(rows > 0 ){
			Long userId = generatedKeyHolder.getKey().longValue();
			u.setId(userId);
		}
		
		return u.getId();
	}

	@Override
	public List<User> listUser(User query) {
		StringBuffer sql = new StringBuffer("");
		sql.append(" select * ").append("\n");
		sql.append(" from user ").append("\n");
		sql.append(" where 1=1 ").append("\n");
		
		if(null != query.getId()){
			sql.append(" and id = :id  ").append("\n");
		}
		sql.append(" order by id desc ").append("\n");
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(query);
		
		RowMapper<User> rowMapper = ParameterizedBeanPropertyRowMapper.newInstance(User.class);
		
//	    BeanPropertyRowMapper<User> mapper = new BeanPropertyRowMapper<User>(User.class);
		
		return getDao().query(sql.toString(), paramSource, rowMapper);

	}

	@Override
	public Long updateUser(User u) {
		StringBuffer sql = new StringBuffer("");
		sql.append(" update user set ");
		sql.append(" username = :username");
		sql.append(" , password = :password ");
		sql.append(" where id = :id  ");
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(u);
		int rows = getDao().update(sql.toString(), paramSource);
		
		return rows > 0 ? u.getId() : null;
	}

	@Override
	public Long deleteUser(User u) {
		StringBuffer sql = new StringBuffer("");
		sql.append(" delete from user ");
		sql.append(" where id = :id  ");
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(u);
		int rows = getDao().update(sql.toString(), paramSource);
		
		return rows > 0 ? u.getId() : null;
	}

	@Override
	public User selectUserById(Long id) {
		
		Assert.notNull(id, "query parameter id is required");
		
		User user = null;
		
		User query = new User();
		query.setId(id);
		
		List<User> list = listUser(query);
		
		if(null != list && list.size() > 0){
			user = list.get(0);
		}
		return user;
	}

}
