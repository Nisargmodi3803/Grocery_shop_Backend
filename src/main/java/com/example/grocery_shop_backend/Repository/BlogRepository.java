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
    @Query("SELECT blog FROM Blog blog WHERE blog.id = :blogId AND blog.isDeleted=1")
    Blog findBlogById(int blogId);

    @Query("SELECT blog FROM Blog blog WHERE blog.slug_title = :slug_title AND blog.isDeleted=1")
    Blog findBlogBySlugTitle(String slug_title);

    @Query("SELECT blog FROM Blog blog WHERE blog.isDeleted=1 ORDER BY blog.id DESC")
    List<Blog> findAllBlogs();

    List<Blog> findByTitleContainingIgnoreCaseAndIsDeleted(String name,int isDeleted);

    @Query("SELECT b.slug_title FROM Blog b WHERE b.isDeleted=1 AND b.slug_title = :slugTitle")
    public String checkSlugTitles(String slugTitle);
}
