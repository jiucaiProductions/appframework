package test.dao.mbt;

import java.util.List;

import org.springframework.stereotype.Repository;

import test.TestBaseDao;
import test.dao.UserDao;
import test.domain.User;

@Repository("UserDaoMbt")
public class UserDaoMbtImpl  extends TestBaseDao implements UserDao{

	@Override
	public Long insertUser(User u) {
		int  rows = getSqlSession().insert("insertUser", u);
		return rows > 0 ? u.getId() : null;
	}

	@Override
	public List<User> listUser(User query) {
		return getSqlSession().selectList("listUser", query);
	}
	

	public User getUser(User query) {
		return getSqlSession().selectOne("");
				
	}

	@Override
	public Long updateUser(User u) {
		int  rows = getSqlSession().update("updateUser", u);
		return rows > 0 ? u.getId() : null;
	}

	@Override
	public Long deleteUser(User u) {
		int  rows = getSqlSession().delete("deleteUser", u);
		return rows > 0 ? u.getId() : null;
	}

	@Override
	public User selectUserById(Long id) {
		return getSqlSession().selectOne("selectUserById", id);
	}

}
