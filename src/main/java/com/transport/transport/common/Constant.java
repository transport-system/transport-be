package com.transport.transport.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {
    @Value("${front.end.origin}")
    private static String FRONT_END_ORIGIN;

    public static class PAGINATION {
        public static final String DEFAULT_PAGE_NUMBER = "0";
        public static final String DEFAULT_PAGE_SIZE = "5";
    }

    public static class SORT {
        public static final String DEFAULT_SORT_BY = "id";
        public static final String DEFAULT_SORT_DIRECTION = "asc";
    }
}
