package test.dao;

import org.springframework.stereotype.Repository;


@Repository("userMapper")
public interface UserMapper extends UserDao {

	//自动映射到 xml 配置文件  namespace="当前接口类名" 中的 select id="listUser"
	
}
