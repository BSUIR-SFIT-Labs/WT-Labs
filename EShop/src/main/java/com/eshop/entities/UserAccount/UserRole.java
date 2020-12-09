package com.eshop.entities.UserAccount;

public class UserRole {
    private int id;
    private int userId;
    private int roleId;

    public UserRole(int id, int userId, int roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRole(int userId, int roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return this.roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
