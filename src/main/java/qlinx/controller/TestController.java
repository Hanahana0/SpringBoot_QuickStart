package qlinx.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qlinx.dto.ApiRequest;
import qlinx.dto.ApiResponse;
import qlinx.entity.Menu;
import qlinx.entity.Translation;
import qlinx.service.MenuService;
import qlinx.service.TranslationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    private final MenuService menuService;
    private final TranslationService translationService;

    public TestController(MenuService menuService, TranslationService translationService) {
        this.menuService = menuService;
        this.translationService = translationService;
    }


    @RequestMapping("/getLang")
    public ResponseEntity<ApiResponse> getTranslation(@RequestBody ApiRequest request) {
        List<Translation> translations = new ArrayList<>();

        // `P_PARAM`에서 값을 추출
        Map<String, Object> params = request.getP_PARAM();
        String lang = params != null ? (String) params.get("lang") : null;
        String msg = params != null ? (String) params.get("msg") : null;

        if (lang == null && msg == null) {
            translations = translationService.getTranslations();
        } else {
            translations = translationService.getTranslations(msg, lang);
        }

        // ApiResponse로 감싸서 반환
        ApiResponse response = new ApiResponse(translations, "Translations fetched successfully", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/menus")
    public ApiResponse getAllMenus(@RequestBody ApiRequest request) {
        List<Menu> menus = menuService.getAllMenus();

        // 응답 객체를 ApiResponse로 포장하여 반환
        return new ApiResponse(menus, "Menus fetched successfully", null);
    }
}
