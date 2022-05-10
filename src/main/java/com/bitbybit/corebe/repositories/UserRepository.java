package com.bitbybit.corebe.repositories;

import com.bitbybit.corebe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u from User u WHERE u.username = :username")
    User getUserByUsername(String username);
}
