package com.gec.domain;

public class Permission {

	private String id;
	private String mapping;
	private String permission;
	
	public Permission(){}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMapping() {
		return mapping;
	}
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	@Override
	public String toString() {
		return "Permission [id=" + id + ", mapping=" + mapping + 
				", permission=" + permission + "]";
	}
}
