package org.springframework.samples.petclinic.restful;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(final String username);
    Optional<Member> findByEmail(final String email);

    @Query("select m from Member m where m.username = ?1")
    Member findByCustom(String username);

}
