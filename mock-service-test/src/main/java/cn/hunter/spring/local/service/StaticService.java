package cn.hunter.spring.local.service;

import cn.hunter.spring.local.vo.DeptInfo;

public class StaticService {

    static DeptInfo findDeptInfo(String deptCode) {
        return DeptInfo.builder().deptCode(deptCode).build();
    }

}
