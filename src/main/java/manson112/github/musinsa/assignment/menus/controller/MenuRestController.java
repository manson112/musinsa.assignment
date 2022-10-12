package manson112.github.musinsa.assignment.menus.controller;

import lombok.RequiredArgsConstructor;
import manson112.github.musinsa.assignment.menus.controller.dto.*;
import manson112.github.musinsa.assignment.menus.service.MenuService;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Validated
@RestController
@RequestMapping("api/menus")
@RequiredArgsConstructor
public class MenuRestController {
    private final MenuService menuService;

    @GetMapping
    public MenuSelectResponse findById(
            @Valid MenuSelectRequest menuSelectRequest
    ) {
        return menuService.findById(menuSelectRequest);
    }

    @GetMapping("/hierarchy")
    public List<MenuSelectResponse> findMenuHierarchy(
            @Valid MenuSelectRequest menuSelectRequest
    ) {
        return menuService.findMenuHierarchy(menuSelectRequest);
    }

    @PostMapping
    public MenuCreateResponse createMenu(
            @Valid @RequestBody MenuCreateRequest menuCreateRequest
    ) {
        return menuService.createMenu(menuCreateRequest);
    }

    @PutMapping
    public MenuUpdateResponse updateMenu(
            @Valid @RequestBody MenuUpdateRequest menuUpdateRequest
    ) {
        return menuService.updateMenu(menuUpdateRequest);
    }

    @DeleteMapping
    public MenuDeleteResponse deleteMenu(
            @Valid @RequestBody MenuDeleteRequest menuDeleteRequest
    ) {
        return menuService.deleteMenu(menuDeleteRequest);
    }

}
