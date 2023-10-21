package com.basic.sample.repository;

import java.util.Optional;

import com.basic.sample.model.AttackAwsDetailBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AttackerAwsDetailRepository extends JpaRepository<AttackAwsDetailBo, Long> {

	Optional<AttackAwsDetailBo> findByUser(Long user);
}
