package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Entities.Blog;
import com.example.grocery_shop_backend.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class BlogController
{
    @Autowired
    private BlogService blogService;

    @GetMapping("/blogs")
    public List<Blog> getAllBlogs()
    {
        return blogService.getAllBlogs();
    }

    @GetMapping("/blog-id/{blogId}")
    public Blog getBlogById(@PathVariable int blogId)
    {
        return blogService.getBlogById(blogId);
    }

    @GetMapping("/blog-slug/{slug}")
    public Blog getBlogBySlug(@PathVariable String slug)
    {
        return blogService.getBlogBySlug(slug);
    }
}
