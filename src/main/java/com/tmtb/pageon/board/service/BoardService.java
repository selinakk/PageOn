package com.tmtb.pageon.board.service;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.board.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    public List<BoardVO> selectFreeAll() {
        return boardMapper.selectFreeAll();
    }

    public List<BoardVO> selectQnaAll() {
        return boardMapper.selectQnaAll();
    }


    public int insertOK(BoardVO vo) {
        return boardMapper.insertOK(vo);
    }

    public BoardVO selectOne(BoardVO vo) {
        return boardMapper.selectOne(vo);
    }

    public int deleteOK(BoardVO vo) {
        return boardMapper.deleteOK(vo);
    }

    public int updateBoardHitCount(BoardVO vo) {
        return boardMapper.updateBoardHitCount(vo);
    }

    public int updateBoard(BoardVO vo) {
        return boardMapper.updateOK(vo);
    }

}
