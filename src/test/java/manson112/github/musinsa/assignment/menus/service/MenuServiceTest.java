package manson112.github.musinsa.assignment.menus.service;

import manson112.github.musinsa.assignment.exceptions.MenuNotFoundException;
import manson112.github.musinsa.assignment.menus.controller.dto.*;
import manson112.github.musinsa.assignment.menus.entity.Banner;
import manson112.github.musinsa.assignment.menus.entity.Menu;
import manson112.github.musinsa.assignment.menus.repository.BannerRepository;
import manson112.github.musinsa.assignment.menus.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MenuServiceTest {
    @Mock
    private BannerRepository bannerRepository;

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    private Optional<Menu> getOptionalMenu(Long menuId, Long parentMenuId) {
        return Optional.ofNullable(this.getMenu(menuId, parentMenuId));
    }

    private Menu getMenu(Long menuId, Long parentMenuId) {
        return Menu.builder().menuId(menuId).menuLink("test").menuTitle("test").sortOrder(0).parentMenuId(parentMenuId).build();
    }

    private List<MenuSelectResponse> getMenuSelectResponses(List<Menu> menus) {
        return menus.stream().map(MenuSelectResponse::from).collect(Collectors.toList());
    }

    private List<Banner> getBanners(Long menuId) {
        return List.of(Banner.builder().bannerId(1L).bannerLink("test").bannerImageUrl("test").menuId(menuId).build(), Banner.builder().bannerId(2L).bannerLink("test").bannerImageUrl("test").menuId(menuId).build());
    }

    private MenuCreateRequest getMenuCreateRequest(Long parentMenuId) {
        return MenuCreateRequest.builder()
                .menuTitle("title")
                .menuLink("link")
                .parentMenuId(parentMenuId)
                .sortOrder(0)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    private MenuUpdateRequest getMenuUpdateRequest(Long menuId, Long parentMenuId) {
        return MenuUpdateRequest.builder()
                .menuId(menuId)
                .parentMenuId(parentMenuId)
                .menuTitle("test")
                .menuLink("test")
                .updateAt(LocalDateTime.now())
                .sortOrder(1)
                .build();
    }

    @Test
    @DisplayName("메뉴 단건 조회 성공 테스트 (배너X)")
    public void findByIdServiceTest01() {
        // given
        Long menuId = 10L;
        Long parentMenuId = 2L;

        given(menuRepository.findById(menuId)).willReturn(getOptionalMenu(menuId, parentMenuId));

        MenuSelectRequest request = new MenuSelectRequest(menuId);
        // when
        MenuSelectResponse response = menuService.findById(request);

        // then
        verify(menuRepository, times(1)).findById(menuId);
        verify(bannerRepository, times(0)).findByMenuId(menuId);

        assertThat(response).isNotNull();
        assertThat(response.getMenuId()).isEqualTo(menuId);
        assertThat(response.getParentMenuId()).isEqualTo(parentMenuId);
        assertThat(response.getBanners()).isNullOrEmpty();
    }

    @Test
    @DisplayName("메뉴 단건 조회 성공 테스트 (배너O)")
    public void findByIdServiceTest02() {
        // given
        Long menuId = 10L;
        Long parentMenuId = 0L;
        List<Banner> banners = getBanners(menuId);
        given(menuRepository.findById(menuId)).willReturn(getOptionalMenu(menuId, parentMenuId));
        given(bannerRepository.findByMenuId(menuId)).willReturn(banners);

        MenuSelectRequest request = new MenuSelectRequest(menuId);

        //when
        MenuSelectResponse response = menuService.findById(request);

        //then
        verify(menuRepository, times(1)).findById(menuId);
        verify(bannerRepository, times(1)).findByMenuId(menuId);

        assertThat(response).isNotNull();
        assertThat(response.getMenuId()).isEqualTo(menuId);
        assertThat(response.getParentMenuId()).isEqualTo(parentMenuId);
        assertThat(response.getBanners()).isNotNull();
        assertThat(response.getBanners().size()).isEqualTo(banners.size());
        assertThat(response.getBanners().get(0).getMenuId()).isEqualTo(menuId);
        assertThat(response.getBanners().get(0).getBannerId()).isEqualTo(1L);
        assertThat(response.getBanners().get(1).getMenuId()).isEqualTo(menuId);
        assertThat(response.getBanners().get(1).getBannerId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("메뉴 단건 조회 실패: 메뉴ID가 NULL인 경우")
    public void findByIdServiceTest03() {
        // given
        MenuSelectRequest request = new MenuSelectRequest(null);
        // when, then

        assertThatThrownBy(() -> menuService.findById(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("menuId must not be null");

        verify(menuRepository, times(0)).findById(null);
        verify(bannerRepository, times(0)).findByMenuId(null);
    }

    @Test
    @DisplayName("메뉴 단건 조회 실패: 메뉴ID가 1보다 작은 경우")
    public void findByIdServiceTest04() {
        Long menuId = 0L;
        // given
        MenuSelectRequest request = new MenuSelectRequest(menuId);

        // when, then
        assertThatThrownBy(() -> menuService.findById(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("menuId must be greater than 0");

        verify(menuRepository, times(0)).findById(menuId);
        verify(bannerRepository, times(0)).findByMenuId(menuId);
    }

    @Test
    @DisplayName("메뉴 단건 조회 실패: 존재하지 않는 메뉴인 경우")
    public void findByIdServiceTest05() {
        // given
        Long menuId = 99L;
        MenuSelectRequest request = new MenuSelectRequest(menuId);

        given(menuRepository.findById(menuId)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> menuService.findById(request))
                .isInstanceOf(MenuNotFoundException.class)
                .hasMessage("Could not find Menu for " + menuId);

        verify(menuRepository, times(1)).findById(menuId);
        verify(bannerRepository, times(0)).findByMenuId(menuId);
    }

    @Test
    @DisplayName("하위메뉴 포함 조회 성공 테스트")
    public void findMenuHierarchyServiceTest01() {
        // given
        Long menuId = 1L;

        List<MenuSelectResponse> menuSelectResponses = getMenuSelectResponses(List.of(
                getMenu(1L, 0L),
                getMenu(2L, 1L),
                getMenu(3L, 2L),
                getMenu(4L, 1L),
                getMenu(5L, 3L)
        ));
        List<Banner> banners = getBanners(menuId);

        given(menuRepository.findMenuHierarchy(menuId)).willReturn(menuSelectResponses);
        given(bannerRepository.findByMenuId(menuId)).willReturn(banners);

        MenuSelectRequest request = new MenuSelectRequest(menuId);
        // when
        List<MenuSelectResponse> response = menuService.findMenuHierarchy(request);
        // then
        verify(menuRepository, times(1)).findMenuHierarchy(menuId);
        verify(bannerRepository, times(1)).findByMenuId(menuId);

        assertThat(response.size()).isEqualTo(menuSelectResponses.size());
        assertThat(response.get(0).getMenuId()).isEqualTo(1L);
        assertThat(response.get(0).getParentMenuId()).isEqualTo(0L);
        assertThat(response.get(0).getBanners()).isNotNull();
        assertThat(response.get(0).getBanners().size()).isEqualTo(banners.size());
        assertThat(response.get(1).getMenuId()).isEqualTo(2L);
        assertThat(response.get(1).getParentMenuId()).isEqualTo(1L);
        assertThat(response.get(2).getMenuId()).isEqualTo(3L);
        assertThat(response.get(2).getParentMenuId()).isEqualTo(2L);
        assertThat(response.get(3).getMenuId()).isEqualTo(4L);
        assertThat(response.get(3).getParentMenuId()).isEqualTo(1L);
        assertThat(response.get(4).getMenuId()).isEqualTo(5L);
        assertThat(response.get(4).getParentMenuId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("하위메뉴 포함 조회 실패: 메뉴ID가 NULL 인 경우")
    public void findMenuHierarchyServiceTest02() {
        // given
        MenuSelectRequest request = new MenuSelectRequest(null);
        // when, then
        assertThatThrownBy(() -> menuService.findMenuHierarchy(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("menuId must not be null");
    }

    @Test
    @DisplayName("하위메뉴 포함 조회 실패: 메뉴ID가 0 보다 작은 경우")
    public void findMenuHierarchyServiceTest03() {
        // given
        MenuSelectRequest request = new MenuSelectRequest(-1L);
        // when, then
        assertThatThrownBy(() -> menuService.findMenuHierarchy(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("menuId must be greater than or equal to 0");
    }


    @Test
    @DisplayName("하위메뉴 포함 조회 실패: 존재하지 않는 메뉴의 경우")
    public void findMenuHierarchyServiceTest04() {
        // given
        Long menuId = Long.MAX_VALUE;
        MenuSelectRequest request = new MenuSelectRequest(menuId);

        given(menuRepository.findMenuHierarchy(menuId)).willReturn(List.of());

        // when, then
        assertThatThrownBy(() -> menuService.findMenuHierarchy(request))
                .isInstanceOf(MenuNotFoundException.class)
                .hasMessage("Could not find Menu for " + menuId);
    }

    @Test
    @DisplayName("메뉴 생성 성공 테스트")
    public void createMenuServiceTest01() {
        // given
        Long parentMenuId = 0L;
        Long createdMenuId = 111L;
        LocalDateTime createAt = LocalDateTime.now();
        LocalDateTime updateAt = LocalDateTime.now();

        MenuCreateRequest request = getMenuCreateRequest(parentMenuId);

        doAnswer(
                invocation -> {
                    Object arg = invocation.getArgument(0);
                    ReflectionTestUtils.setField((Menu) arg, "menuId", createdMenuId);
                    return null;
                }
        ).when(menuRepository).createMenu(any(Menu.class));

        // when
        MenuCreateResponse response = menuService.createMenu(request);

        // then
        verify(menuRepository, times(0)).findById(parentMenuId);
        verify(menuRepository, times(1)).createMenu(any(Menu.class));

        assertThat(response.getMenuId()).isEqualTo(createdMenuId);
        assertThat(response.getParentMenuId()).isEqualTo(parentMenuId);
    }

    @Test
    @DisplayName("메뉴 생성 실패: 상위메뉴가 존재하지 않을 경우")
    public void createMenuServiceTest02() {
        // given
        Long parentMenuId = 123L;
        given(menuRepository.findById(parentMenuId)).willReturn(Optional.empty());

        MenuCreateRequest request = getMenuCreateRequest(parentMenuId);

        // when, then
        assertThatThrownBy(() -> menuService.createMenu(request))
                .isInstanceOf(MenuNotFoundException.class)
                .hasMessage("Could not find Menu for " + parentMenuId);

        verify(menuRepository, times(1)).findById(parentMenuId);
        verify(menuRepository, times(0)).createMenu(any(Menu.class));
    }


    @Test
    @DisplayName("메뉴 수정 성공 테스트")
    public void updateMenuServiceTest01() {
        // given
        Long menuId = 10L;
        Long parentMenuId = 1L;

        MenuUpdateRequest request = getMenuUpdateRequest(menuId, parentMenuId);

        given(menuRepository.findById(parentMenuId)).willReturn(getOptionalMenu(parentMenuId, 0L));
        given(menuRepository.findById(menuId)).willReturn(getOptionalMenu(menuId, parentMenuId));
        doAnswer(invocation -> {
            Menu menu = (Menu) invocation.getArgument(0);
            assertThat(menu.getMenuId()).isEqualTo(menuId);
            assertThat(menu.getParentMenuId()).isEqualTo(parentMenuId);
            return null;
        }).when(menuRepository).updateMenu(any(Menu.class));

        // when
        MenuUpdateResponse response = menuService.updateMenu(request);

        // then
        verify(menuRepository, times(1)).updateMenu(any(Menu.class));
        verify(menuRepository, times(1)).findById(parentMenuId);
        verify(menuRepository, times(1)).findById(menuId);

        assertThat(response.getMenuId()).isEqualTo(menuId);
        assertThat(response.getParentMenuId()).isEqualTo(parentMenuId);
    }

    @Test
    @DisplayName("메뉴 수정 실패: 메뉴ID가 NULL 인 경우")
    public void updateMenuServiceTest02() {
        // given
        Long menuId = null;
        Long parentMenuId = 19L;
        MenuUpdateRequest request = getMenuUpdateRequest(menuId, parentMenuId);
        // when, then
        assertThatThrownBy(() -> menuService.updateMenu(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("menuId must not be null");

        verify(menuRepository, times(0)).updateMenu(any(Menu.class));
        verify(menuRepository, times(0)).findById(parentMenuId);
        verify(menuRepository, times(0)).findById(menuId);
    }

    @Test
    @DisplayName("메뉴 수정 실패: 메뉴ID가 1보다 작은 경우")
    public void updateMenuServiceTest03() {
        // given
        Long menuId = 0L;
        Long parentMenuId = 19L;
        MenuUpdateRequest request = getMenuUpdateRequest(menuId, parentMenuId);
        // when, then
        assertThatThrownBy(() -> menuService.updateMenu(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("menuId must be greater than 0");

        verify(menuRepository, times(0)).updateMenu(any(Menu.class));
        verify(menuRepository, times(0)).findById(parentMenuId);
        verify(menuRepository, times(0)).findById(menuId);
    }

    @Test
    @DisplayName("메뉴 수정 실패: 상위메뉴ID와 수정할 메뉴ID가 같은 경우")
    public void updateMenuServiceTest04() {
        // given
        Long menuId = 19L;
        Long parentMenuId = 19L;
        MenuUpdateRequest request = getMenuUpdateRequest(menuId, parentMenuId);
        // when, then
        assertThatThrownBy(() -> menuService.updateMenu(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("menuId and parentMenuId can't have same value");

        verify(menuRepository, times(0)).updateMenu(any(Menu.class));
        verify(menuRepository, times(0)).findById(parentMenuId);
        verify(menuRepository, times(0)).findById(menuId);
    }

    @Test
    @DisplayName("메뉴 수정 실패: 상위메뉴가 존재하지 않는 경우")
    public void updateMenuServiceTest05() {
        // given
        Long menuId = 18L;
        Long parentMenuId = 19L;

        given(menuRepository.findById(parentMenuId)).willReturn(Optional.empty());

        MenuUpdateRequest request = getMenuUpdateRequest(menuId, parentMenuId);
        // when, then
        assertThatThrownBy(() -> menuService.updateMenu(request))
                .isInstanceOf(MenuNotFoundException.class)
                .hasMessage("Could not find Menu for " + parentMenuId);

        verify(menuRepository, times(0)).updateMenu(any(Menu.class));
        verify(menuRepository, times(1)).findById(parentMenuId);
        verify(menuRepository, times(0)).findById(menuId);
    }

    @Test
    @DisplayName("메뉴 수정 실패: 수정할 메뉴가 존재하지 않는 경우")
    public void updateMenuServiceTest06() {
        // given
        Long menuId = 18L;
        Long parentMenuId = 19L;

        given(menuRepository.findById(parentMenuId)).willReturn(getOptionalMenu(parentMenuId, 0L));
        given(menuRepository.findById(menuId)).willReturn(Optional.empty());

        MenuUpdateRequest request = getMenuUpdateRequest(menuId, parentMenuId);
        // when, then
        assertThatThrownBy(() -> menuService.updateMenu(request))
                .isInstanceOf(MenuNotFoundException.class)
                .hasMessage("Could not find Menu for " + menuId);

        verify(menuRepository, times(0)).updateMenu(any(Menu.class));
        verify(menuRepository, times(1)).findById(parentMenuId);
        verify(menuRepository, times(1)).findById(menuId);
    }


    @Test
    @DisplayName("메뉴 삭제 성공 테스트")
    public void deleteMenuServiceTest01() {
        // given
        Long menuId = 1L;
        Long parentMenuId = 0L;
        List<Menu> menus = List.of(getMenu(menuId, parentMenuId));

        given(menuRepository.findById(menuId)).willReturn(getOptionalMenu(menuId, parentMenuId));
        given(menuRepository.findMenuHierarchy(menuId)).willReturn(getMenuSelectResponses(menus));
        willDoNothing().given(menuRepository).deleteMenu(menuId);

        MenuDeleteRequest request = new MenuDeleteRequest(menuId);
        // when
        MenuDeleteResponse response = menuService.deleteMenu(request);

        // then
        verify(menuRepository, times(1)).findMenuHierarchy(menuId);
        verify(menuRepository, times(1)).deleteMenu(menuId);

        assertThat(response.getParentMenuId()).isEqualTo(parentMenuId);
    }

    @Test
    @DisplayName("메뉴 삭제 실패: 메뉴ID가 NULL 인 경우")
    public void deleteMenuServiceTest02() {
        // given
        MenuDeleteRequest request = new MenuDeleteRequest(null);

        // when, then
        assertThatThrownBy(() -> menuService.deleteMenu(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("menuId must not be null");
    }

    @Test
    @DisplayName("메뉴 삭제 실패: 메뉴ID가 1보다 작은 경우")
    public void deleteMenuServiceTest03() {
        // given
        MenuDeleteRequest request = new MenuDeleteRequest(0L);

        // when, then
        assertThatThrownBy(() -> menuService.deleteMenu(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("menuId must be greater than 0");
    }

    @Test
    @DisplayName("메뉴 삭제 실패: 존재하지 않는 메뉴ID인 경우")
    public void deleteMenuServiceTest04() {
        // given
        Long menuId = 1232L;
        MenuDeleteRequest request = new MenuDeleteRequest(menuId);
        // when
        given(menuRepository.findById(menuId)).willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> menuService.deleteMenu(request))
                .isInstanceOf(MenuNotFoundException.class)
                .hasMessage("Could not find Menu for " + menuId);
    }

    @Test
    @DisplayName("메뉴 삭제 실패: 하위 메뉴가 있는 경우")
    public void deleteMenuServiceTest05() {
        // given
        Long menuId = 13L;
        Long parentMenuId = 14L;

        List<MenuSelectResponse> menuSelectResponses = getMenuSelectResponses(List.of(
                getMenu(menuId, parentMenuId),
                getMenu(2L, menuId),
                getMenu(3L, 2L),
                getMenu(4L, menuId),
                getMenu(5L, 3L)
        ));

        given(menuRepository.findById(menuId)).willReturn(getOptionalMenu(menuId, parentMenuId));
        given(menuRepository.findMenuHierarchy(menuId)).willReturn(menuSelectResponses);

        MenuDeleteRequest request = new MenuDeleteRequest(menuId);

        // when, then

        assertThatThrownBy(() -> menuService.deleteMenu(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot delete menu which has lower tier menus");
    }



}