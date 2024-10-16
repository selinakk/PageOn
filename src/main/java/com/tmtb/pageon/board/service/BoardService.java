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

    public List<BoardVO> getFreeBoardList(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return boardMapper.getFreeBoardList(offset, pageSize);
    }

    public List<BoardVO> getQnaBoardList(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return boardMapper.getQnaBoardList(offset, pageSize);
    }

    public List<BoardVO> getFreeBoardListByHitCount(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return boardMapper.getFreeBoardListByHitCount(offset, pageSize);
    }

    public List<BoardVO> getQnaBoardListByHitCount(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return boardMapper.getQnaBoardListByHitCount(offset, pageSize);
    }


    public int getTotalCount() {
        return boardMapper.getTotalCount();
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

    //검색 관련
    public List<BoardVO> searchBoardByTitle(String searchWord, String category, int offset, int pageSize) {
        return boardMapper.searchBoardByTitle(searchWord, category, offset, pageSize);
    }
    public List<BoardVO> searchBoardByContent(String searchWord, String category, int offset, int pageSize) {
        return boardMapper.searchBoardByContent(searchWord, category, offset, pageSize);
    }
    public int getTotalCountByTitle(String searchWord, String category) {
        return boardMapper.getTotalCountByTitle(searchWord, category);
    }
    public int getTotalCountByContent(String searchWord, String category) {
        return boardMapper.getTotalCountByContent(searchWord, category);
    }

//    public List<BoardVO> getBoardList(int page, int pageSize) {
//        int offset = (page - 1) * pageSize;
//        return boardMapper.getBoardList(offset, pageSize);
//    }





}
