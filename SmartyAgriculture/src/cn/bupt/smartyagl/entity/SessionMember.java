package cn.bupt.smartyagl.entity;
/**
 * 存入session的用户结构体
 * @author ohaha
 *
 */
public class SessionMember {

    private Integer id;
    private String username;
    private Integer[] roleIds;
    private Integer[] groupIds;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer[] getRoleIds() {
        return roleIds;
    }
    public void setRoleIds(Integer[] roleIds) {
        this.roleIds = roleIds;
    }
    public Integer[] getGroupIds() {
        return groupIds;
    }
    public void setGroupIds(Integer[] groupIds) {
        this.groupIds = groupIds;
    }
}
