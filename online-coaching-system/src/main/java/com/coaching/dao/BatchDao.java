package com.coaching.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coaching.entity.Batch;

public interface BatchDao extends JpaRepository<Batch, Long>{

}
