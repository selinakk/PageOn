package com.tmtb.pageon.board.service;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    public List<BoardVO> findAll() {
        return boardMapper.findAll();
    }
}
