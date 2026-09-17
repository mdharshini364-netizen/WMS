
package com.examly.springapp.repository;

import com.examly.springapp.entity.Orders;
import com.examly.springapp.entity.PickTask;
import com.examly.springapp.enums.PickTaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickTaskRepository extends JpaRepository<PickTask, Long> {
    List<PickTask> findByOrder(Orders order);
    List<PickTask> findByStatus(PickTaskStatus status);
}

