package org.springframework.samples.petclinic.restful;

import java.util.List;

public interface MemberService {
    Member add(final Member member);
    Member get(final Long id);
    Member update(final Member member);
    boolean delete(final Long id);
    Member findByUsername(final String username);
    List<Member> findByAll();
}
