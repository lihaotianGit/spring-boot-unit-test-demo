package cn.hunter.spring.local.vo;

import lombok.*;

@Data
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DeptInfo {

    private String deptCode;

    private String deptName;

    private Integer deptStaffNumber;

}
