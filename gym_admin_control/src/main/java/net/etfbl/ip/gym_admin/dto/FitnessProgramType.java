package net.etfbl.ip.gym_admin.dto;

import java.io.Serializable;

public class FitnessProgramType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FitnessProgramType()
	{
		super();
	}
	
	private Integer id;
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (!id.equals(other.getId()))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();

	    sb.append("ID: ").append(this.id).append("\n");
	    sb.append("Category name: ").append(this.name);
	    return sb.toString();
	}

	
}
