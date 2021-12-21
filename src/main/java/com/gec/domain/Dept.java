package com.gec.domain;

public class Dept {

	private String id;
	private String parentId;
	private String pids;
	private String supDeptName;
	private String deptName;
	private String deptDesc;
	private String hasSub;   //是否有下一级
	private String level;

	public Dept(){}
	public Dept(String id, String deptName) {
		this.id = id;
		this.deptName = deptName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getPids() {
		return pids;
	}
	public void setPids(String pids) {
		this.pids = pids;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptDesc() {
		return deptDesc;
	}
	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}
	public String getHasSub() {
		return hasSub;
	}
	public void setHasSub(String hasSub) {
		this.hasSub = hasSub;
	}

	public String getSupDeptName() {
		return supDeptName;
	}

	public void setSupDeptName(String supDeptName) {
		this.supDeptName = supDeptName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Dept{" +
				"id='" + id + '\'' +
				", parentId='" + parentId + '\'' +
				", pids='" + pids + '\'' +
				", supDeptName='" + supDeptName + '\'' +
				", deptName='" + deptName + '\'' +
				", deptDesc='" + deptDesc + '\'' +
				", hasSub='" + hasSub + '\'' +
				", level='" + level + '\'' +
				'}';
	}
}
