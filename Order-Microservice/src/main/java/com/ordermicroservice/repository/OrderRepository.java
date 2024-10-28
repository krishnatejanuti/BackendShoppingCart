package com.ordermicroservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordermicroservice.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> 
{
	List<Orders> findByUserId(Long userId);
}
