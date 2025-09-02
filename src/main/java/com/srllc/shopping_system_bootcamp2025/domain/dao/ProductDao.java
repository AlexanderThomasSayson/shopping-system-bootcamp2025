package com.srllc.shopping_system_bootcamp2025.domain.dao;

import com.srllc.shopping_system_bootcamp2025.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
}
