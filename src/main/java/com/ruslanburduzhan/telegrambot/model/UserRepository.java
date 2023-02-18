package com.ruslanburduzhan.telegrambot.model;


import com.ruslanburduzhan.telegrambot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByChatId(long chatId);

    @Query(value = "select * from users_data u where chat_id != :id", nativeQuery = true)
    List<User> findAllForSendAll(@Param("id") long chatId);
}
