package manson112.github.musinsa.assignment.menus.service;

import lombok.RequiredArgsConstructor;
import manson112.github.musinsa.assignment.exceptions.MenuNotFoundException;
import manson112.github.musinsa.assignment.menus.controller.dto.*;
import manson112.github.musinsa.assignment.menus.entity.Menu;
import manson112.github.musinsa.assignment.menus.repository.BannerRepository;
import manson112.github.musinsa.assignment.menus.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 메뉴를 등록, 수정, 삭제, 조회하는 서비스
 * @version 0.1
 * @author 김관우
 */
@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final BannerRepository bannerRepository;

    /**
     * 하나의 Menu를 조회한다
     * 최상위 메뉴일 경우 배너를 조회한다.
     * @param menuSelectRequest 메뉴조회요청
     * @return 메뉴조회결과
     */
    @Transactional(readOnly = true)
    public MenuSelectResponse findById(MenuSelectRequest menuSelectRequest) {
        Assert.notNull(menuSelectRequest.getMenuId(), "menuId must not be null");
        Assert.isTrue(menuSelectRequest.getMenuId() > 0L, "menuId must be greater than 0");

        Long menuId = menuSelectRequest.getMenuId();
        // 1. 메뉴를 조회한다
        MenuSelectResponse response = MenuSelectResponse.from(findByIdOrElseThrow(menuId));

        // 2. 최상위 메뉴일 경우 배너를 조회한다.
        response.setBanners(findBannerForTopTierMenu(menuId, response.getParentMenuId()));

        return response;
    }

    /**
     * 자신과 하위 메뉴를 포함한 메뉴 목록을 조회한다.
     * 반환되는 객체에는 메뉴의 기본정보 및 레벨, 메뉴경로, 정렬순서가 포함된다.
     * @param menuSelectRequest 메뉴조회요청
     * @return 메뉴조회결과
     */
    @Transactional(readOnly = true)
    public List<MenuSelectResponse> findMenuHierarchy(MenuSelectRequest menuSelectRequest) {
        Assert.notNull(menuSelectRequest.getMenuId(), "menuId must not be null");
        Assert.isTrue(menuSelectRequest.getMenuId() >= 0L, "menuId must be greater than or equal to 0");

        // 메뉴 및 최상위메뉴 배너 조회
        List<MenuSelectResponse> result = menuRepository.findMenuHierarchy(menuSelectRequest.getMenuId()).stream()
                .peek(it -> it.setBanners(findBannerForTopTierMenu(it.getMenuId(), it.getParentMenuId())))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new MenuNotFoundException(menuSelectRequest.getMenuId());
        }

        return result;
    }

    /**
     * 메뉴를 생성한다.
     * @param menuCreateRequest 메뉴생성요청
     * @return 메뉴생성결과
     */
    public MenuCreateResponse createMenu(MenuCreateRequest menuCreateRequest) {
        // 1. 상위 메뉴 존재여부 확인
        checkParentMenuExists(menuCreateRequest.getParentMenuId());

        // 2. 등록
        Menu menu = Menu.from(menuCreateRequest);
        menuRepository.createMenu(menu);

        return MenuCreateResponse.of(menu.getMenuId(), menu.getParentMenuId());
    }

    /**
     * 메뉴를 수정한다.
     * @param menuUpdateRequest 메뉴수정요청
     * @return 메뉴수정결과
     */
    public MenuUpdateResponse updateMenu(MenuUpdateRequest menuUpdateRequest) {
        Assert.notNull(menuUpdateRequest.getMenuId(), "menuId must not be null");
        Assert.isTrue(menuUpdateRequest.getMenuId() > 0L, "menuId must be greater than 0");
        Assert.isTrue(menuUpdateRequest.getMenuId() != menuUpdateRequest.getParentMenuId(), "menuId and parentMenuId can't have same value");
        // 1. 상위 메뉴 존재여부 확인
        checkParentMenuExists(menuUpdateRequest.getParentMenuId());

        // 2. 현재 메뉴 존재여부 확인
        findByIdOrElseThrow(menuUpdateRequest.getMenuId());

        // 3. 업데이트
        menuRepository.updateMenu(Menu.from(menuUpdateRequest));

        // 상위 메뉴ID와 변경된 메뉴ID 반환
        return MenuUpdateResponse.of(menuUpdateRequest.getMenuId(), menuUpdateRequest.getParentMenuId());
    }

    /**
     * 메뉴를 삭제한다.
     * @param menuDeleteRequest 메뉴ID
     * @return 삭제된 메뉴의 상위 메뉴ID
     */
    public MenuDeleteResponse deleteMenu(MenuDeleteRequest menuDeleteRequest) {
        Assert.notNull(menuDeleteRequest.getMenuId(), "menuId must not be null");
        Assert.isTrue(menuDeleteRequest.getMenuId() > 0, "menuId must be greater than 0");

        Long menuId = menuDeleteRequest.getMenuId();

        // 1. 삭제하려는 메뉴 존재여부 확인
        Menu menu = findByIdOrElseThrow(menuId);

        // 2. 하위 메뉴를 포함하여 조회
        if (menuRepository.findMenuHierarchy(menuId).size() != 1) {  // 하위 메뉴가 있는 메뉴는 삭제 불가
            throw new IllegalArgumentException("Cannot delete menu which has lower tier menus");
        }

        // 2. 삭제
        menuRepository.deleteMenu(menuId);

        // 재조회를 위해 삭제된 메뉴의 상위 메뉴ID를 반환
        return MenuDeleteResponse.from(menu.getParentMenuId());
    }

    /**
     * 최상위 메뉴의 배너를 조회한다.
     * @param menuId 메뉴ID
     * @param parentMenuId 상위메뉴ID (최상위 = 0)
     * @return 배너조회결과
     */
    private List<BannerSelectResponse> findBannerForTopTierMenu(Long menuId, Long parentMenuId) {
        if (parentMenuId != 0L) return null;

        return bannerRepository.findByMenuId(menuId)
                .stream()
                .map(BannerSelectResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 메뉴를 조회하고 존재하지 않으면 MenuNotFoundException 을 throw 한다.
     * @param menuId 메뉴ID
     * @return 메뉴Entity
     */
    private Menu findByIdOrElseThrow(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException(menuId));
    }

    /**
     * 상위메뉴가 존재하는지 확인한다.
     * @param parentMenuId 상위 메뉴ID
     */
    private void checkParentMenuExists(Long parentMenuId) {
        if (parentMenuId == 0L) return;
        findByIdOrElseThrow(parentMenuId);
    }
}
