package manson112.github.musinsa.assignment.menus.controller;

import lombok.RequiredArgsConstructor;
import manson112.github.musinsa.assignment.menus.controller.dto.*;
import manson112.github.musinsa.assignment.menus.service.MenuService;
import manson112.github.musinsa.assignment.utils.ApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static manson112.github.musinsa.assignment.utils.ApiUtils.success;

@Validated
@RestController
@RequestMapping("api/menus")
@RequiredArgsConstructor
public class MenuRestController {
    private final MenuService menuService;

    @GetMapping
    public ApiUtils.ApiResult<MenuSelectResponse> findById(
            @ModelAttribute @Valid MenuSelectRequest menuSelectRequest
    ) {
        return success(menuService.findById(menuSelectRequest));
    }

    @GetMapping("/hierarchy")
    public ApiUtils.ApiResult<List<MenuSelectResponse>> findMenuHierarchy(
            @Valid @ModelAttribute MenuSelectRequest menuSelectRequest
    ) {
        return success(menuService.findMenuHierarchy(menuSelectRequest));
    }

    @PostMapping
    public ApiUtils.ApiResult<MenuCreateResponse> createMenu(
            @Valid @RequestBody MenuCreateRequest menuCreateRequest
    ) {
        return success(menuService.createMenu(menuCreateRequest));
    }

    @PutMapping
    public ApiUtils.ApiResult<MenuUpdateResponse> updateMenu(
            @Valid @RequestBody MenuUpdateRequest menuUpdateRequest
    ) {
        return success(menuService.updateMenu(menuUpdateRequest));
    }

    @DeleteMapping
    public ApiUtils.ApiResult<MenuDeleteResponse> deleteMenu(
            @Valid @RequestBody MenuDeleteRequest menuDeleteRequest
    ) {
        return success(menuService.deleteMenu(menuDeleteRequest));
    }

}
