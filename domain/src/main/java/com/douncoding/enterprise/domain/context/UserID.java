package com.douncoding.enterprise.domain.context;

import java.util.UUID;

/**
 * 접속한 위치의 IP?
 * 접속한 일자?
 */
public class UserID {
    String UserID;

    public UserID() {
        this.UserID = UUID.randomUUID().toString();
    }
}
