package com.tmtb.pageon.community.service;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.notice.model.NoticeVO;

import com.tmtb.pageon.community.mapper.CommunityMapper;
import com.tmtb.pageon.community.model.CommunityVO;
import com.tmtb.pageon.forum.model.ForumVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class CommunityService {

    @Autowired
    CommunityMapper mapper;


    public List<NoticeVO> noticeCommunity() {
        return mapper.noticeCommunity();
    }

    public List<ForumVO> forumCommunity() {
        return mapper.forumCommunity();
    }

//    public List<ReviewVO> reviewCommunity() {
//        return mapper.reviewCommunity();
//    }

    public List<BoardVO> boardCommunity() {
        return mapper.boardCommunity();
    }

    public BoardVO boardSelectOne(BoardVO vo) {
        return mapper.boardSelectOne(vo);
    }

//    public ReviewVO reviewSelectOne(ReviewVO vo) {
//        return mapper.reviewSelectOne(vo);
//    }

    public ForumVO forumSelectOne(ForumVO vo) {
        return mapper.forumSelectOne(vo);
    }

}
