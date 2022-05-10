package com.bitbybit.corebe.repositories;

import com.bitbybit.corebe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
