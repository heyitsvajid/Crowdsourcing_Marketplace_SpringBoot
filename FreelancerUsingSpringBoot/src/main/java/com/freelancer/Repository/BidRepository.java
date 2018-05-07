package com.freelancer.Repository;

import com.freelancer.Entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
//    List<Bid> findByProjectIdAndUserId(Long project_id,Long user_id);
    List<Bid> findByProjectId(Long project_id);

}
