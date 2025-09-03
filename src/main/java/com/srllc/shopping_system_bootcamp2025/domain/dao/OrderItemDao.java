package com.srllc.shopping_system_bootcamp2025.domain.dao;

import com.srllc.shopping_system_bootcamp2025.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, Long> {
}
