package org.example.testjpaaot.jpa;

import java.util.Optional;
import java.util.Set;

import jakarta.persistence.LockModeType;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<UserEntity> findByIdAndIdIsNotNull(Long id);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @NullMarked
//    Optional<UserEntity> findById(Long id);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("select u from UserEntity u where u.id = :id")
//    Optional<UserEntity> findLockedById(Long id);
}
