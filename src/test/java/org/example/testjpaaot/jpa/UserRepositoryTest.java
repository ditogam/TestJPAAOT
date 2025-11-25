package org.example.testjpaaot.jpa;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void shouldFindDistinctUserIdsByStreetLike() {
        // given
        UserEntity user = new UserEntity();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        userRepository.save(user);

        AddressEntity address = new AddressEntity();
        address.setStreet("Main Street 1");
        address.setCity("Tbilisi");
        address.setCountry("Georgia");
        address.setUser(user);
        addressRepository.save(address);

        // when
        Set<Long> result = userRepository.findDistinctUserIdsByStreetLike("%Main%");

        // then
        assertThat(result).containsExactly(user.getId());
    }

    @Test
    void shouldDeleteByEmailAndIdIsNotNull() {
        // given
        UserEntity user = new UserEntity();
        user.setName("Jane Doe");
        user.setEmail("jane@example.com");
        user = userRepository.saveAndFlush(user);

        // when
        userRepository.deleteByEmailAndIdIsNotNull("jane@example.com");
        userRepository.flush();

        // then
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    void shouldDeleteByEmail() {
        // given
        UserEntity user = new UserEntity();
        user.setName("Bob Doe");
        user.setEmail("bob@example.com");
        user = userRepository.saveAndFlush(user);

        // when
        userRepository.deleteByEmail("bob@example.com");
        userRepository.flush();

        // then
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }
}
