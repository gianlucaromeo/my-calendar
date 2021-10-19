package mycalendar.model;

import lombok.NonNull;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class User implements Serializable {

	private static final long serialVersionUID = 5831807669489821229L;

	@Getter
	@Setter
	private String firstName;
	
	@Getter
	@Setter
	private String lastName;
	
	@Getter
	@Setter
	private String username;
	
	@Getter
	@Setter
	private String password;
	
	@Getter
	@Setter
	private String email;
	
	
	public User(@NonNull String firstName, @NonNull String lastName, @NonNull String username, @NonNull String password, @NonNull String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
} // class User
