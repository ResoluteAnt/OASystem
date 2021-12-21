package com.gec.domain;

/*
 * ProcessConfig 类
 * 流程配置类
 * ps: 用在布署流程时, 装载数据。
 *     最好用反向工程生成。
 */
public class ProcessConfig
		implements java.io.Serializable {

	private String id;
	private String deplomentId;
	private String procDefId;
	private String procCategory;
	private Integer version;
	private String procKey;
	private String note;
	private String createDate;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeplomentId() {
		return deplomentId;
	}
	public void setDeplomentId(String deplomentId) {
		this.deplomentId = deplomentId;
	}
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getProcCategory() {
		return procCategory;
	}
	public void setProcCategory(String procCategory) {
		this.procCategory = procCategory;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getProcKey() {
		return procKey;
	}
	public void setProcKey(String procKey) {
		this.procKey = procKey;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "ProcessConfig [id=" + id + ", deplomentId=" + deplomentId + ", procDefId=" + procDefId
				+ ", procCategory=" + procCategory + ", version=" + version + ", procKey=" + procKey + ", note=" + note
				+ ", createDate=" + createDate + "]";
	}

}
