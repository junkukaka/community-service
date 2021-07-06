package com.community.aspn.authority.service;


import com.community.aspn.pojo.sys.Authority;
import com.community.aspn.pojo.sys.Department;

import java.util.List;
import java.util.Map;

public interface AuthorityService {
    //Department
    List<Map<String,Object>> getAllDepartment();
    void insertDepartment(Department department);
    int deleteDepartment(String id);
    void updateDepartment(Department department);

    //Authority
    List<Authority> getAllAuthority();
    void insertAuthority(Authority authority);
    int deleteAuthority(Integer id);
    void updateAuthority(Authority authority);

    //Authority item
    List<Map<String,Object>> insertAuthorityItem(List<Integer> menus, Integer aId, Integer memberId,String flag);
    List<Map<String,Object>> getAuthorityItems(Integer aId, String flag);
    void deleteAuthorityItem(Integer id,String flag);
    void updateAuthorityItem(Integer id,String flag,Integer viewYn,Integer editYn,Integer memberId);

}
