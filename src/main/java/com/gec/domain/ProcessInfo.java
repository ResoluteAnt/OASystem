package com.gec.domain;

import org.activiti.engine.repository.ProcessDefinition;

public class ProcessInfo {
	private String id;
	private String deploymentId;
	private String description;
	private String key;
	private int version;
	
	public ProcessInfo(ProcessDefinition def){
		this.id = def.getId();
		this.deploymentId = def.getDeploymentId();
		this.description = def.getDescription();
		this.key = def.getKey();
		this.version = def.getVersion();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "ProcessInfo [id=" + id + ", deploymentId=" + deploymentId + ", description=" + description + ", key="
				+ key + ", version=" + version + "]";
	}
	
}
