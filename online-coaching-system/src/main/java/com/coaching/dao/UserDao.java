package com.coaching.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.User;

public interface UserDao extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

}
