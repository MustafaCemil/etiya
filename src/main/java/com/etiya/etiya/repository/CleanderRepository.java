package com.etiya.etiya.repository;

import com.etiya.etiya.entity.Cleander;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CleanderRepository extends JpaRepository<Cleander, Long> {
}

