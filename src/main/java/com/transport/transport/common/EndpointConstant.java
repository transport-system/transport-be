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
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Company {
        public static final String COMPANY_ENDPOINT = ROOT_ENDPOINT + "/company";

    }
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Vehicle {
        public static final String VEHICLE_ENDPOINT = ROOT_ENDPOINT + "/vehicle";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Feedback {
        public static final String FEEDBACK_ENDPOINT = ROOT_ENDPOINT + "/feedbacks";
    }
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Trip {
        public static final String TRIP_ENDPOINT = ROOT_ENDPOINT + "/trip";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Route {
        public static final String ROUTE_ENDPOINT = ROOT_ENDPOINT + "/route";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Booking {
        public static final String BOOKING_ENDPOINT = ROOT_ENDPOINT + "/booking";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Payment {
        public static final String PAYMENT_ENDPOINT = ROOT_ENDPOINT + "/paypal";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Seat {
        public static final String SEAT_ENDPOINT = ROOT_ENDPOINT + "/seat";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Voucher {
        public static final String VOUCHER_ENDPOINT = ROOT_ENDPOINT + "/voucher";
    }
}