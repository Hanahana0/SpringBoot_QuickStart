package qlinx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qlinx.dto.ApiRequest;
import qlinx.dto.ApiResponse;
import qlinx.entity.Translation;
import qlinx.service.TranslationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TranslationController {

    private final TranslationService translationService;

    @Autowired
    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping("/translation")
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
}
