package com.transport.transport.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Status {
    public enum Feedback {
        ACTIVE,
        INACTIVE,
        PENDING,
        REJECTED,
        ACCEPT
    }

    public enum Account {
        ACTIVE,
        INACTIVE,
        REQUEST
    }

    public enum Booking {
        PENDING,
        REQUEST,
        DONE,
        REJECTED,
        UNAVAILABLE,
        EXPIRED,
        ACCEPT
    }

    public enum CheckTime {
        AVAILABLE,
        UNAVAILABLE
    }

    public enum Vehicle {
        ACTIVE,
        INACTIVE
    }
    public enum Trip {
        PENDING,
        REQUEST,
        DONE,
        REJECTED,
        UNAVAILABLE,
        EXPIRED,
        ACCEPT
    }

}
