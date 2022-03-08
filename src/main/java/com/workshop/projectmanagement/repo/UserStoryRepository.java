package com.workshop.projectmanagement.repo;

import com.workshop.projectmanagement.entity.UserStoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStoryEntity, Integer> {
}
