package com.transport.transport.repository;

import com.transport.transport.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByCompanyName(String name);

   // @Query("SELECT c FROM Company c WHERE c.account.username = ?1")
    Company findCompanyByAccount_Username(String username);

    @Query("SELECT c FROM Company c WHERE c.account.id = ?1")
    Company findCompanyByAccount_Id(Long id);
}
