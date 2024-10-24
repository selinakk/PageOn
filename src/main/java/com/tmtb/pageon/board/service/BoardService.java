package com.tmtb.pageon.board.service;
import com.tmtb.pageon.board.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BoardService {

    @Autowired
    BoardMapper mapper;


}
