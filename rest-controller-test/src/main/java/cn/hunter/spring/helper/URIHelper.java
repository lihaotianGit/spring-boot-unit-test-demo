package cn.hunter.spring.helper;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class URIHelper {

    public static URI createUri(String path, Object... value) {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        return builder.path(path)
                .buildAndExpand(value)
                .toUri();
    }

}
