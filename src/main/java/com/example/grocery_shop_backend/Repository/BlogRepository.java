package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Blog Repository

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer>
{
    @Query("SELECT blog FROM Blog blog WHERE blog.id = :blogId")
    Blog findBlogById(int blogId);

    @Query("SELECT blog FROM Blog blog WHERE blog.slug_title = :slug_title")
    Blog findBlogBySlugTitle(String slug_title);
}
