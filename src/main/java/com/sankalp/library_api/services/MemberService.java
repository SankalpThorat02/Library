package com.sankalp.library_api.services;

import com.sankalp.library_api.exceptions.MemberNotFoundException;
import com.sankalp.library_api.models.Member;
import com.sankalp.library_api.repositories.MemberRepository;
import com.sankalp.library_api.dtos.MemberCreateRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member saveMember(MemberCreateRequest requestDTO) {
        Member newMember = new Member();

        newMember.setFullName(requestDTO.getFullName());
        newMember.setEmail(requestDTO.getEmail());

        return memberRepository.save(newMember);
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }
}
