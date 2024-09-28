package com.eroom.repositories;

import com.eroom.entities.LoginHistory;
import com.eroom.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from login_history as lh where lh.user_id=:userId and lh.id NOT IN (select lh.id  from login_history as lh where lh.user_id=:userId ORDER BY lh.created_on DESC LIMIT :limit)")
    void deleteLoginHistoryDetails(@Param("userId") Long userId, @Param("limit") int limit);

    List<LoginHistory> findByUserAndLogoutTimeIsNull(User user);
}
