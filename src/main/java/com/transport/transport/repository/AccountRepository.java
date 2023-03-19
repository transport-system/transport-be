package com.transport.transport.repository;

import com.transport.transport.config.security.user.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@EnableJpaRepositories

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);

    List<Account> findAllByStatusIsNotNull(Pageable pageable);
    Account findAccountByUsernameAndStatus(String username, String status);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    boolean existsByPhone(String username);

    @Query("SELECT a FROM Account a WHERE CONCAT(a.lastname, ' ', a.firstname) LIKE %?1%")
    List<Account> searchAccountsByFullName(String search, Pageable pageable);

    List<Account> getAccountsByRoleAndStatus(String role, String status);

    List<Account> getAccountsByRole(String role);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.role = ?1")
    int countAccountsByRole(String role);
}
