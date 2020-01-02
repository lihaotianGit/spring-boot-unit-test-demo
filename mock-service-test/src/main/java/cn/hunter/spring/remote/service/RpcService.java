package cn.hunter.spring.remote.service;

import cn.hunter.spring.remote.vo.UserRpcRequest;
import cn.hunter.spring.remote.vo.UserRpcResponse;

public interface RpcService {

    UserRpcResponse findUserInfo(UserRpcRequest request);

}
