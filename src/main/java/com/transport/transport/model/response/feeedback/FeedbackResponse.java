package com.transport.transport.model.response.feeedback;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedbackResponse {
    private Long  feedBackId;
    private Long  accountId;
    private String avatar;
    private String name;
    private int rating;
    private String detail;
    private String date;
}
