package com.douncoding.enterprise.domain.context;

/**
 * 도메인 모델 (엔티티)
 * . 개인정보 특성을 소유하는 객체
 * . User 클래스와 통합될 수 있지만 보완상의 이유로 분할
 */
public class Person {
    private FullName fullName;
    private EmailAddress emailAddress;

    protected Person() {
        super();
    }
}
