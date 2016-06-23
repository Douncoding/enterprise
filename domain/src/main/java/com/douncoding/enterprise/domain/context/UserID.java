package com.douncoding.enterprise.domain.context;

import java.util.UUID;

/**
 * 접속한 위치의 IP?
 * 접속한 일자?
 *
 * UUID 사용 메카니즘
 * http://android-developers.blogspot.kr/2011/03/identifying-app-installations.html
 */
public class UserID {
    String UserID;

    public UserID() {
        this.UserID = UUID.randomUUID().toString();
    }
}
