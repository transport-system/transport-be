package com.transport.transport.service.impl.feedback;

import com.transport.transport.common.Status;
import com.transport.transport.exception.NotFoundException;
import com.transport.transport.model.entity.Company;
import com.transport.transport.model.entity.FeedBack;
import com.transport.transport.model.request.feedback.FeedbackRequest;
import com.transport.transport.repository.AccountRepository;
import com.transport.transport.repository.CompanyRepository;
import com.transport.transport.repository.FeedbackRepository;
import com.transport.transport.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImplement implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final AccountRepository accountRepository;
    private final CompanyRepository companyRepository;

    @Override
    public FeedBack createFeedback(FeedbackRequest request) throws ParseException {
        FeedBack newFeedback = new FeedBack();
        newFeedback.setDetail(request.getDetail());
        newFeedback.setRatingScore(request.getRatingScore());
        Date date = Date.valueOf(LocalDate.now());
        newFeedback.setCreateTime(date);
        newFeedback.setStatus(Status.Feedback.ACTIVE.name());
        newFeedback.setAccount(accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new NotFoundException("account not found: " + request.getAccountId())));

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new NotFoundException("company not found: " + request.getCompanyId()));

        newFeedback.setCompany(company);

        List<FeedBack> feedbackList = company.getFeedBacks();
        // not call totalRatingScore =0 because it save n size
        int totalRatingScore = (int) (company.getRating() * feedbackList.size());

        int newRatingScore = request.getRatingScore();

        int newTotalRatingScore = totalRatingScore + newRatingScore;

        double newAverageRating = (double) newTotalRatingScore / (feedbackList.size() + 1);
        company.setRating(newAverageRating);
        return feedbackRepository.save(newFeedback);
    }


    @Override
    public void changeStatus(Long id) {
        FeedBack change = feedbackRepository.findById(id).get();
        if (change == null) {
            throw new RuntimeException("Not Esixt Feedback");
        }
        change.setStatus(Status.Feedback.INACTIVE.name());
    }

    @Override
    public List<FeedBack> getAllByCompany(Long id) {
        return feedbackRepository.getAllByCompany_Id(id);
    }

    @Override
    public FeedBack upadteFeedBack(FeedbackRequest request, Long feedbackId) {
        FeedBack feedBack = feedbackRepository.findById(feedbackId).get();
        feedBack.setDetail(request.getDetail());
        feedBack.setRatingScore(request.getRatingScore());
        Date date = Date.valueOf(LocalDate.now());
        feedBack.setCreateTime(date);
        return feedbackRepository.save(feedBack);

    }

    @Override
    public List<FeedBack> getAll() {
        return feedbackRepository.findAll();
    }

}
