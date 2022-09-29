package manson112.github.musinsa.assignment.utils;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;


/**
 * 모든 Api가 일관된 결과를 반환하도록 하는 클래스
 * @version 0.1
 * @author 김관우
 */
public class ApiUtils {

    /**
     * 요청 결과를 ApiResult로 감싸서 반환한다.
     * @param response 요청에 대한 결과 객체
     * @return ApiResult (success=true, response, error=null)
     */
    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response, null);
    }

    /**
     * 처리중 발생하는 Exception을 ApiResult로 감싸서 반환한다.
     * @param message 발생한 Exception 메시지
     * @param status    Http 상태코드
     * @return ApiResult (success=false, response=null, error=ApiError)
     */
    public static ApiResult<?> error(String message, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(message, status));
    }


    /**
     * @version 0.1
     * @author 김관우
     */
    @Getter
    @ToString
    public static class ApiError {
        private final String message;
        private final int status;

        ApiError(String message, HttpStatus status) {
            this.message = message;
            this.status = status.value();
        }
    }

    /**
     * @version 0.1
     * @author 김관우
     */
    @Getter
    @ToString
    public static class ApiResult<T> {
        private final boolean success;
        private final T response;
        private final ApiError error;

        private ApiResult(boolean success, T response, ApiError error) {
            this.success = success;
            this.response = response;
            this.error = error;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}
