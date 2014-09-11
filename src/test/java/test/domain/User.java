package test.domain;

import java.io.Serializable;

/**
 * 
<pre>

 CREATE TABLE user  
 (  
  id int(11) NOT NULL AUTO_INCREMENT,  
   username varchar(200) NOT NULL,  
   password varchar(200) NOT NULL,  
   PRIMARY KEY (id)  
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
INSERT INTO user VALUES ('1', 'admin', 'admin');

</pre>
 * 
 * @author jiucai
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = 2446819586362427075L;

	private Long id;
	private String username;
	private String password;

	public User() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}

}
