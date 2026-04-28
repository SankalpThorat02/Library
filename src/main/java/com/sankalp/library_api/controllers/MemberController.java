package com.sankalp.library_api.controllers;

import com.sankalp.library_api.dtos.MemberCreateRequest;
import com.sankalp.library_api.models.Member;
import com.sankalp.library_api.services.MemberService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public Member addMember(@Valid @RequestBody MemberCreateRequest requestDTO) {
        return memberService.saveMember(requestDTO);
    }

    @GetMapping("/{id}")
    public Member fetchMember(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }
}
