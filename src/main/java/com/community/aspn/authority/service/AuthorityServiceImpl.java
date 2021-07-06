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
import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public List<Map<String,Object>> getAllDepartment() {
        return authorityMapper.getDepartments();
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
        authority.setRegisterTime(new Date());
        authority.setUpdateTime(new Date());
        authority.setRegisterId(authority.getUpdateId());
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
        List<Integer> notExistsMenus = this.getNotExistsMenus(menus, aId, flag);
        for (int i = 0; i < notExistsMenus.size(); i++) {
            Object obj = this.setAuthorityCommunity(aId, memberId, notExistsMenus.get(i),flag);
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
     * @Description // delete authority item by id
     * @Date 10:18 2021/6/25
     * @Param [id, flag]
     * @return void
     **/
    @Override
    public void deleteAuthorityItem(Integer id,String flag) {
        if("C".equals(flag)){
            authorityCommunityMapper.deleteById(id);
        }else {
            authorityWikiMapper.deleteById(id);
        }
    }

    /**
     * @Author nanguangjun
     * @Description // authority item 권한 수정
     * @Date 16:25 2021/6/25
     * @Param [id, flag, viewYn, editYn, memberId]
     * @return void
     **/
    @Override
    public void updateAuthorityItem(Integer id, String flag, Integer viewYn, Integer editYn,Integer memberId) {
        if("C".equals(flag)){
            AuthorityCommunity authorityCommunity =
                    (AuthorityCommunity) this.setUpdateAuthorityItem(id, flag, viewYn, editYn, memberId);
            authorityCommunityMapper.updateById(authorityCommunity);
        }else {
            AuthorityWiki authorityWiki =
                    (AuthorityWiki) this.setUpdateAuthorityItem(id, flag, viewYn, editYn, memberId);
            authorityWikiMapper.updateById(authorityWiki);
        }
    }

    /**
     * @Author nanguangjun
     * @Description // get not exist menuId by aId, when insert authority item table
     * @Date 8:54 2021/6/25
     * @Param [menus, authority Master id]
     * @return java.util.List<java.lang.Integer>
     **/
    private List<Integer> getNotExistsMenus(List<Integer> menus, Integer aId,String flag){
        List<Integer> result = new ArrayList<>();
        if("C".equals(flag)){
            List<Integer> communityList = new ArrayList<>();
            List<AuthorityCommunity> communities =
                    authorityCommunityMapper.selectList(new QueryWrapper<AuthorityCommunity>().eq("a_id", aId));
            for (int i = 0; i < communities.size(); i++) {
                 communityList.add(communities.get(i).getMenuId());
            }
            communityList.removeAll(menus); //差集
            menus.addAll(communityList); //无重复并集
        }else {
            List<Integer> wikiList = new ArrayList<>();
            List<AuthorityWiki> wikis =
                    authorityWikiMapper.selectList(new QueryWrapper<AuthorityWiki>().eq("a_id", aId));
            for (int i = 0; i < wikis.size(); i++) {
                wikiList.add(wikis.get(i).getMenuId());
            }
            wikiList.removeAll(menus); //差集
            menus.addAll(wikiList); //无重复并集
        }
        return menus;
    }

    private Object setUpdateAuthorityItem(Integer id, String flag, Integer viewYn, Integer editYn,Integer memberId){
        if("C".equals(flag)){
            AuthorityCommunity authorityCommunity = new AuthorityCommunity();
            authorityCommunity.setId(id);
            authorityCommunity.setViewYn(viewYn);
            authorityCommunity.setEditYn(editYn);
            authorityCommunity.setUpdateTime(new Date());
            authorityCommunity.setUpdateId(memberId);
            return authorityCommunity;
        }else {
            AuthorityWiki authorityWiki = new AuthorityWiki();
            authorityWiki.setId(id);
            authorityWiki.setViewYn(viewYn);
            authorityWiki.setEditYn(editYn);
            authorityWiki.setUpdateTime(new Date());
            authorityWiki.setUpdateId(memberId);
            return authorityWiki;
        }
    };

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
