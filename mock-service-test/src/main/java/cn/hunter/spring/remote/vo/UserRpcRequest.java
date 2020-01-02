package cn.hunter.spring.remote.vo;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserRpcRequest implements Serializable {

    private String sysCode;

    private Long userId;

}
