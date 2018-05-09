package com.x2016.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.x2016.poco.SysRole;

public interface SysRoleMapper {

  SysRole findById(String roleId);

  List<SysRole> findList(SysRole record);
  
  /**
   * 通过用户id查询角色list
   * @param userId
   * @return
   */
  List<SysRole> findListByUserId(@Param("userId") String userId);

  int save(SysRole record);

  int editById(SysRole record);
}
