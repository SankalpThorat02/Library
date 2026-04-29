package com.sankalp.library_api.controllers;

import com.sankalp.library_api.dtos.MemberCreateRequest;
import com.sankalp.library_api.models.Member;
import com.sankalp.library_api.services.MemberService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberCreateRequest dto) {

            Member updatedMember = memberService.updateMember(id, dto);
            return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
