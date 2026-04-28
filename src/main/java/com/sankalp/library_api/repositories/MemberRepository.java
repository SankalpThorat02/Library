package com.sankalp.library_api.repositories;

import com.sankalp.library_api.models.Member;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
