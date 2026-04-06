package com.flower.d2c.repository;

import com.flower.d2c.model.Web2Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Web2BlockRepository extends JpaRepository<Web2Block, Long> {
}
