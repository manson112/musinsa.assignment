package manson112.github.musinsa.assignment.menus.service;

import manson112.github.musinsa.assignment.exceptions.BannerNotFoundException;
import manson112.github.musinsa.assignment.exceptions.MenuNotFoundException;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerCreateRequest;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerCreateResponse;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerDeleteRequest;
import manson112.github.musinsa.assignment.menus.controller.dto.BannerDeleteResponse;
import manson112.github.musinsa.assignment.menus.entity.Banner;
import manson112.github.musinsa.assignment.menus.entity.Menu;
import manson112.github.musinsa.assignment.menus.repository.BannerRepository;
import manson112.github.musinsa.assignment.menus.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BannerServiceTest {
    @Mock
    private BannerRepository bannerRepository;

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private BannerService bannerService;

    private Optional<Menu> getMenu(Long menuId, Long parentMenuId) {
        return Optional.ofNullable(Menu.builder()
                .menuId(menuId)
                .menuTitle("MENU TITLE")
                .menuLink("http://testLink.com")
                .parentMenuId(parentMenuId)
                .sortOrder(0)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build());
    }

    private Optional<Banner> getBanner(Long bannerId, Long menuId) {
        return Optional.ofNullable(Banner.builder()
                .bannerId(bannerId)
                .bannerLink("test")
                .bannerImageUrl("test")
                .menuId(menuId)
                .build()
        );
    }

    @Test
    @DisplayName("배너 생성 성공 테스트")
    public void createBannerServiceTest01() {
        //given
        Long menuId = 1L;
        Long parentMenuId = 0L;
        Long bannerId = 19L;

        given(menuRepository.findById(anyLong())).willReturn(getMenu(menuId, parentMenuId));
        doAnswer(invocation -> {
            Object arg = invocation.getArgument(0);
            ReflectionTestUtils.setField((Banner) arg, "bannerId", bannerId);
            return null;
        }).when(bannerRepository).createBanner(any(Banner.class));

        BannerCreateRequest request = new BannerCreateRequest(menuId, "link", "url");
        //when
        BannerCreateResponse response = bannerService.createBanner(request);

        verify(menuRepository, times(1)).findById(anyLong());
        verify(bannerRepository, times(1)).createBanner(any(Banner.class));

        assertThat(response.getMenuId()).isEqualTo(menuId);
        assertThat(response.getBannerId()).isEqualTo(bannerId);
    }

    @Test
    @DisplayName("배너 생성 실패: 최상위 메뉴가 아닌 경우")
    public void createBannerServiceTest02() {
        // given
        Long menuId = 10L;
        Long parentMenuId = 2L;

        given(menuRepository.findById(anyLong())).willReturn(getMenu(menuId, parentMenuId));

        BannerCreateRequest request = new BannerCreateRequest(menuId, "link", "url");

        // when, then
        assertThatThrownBy(() -> bannerService.createBanner(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Banner can't be registered for non-top tier menu");

        verify(menuRepository, times(1)).findById(anyLong());
        verify(bannerRepository, times(0)).createBanner(any(Banner.class));
    }

    @Test
    @DisplayName("배너 생성 실패: 존재하지 않는 메뉴의 경우")
    public void createBannerServiceTest03() {
        // given
        Long menuId = 10L;

        given(menuRepository.findById(anyLong())).willReturn(Optional.empty());

        BannerCreateRequest request = new BannerCreateRequest(menuId, "link", "url");

        // when, then
        assertThatThrownBy(() -> bannerService.createBanner(request))
                .isInstanceOf(MenuNotFoundException.class)
                .hasMessage("Could not find Menu for " + menuId);

        verify(menuRepository, times(1)).findById(anyLong());
        verify(bannerRepository, times(0)).createBanner(any(Banner.class));
    }

    @Test
    @DisplayName("배너 삭제 성공 테스트")
    public void deleteBannerServiceTest01() {
        // given
        Long bannerId = 10L;
        Long menuId = 22L;

        given(bannerRepository.findById(bannerId)).willReturn(getBanner(bannerId, menuId));
        // when
        BannerDeleteRequest request = new BannerDeleteRequest(bannerId);
        BannerDeleteResponse response = bannerService.deleteBanner(request);

        // then
        verify(bannerRepository, times(1)).findById(bannerId);
        verify(bannerRepository, times(1)).deleteBanner(bannerId);

        assertThat(response.getMenuId()).isEqualTo(menuId);
    }

    @Test
    @DisplayName("배너 삭제 실패: 존재하지 않는 배너의 경우")
    public void deleteBannerServiceTest02() {
        // given
        Long bannerId = 10L;

        given(bannerRepository.findById(anyLong())).willReturn(Optional.empty());

        BannerDeleteRequest request = new BannerDeleteRequest(bannerId);

        // when, then
        assertThatThrownBy(() -> bannerService.deleteBanner(request))
                .isInstanceOf(BannerNotFoundException.class)
                .hasMessage("Could not find Banner for " + bannerId);

    }

}