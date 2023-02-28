package com.transport.transport.model.response.feeedback;

import com.transport.transport.model.response.trip.TripResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class FeedbackMsg {
    private String message;
    private FeedbackResponse data;
    private List<FeedbackResponse> list_trip;
    public FeedbackMsg(String message, FeedbackResponse feedbackResponse) {
        this.message = message;
        this.data = feedbackResponse;
    }
    public FeedbackMsg(String message, List<FeedbackResponse> list) {
        this.message = message;
        this.list_trip = list;
    }
}
