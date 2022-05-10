package com.bitbybit.corebe.DAO;

import com.bitbybit.corebe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {
}
