package org.springframework.samples.petclinic.restful;

import java.util.List;
import org.springframework.samples.petclinic.restful.exception.MemberExistedException;
import org.springframework.samples.petclinic.restful.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService {
    static final boolean DELETE_SUCCESS = true;
    static final boolean DELETE_FAILED = false;

    private final MemberRepository memberRepository;
    public MemberServiceImpl( MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member add(final Member member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new MemberExistedException("member is already joined : " + member.getEmail());
        } else {
            return memberRepository.save(member);
        }
    }

    @Override
    public Member get(final Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new MemberNotFoundException("[findById]Member is not found by : " + id));
    }

    @Override
    public Member update(final Member member) {
        if (memberRepository.findById(member.getId()).isPresent()) {
            memberRepository.save(member);
        } else {
            throw new MemberNotFoundException("[update] Member is not found by : " + member.getId());
        }
        return member;
    }

    @Override
    public boolean delete(final Long id) {
        memberRepository.deleteById(id);
        if (!memberRepository.findById(id).isPresent()) {
            return DELETE_SUCCESS;
        } else {
            return DELETE_FAILED;
        }
    }

    @Override
    public Member findByUsername(final String username) {
        return memberRepository.findByUsername(username).orElseThrow(() ->
                new MemberNotFoundException("[findByUsername] Member is not found by : " + username));
    }

    @Override
    public List<Member> findByAll() {
        return memberRepository.findAll();
    }
}
