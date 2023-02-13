package com.transport.transport.repository;

import com.transport.transport.common.RoleEnum;
import com.transport.transport.common.Status;
import com.transport.transport.model.entity.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

    List<Account> findAllByStatusIsNotNull(Pageable pageable);

    Account findAccountByUsernameAndStatus(String username, String status);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String username);

    @Query("SELECT a FROM Account a WHERE CONCAT(a.lastname, ' ', a.firstname) LIKE %?1%")
    List<Account> searchAccountsByFullName(String search, Pageable pageable);

    List<Account> getAccountsByRoleAndStatus(String role, String status);

    List<Account> getAccountsByRole(String role);
    List<Account> findAccountByStatus(String status);

}
