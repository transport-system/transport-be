package com.transport.transport.model.request.feedback;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedbackRequest {
    private String detail;
    private int ratingScore;
    private Long bookingId;
    private Long companyId;
    private Long accountId;
}
