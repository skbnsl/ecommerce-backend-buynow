package com.dailycodework.buynowdotcom.repository;

import com.dailycodework.buynowdotcom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByCategoryName(String category);

    List<Product> findByNameAndBrand(String brand, String name);

    List<Product> findByBrand(String name);

    @Query("SELECT p FROM Product p WHERE LOWER(P.NAME) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByName(String name);

    boolean existByNameAndBrand(String name, String brand);
}