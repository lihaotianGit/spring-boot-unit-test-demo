package cn.hunter.spring.service;

import cn.hunter.spring.base.BaseTest;
import cn.hunter.spring.local.service.MainService;
import cn.hunter.spring.local.service.StaticService;
import cn.hunter.spring.local.vo.DeptInfo;
import cn.hunter.spring.local.vo.UserInfo;
import cn.hunter.spring.remote.service.RpcService;
import cn.hunter.spring.remote.vo.UserRpcResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import mockit.Mock;
import mockit.MockUp;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.mockito.internal.matchers.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
public class MainServiceTest extends BaseTest {

    @MockBean
    private RpcService rpcService;

    @Autowired
    private MainService mainService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String USER_1_KEY = "user_1";

    private static final String USER_1_FILE_PATH = "json/user_1.json";

    /*
        Enable gather log.
        @see https://stefanbirkner.github.io/system-rules/index.html
     */
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Before
    public void setUp() throws Exception {
        redisTemplate.opsForValue().set(USER_1_KEY, readJsonFile(USER_1_FILE_PATH));
        Boolean hasKey = redisTemplate.hasKey(USER_1_KEY);
        assertNotNull(hasKey);
        assertTrue(hasKey);
    }

    @After
    public void tearDown() throws Exception {
        redisTemplate.delete(USER_1_KEY);
        Boolean hasKey = redisTemplate.hasKey(USER_1_KEY);
        assertNotNull(hasKey);
        assertFalse(hasKey);
    }

    @Test
    public void should_success_get_user_info() {
        Long userId = 1L;
        String username = "demo";
        Integer age = 18;
        String city = "Beijing";

        UserRpcResponse response = UserRpcResponse.builder()
                .isSuccess(true)
                .code("0000")
                .message("success")
                .userInfo(UserInfo.builder().userId(userId).username(username).age(age).city(city).build())
                .build();

        when(rpcService.findUserInfo(any())).thenReturn(response);

        UserInfo userInfo = mainService.getUserInfo(1L);

        String consoleLog = systemOutRule.getLog();
        assertTrue(consoleLog.contains("MainService-getUserInfo-response={\"code\":\"0000\",\"message\":\"success\",\"success\":true,\"userInfo\":{\"age\":18,\"city\":\"Beijing\",\"userId\":1,\"username\":\"demo\"}}"));

        assertEquals(userId, userInfo.getUserId());
        assertEquals(username, userInfo.getUsername());
        assertEquals(age, userInfo.getAge());
        assertEquals(city, userInfo.getCity());
    }

    @Test
    public void should_success_get_dept_info() {
        String deptCode = "000000";
        String deptName = "中台研发部";
        Integer deptStaffNumber = 66;

        /*
            Use Jmockit to mock static method.
            Needs set JVM arg with the "-javaagent:<proper path>/jmockit.x.x.jar", it can specified in the pom.xml file with a plugin tag.
            @see https://jmockit.github.io/
            @see http://jmockit.github.io/tutorial/Introduction.html#runningTests
         */
        new MockUp<StaticService>() {
            @Mock
            DeptInfo findDeptInfo(String deptCode) {
                return DeptInfo.builder().deptCode(deptCode).deptName(deptName).deptStaffNumber(deptStaffNumber).build();
            }
        };

        DeptInfo deptInfo = mainService.getDeptInfo(deptCode);

        String consoleLog = systemOutRule.getLog();
        assertTrue(consoleLog.contains("MainService-getDeptInfo-response={\"deptCode\":\"000000\",\"deptName\":\"中台研发部\",\"deptStaffNumber\":66}"));

        assertEquals(deptCode, deptInfo.getDeptCode());
        assertEquals(deptName, deptInfo.getDeptName());
        assertEquals(deptStaffNumber, deptInfo.getDeptStaffNumber());
    }

    @Test
    public void should_success_get_user_info_from_redis() {
        String json = readJsonFile(USER_1_FILE_PATH);
        UserInfo fileUserInfo = JSON.parseObject(json, new TypeReference<UserInfo>() {
        });

        UserInfo redisUserInfo = mainService.getUserInfoFromRedis(USER_1_KEY);

        assertEquals(fileUserInfo, redisUserInfo);
    }
}
