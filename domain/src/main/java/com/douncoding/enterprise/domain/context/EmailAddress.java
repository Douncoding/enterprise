package com.douncoding.enterprise.domain.context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class EmailAddress {
    private String address;

    public EmailAddress(String address) {
        super();
        this.setAddress(address);
    }

    protected void setAddress(String address) {
        if (address == null) {
            throw new IllegalArgumentException("이메일을 참조할 수 없습니다.");
        }

        if (isValidEmail(address)) {
            this.address = address;
        } else {
            throw new IllegalArgumentException("잘못된 이메일 형식");
        }
    }

    private boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }

        return err;
    }
}
