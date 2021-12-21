package com.gec.domain;

/**
 * @Author : JingJie
 * @Date : 2021/12/12 20:32
 * @Description : com.gec.domain
 * @Version : 1.0
 */
public class Menu {
    private String id;
    private String parentId;
    private String parIds;
    private String menuName;
    private String note;
    private String mapping;
    private String visible;
    private String permission;
    public Menu(){}

    public Menu(String id, String parentId,
                String parIds, String menuName,
                String note, String mapping,
                String visible, String permission) {
        this.id = id;
        this.parentId = parentId;
        this.parIds = parIds;
        this.menuName = menuName;
        this.note = note;
        this.mapping = mapping;
        this.visible = visible;
        this.permission = permission;
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

    public String getParIds() {
        return parIds;
    }

    public void setParIds(String parIds) {
        this.parIds = parIds;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", note='" + note + '\'' +
                ", mapping='" + mapping + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}
