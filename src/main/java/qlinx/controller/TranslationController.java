package qlinx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qlinx.entity.Translation;
import qlinx.service.TranslationService;

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

    @GetMapping("/translation")
    public ResponseEntity<List<Translation>> getTranslation(
            @RequestParam(required = false) String msg,
            @RequestParam String lang) {

        List<Translation> translations = translationService.getTranslations(msg, lang);
        return ResponseEntity.ok(translations);
    }
}
