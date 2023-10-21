package com.basic.sample.repository;

import java.util.List;
import java.util.Optional;

import com.basic.sample.dto.AttackType;
import com.basic.sample.model.AttackSummaryBo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AttackSummaryRepository extends JpaRepository<AttackSummaryBo, Long> {

	
	List<AttackSummaryBo> findAllByAttackIdAndParentIdIsNull(String attackId);

	
	List<AttackSummaryBo> findAllByAttackId(String attackId);
	
	List<AttackSummaryBo> findByIdIn(List<Long> ids);
	
	Optional<AttackSummaryBo> findFirstByActionOrderByTimeStampDesc(AttackType attackType);
	
	@Query(nativeQuery = true,value =  "select * from attack_summary where cloudstate_id = ? limit 1")
	Optional<AttackSummaryBo> findByCloudStateId(Long cloudStateId);
	
	@Query(nativeQuery = true,value =  "SELECT resource_type, count(*) FROM attack_summary where user_id = ? group by resource_type")
	List<Object[]> statsByServices(Long userId);
	
	@Query(nativeQuery = true,value =  "SELECT action, count(*) FROM attack_summary where user_id = ? group by action")
	List<Object[]> statsByAttackTypes(Long userId);
	
	@Query(nativeQuery = true,value =  "SELECT status, count(*) FROM attack_summary where user_id = ? group by status")
	List<Object[]> statsByAttackTotal(Long userId);
	
	@Query(nativeQuery = true, value =  "SELECT DATE_FORMAT(timeStamp, '%M') AS month, COUNT(*) FROM attack_summary WHERE user_id = ? GROUP BY month")
	List<Object[]> statsByAttackMonthlyCount(Long userId);
	
	@Query(nativeQuery = true, value =  "SELECT WEEK(a.timeStamp), count(*) from attack_summary a where a.user_id = ? group by WEEK(a.timeStamp)")
	List<Object[]> statsByAttackWeeklyCount(Long userId);
	
	@Query(nativeQuery = true, value =  "SELECT DATE_FORMAT(timeStamp, '%a') AS m, COUNT(*) FROM attack_summary WHERE user_id = ? GROUP BY m")
	List<Object[]> statsByAttackDayCount(Long userId);
	
	Page<AttackSummaryBo> findAllByUserIdOrderByTimeStampDesc(Long userId, Pageable pageable);


	
}
