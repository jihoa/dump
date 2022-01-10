package org.springframework.samples.petclinic.hello;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // JPQL select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);

//	Optional<Member> findByNameAndId(String name, Long Id);

//	Optional<Member> findByNameOrId(String name, Long id);
}
