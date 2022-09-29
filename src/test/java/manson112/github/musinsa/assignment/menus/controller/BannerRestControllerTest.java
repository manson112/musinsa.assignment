package manson112.github.musinsa.assignment.menus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerDeleteRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class BannerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Object> generateBannerMap(Long menuId, String bannerLink, String bannerImageUrl) {
        return new HashMap<>(){{
            put("menuId", menuId);
            put("bannerLink", bannerLink);
            put("bannerImageUrl", bannerImageUrl);
        }};
    }
    private Map<String, Object> generateBannerDeleteMap(Long bannerId) {
        return new HashMap<>(){{
            put("bannerId", bannerId);
        }};
    }


    @Test
    @DisplayName("배너 생성 테스트 - 성공")
    @Order(1)
    void createBannerTest01() throws Exception {
        Map<String, Object> requestMap = generateBannerMap(2L, "https://bannerLink.com", "https://bannerImage.com?image=he21cd");

        ResultActions result = mockMvc.perform(
            post("/api/banners")
            .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestMap))
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(BannerRestController.class))
                .andExpect(handler().methodName("createBanner"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.response.bannerId", is(19)))
                .andExpect(jsonPath("$.response.bannerId").isNumber())
                .andExpect(jsonPath("$.response.menuId", is(2)))
                .andExpect(jsonPath("$.response.menuId").isNumber())
                .andExpect(jsonPath("$.error").doesNotExist())
                ;
    }

    @Test
    @DisplayName("배너 생성 테스트 - 실패: 존재하지 않는 메뉴ID")
    @Order(2)
    public void createBannerTest02() throws Exception {
        Long menuId = Long.MAX_VALUE;
        Map<String, Object> requestMap = generateBannerMap(menuId, "https://bannerLink.com", "https://bannerImage.com?image=he21cd");

        ResultActions result = mockMvc.perform(
                post("/api/banners")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestMap))
        );

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(BannerRestController.class))
                .andExpect(handler().methodName("createBanner"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(404)))
                ;
    }

    @Test
    @DisplayName("배너 생성 테스트 - 실패: 메뉴ID NULL")
    @Order(3)
    public void createBannerTest03() throws Exception {
        Map<String, Object> requestMap = generateBannerMap(null, "https://bannerLink.com", "https://bannerImage.com?image=he21cd");
        ResultActions result = mockMvc.perform(
                post("/api/banners")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestMap))
        );

        result.andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(handler().handlerType(BannerRestController.class))
            .andExpect(handler().methodName("createBanner"))
            .andExpect(jsonPath("$.success", is(false)))
            .andExpect(jsonPath("$.response").doesNotExist())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.error.status", is(400)))
            .andExpect(jsonPath("$.error.message", is("menuId must not be null")))
        ;
    }

    @Test
    @DisplayName("배너 생성 테스트 - 실패: 배너링크, 배너이미지URL 형식")
    @Order(4)
    public void createBannerTest04() throws Exception {
        List<Map<String, Object>> requestMapList = List.of(generateBannerMap(2L, "bannerLink.com", "https://123.com"),
                                                        generateBannerMap(2L, "https://bannerLink.com", "test.com"));

        for (Map<String, Object> requestMap: requestMapList) {

            ResultActions result = mockMvc.perform(
                    post("/api/banners")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestMap))
            );

            result.andDo(print())
                    .andExpect(status().is4xxClientError())
                    .andExpect(handler().handlerType(BannerRestController.class))
                    .andExpect(handler().methodName("createBanner"))
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.response").doesNotExist())
                    .andExpect(jsonPath("$.error").exists())
                    .andExpect(jsonPath("$.error.status", is(400)))
                    .andExpect(jsonPath("$.error.message", is("must be a valid URL")))
            ;
        }
    }


    @Test
    @DisplayName("배너 삭제 테스트 - 성공")
    @Order(5)
    public void bannerDeleteTest01() throws Exception {
        Long bannerId = 1L;

        ResultActions result = mockMvc.perform(
                delete("/api/banners")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generateBannerDeleteMap(bannerId)))
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(BannerRestController.class))
                .andExpect(handler().methodName("deleteBanner"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.error").doesNotExist())
        ;
    }

    @Test
    @DisplayName("배너 삭제 테스트 - 실패: 배너ID NULL")
    @Order(6)
    public void bannerDeleteTest02() throws Exception {
        BannerDeleteRequest request = new BannerDeleteRequest(null);
        ResultActions result = mockMvc.perform(
                delete("/api/banners")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(BannerRestController.class))
                .andExpect(handler().methodName("deleteBanner"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.response").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(400)))
                .andExpect(jsonPath("$.error.message", is("must not be null")))
        ;
    }

    @Test
    @DisplayName("배너 삭제 테스트 - 실패: 배너ID (0, -1)")
    @Order(7)
    public void bannerDeleteTest03() throws Exception {
        Long[] bannerIdList = new Long[] {0L, -1L};

        for (Long bannerId: bannerIdList) {
            ResultActions result = mockMvc.perform(
                    delete("/api/banners")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(generateBannerDeleteMap(bannerId)))
            );

            result.andDo(print())
                    .andExpect(status().is4xxClientError())
                    .andExpect(handler().handlerType(BannerRestController.class))
                    .andExpect(handler().methodName("deleteBanner"))
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.response").doesNotExist())
                    .andExpect(jsonPath("$.error").exists())
                    .andExpect(jsonPath("$.error.status", is(400)))
                    .andExpect(jsonPath("$.error.message", is("must be greater than 0")))
            ;
        }
    }





}