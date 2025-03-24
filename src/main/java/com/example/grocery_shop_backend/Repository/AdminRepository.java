package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>
{
    @Query("SELECT admin FROM Admin admin WHERE admin.userName= :userName AND admin.isDeleted=1")
    public Admin findByUserName(String userName);

    @Query("SELECT admin.password FROM Admin admin WHERE admin.userName= :userName AND admin.isDeleted=1")
    public String findPasswordByUserName(String userName);
}
