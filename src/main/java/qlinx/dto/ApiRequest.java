package qlinx.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * ApiRequest는 클라이언트가 서버로 데이터를 송신할 때 사용하는 데이터 전송 객체
 * URL과 필요한 데이터(payload)를 담아 보냅니다.
 * - P_ACT: Controller에서 분기 처리를 위한 문자열 값
 * - P_PARAM: 전달할 데이터 (키-오브젝트 구조)
 * - extraFields: 추가적으로 동적으로 들어오는 필드
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest {

    @JsonProperty("P_ACT")
    private String P_ACT; // controller에서 분기처리할 스트링값
//
//    @JsonProperty("P_PARAM")
//    private Map<String, Object> P_PARAM; // 데이터가 담길 Map 형태의 객체

    // 추가 필드를 저장할 Map
    private Map<String, Object> P_PARAM = new HashMap<>();

    // JSON에 존재하지만 매핑되지 않은 필드를 extraFields에 추가
    @JsonAnySetter
    public void setParam(String key, Object value) {
        P_PARAM.put(key, value);
    }
    @JsonProperty("GV_USERINFO")
    private Map<String, Object> GV_USERINFO; // 유저정보!

    @JsonProperty("GV_VAL")
    private Map<String, Object> GV_VAL; // 글로벌값


    // 데이터 유효성 검사 메서드
    public void validate() {
        if (P_ACT == null || P_ACT.isEmpty()) {
            throw new IllegalArgumentException("P_ACT cannot be empty.");
        }
        // 추가적인 유효성 검사 로직을 여기에 작성할 수 있습니다.
    }
}
