package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.Member;

public interface MemberRepository {

    List<Member> findAll();

}
