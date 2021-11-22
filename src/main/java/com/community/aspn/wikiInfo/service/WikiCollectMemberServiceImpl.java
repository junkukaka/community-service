package com.community.aspn.wikiInfo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.community.aspn.pojo.wiki.WikiCollect;
import com.community.aspn.pojo.wiki.WikiMemberCollectMenu;
import com.community.aspn.wikiInfo.mapper.WikiCollectMapper;
import com.community.aspn.wikiInfo.mapper.WikiMemberCollectMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WikiCollectMemberServiceImpl implements WikiCollectMemberService{

    @Resource
    WikiMemberCollectMenuMapper wikiMemberCollectMenuMapper;

    @Resource
    WikiCollectMapper wikiCollectMapper;

    /**
     * @Author nanguangjun
     * @Description // 위키즐겨찾기 메뉴 저장
     * @Date 14:35 2021/11/17
     * @Param [wikiMemberCollectMenu]
     * @return void
     **/
    @Override
    public void saveCollectWikiMemberCollectMenu(WikiMemberCollectMenu wikiMemberCollectMenu) {
        wikiMemberCollectMenu.setUpdateId(wikiMemberCollectMenu.getMemberId());
        wikiMemberCollectMenu.setUpdateTime(new Date());
        wikiMemberCollectMenuMapper.insert(wikiMemberCollectMenu);
    }

    /**
     * @Author nanguangjun
     * @Description // 위키 즐겨찾기 메뉴수정
     * @Date 14:35 2021/11/17
     * @Param [wikiMemberCollectMenu]
     * @return void
     **/
    @Override
    public void updateCollectWikiMemberCollectMenu(WikiMemberCollectMenu wikiMemberCollectMenu) {
        wikiMemberCollectMenu.setUpdateTime(new Date());
        wikiMemberCollectMenuMapper.updateById(wikiMemberCollectMenu);
    }

    /**
     * @Author nanguangjun
     * @Description // 위키 즐겨찾기 메뉴 삭제
     * @Date 14:35 2021/11/17
     * @Param [id]
     * @return void
     **/
    @Override
    public void deleteCollectWikiMemberCollectMenu(Integer id) {
        //1.delete wiki collect
        QueryWrapper<WikiCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collect_menu_id",id);
        wikiCollectMapper.delete(queryWrapper);
        //2.delete wiki collect menu
        wikiMemberCollectMenuMapper.deleteById(id);
    }

    /**
     * @Author nanguangjun
     * @Description // 위키 즐겨찾기 메뉴
     * @Date 14:34 2021/11/17
     * @Param [memberId]
     * @return java.util.List<com.community.aspn.pojo.wiki.WikiMemberCollectMenu>
     **/
    @Override
    public List<WikiMemberCollectMenu> selectCollectWikiMemberCollectMenu(Integer memberId) {
        QueryWrapper<WikiMemberCollectMenu> wikiMemberCollectMenuQueryWrapper = new QueryWrapper<>();
        wikiMemberCollectMenuQueryWrapper.eq("member_id",memberId);
        List<WikiMemberCollectMenu> wikiMemberCollectMenus = wikiMemberCollectMenuMapper.selectList(wikiMemberCollectMenuQueryWrapper);
        return wikiMemberCollectMenus;
    }

    /**
     * @Author nanguangjun
     * @Description // 위키 즐겨찾기 저장
     * @Date 14:34 2021/11/17
     * @Param [wikiCollect]
     * @return void
     **/
    @Override
    public void saveWikiCollect(WikiCollect wikiCollect) {
        wikiCollect.setUpdateTime(new Date());
        QueryWrapper<WikiCollect> queryWrapper = new QueryWrapper<WikiCollect>().eq("wiki_id", wikiCollect.getWikiId())
                .eq("member_id", wikiCollect.getMemberId());
        Integer cnt = wikiCollectMapper.selectCount(queryWrapper);
        if(cnt > 0 ){
            wikiCollectMapper.update(wikiCollect,queryWrapper);
        }else {
            wikiCollectMapper.insert(wikiCollect);
        }

    }

    /**
     * @Author nanguangjun
     * @Description // collect 메뉴별고 위키 조회하기
     * @Date 14:49 2021/11/18
     * @Param [id]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> selectWikiCollect(Integer id) {
        List<Map<String, Object>> result = wikiCollectMapper.selectWikiCollect(id);
        return result;
    }

    /**
     * @Author nanguangjun
     * @Description // 전체 회원별 즐겨찾기
     * @Date 9:49 2021/11/19
     * @Param [id]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> selectAllWikiCollect(Integer memberId) {
        return wikiCollectMapper.selectAllWikiCollect(memberId);
    }

    /**
     * @Author nanguangjun
     * @Description // 위키 즐겨찾기 삭제하기
     * @Date 16:07 2021/11/18
     * @Param [params]
     * @return void
     **/
    @Override
    public void deleteWikiCollect(Map<String, Integer> params) {
        QueryWrapper<WikiCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wiki_id",params.get("wikiId")).eq("member_id",params.get("memberId"));
        wikiCollectMapper.delete(queryWrapper);
    }
}
