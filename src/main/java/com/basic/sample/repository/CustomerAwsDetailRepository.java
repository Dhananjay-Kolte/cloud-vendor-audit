package com.basic.sample.repository;

import java.util.Optional;

import com.basic.sample.model.CustomerAwsDetailBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAwsDetailRepository extends JpaRepository<CustomerAwsDetailBo, Long> {

	Optional<CustomerAwsDetailBo> findByUser(Long user);
}
