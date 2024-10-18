package com.tmtb.pageon.webtoon.service;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.webtoon.mapper.WebtoonMapper;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebtoonService {

    @Autowired
    private WebtoonMapper webtoonMapper;

    public List<WebtoonVO> getWebtoonList(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return webtoonMapper.getWebtoonList(offset, pageSize);
    }

    public List<WebtoonVO> searchWebtoonByTitle(String searchWord, int offset, int pageSize) {
        return webtoonMapper.searchWebtoonByTitle(searchWord, offset, pageSize);
    }
    public List<WebtoonVO> searchWebtoonWriter(String searchWord, int offset, int pageSize) {
        return webtoonMapper.searchWebtoonWriter(searchWord, offset, pageSize);
    }
    public int getTotalCountByTitle(String searchWord) {
        return webtoonMapper.getTotalCountByTitle(searchWord);
    }
    public int getTotalCountByContent(String searchWord) {
        return webtoonMapper.getTotalCountByContent(searchWord);
    }

    public int getTotalCount() {
        return webtoonMapper.getTotalCount();
    }

    public WebtoonVO selectOne(WebtoonVO vo) {
        return webtoonMapper.selectOne(vo);
    }



}
