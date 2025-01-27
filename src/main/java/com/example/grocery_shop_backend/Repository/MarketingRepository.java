package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Marketing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketingRepository extends JpaRepository<Marketing, Integer>
{
    @Query("SELECT marketing FROM Marketing marketing WHERE marketing.marketingTagId = :id AND marketing.isDeleted=1")
    Marketing getMarketingTagById(int id);
}
