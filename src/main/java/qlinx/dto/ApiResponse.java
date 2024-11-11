package qlinx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ApiResponse는 서버가 클라이언트로 응답할 때 사용하는 데이터 전송 객체
 * - success: 요청 처리 성공 여부 (true/false)
 * - rtnData: 반환 데이터 (키-오브젝트 구조)
 * - rtnMsg: 응답 메시지
 * - rtnCode: 오류 코드 (있을 경우)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    @JsonProperty("RTN_DATA") // JSON 응답의 필드 이름을 지정
    private Object RTN_DATA;   // 반환 데이터

    @JsonProperty("RTN_MSG")   // JSON 응답의 필드 이름을 지정
    private String RTN_MSG;    // 응답 메시지

    @JsonProperty("RTN_CD")    // JSON 응답의 필드 이름을 지정
    private Integer RTN_CD;    // 오류 코드 (옵션)

}
