package com.ijse.bookstore.QueryModel.repository;

import com.ijse.bookstore.QueryModel.entity.OrderQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderQueryRepository extends JpaRepository<OrderQuery,Long>{

    @Query("SELECT o FROM OrderQuery o LEFT JOIN FETCH o.orderDetails WHERE o.userId = :userId")
    List<OrderQuery> findByUserIdWithDetails(@Param("userId") Long userId);

}
