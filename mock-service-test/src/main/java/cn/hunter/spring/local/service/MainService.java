package cn.hunter.spring.local.service;

import cn.hunter.spring.local.vo.DeptInfo;
import cn.hunter.spring.local.vo.UserInfo;
import cn.hunter.spring.remote.service.RpcService;
import cn.hunter.spring.remote.vo.UserRpcRequest;
import cn.hunter.spring.remote.vo.UserRpcResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MainService {

    @Autowired
    private RpcService rpcService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public UserInfo getUserInfo(Long userId) {
        UserRpcRequest request = UserRpcRequest.builder()
                .sysCode("test-sys-code")
                .userId(userId)
                .build();

        UserRpcResponse response = rpcService.findUserInfo(request);
        log.info("MainService-getUserInfo-response={}", JSON.toJSONString(response));
        return (UserInfo) response.getUserInfo();
    }

    public DeptInfo getDeptInfo(String deptCode) {
        DeptInfo deptInfo = StaticService.findDeptInfo(deptCode);
        log.info("MainService-getDeptInfo-response={}", JSON.toJSONString(deptInfo));
        return deptInfo;
    }

    public UserInfo getUserInfoFromRedis(String key) {
        String value = redisTemplate.opsForValue().get(key);
        log.info("MainService-getUserInfoFromRedis-value={}", value);
        return JSON.parseObject(value, new TypeReference<UserInfo>() {
        });
    }

}
