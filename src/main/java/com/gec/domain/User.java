package com.gec.domain;

import java.util.HashSet;
import java.util.Set;

public class User {
	private String id;
	private String username;
	private String password;
	private String nickName;
	private String email;
	private String no;
	private String createDate;
	private Dept dept;
	private Set<Role> roles;
	private String roleName;
	private String deptId;
	private String deptName;
	private String role_id;

	public User() { }
	public User(String id, String username, Dept dept, Role role) {
		this.id = id;
		this.username = username;
		this.dept = dept;
		roles = new HashSet();
		roles.add(role);
	}
	public String getDeptName(){
		if ( dept!=null ){
			return this.dept.getDeptName();
		}
		return null;
	}

	public Role getRole(){
		if (roles == null || roles.size() == 0
				|| roles.isEmpty()){
			return null;
		}
		return roles.iterator().next();
	}
	public String getRoleName(){
		if ( roles == null || roles.size() == 0
				|| roles.isEmpty()){
			return null;
		}
		return roles.iterator()
				.next().getRoleDesc();
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}

	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", nickName='" + nickName + '\'' +
				", email='" + email + '\'' +
				", no='" + no + '\'' +
				", createDate='" + createDate + '\'' +
				", dept=" + dept +
				", roles=" + roles +
				", roleName='" + roleName + '\'' +
				", deptId='" + deptId + '\'' +
				", deptName='" + deptName + '\'' +
				", role_id='" + role_id + '\'' +
				'}';
	}
}
