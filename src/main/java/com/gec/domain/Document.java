package com.gec.domain;

import java.util.Date;

public class Document {
    private String id;

    private String documentname;

    private String uploader;

    private String filename;

    private String filesize;

    private Integer downtimes;

    private Date createdate;

    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDocumentname() {
        return documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname == null ? null : documentname.trim();
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader == null ? null : uploader.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize == null ? null : filesize.trim();
    }

    public Integer getDowntimes() {
        return downtimes;
    }

    public void setDowntimes(Integer downtimes) {
        this.downtimes = downtimes;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    @Override
    public String toString() {
        return "Document{" +
                "id='" + id + '\'' +
                ", documentname='" + documentname + '\'' +
                ", uploader='" + uploader + '\'' +
                ", filename='" + filename + '\'' +
                ", filesize='" + filesize + '\'' +
                ", downtimes=" + downtimes +
                ", createdate=" + createdate +
                ", note='" + note + '\'' +
                '}';
    }
}