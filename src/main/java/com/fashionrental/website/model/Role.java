package com.fashionrental.website.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false, unique = true)
	@NotEmpty
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	public Role() {}

	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public List<User> getUsers() { return users; }

	public void setUsers(List<User> users) { this.users = users; }
	
}
