package cn.hunter.spring.local.vo;

import lombok.*;

@Data
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private Long userId;

    private String username;

    private Integer age;

    private String city;
}
