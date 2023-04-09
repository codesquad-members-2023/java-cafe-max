package kr.codesqaud.cafe.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.exception.member.MemberNotFoundException;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long signUp(SignUpRequest signUpRequest) {
        return memberRepository.save(signUpRequest.toMember());
    }

    @Transactional(readOnly = true)
    public MemberResponse findById(long id) {
        return MemberResponse.from(memberRepository.findById(id)
            .orElseThrow(MemberNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        return memberRepository.findAll()
            .stream()
            .sorted(Comparator.comparing(Member::getId)
                .reversed())
            .map(MemberResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public void update(ProfileEditRequest profileEditRequest) {
        Member findMember = memberRepository.findById(profileEditRequest.getId())
            .orElseThrow(MemberNotFoundException::new);
        memberRepository.update(profileEditRequest.toMember(findMember.getCreateDate()));
    }

    @Transactional(readOnly = true)
    public boolean isDuplicateEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean isDuplicateEmailAndId(String email, Long id) {
        return !memberRepository.findByEmail(email)
            .orElseThrow(MemberNotFoundException::new)
            .equalsId(id);
    }

    @Transactional(readOnly = true)
    public boolean isNotSamePassword(Long id, String password) {
        return !memberRepository.findById(id)
            .orElseThrow()
            .equalsPassword(password);
    }
}
