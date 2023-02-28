package com.transport.transport.controller.feedback;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.mapper.FeedbackMapper;
import com.transport.transport.mapper.TripMapper;
import com.transport.transport.model.entity.FeedBack;
import com.transport.transport.model.entity.Trip;
import com.transport.transport.model.request.feedback.FeedbackRequest;
import com.transport.transport.model.request.trip.TripRequest;
import com.transport.transport.model.response.conpany.CompanyResponseMsg;
import com.transport.transport.model.response.feeedback.FeedbackMsg;
import com.transport.transport.model.response.feeedback.FeedbackResponse;
import com.transport.transport.model.response.trip.TripMsg;
import com.transport.transport.model.response.trip.TripResponse;
import com.transport.transport.service.FeedbackService;
import com.transport.transport.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

import static com.transport.transport.common.EndpointConstant.Feedback.FEEDBACK_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndpointConstant.Feedback.FEEDBACK_ENDPOINT)
public class FeedBackController {

    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;

    //Customer
    @PostMapping("/create")
    public ResponseEntity<FeedbackMsg> create(@Valid @RequestBody FeedbackRequest request) throws ParseException {
        FeedBack feedBack = feedbackService.createFeedback(request);
        FeedbackResponse response = feedbackMapper.mapFeedbackResponseFromFeedback(feedBack);
        return new ResponseEntity<>(new FeedbackMsg("create success", response), null, 200);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<FeedbackMsg> update(@Valid @RequestBody FeedbackRequest request,
                                                @PathVariable(name = "id") Long id) throws ParseException {
        FeedBack feedBack = feedbackService.upadteFeedBack(request, id);
        FeedbackResponse response = feedbackMapper.mapFeedbackResponseFromFeedback(feedBack);
        return new ResponseEntity<>(new FeedbackMsg("update success", response), null, 200);
    }

    //Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable(name = "id") Long id) {
        feedbackService.changeStatus(id);
        return new ResponseEntity<>(new CompanyResponseMsg("Delete successfully"), null, 204);
    }
    @GetMapping("/all")
    public ResponseEntity<FeedbackMsg> getAll() {
        List<FeedBack> feedBack = feedbackService.getAll();
        List<FeedbackResponse> list =  feedbackMapper.mapToFeedbackResponse(feedBack);
        return new ResponseEntity<>(new FeedbackMsg("List", list), HttpStatus.OK);
    }

    //Company
    @GetMapping("/all/company/{id}")
    public ResponseEntity<FeedbackMsg> getAllOfCompany(@PathVariable(name = "id") Long id) {
        List<FeedBack> feedBack = feedbackService.getAllByCompany(id);
        List<FeedbackResponse> list =  feedbackMapper.mapToFeedbackResponse(feedBack);
        return new ResponseEntity<>(new FeedbackMsg("List", list), HttpStatus.OK);
    }

}

