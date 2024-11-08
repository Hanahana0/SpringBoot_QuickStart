package qlinx.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qlinx.dto.ApiResponse;
import qlinx.entity.Menu;
import qlinx.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/menus")
    public ApiResponse getAllMenus() {
        List<Menu> menus = menuService.getAllMenus();

        // 응답 객체를 ApiResponse로 포장하여 반환
        return new ApiResponse(menus, "Menus fetched successfully", null);
    }
}
