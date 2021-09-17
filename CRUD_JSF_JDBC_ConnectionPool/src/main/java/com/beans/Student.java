package com.beans;

import java.io.Serializable;


import javax.faces.bean.ManagedBean;

@ManagedBean(name="student")
public class Student implements Serializable{
	
	// Attributes
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4664046745521413861L;
	private int id;
	private String fNname;
	private String lName;
	private String email;
	
	// Constructors
	
	public Student() {
		super();
	}
	
	public Student(int id, String fNname, String lName, String email) {
		super();
		this.id = id;
		this.fNname = fNname;
		this.lName = lName;
		this.email = email;
	}
	
	public Student(String fNname, String lName, String email) {
		super();
		this.fNname = fNname;
		this.lName = lName;
		this.email = email;
	}


	// Getters & Setters

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getfNname() {
		return fNname;
	}


	public void setfNname(String fNname) {
		this.fNname = fNname;
	}


	public String getlName() {
		return lName;
	}


	public void setlName(String lName) {
		this.lName = lName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "Student [id=" + id + ", fNname=" + fNname + ", lName=" + lName + ", email=" + email + "]";
	}
	
}
