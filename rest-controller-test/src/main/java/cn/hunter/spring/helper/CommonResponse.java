package cn.hunter.spring.helper;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommonResponse extends HashMap<String, Object> implements Serializable {

    public boolean isSuccess() {
        return get("success") != null && (Boolean) get("success");
    }

    public String getMessage() {
        if (get("message") != null) {
            return (String) get("message");
        }
        return "";
    }

    public CommonResponse success() {
        this.put("success", true);
        return this;
    }

    public CommonResponse success(String message) {
        this.put("success", true);
        this.put("message", message);
        return this;
    }

    public CommonResponse fail(String message) {
        this.put("success", false);
        this.put("message", message);
        return this;
    }

    public CommonResponse setData(String key, Object data) {
        this.put(key, data);
        return this;
    }

    public static CommonResponse createCommonResponse() {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.success();
        return commonResponse;
    }
}