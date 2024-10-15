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

    public List<ForumVO> selectAll() {
        return mapper.selectAll();
    }
}
