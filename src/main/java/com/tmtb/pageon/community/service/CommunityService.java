package com.tmtb.pageon.community.service;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.notice.model.NoticeVO;

import com.tmtb.pageon.community.mapper.CommunityMapper;
import com.tmtb.pageon.forum.model.ForumVO;
import com.tmtb.pageon.user.model.ReviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class CommunityService {

    @Autowired
    CommunityMapper mapper;

    public List<BoardVO> boardCommunity() {
        return mapper.boardCommunity();
    }

    public List<NoticeVO> noticeCommunity() {
        return mapper.noticeCommunity();
    }

        public List<ReviewVO> reviewCommunity() {
        return mapper.reviewCommunity();
    }

    public List<ForumVO> forumCommunity() {
        return mapper.forumCommunity();
    }


    public BoardVO boardSelectOne(BoardVO vo) {
        return mapper.boardSelectOne(vo);
    }

    public ReviewVO reviewSelectOne(ReviewVO vo) {
        return mapper.reviewSelectOne(vo);
    }

    public ForumVO forumSelectOne(ForumVO vo) {
        return mapper.forumSelectOne(vo);
    }

    public List<BoardVO> boardSelectList() {
        return mapper.boardSelectList();
    }

    public List<NoticeVO> noticeSelectList() {
        return mapper.noticeSelectList();
    }

    public List<ReviewVO> reviewSelectList() {
        return mapper.reviewSelectList();
       }

    public List<ForumVO> forumSelectList() {
        return mapper.forumSelectList();
    }


}
