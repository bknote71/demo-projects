package com.bknote71.muzinsa.controller;

import com.bknote71.muzinsa.dto.account.AccountDto;
import com.bknote71.muzinsa.dto.account.MemberDto;
import com.bknote71.muzinsa.dto.account.PartnerDto;
import com.bknote71.muzinsa.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/accounts/register/member")
    public Long registerMember(@RequestBody MemberDto dto) {
        return accountService.registerMember(dto);
    }

    @PostMapping("/accounts/register/partner")
    public Long registerPartner(@RequestBody PartnerDto dto) {
        return accountService.registerPartner(dto);
    }
}
