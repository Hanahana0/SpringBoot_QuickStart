package qlinx.service;

import org.springframework.stereotype.Service;
import qlinx.entity.Translation;
import qlinx.repository.TranslationRepository;

import java.util.Collections;
import java.util.List;

@Service
public class TranslationService {
    private final TranslationRepository translationRepository;

    public TranslationService(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    // 전체조회
    public List<Translation> getTranslations() {
        return translationRepository.findAll();
    }

    // lang에 따라 분기조회
    public List<Translation> getTranslations(String msg, String lang) {
        if (msg != null && !msg.isEmpty()) {
            // msg와 lang이 모두 있는 경우
            return translationRepository.findByMsgAndLang(msg, lang);
        } else {
            // msg가 없는 경우, lang만으로 조회
            return translationRepository.findByLang(lang);
        }
    }
}