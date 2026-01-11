package org.example.testjpaaot.jpa;

import java.util.Optional;
import java.util.Set;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "10000")})
    Optional<UserEntity> findByIdAndIdIsNotNull(Long id);
}
