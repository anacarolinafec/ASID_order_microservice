package com.ijse.bookstore.CommandModel.repository;

import com.ijse.bookstore.CommandModel.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderDetails WHERE o.userId = :userId")
    List<Order> findByUserIdWithDetails(@Param("userId") Long userId);

}
