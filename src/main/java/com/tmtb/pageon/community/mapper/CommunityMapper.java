package com.tmtb.pageon.community.mapper;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.notice.model.NoticeVO;
import com.tmtb.pageon.forum.model.ForumVO;
import com.tmtb.pageon.user.model.ReviewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CommunityMapper {

    public List<NoticeVO> noticeCommunity();

    public List<BoardVO> boardCommunity();

    public List<ReviewVO> reviewCommunity();

    public List<ForumVO> forumCommunity();

    public BoardVO boardSelectOne(BoardVO vo);

    public ReviewVO reviewSelectOne(ReviewVO vo);

    public ForumVO forumSelectOne(ForumVO vo);

    public List<NoticeVO> noticeSelectList();

    public List<BoardVO> boardSelectList();

    public List<ReviewVO> reviewSelectList();

    public List<ForumVO> forumSelectList();
}


