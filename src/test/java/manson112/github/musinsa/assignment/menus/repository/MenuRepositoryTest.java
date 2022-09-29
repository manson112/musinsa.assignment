package manson112.github.musinsa.assignment.menus.repository;

import manson112.github.musinsa.assignment.menus.controller.dto.MenuSelectResponse;
import manson112.github.musinsa.assignment.menus.entity.Banner;
import manson112.github.musinsa.assignment.menus.entity.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MenuRepositoryTest {

    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MenuRepository menuRepository;

    @BeforeEach
    void setUp() {
        menuRepository.deleteAll();
    }

    private Menu createSampleMenu(Long parentMenuId) {
        Menu menu = Menu.builder()
                .menuTitle("TITLE")
                .menuLink("LINK")
                .parentMenuId(parentMenuId)
                .sortOrder(0)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

        menuRepository.createMenu(menu);
        return menu;
    }

    @Test
    void findById(){
        Long parentMenuId = 19L;
        try {
            Menu menu = createSampleMenu(parentMenuId);

            Menu select = menuRepository.findById(menu.getMenuId()).orElseThrow(Exception::new);

            assertThat(menu.getMenuId()).isEqualTo(select.getMenuId());
            assertThat(menu.getMenuTitle()).isEqualTo(select.getMenuTitle());
            assertThat(menu.getMenuLink()).isEqualTo(select.getMenuLink());
            assertThat(menu.getParentMenuId()).isEqualTo(select.getParentMenuId());
            assertThat(menu.getSortOrder()).isEqualTo(select.getSortOrder());
            assertThat(menu.getCreateAt()).isEqualTo(select.getCreateAt());
            assertThat(menu.getUpdateAt()).isEqualTo(select.getUpdateAt());

        } catch (Exception e) {
            assertThat(1).isEqualTo(0);
        }

    }

    @Test
    void findMenuHierarchy() {
        Menu menu1 = createSampleMenu(0L);
        Menu menu2 = createSampleMenu(menu1.getMenuId());
        Menu menu3 = createSampleMenu(menu2.getMenuId());
        Menu menu4 = createSampleMenu(menu3.getMenuId());
        Menu menu5 = createSampleMenu(menu4.getMenuId());
        Menu menu6 = createSampleMenu(menu2.getMenuId());
        Menu menu7 = createSampleMenu(menu2.getMenuId());
        Menu menu8 = createSampleMenu(menu5.getMenuId());
        Menu menu9 = createSampleMenu(menu6.getMenuId());
        Menu menu10 = createSampleMenu(menu7.getMenuId());

        List<MenuSelectResponse> hierarchy = menuRepository.findMenuHierarchy(menu1.getMenuId());

        log.debug("findMenuHierarchy :: {}", hierarchy);

        assertThat(hierarchy.size()).isEqualTo(10);
    }

    @Test
    void createMenu() {
        Long parentMenuId = 19L;
        try {
            Menu menu = createSampleMenu(parentMenuId);

            Menu select = menuRepository.findById(menu.getMenuId()).orElseThrow(Exception::new);

            assertThat(menu.getMenuId()).isEqualTo(select.getMenuId());
            assertThat(menu.getMenuTitle()).isEqualTo(select.getMenuTitle());
            assertThat(menu.getMenuLink()).isEqualTo(select.getMenuLink());
            assertThat(menu.getParentMenuId()).isEqualTo(select.getParentMenuId());
            assertThat(menu.getSortOrder()).isEqualTo(select.getSortOrder());
            assertThat(menu.getCreateAt()).isEqualTo(select.getCreateAt());
            assertThat(menu.getUpdateAt()).isEqualTo(select.getUpdateAt());

        } catch (Exception e) {
            assertThat(1).isEqualTo(0);
        }
    }

    @Test
    void updateMenu() {
        Menu menu = createSampleMenu(0L);

        Menu newMenu = Menu.builder()
                .menuId(menu.getMenuId())
                .menuLink("updated link")
                .menuTitle("up!!")
                .parentMenuId(13L)
                .updateAt(LocalDateTime.now())
                .build();

        menuRepository.updateMenu(newMenu);
        try {
            Menu select = menuRepository.findById(menu.getMenuId()).orElseThrow(Exception::new);

            assertThat(select.getMenuId()).isEqualTo(newMenu.getMenuId());
            assertThat(select.getMenuLink()).isEqualTo(newMenu.getMenuLink());
            assertThat(select.getMenuTitle()).isEqualTo(newMenu.getMenuTitle());
            assertThat(select.getParentMenuId()).isEqualTo(newMenu.getParentMenuId());
            assertThat(select.getUpdateAt()).isEqualTo(newMenu.getUpdateAt());

        } catch (Exception e) {
            assertThat(0).isEqualTo(1);
        }
    }

    @Test
    void deleteMenu() {
        Menu menu = createSampleMenu(199L);

        assertThat(menuRepository.findById(menu.getMenuId())).isNotEmpty();

        menuRepository.deleteMenu(menu.getMenuId());

        assertThat(menuRepository.findById(menu.getMenuId())).isEmpty();
    }

}