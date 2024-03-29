package com.transport.transport.service;

import com.transport.transport.model.entity.FeedBack;
import com.transport.transport.model.request.feedback.FeedbackRequest;
import com.transport.transport.repository.FeedbackRepository;

import java.text.ParseException;
import java.util.List;

public interface FeedbackService {
    FeedBack createFeedback(FeedbackRequest request) throws ParseException;
    public void changeStatus(Long id);
    List<FeedBack> getAllByCompany(Long id);
    List<FeedBack> getAllByUserId(Long id);
    List<FeedBack> getAll();
    FeedBack upadteFeedBack(FeedbackRequest request, Long feedbackId);
    public void approvalFeedback(Long id);

    public void reportFeeback(Long id);
    public void approveReport(Long id);
    public void rejectReport(Long id);
}
