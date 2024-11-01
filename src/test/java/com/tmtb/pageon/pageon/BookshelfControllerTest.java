package com.tmtb.pageon.pageon;

import com.tmtb.pageon.bookshelf.service.BookshelfService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookshelfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookshelfService service;

    @Test
    @DisplayName("서재 추가 성공 테스트")
    void insertBookshelf_Success() throws Exception {
        // given
        String userId = "tester2";
        String sort = "reading";
        int workNum = 1;

        when(service.insertBookshelfOK(userId, sort, workNum)).thenReturn(true);

        // when & then
        mockMvc.perform(post("/bookshelf/insertOK")
                        .param("user_id", userId)
                        .param("sort", sort)
                        .param("work_num", String.valueOf(workNum)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookshelf/list?userId=tester2"))
                .andExpect(flash().attribute("successMsg", "서재에 추가되었습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("서재 추가 실패 테스트")
    void insertBookshelf_Failure() throws Exception {
        // given
        String userId = "tester2";
        String sort = "reading";
        int workNum = 1;

        when(service.insertBookshelfOK(userId, sort, workNum)).thenReturn(false);

        // when & then
        mockMvc.perform(post("/bookshelf/insertOK")
                        .param("user_id", userId)
                        .param("sort", sort)
                        .param("work_num", String.valueOf(workNum)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookshelf/list?userId=tester2"))
                .andExpect(flash().attribute("errorMsg", "다시 시도해 주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("서재 중복 등록 테스트")
    void insertBookshelf_Duplicate() throws Exception {
        // given
        String userId = "tester2";
        String sort = "reading";
        int workNum = 1;

        when(service.insertBookshelfOK(userId, sort, workNum))
                .thenThrow(new DuplicateKeyException("Duplicate Entry"));

        // when & then
        mockMvc.perform(post("/bookshelf/insertOK")
                        .param("user_id", userId)
                        .param("sort", sort)
                        .param("work_num", String.valueOf(workNum)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookshelf/list?userId=tester2"))
                .andExpect(flash().attribute("errorMsg", "이미 서재에 등록된 작품입니다."))
                .andDo(print());
    }
}