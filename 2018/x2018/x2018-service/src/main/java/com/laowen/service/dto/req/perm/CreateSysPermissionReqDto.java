package com.laowen.service.dto.req.perm;

import com.laowen.service.dto.base.SysPermissionBase;

import java.util.Date;

public class CreateSysPermissionReqDto extends SysPermissionBase {

    private static final long serialVersionUID = -266199995956589020L;

    // create_time
    private Date createTime;

    // create_user
    private String createUser;

    /**
     * create_time
     *
     * @return
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * create_time
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * create_user
     *
     * @return
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * create_user
     *
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

}
