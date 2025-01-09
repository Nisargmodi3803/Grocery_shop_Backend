package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Entities.Blog;
import com.example.grocery_shop_backend.Exception.BlogNotFoundException;
import com.example.grocery_shop_backend.Repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService
{
    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> getAllBlogs()
    {
        return blogRepository.findAll();
    }

    public Blog getBlogById(int blogId)
    {
        Optional<Blog> foundBlog = blogRepository.findById(blogId);
        Blog blog = null;

        if(foundBlog.isPresent())
        {
            blog = foundBlog.get();
        }
        else
        {
            throw new BlogNotFoundException("Blog with id " + blogId + " not found");
        }

        return blog;
    }

    public Blog getBlogBySlug(String slug)
    {
        Blog blog = blogRepository.findBlogBySlugTitle(slug);
        if (blog == null) {
            throw new BlogNotFoundException("Blog with slug " + slug + " not found");
        }
        return blog;
    }
}
