package org.example.testjpaaot.jpa;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("""
        SELECT DISTINCT a.user.id
        FROM AddressEntity a
        WHERE a.street LIKE :streetPattern
        """)
    Set<Long> findDistinctUserIdsByStreetLike(String streetPattern);

    @Modifying
    void deleteByEmailAndIdIsNotNull(String email);

    void deleteByEmail(String email);
}
