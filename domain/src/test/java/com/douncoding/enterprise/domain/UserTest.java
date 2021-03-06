package com.douncoding.enterprise.domain;

import com.douncoding.enterprise.domain.context.User;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 *
 */
public class UserTest {

    private static final int FAKE_USER_ID = 9;

    private User user;

    @Before
    public void setUp() {
        user = new User(FAKE_USER_ID);
    }

    @Test
    public void testUserConstructorHappyCase() {
        int userId = user.getUserId();
        assertThat(userId, is(FAKE_USER_ID));
    }
}
