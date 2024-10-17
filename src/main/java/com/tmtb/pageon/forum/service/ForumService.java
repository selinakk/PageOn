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
    public int getListCount(){
        return mapper.getListCount();
    }
    public List<ForumVO> searchForum(String searchKey, String searchTerm, int page, int size){
        return mapper.searchForum(searchKey, searchTerm, page, size);
    }
    public int searchForumCount(String searchKey, String searchWord){
        return mapper.searchForumCount(searchKey, searchWord);
    }
    public ForumVO selectOne(ForumVO vo){
        return mapper.selectOne(vo);
    }
    public boolean insertForumOK(ForumVO vo){
        return mapper.insertForumOK(vo);
    }

    public boolean updateForumOK(ForumVO vo) {return mapper.updateForumOK(vo);}

    public boolean deleteForumOK(ForumVO vo) {return mapper.deleteForumOK(vo);}
}
