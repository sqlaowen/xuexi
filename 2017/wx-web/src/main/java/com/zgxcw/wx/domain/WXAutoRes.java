package com.zgxcw.wx.domain;

import java.io.Serializable;

/**
 * Created by huolh on 2016/3/8.
 */
public class WXAutoRes implements Serializable {

  public  WXAutoRes(){}

  private static final long serialVersionUID = 5823062449906911416L;

  private String resId;

  private String wxId;

  private String resName;

  private String resType;

  private Integer resState;

  private Integer disableState;

  private Integer msgType;

  private String msgContent;

  private Long createTime;

  private String createUser;

  private Long updateTime;

  private String updateUser;

  public String getResId() {
    return resId;
  }

  public void setResId(String resId) {
    this.resId = resId == null ? null : resId.trim();
  }

  public String getWxId() {
    return wxId;
  }

  public void setWxId(String wxId) {
    this.wxId = wxId == null ? null : wxId.trim();
  }

  public String getResName() {
    return resName;
  }

  public void setResName(String resName) {
    this.resName = resName == null ? null : resName.trim();
  }


  public String getResType() {
    return resType;
  }

  public void setResType(String resType) {
    this.resType = resType == null ? null : resType.trim();
  }

  public Integer getResState() {
    return resState;
  }

  public void setResState(Integer resState) {
    this.resState = resState;
  }

  public Integer getDisableState() {
    return disableState;
  }

  public void setDisableState(Integer disableState) {
    this.disableState = disableState;
  }

  public Integer getMsgType() {
    return msgType;
  }

  public void setMsgType(Integer msgType) {
    this.msgType = msgType;
  }

  public String getMsgContent() {
    return msgContent;
  }

  public void setMsgContent(String msgContent) {
    this.msgContent = msgContent;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser == null ? null : createUser.trim();
  }

  public Long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Long updateTime) {
    this.updateTime = updateTime;
  }

  public String getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser == null ? null : updateUser.trim();
  }


}
