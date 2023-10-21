package com.basic.sample.repository;

import java.util.Optional;

import com.basic.sample.contract.dto.ResourceType;
import com.basic.sample.model.CloudStateBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CloudStateRepository extends JpaRepository<CloudStateBo, Long> {

	Optional<CloudStateBo> findFirstByResourceTypeOrderByTimeStampDesc(ResourceType resourceType);
	Optional<CloudStateBo> findFirstByResourceTypeAndResourceNameOrderByTimeStampDesc(ResourceType resourceType, String resourceName);
}
