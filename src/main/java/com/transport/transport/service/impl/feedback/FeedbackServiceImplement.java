package com.transport.transport.service.impl.feedback;

import com.transport.transport.common.Status;
import com.transport.transport.config.security.user.Account;
import com.transport.transport.model.entity.Customer;
import com.transport.transport.model.entity.FeedBack;
import com.transport.transport.model.request.feedback.FeedbackRequest;
import com.transport.transport.repository.AccountRepository;
import com.transport.transport.repository.CompanyRepository;
import com.transport.transport.repository.CustomerRepository;
import com.transport.transport.repository.FeedbackRepository;
import com.transport.transport.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImplement  implements FeedbackService {

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
        newFeedback.setAccount(accountRepository.findById(request.getAccountId()).get());
        newFeedback.setCompany(companyRepository.findById(request.getCompanyId()).get());
        return feedbackRepository.save(newFeedback);
    }

    @Override
    public void changeStatus(Long id) {
        FeedBack change = feedbackRepository.findById(id).get();
        if(change ==  null){
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
