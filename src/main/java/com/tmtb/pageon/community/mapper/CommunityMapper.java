package com.tmtb.pageon.community.mapper;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.notice.model.NoticeVO;
//import com.tmtb.pageon.review.model.ReviewVO;
import com.tmtb.pageon.community.model.CommunityVO;
import com.tmtb.pageon.forum.model.ForumVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CommunityMapper {


    public List<NoticeVO> noticeCommunity();

    public List<ForumVO> forumCommunity();

    //public List<ReviewVO> reviewCommunity();

    public List<BoardVO> boardCommunity();

    //public ReviewVO reviewSelectOne(ReviewVO vo);

    public ForumVO forumSelectOne(ForumVO vo);

    public BoardVO boardSelectOne(BoardVO vo);
}


