package com.transport.transport.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndpointConstant {
    private static final String ROOT_ENDPOINT = "/api";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Authentication {
        public static final String AUTHENTICATION_ENDPOINT = ROOT_ENDPOINT + "/auth";
        public static final String LOGIN_ENDPOINT = AUTHENTICATION_ENDPOINT + "/login";
        public static final String LOGOUT_ENDPOINT = AUTHENTICATION_ENDPOINT + "/logout";
        public static final String REGISTER_ENDPOINT = AUTHENTICATION_ENDPOINT + "/register";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Account {
        public static final String ACCOUNT_ENDPOINT = ROOT_ENDPOINT + "/accounts";
        public static final String ACCOUNT_CHANGE_ENDPOINT = ACCOUNT_ENDPOINT + "/change";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Feedback {
        public static final String FEEDBACK_ENDPOINT = ROOT_ENDPOINT + "/feedbacks";
    }

}