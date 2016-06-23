package com.douncoding.enterprise.data.net;

import com.douncoding.enterprise.data.entity.User;

import java.util.List;

import rx.Observable;

/**
 * RestApi for retrieving data from
 */
public interface RestApi {
    String API_BASE_URL = "http://mixboard.douncoding.xyz/api/v1/";

    String API_URL_GET_USER_LIST = API_BASE_URL + "";
    String API_URL_GET_USER_DETAIL = API_BASE_URL + "";

    // 사용자 목록 검색
    Observable<List<User>> userList();

    // 사용자 세부목록 검색
    Observable<User> userById();
}
