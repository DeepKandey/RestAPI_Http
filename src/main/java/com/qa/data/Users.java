package com.qa.data;

//POJO -plain old java object
public class Users {
	String name;
	String job;
	int id;
	String createdAt;

    public Users() {
		
	}
	
	public Users(String name, String job) {
		this.name=name;
		this.job=job;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
}
