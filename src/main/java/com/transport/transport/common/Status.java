package com.transport.transport.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Status {
    public enum Feedback {
        APPROVAL,
        ACTIVE,
        INACTIVE
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
        REFUNDED,
        REQUESTREFUND,
        PAYLATER
    }

    public enum CheckTime {
        AVAILABLE,
        UNAVAILABLE
    }

    public enum Vehicle {
        DOING,
        ACTIVE,
        INACTIVE
    }
    public enum Trip {
        ACTIVE,
        DOING,
        INACTIVE,
        UPDATE
    }

    public enum Seat {
        ACTIVE,
        INACTIVE,
        PENDING,
        }

        public enum Voucher {
            ACTIVE,
            INACTIVE,
            EXPIRED
        }
}
