package com.community.aspn.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.authority.mapper.AuthorityCommunityMapper;
import com.community.aspn.authority.mapper.AuthorityMapper;
import com.community.aspn.authority.mapper.AuthorityWikiMapper;
import com.community.aspn.authority.mapper.DepartmentMapper;
import com.community.aspn.member.mapper.MemberMapper;
import com.community.aspn.pojo.member.Member;
import com.community.aspn.pojo.sys.Authority;
import com.community.aspn.pojo.sys.AuthorityCommunity;
import com.community.aspn.pojo.sys.AuthorityWiki;
import com.community.aspn.pojo.sys.Department;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AuthorityServiceImpl implements AuthorityService {


    @Resource
    DepartmentMapper departmentMapper;

    @Resource
    MemberMapper memberMapper;

    @Resource
    AuthorityMapper authorityMapper;

    @Resource
    AuthorityCommunityMapper authorityCommunityMapper;

    @Resource
    AuthorityWikiMapper authorityWikiMapper;

    @Override
    public List<Department> getAllDepartment() {
        return departmentMapper.selectList(null);
    }

    @Override
    public void insertDepartment(Department department) {
        departmentMapper.insert(department);
    }

    @Override
    public int deleteDepartment(String id) {
        Department department = departmentMapper.selectById(id);
        Integer hasYn = memberMapper.selectCount(new QueryWrapper<Member>().eq("department", department.getId()));
        if(hasYn>0){
            return 0;
        }else {
            departmentMapper.deleteById(id);
            return 1;
        }
    }

    @Override
    public void updateDepartment(Department department) {
        departmentMapper.updateById(department);
    }


    @Override
    public List<Authority> getAllAuthority() {
        return authorityMapper.selectList(null);
    }

    @Override
    public void insertAuthority(Authority authority) {
        authorityMapper.insert(authority);
    }

    @Override
    public int deleteAuthority(Integer id) {
        return authorityMapper.deleteById(id);
    }

    @Override
    public void updateAuthority(Authority authority) {
        authorityMapper.updateById(authority);
    }

    /**
     * @Author nanguangjun
     * @Description // insert Authority item
     * @Date 10:24 2021/6/24
     * @Param [menus, aId]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> insertAuthorityItem(List<Integer> menus, Integer aId, Integer memberId,String flag) {
        for (int i = 0; i < menus.size(); i++) {
            Object obj = this.setAuthorityCommunity(aId, memberId, menus.get(i),flag);
            if("C".equals(flag)){ //community insert
                authorityCommunityMapper.insert((AuthorityCommunity) obj);
            }else { //wiki insert
                authorityWikiMapper.insert((AuthorityWiki) obj);
            }
        }
        return authorityMapper.getAuthorityCommunityList(aId);
    }

    /**
     * @Author nanguangjun
     * @Description //
     * @Date 15:03 2021/6/24
     * @Param [aId, flag]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> getAuthorityItems(Integer aId, String flag) {
        if("C".equals(flag)){
            return authorityMapper.getAuthorityCommunityList(aId);
        }else {
            return authorityMapper.getAuthorityWikiList(aId);
        }
    }

    /**
     * @Author nanguangjun
     * @Description // set AuthorityItem
     * @Date 10:55 2021/6/24
     * @Param [aId, memberId, menuId]
     * @return com.community.aspn.pojo.sys.AuthorityCommunity
     **/
    private Object setAuthorityCommunity(Integer aId,Integer memberId,Integer menuId,String flag){
        if ("C".equals(flag)){
            AuthorityCommunity authorityCommunity = new AuthorityCommunity();
            authorityCommunity.setAId(aId);
            authorityCommunity.setMenuId(menuId);
            authorityCommunity.setEditYn(1);
            authorityCommunity.setViewYn(1);
            authorityCommunity.setRegisterTime(new Date());
            authorityCommunity.setRegisterId(memberId);
            authorityCommunity.setUpdateTime(new Date());
            authorityCommunity.setUpdateId(memberId);
            return authorityCommunity;
        }else {
            AuthorityWiki authorityWiki = new AuthorityWiki();
            authorityWiki.setAId(aId);
            authorityWiki.setMenuId(menuId);
            authorityWiki.setEditYn(1);
            authorityWiki.setViewYn(1);
            authorityWiki.setRegisterTime(new Date());
            authorityWiki.setRegisterId(memberId);
            authorityWiki.setUpdateTime(new Date());
            authorityWiki.setUpdateId(memberId);
            return authorityWiki;
        }


    }
}
