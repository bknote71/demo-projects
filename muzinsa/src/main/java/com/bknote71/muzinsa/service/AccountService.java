package com.bknote71.muzinsa.service;

import com.bknote71.muzinsa.domain.account.Account;
import com.bknote71.muzinsa.domain.account.Member;
import com.bknote71.muzinsa.domain.account.Partner;
import com.bknote71.muzinsa.domain.repository.BrandRepository;
import com.bknote71.muzinsa.dto.account.MemberDto;
import com.bknote71.muzinsa.dto.account.PartnerDto;
import com.bknote71.muzinsa.infra.repository.nonedip.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BrandRepository brandRepository;
    private final PasswordEncoder passwordEncoder;

    public Long registerMember(MemberDto dto) {
        if (checkIfAccountAlreadyExists(dto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 유저입니다. 다른 username을 입력하세요.");
        }

        Member member = Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .grade("동")
                .build();

        return accountRepository.save(member).getId();
    }

    public Long registerPartner(PartnerDto dto) {
        checkIfAccountAlreadyExists(dto.getUsername());

        Long brandId = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("존재하는 브랜드 id가 아닙니다."))
                .getId();

        Partner partner = Partner.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .brandId(brandId)
                .build();

        return accountRepository.save(partner).getId();
    }

    public Boolean checkIfAccountAlreadyExists(String username) {
        Optional<Account> optional = accountRepository.findByUsername(username);
        return optional.isPresent();
    }

}
