package cn.hunter.spring.remote.vo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserRpcResponse implements Serializable {

    private boolean isSuccess;

    private String code;

    private String message;

    private Object userInfo;

}
