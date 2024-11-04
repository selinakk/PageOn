package com.tmtb.pageon.forum.service;

import com.tmtb.pageon.forum.mapper.ForumMapper;
import com.tmtb.pageon.forum.model.ForumVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ForumService {
    @Autowired
    ForumMapper mapper;

    public List<ForumVO> getList(int page, int size, String sortField, String sortDir) {
        int offset = (page - 1) * size;
        return mapper.getList(offset, size, sortField, sortDir);
    }
    public int getListCnt(){
        return mapper.getListCnt();
    }
    public List<ForumVO> searchForum(String searchKey, String searchTerm, int page, int size){
        int offset = (page - 1) * size;
        return mapper.searchForum(searchKey, searchTerm, offset, size);
    }
    public int searchForumCnt(String searchKey, String searchWord){
        return mapper.searchForumCnt(searchKey, searchWord);
    }
    public ForumVO selectOne(ForumVO vo){
        return mapper.selectOne(vo);
    }
    public String getWorkTitle(int item_id) {return mapper.getWorkTitle(item_id);}
    //
    public boolean insertForumOK(ForumVO vo){return mapper.insertForumOK(vo);}
    public boolean updateForumOK(ForumVO vo) {return mapper.updateForumOK(vo);}
    public boolean deleteForumOK(ForumVO vo) {return mapper.deleteForumOK(vo);}
    public void reportForum(int num) {mapper.reportForumOK(num);}
    public void increaseForumHit(int num) {mapper.increaseForumHit(num);}
}
