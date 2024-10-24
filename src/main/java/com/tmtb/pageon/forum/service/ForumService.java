package com.tmtb.pageon.forum.service;
import com.tmtb.pageon.forum.mapper.ForumMapper;
import com.tmtb.pageon.forum.model.ForumVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ForumService {

    @Autowired
    ForumMapper mapper;


//    public List<ForumVO> forumCommunity() {
//        return mapper.forumCommunity();
//    }
}
