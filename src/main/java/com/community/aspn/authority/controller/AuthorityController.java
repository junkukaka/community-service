package com.community.aspn.authority.controller;

import com.community.aspn.authority.service.AuthorityService;
import com.community.aspn.pojo.sys.Authority;
import com.community.aspn.pojo.sys.Department;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Resource
    AuthorityService authorityService;


    /**
     * @Author nanguangjun
     * @Description // get all department
     * @Date 14:04 2021/6/21
     * @Param []
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/getDepartments")
    public AjaxResponse getAllDepartment(){
        List<Department> allDepartment = authorityService.getAllDepartment();
        return AjaxResponse.success(allDepartment);
    }

    /**
     * @Author nanguangjun
     * @Description // insert department
     * @Date 14:20 2021/6/21
     * @Param [department]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/addDepartments")
    public AjaxResponse insertDepartment(@RequestBody Department department){
        try {
            authorityService.insertDepartment(department);
            return AjaxResponse.success();
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }

    /**
     * @Author nanguangjun
     * @Description // edit department
     * @Date 14:35 2021/6/21
     * @Param [department]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PutMapping("/editDepartments")
    public AjaxResponse updateDepartment(@RequestBody  Department department){
        try {
            authorityService.updateDepartment(department);
            return AjaxResponse.success();
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }

    /**
     * @Author nanguangjun
     * @Description // delete department by id
     * @Date 14:35 2021/6/21
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @DeleteMapping("/deleteDepartment/{id}")
    public AjaxResponse deleteDepartment(@PathVariable String id){
        try {
            int result = authorityService.deleteDepartment(id);
            if(result > 0){
                return AjaxResponse.success();
            }else {
                return AjaxResponse.error("사용중의 부서는 삭제 할수 없습니다.");
            }
        }catch (Exception e){
            return AjaxResponse.error(e);
        }
    }


    /**
     * @Author nanguangjun
     * @Description // get all authority
     * @Date 9:29 2021/6/23
     * @Param []
     * @return com.community.aspn.util.AjaxResponse
     **/
    @GetMapping("/getAuthority")
    public AjaxResponse getAllAuthority(){
        List<Authority> allAuthority = authorityService.getAllAuthority();
        return AjaxResponse.success(allAuthority);
    }

    /**
     * @Author nanguangjun
     * @Description // insert authority
     * @Date 9:29 2021/6/23
     * @Param [department]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/addAuthority")
    public AjaxResponse insertAuthority(@RequestBody Authority authority){
        try {
            authorityService.insertAuthority(authority);
            return AjaxResponse.success();
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }

    /**
     * @Author nanguangjun
     * @Description //TODO
     * @Date 9:29 2021/6/23
     * @Param [department]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PutMapping("/editAuthority")
    public AjaxResponse updateAuthority(@RequestBody  Authority authority){
        try {
            authorityService.updateAuthority(authority);
            return AjaxResponse.success();
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }

    /**
     * @Author nanguangjun
     * @Description //  delete authority by id
     * @Date 9:29 2021/6/23
     * @Param [id]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @DeleteMapping("/deleteAuthority/{id}")
    public AjaxResponse deleteAuthority(@PathVariable Integer id){
        try {
            int result = authorityService.deleteAuthority(id);
            if(result > 0){
                return AjaxResponse.success();
            }else {
                return AjaxResponse.error("사용중의 권한을 삭제 할수 없습니다.");
            }
        }catch (Exception e){
            return AjaxResponse.error(e);
        }
    }

    @GetMapping("/getAuthorityItem/{flag}")
    public AjaxResponse getAllAuthorityItem(@PathVariable String flag){
        List<Authority> allAuthority = authorityService.getAllAuthority();
        return AjaxResponse.success(allAuthority);
    }

    /**
     * @Author nanguangjun
     * @Description // insert authority
     * @Date 9:29 2021/6/23
     * @Param [department]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PostMapping("/addAuthorityItem")
    public AjaxResponse insertAuthorityItem(@RequestBody Authority authority){
        try {
            authorityService.insertAuthority(authority);
            return AjaxResponse.success();
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }

    /**
     * @Author nanguangjun
     * @Description //TODO
     * @Date 9:29 2021/6/23
     * @Param [department]
     * @return com.community.aspn.util.AjaxResponse
     **/
    @PutMapping("/editAuthorityItem")
    public AjaxResponse updateAuthorityItem(@RequestBody  Authority authority){
        try {
            authorityService.updateAuthority(authority);
            return AjaxResponse.success();
        }catch (Exception e){
            return AjaxResponse.error();
        }
    }


    @PostMapping("/addItems")
    public AjaxResponse insertAuthorityItem(@RequestBody Map<String,Object> request){
        ArrayList menuList = (ArrayList<Integer>) request.get("menus");
        Integer aId = Integer.parseInt(request.get("aId").toString());
        int memberId = Integer.parseInt(request.get("memberId").toString());
        String flag = request.get("flag").toString();
        authorityService.insertAuthorityItem(menuList,aId,memberId,flag);
        return AjaxResponse.success(request);
    }

    @GetMapping("/getItems")
    public AjaxResponse getAuthorityItems(HttpServletRequest request){
        int aId = Integer.parseInt(request.getParameter("aId"));
        String flag = request.getParameter("flag");
        List<Map<String, Object>> result = authorityService.getAuthorityItems(aId, flag);
        return AjaxResponse.success(result);
    }

    @PostMapping("/deleteItem")
    public AjaxResponse deleteAuthorityItem(@RequestBody Map<String,Object> request){
        int aId = Integer.parseInt(request.get("aId").toString());
        String flag = request.get("flag").toString();
        authorityService.deleteAuthorityItem(aId,flag);
        return AjaxResponse.success();
    }

    @PutMapping("/editItem")
    public AjaxResponse deleteEditItem(@RequestBody Map<String,Object> request){
        try {
            Integer id = Integer.parseInt(request.get("id").toString());
            String flag = request.get("flag").toString();
            Integer viewYn = Integer.parseInt(request.get("viewYn").toString());
            Integer editYn = Integer.parseInt(request.get("editYn").toString());
            Integer memberId = Integer.parseInt(request.get("memberId").toString());
            authorityService.updateAuthorityItem(id,flag,viewYn,editYn,memberId);
            return AjaxResponse.success();
        }catch (Exception e){
            return AjaxResponse.error(e);
        }

    }



}
