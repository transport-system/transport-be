package com.transport.transport.repository;

import com.transport.transport.model.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<FeedBack, Long> {

    List<FeedBack> getAllByCompany_Id(Long id);

    List<FeedBack> getAllByAccount_Id(Long id);
}
