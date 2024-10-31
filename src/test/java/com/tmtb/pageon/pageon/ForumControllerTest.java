package com.tmtb.pageon.pageon;

import com.tmtb.pageon.forum.model.ForumVO;
import com.tmtb.pageon.forum.service.ForumService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ForumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForumService service;

    @Test
    @DisplayName("기본 파라미터로 포럼 목록 조회 테스트")
    void forumList_DefaultParameters() throws Exception {
        // given
        List<ForumVO> mockList = Arrays.asList(
                new ForumVO(),  // 실제 VO 필드에 맞게 데이터 설정 필요
                new ForumVO(),
                new ForumVO(),
                new ForumVO()
        );

        when(service.getList(1, 4, "wdate", "desc")).thenReturn(mockList);
        when(service.getListCnt()).thenReturn(10);

        // when & then
        mockMvc.perform(get("/forum/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("forum/list"))
                .andExpect(model().attributeExists("list"))
                .andExpect(model().attribute("currentPage", 1))
                .andExpect(model().attribute("totalPages", 3))
                .andExpect(model().attribute("totalList", 10))
                .andExpect(model().attribute("sortField", "wdate"))
                .andExpect(model().attribute("sortDir", "desc"))
                .andExpect(model().attribute("reverseSortDir", "asc"))
                .andDo(print());
    }

    @Test
    @DisplayName("사용자 지정 파라미터로 포럼 목록 조회 테스트")
    void forumList_CustomParameters() throws Exception {
        // given
        List<ForumVO> mockList = Arrays.asList(
                new ForumVO(),  // 실제 VO 필드에 맞게 데이터 설정 필요
                new ForumVO(),
                new ForumVO()
        );

        when(service.getList(2, 3, "title", "asc")).thenReturn(mockList);
        when(service.getListCnt()).thenReturn(8);

        // when & then
        mockMvc.perform(get("/forum/list")
                        .param("page", "2")
                        .param("size", "3")
                        .param("sortField", "title")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(view().name("forum/list"))
                .andExpect(model().attributeExists("list"))
                .andExpect(model().attribute("currentPage", 2))
                .andExpect(model().attribute("totalPages", 3))
                .andExpect(model().attribute("totalList", 8))
                .andExpect(model().attribute("sortField", "title"))
                .andExpect(model().attribute("sortDir", "asc"))
                .andExpect(model().attribute("reverseSortDir", "desc"))
                .andDo(print());
    }

    @Test
    @DisplayName("마지막 페이지 조회 테스트")
    void forumList_LastPage() throws Exception {
        // given
        List<ForumVO> mockList = Arrays.asList(
                new ForumVO(),  // 실제 VO 필드에 맞게 데이터 설정 필요
                new ForumVO()
        );

        when(service.getList(3, 4, "wdate", "desc")).thenReturn(mockList);
        when(service.getListCnt()).thenReturn(10);

        // when & then
        mockMvc.perform(get("/forum/list")
                        .param("page", "3"))
                .andExpect(status().isOk())
                .andExpect(view().name("forum/list"))
                .andExpect(model().attributeExists("list"))
                .andExpect(model().attribute("currentPage", 3))
                .andExpect(model().attribute("totalPages", 3))
                .andExpect(model().attribute("totalList", 10))
                .andDo(print());
    }

    @Test
    @DisplayName("빈 목록 조회 테스트")
    void forumList_EmptyList() throws Exception {
        // given
        List<ForumVO> mockList = Collections.emptyList();

        when(service.getList(1, 4, "wdate", "desc")).thenReturn(mockList);
        when(service.getListCnt()).thenReturn(0);

        // when & then
        mockMvc.perform(get("/forum/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("forum/list"))
                .andExpect(model().attribute("list", Collections.emptyList()))
                .andExpect(model().attribute("currentPage", 1))
                .andExpect(model().attribute("totalPages", 0))
                .andExpect(model().attribute("totalList", 0))
                .andDo(print());
    }
}