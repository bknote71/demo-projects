package com.bknote71.muzinsa.infra.repository.nonedip;

import com.bknote71.muzinsa.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
