package com.transport.transport.repository;

import com.transport.transport.config.security.user.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Repository
@EnableJpaRepositories

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    Account findAccountByUsername(String username);
    List<Account> findAllByStatusIsNotNull(Pageable pageable);
    Account findAccountByUsernameAndStatus(String username, String status);

    boolean existsByEmail(String email);

    boolean existsByPhone(String username);

    @Query("SELECT a FROM Account a WHERE CONCAT(a.lastname, ' ', a.firstname) LIKE %?1%")
    List<Account> searchAccountsByFullName(String search, Pageable pageable);

    List<Account> getAccountsByRoleAndStatus(String role, String status);

    List<Account> getAccountsByRole(String role);

    @Query("SELECT a FROM Account a WHERE a.email = ?1")
    public Account findByEmail(String email);

    @Query("SELECT a FROM Account a WHERE a.resetPasswordToken = ?1")
    public Account findByResetPasswordToken(String token);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.role = ?1")
    int countAccountsByRole(String role);


    //=======ADMIN=======


    //=======COMPANY=======
    @Query(value = "SELECT ((SELECT COUNT(b.booking_id) as TotalCustomer FROM booking b JOIN accounts a ON b.account_id = a.account_id JOIN trip t ON b.trip_id = t.trip_id JOIN company c2 ON t.company_id = c2.company_id AND b.status = 'DONE' WHERE c2.company_id = 1) + (SELECT COUNT(b.booking_id) as TotalCustomer FROM booking b JOIN customer c ON b.customer_id = c.customer_id JOIN trip t ON b.trip_id = t.trip_id JOIN company c2 ON t.company_id = c2.company_id AND b.status = 'DONE' WHERE c2.company_id = 1) ) AS TotalCustomer", nativeQuery = true)
    int countTotalCustomerByCompanyId(Long id);

    @Query(value = "SELECT ((SELECT COUNT(b.booking_id) as TotalCustomer FROM booking b JOIN accounts a ON b.account_id = a.account_id JOIN trip t ON b.trip_id = t.trip_id JOIN company c2 ON t.company_id = c2.company_id AND b.status = 'DONE' WHERE c2.company_id = 1 AND t.trip_id = 1) + (SELECT COUNT(b.booking_id) as TotalCustomer FROM booking b JOIN customer c ON b.customer_id = c.customer_id JOIN trip t ON b.trip_id = t.trip_id JOIN company c2 ON t.company_id = c2.company_id AND b.status = 'DONE' WHERE c2.company_id = 1 AND t.trip_id = 1) ) AS TotalCustomer", nativeQuery = true)
    int countTotalCustomerByCompanyIdAndTripId(Long id, Long tripId);

    @Query(value = "SELECT ((SELECT COUNT(b.booking_id) as TotalCustomer FROM booking b JOIN accounts a ON b.account_id = a.account_id JOIN trip t ON b.trip_id = t.trip_id JOIN company c2 ON t.company_id = c2.company_id AND b.status = 'DONE' WHERE c2.company_id = 1 AND b.create_booking_time >= DATE(NOW() - INTERVAL 7 DAY)) + (SELECT COUNT(b.booking_id) as TotalCustomer FROM booking b JOIN customer c ON b.customer_id = c.customer_id JOIN trip t ON b.trip_id = t.trip_id JOIN company c2 ON t.company_id = c2.company_id AND b.status = 'DONE' WHERE c2.company_id = 1 AND t.id = ? AND b.create_booking_time >= DATE(NOW() - INTERVAL 7 DAY) )) AS TotalCustomer", nativeQuery = true)
    int countTotalCustomerByCompanyIdAndTripIdLast7Days(Long id, Long tripId);

    @Query(value = "SELECT ((SELECT COUNT(b.booking_id) as TotalCustomer FROM booking b JOIN accounts a ON b.account_id = a.account_id JOIN trip t ON b.trip_id = t.trip_id JOIN company c2 ON t.company_id = c2.company_id AND b.status = 'DONE' WHERE c2.company_id = 1 AND b.create_booking_time >= DATE(NOW() - INTERVAL 7 DAY)) + (SELECT COUNT(b.booking_id) as TotalCustomer FROM booking b JOIN customer c ON b.customer_id = c.customer_id JOIN trip t ON b.trip_id = t.trip_id JOIN company c2 ON t.company_id = c2.company_id AND b.status = 'DONE' WHERE c2.company_id = 1 AND b.create_booking_time >= DATE(NOW() - INTERVAL 7 DAY) )) AS TotalCustomer", nativeQuery = true)
    int countTotalCustomerByCompanyIdLast7Days(Long id);
}
