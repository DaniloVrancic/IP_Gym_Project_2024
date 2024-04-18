package net.etfbl.ip.gym_admin.dto;

import java.io.Serializable;;

public class User implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7249814167430841592L;

	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String city;
	private String avatar;
	private String email;
	private Boolean activated;
	private Integer type;

	public User() {}

	public User(int id, String username, String password, String firstName, String lastName,
			String city, String avatar, String email, Boolean activated, Integer type) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.avatar = avatar;
		this.email = email;
		this.activated = activated;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		User other = (User) obj;
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("=============================").append("\n");
	    sb.append("User ID: ").append(this.id).append("\n");
	    sb.append("Username: ").append(this.username).append("\n");
	    sb.append("Password: ").append(this.password).append("\n");
	    sb.append("First Name: ").append(this.firstName).append("\n");
	    sb.append("Last Name: ").append(this.lastName).append("\n");
	    sb.append("City: ").append(this.city).append("\n");
	    sb.append("Avatar: ").append(this.avatar).append("\n");
	    sb.append("Email: ").append(this.email).append("\n");
	    sb.append("Activated: ").append(this.activated).append("\n");
	    sb.append("Type: ").append(this.type).append("\n");
	    sb.append("=============================");
	    return sb.toString();
	}



}
