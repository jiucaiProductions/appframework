package test.dao;

import java.util.List;

import test.domain.User;


public interface UserDao {

	public List<User> listUser(User query);
	
	public Long insertUser(User u);
	
	public Long updateUser(User u);
	
	public Long deleteUser(User u);
	
	public User selectUserById(Long id);
}
