package manson112.github.musinsa.assignment.menus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import manson112.github.musinsa.assignment.menus.controller.dto.*;
import manson112.github.musinsa.assignment.menus.entity.Menu;
import manson112.github.musinsa.assignment.menus.repository.MenuRepository;
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

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class MenuRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @DisplayName("메뉴 조회 (단건) 성공 (배너X) 테스트")
    @Order(1)
    public void findByIdTest01() throws Exception {
        Long menuId = 190L;

        ResultActions result = mockMvc.perform(
                get("/api/menus")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("menuId", menuId.toString())
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("findById"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.response.menuId").value(menuId))
                .andExpect(jsonPath("$.response.menuTitle").exists())
                .andExpect(jsonPath("$.response.menuLink").exists())
                .andExpect(jsonPath("$.response.parentMenuId", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.response.depth").doesNotExist())
                .andExpect(jsonPath("$.response.path").doesNotExist())
                .andExpect(jsonPath("$.response.sortOrder", is("0")))
                .andExpect(jsonPath("$.response.banners").doesNotExist())
                .andExpect(jsonPath("$.error").doesNotExist())
                ;
    }

    @Test
    @DisplayName("메뉴 조회 (단건) 성공 (배너O) 테스트")
    @Order(2)
    public void findByIdTest02() throws Exception {
        Long menuId = 1L;

        ResultActions result = mockMvc.perform(
                get("/api/menus")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("menuId", menuId.toString())
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("findById"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.response.menuId").value(menuId))
                .andExpect(jsonPath("$.response.menuTitle").exists())
                .andExpect(jsonPath("$.response.menuLink").exists())
                .andExpect(jsonPath("$.response.parentMenuId", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.response.depth").doesNotExist())
                .andExpect(jsonPath("$.response.path").doesNotExist())
                .andExpect(jsonPath("$.response.sortOrder", is("0")))
                .andExpect(jsonPath("$.response.banners").exists())
                .andExpect(jsonPath("$.response.banners").isArray())
                .andExpect(jsonPath("$.response.banners[0].menuId").value(menuId))
                .andExpect(jsonPath("$.error").doesNotExist())
        ;
    }


    @Test
    @DisplayName("메뉴 조회 (단건) 실패: 메뉴ID NULL 테스트")
    @Order(3)
    public void findByIdTest03() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/menus")
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("findById"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.response").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.message", is("must not be null")))
                .andExpect(jsonPath("$.error.status", is(400)))
        ;

    }

    @Test
    @DisplayName("메뉴 조회 (단건) 실패: 메뉴ID (0, -1) 테스트")
    @Order(4)
    public void findByIdTest04() throws Exception {
        Long[] menuIdList = new Long[] {0L, -1L};

        for (Long menuId: menuIdList) {
            ResultActions result = mockMvc.perform(
                    get("/api/menus")
                            .accept(MediaType.APPLICATION_JSON)
                            .param("menuId", menuId.toString())
            );

            result.andDo(print())
                    .andExpect(status().is4xxClientError())
                    .andExpect(handler().handlerType(MenuRestController.class))
                    .andExpect(handler().methodName("findById"))
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.response").doesNotExist())
                    .andExpect(jsonPath("$.error").exists())
                    .andExpect(jsonPath("$.error.message", is("menuId must be greater than 0")))
                    .andExpect(jsonPath("$.error.status", is(400)))
            ;
        }
    }

    @Test
    @DisplayName("메뉴 조회 (단건) 실패: 존재하지 않는 메뉴ID 테스트")
    @Order(5)
    public void findByIdTest05() throws Exception {
        Long menuId = Long.MAX_VALUE;

        ResultActions result = mockMvc.perform(
                get("/api/menus")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("menuId", menuId.toString())
        );

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("findById"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.response").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.message", is("Could not find Menu for " + menuId)))
                .andExpect(jsonPath("$.error.status", is(404)))
        ;
    }


    @Test
    @DisplayName("하위메뉴 포함 조회 성공: 전체메뉴조회 (배너O) 테스트")
    @Order(6)
    public void findMenuHierarchyTest01() throws Exception {
        Long menuId = 0L;
        ResultActions result = mockMvc.perform(
                get("/api/menus/hierarchy")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("menuId", menuId.toString())
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("findMenuHierarchy"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response[0].parentMenuId").value(menuId))
                .andExpect(jsonPath("$.error").doesNotExist())
        ;
    }

    @Test
    @DisplayName("하위메뉴 포함 조회 성공 (배너O) 테스트")
    @Order(7)
    public void findMenuHierarchyTest02() throws Exception {
        Long menuId = 1L;
        ResultActions result = mockMvc.perform(
                get("/api/menus/hierarchy")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("menuId", menuId.toString())
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("findMenuHierarchy"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response[0].menuId").value(menuId))
                .andExpect(jsonPath("$.response[0].depth", is(0)))
                .andExpect(jsonPath("$.response[0].path").exists())
                .andExpect(jsonPath("$.response[0].banners").exists())
                .andExpect(jsonPath("$.response[1].parentMenuId").value(menuId))
                .andExpect(jsonPath("$.error").doesNotExist())
        ;
    }

    @Test
    @DisplayName("하위메뉴 포함 조회 성공 (배너X) 테스트")
    @Order(8)
    public void findMenuHierarchyTest03() throws Exception {
        Long menuId = 190L;
        ResultActions result = mockMvc.perform(
                get("/api/menus/hierarchy")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("menuId", menuId.toString())
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("findMenuHierarchy"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response[0].menuId").value(menuId))
                .andExpect(jsonPath("$.response[0].depth", is(0)))
                .andExpect(jsonPath("$.response[0].path").exists())
                .andExpect(jsonPath("$.response[0].banners").doesNotExist())
                .andExpect(jsonPath("$.error").doesNotExist())
        ;
    }

    @Test
    @DisplayName("하위메뉴 포함 조회 실패: 메뉴ID NULL 테스트")
    @Order(9)
    public void findMenuHierarchyTest04() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/menus/hierarchy")
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("findMenuHierarchy"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.response").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.message", is("must not be null")))
                .andExpect(jsonPath("$.error.status", is(400)))
        ;
    }

    @Test
    @DisplayName("하위메뉴 포함 조회 실패: 메뉴ID(-1) 테스트")
    @Order(10)
    public void findMenuHierarchyTest05() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/menus/hierarchy")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("menuId", "-1")
        );

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("findMenuHierarchy"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.response").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.message", is("menuId must be greater than or equal to 0")))
                .andExpect(jsonPath("$.error.status", is(400)))
        ;
    }

    @Test
    @DisplayName("하위메뉴 포함 조회 실패: 존재하지 않는 메뉴ID 테스트")
    @Order(11)
    public void findMenuHierarchyTest06() throws Exception {
        Long menuId = Long.MAX_VALUE;
        ResultActions result = mockMvc.perform(
                get("/api/menus/hierarchy")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("menuId", menuId.toString())
        );

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("findMenuHierarchy"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.response").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.message", is("Could not find Menu for " + menuId)))
                .andExpect(jsonPath("$.error.status", is(404)))
        ;
    }

    @Test
    @DisplayName("메뉴 생성 성공 테스트")
    @Order(12)
    public void createMenuTest01() throws Exception {
        long parentMenuId = 0L;
        MenuCreateRequest request = MenuCreateRequest.builder()
                .menuTitle("NEW MENU TITLE")
                .menuLink("https://test.com")
                .sortOrder(0)
                .parentMenuId(parentMenuId)
                .build();

        ResultActions result = mockMvc.perform(
                post("/api/menus")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("createMenu"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.response.menuId").isNumber())
                .andExpect(jsonPath("$.response.parentMenuId").value(parentMenuId))
                .andExpect(jsonPath("$.error").doesNotExist())
        ;
    }

    @Test
    @DisplayName("메뉴 생성 실패: 메뉴명 NULL, 길이(1~20) 테스트")
    @Order(13)
    public void createMenuTest02() throws Exception {
        MenuCreateRequest[] menuCreateRequests = new MenuCreateRequest[]{
                MenuCreateRequest.builder()
                        .menuLink("http://testLink.com")
                        .build(),
                MenuCreateRequest.builder()
                        .menuTitle("")
                        .menuLink("http://testLink.com")
                        .build(),
                MenuCreateRequest.builder()
                        .menuTitle("abcdefghijklmnopqrstubwxyz")
                        .menuLink("http://testLink.com")
                        .build()
        };
        String[] errorMessage = new String[] {
                "must not be null",
                "length must be between 1 and 20",
                "length must be between 1 and 20"
        };

        int index = 0;
        for (MenuCreateRequest request: menuCreateRequests) {
            ResultActions result = mockMvc.perform(
                    post("/api/menus")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            result.andDo(print())
                    .andExpect(status().is4xxClientError())
                    .andExpect(handler().handlerType(MenuRestController.class))
                    .andExpect(handler().methodName("createMenu"))
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.response").doesNotExist())
                    .andExpect(jsonPath("$.error").exists())
                    .andExpect(jsonPath("$.error.message", is(errorMessage[index++])))
                    .andExpect(jsonPath("$.error.status", is(400)))
            ;
        }
    }

    @Test
    @DisplayName("메뉴 생성 실패: 메뉴링크 NULL, 길이(1~2083), URL형식 테스트")
    @Order(14)
    public void createMenuTest03() throws Exception {
        MenuCreateRequest[] menuCreateRequests = new MenuCreateRequest[]{
                MenuCreateRequest.builder()
                        .menuTitle("menuTitle")
                        .build(),
                MenuCreateRequest.builder()
                        .menuTitle("menuTitle")
                        .menuLink("")
                        .build(),
                MenuCreateRequest.builder()
                        .menuTitle("menuTitle")
                        .menuLink("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unc")
                        .build(),
                MenuCreateRequest.builder()
                        .menuTitle("menuTitle")
                        .menuLink("testLink.com")
                        .build()
        };

        for (MenuCreateRequest request : menuCreateRequests) {
            ResultActions result = mockMvc.perform(
                    post("/api/menus")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            result.andDo(print())
                    .andExpect(status().is4xxClientError())
                    .andExpect(handler().handlerType(MenuRestController.class))
                    .andExpect(handler().methodName("createMenu"))
                    .andExpect(jsonPath("$.success", is(false)))
                    .andExpect(jsonPath("$.response").doesNotExist())
                    .andExpect(jsonPath("$.error").exists())
                    .andExpect(jsonPath("$.error.message").exists())
                    .andExpect(jsonPath("$.error.status", is(400)))
            ;
        }
    }


    @Test
    @DisplayName("메뉴 생성 실패: 정렬순서, 상위메뉴ID 범위 테스트")
    @Order(15)
    public void createMenuTest04() throws Exception {
        MenuCreateRequest[] menuCreateRequests = new MenuCreateRequest[]{
                MenuCreateRequest.builder()
                        .menuTitle("menuTitle")
                        .menuLink("https://testLink.com")
                        .sortOrder(-1L)
                        .build(),
                MenuCreateRequest.builder()
                        .menuTitle("menuTitle")
                        .menuLink("https://testLink.com")
                        .parentMenuId(-1L)
                        .build()
        };

        for (MenuCreateRequest request : menuCreateRequests) {
            ResultActions result = mockMvc.perform(
                    post("/api/menus")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("createMenu"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.response").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.message", is("must be greater than or equal to 0")))
                .andExpect(jsonPath("$.error.status", is(400)))
            ;
        }
    }

    @Test
    @DisplayName("메뉴 생성 실패: 존재하지 않는 상위 메뉴ID 테스트")
    @Order(16)
    public void createMenu05() throws Exception {
        long parentMenuId = Long.MAX_VALUE;
        MenuCreateRequest request =MenuCreateRequest.builder()
                .menuTitle("menuTitle")
                .menuLink("https://testLink.com")
                .parentMenuId(parentMenuId)
                .build();

        ResultActions result = mockMvc.perform(
                post("/api/menus")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        result.andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(handler().handlerType(MenuRestController.class))
            .andExpect(handler().methodName("createMenu"))
            .andExpect(jsonPath("$.success", is(false)))
            .andExpect(jsonPath("$.response").doesNotExist())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.error.message", is("Could not find Menu for " + parentMenuId)))
            .andExpect(jsonPath("$.error.status", is(404)))
        ;
    }


    @Test
    @DisplayName("메뉴 수정 성공 테스트")
    @Order(17)
    public void updateMenuTest01() throws Exception {
        Long menuId = 1L;
        Long parentMenuId = 0L;
        MenuUpdateRequest request = MenuUpdateRequest.builder()
                .menuId(menuId)
                .menuTitle("UPDATE TEST")
                .menuLink("https://update.com")
                .sortOrder(1L)
                .parentMenuId(parentMenuId)
                .build();
        ResultActions result = mockMvc.perform(
                put("/api/menus")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("updateMenu"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.response.menuId").value(menuId))
                .andExpect(jsonPath("$.response.parentMenuId").value(parentMenuId))
                .andExpect(jsonPath("$.error").doesNotExist())
        ;
    }

    @Test
    @DisplayName("메뉴 삭제 성공 테스트")
    @Order(18)
    public void deleteMenuTest01() throws Exception {
        Long menuId = 192L;
        MenuDeleteRequest request = new MenuDeleteRequest(menuId);

        Menu menu = menuRepository.findById(menuId).get();

        ResultActions result = mockMvc.perform(
                delete("/api/menus")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
        );

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("deleteMenu"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.response.parentMenuId").value(menu.getParentMenuId()))
                .andExpect(jsonPath("$.error").doesNotExist())
        ;
    }

    @Test
    @DisplayName("메뉴 삭제 실패: 하위 메뉴가 존재하는 메뉴 삭제 테스트")
    @Order(19)
    public void deleteMenuTest02() throws Exception {
        Long menuId = 1L;
        MenuDeleteRequest request = new MenuDeleteRequest(menuId);

        ResultActions result = mockMvc.perform(
                delete("/api/menus")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        result.andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(handler().handlerType(MenuRestController.class))
            .andExpect(handler().methodName("deleteMenu"))
            .andExpect(jsonPath("$.success", is(false)))
            .andExpect(jsonPath("$.response").doesNotExist())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.error.status", is(400)))
            .andExpect(jsonPath("$.error.message", is("Cannot delete menu which has lower tier menus")))
        ;
    }

    @Test
    @DisplayName("메뉴 삭제 실패: 존재하지 않는 메뉴ID 삭제 테스트")
    @Order(20)
    public void deleteMenuTest03() throws Exception {
        Long menuId = Long.MAX_VALUE;
        MenuDeleteRequest request = new MenuDeleteRequest(menuId);

        ResultActions result = mockMvc.perform(
                delete("/api/menus")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("deleteMenu"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.response").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(404)))
                .andExpect(jsonPath("$.error.message", is("Could not find Menu for " + menuId)))
        ;
    }

    @Test
    @DisplayName("메뉴 삭제 실패: 메뉴ID NULL 테스트")
    @Order(21)
    public void deleteMenuTest04() throws Exception {
        Long menuId = null;
        MenuDeleteRequest request = new MenuDeleteRequest(menuId);

        ResultActions result = mockMvc.perform(
                delete("/api/menus")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("deleteMenu"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.response").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(400)))
                .andExpect(jsonPath("$.error.message", is("must not be null")))
        ;
    }

    @Test
    @DisplayName("메뉴 삭제 실패: 메뉴ID -1 테스트")
    @Order(22)
    public void deleteMenuTest05() throws Exception {
        Long menuId = -1L;
        MenuDeleteRequest request = new MenuDeleteRequest(menuId);

        ResultActions result = mockMvc.perform(
                delete("/api/menus")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(MenuRestController.class))
                .andExpect(handler().methodName("deleteMenu"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.response").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(400)))
                .andExpect(jsonPath("$.error.message", is("must be greater than or equal to 0")))
        ;
    }
}