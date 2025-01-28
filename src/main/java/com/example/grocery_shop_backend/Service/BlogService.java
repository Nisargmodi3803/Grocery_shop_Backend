package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.BlogDTO;
import com.example.grocery_shop_backend.Entities.Blog;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            throw new objectNotFoundException("Blog with id " + blogId + " not found");
        }

        return blog;
    }

    public Blog getBlogBySlug(String slug)
    {
        Blog blog = blogRepository.findBlogBySlugTitle(slug);
        if (blog == null) {
            throw new objectNotFoundException("Blog with title " + slug + " not found");
        }
        return blog;
    }

    // Add Blog Service
    @Transactional
    public void addBlog(BlogDTO blogDTO)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        String blogDate = now.format(dateFormatter);
        String cDate = now.format(formatter);

        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setDescription(blogDTO.getDescription());
        blog.setImage_url(blogDTO.getImage());
        blog.setDate(blogDate);
        blog.setKeywords(blogDTO.getKeywords());
        blog.setSlug_title(blogDTO.getSlugTitle());
        blog.setIs_deleted(1);
        blog.setC_date(cDate);
        blogRepository.save(blog);
    }

    // Delete Blog Service
    @Transactional
    public void deleteBlog(int blogId)
    {
        Blog blog = blogRepository.findBlogById(blogId);

        if(blog!=null)
        {
            blog.setIs_deleted(2);
            blogRepository.save(blog);
        }
        else
            throw new objectNotFoundException("Blog with id " + blogId + " not found");
    }

    // Retrieve Blog Service
    @Transactional
    public boolean retrieveBlog(int blogId)
    {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new objectNotFoundException("Blog with id " + blogId + " not found"));

        if(blog.getIs_deleted()==1)
            return false;
        else
        {
            blog.setIs_deleted(1);
            blogRepository.save(blog);
            return true;
        }
    }

    // Update Blog Service
    @Transactional
    public Blog updateBlog(int blogId,BlogDTO blogDTO)
    {
        Blog blog = blogRepository.findBlogById(blogId);

        if (blog != null)
        {
            if(blogDTO.getTitle() != null)
                blog.setTitle(blogDTO.getTitle());
            if(blogDTO.getDescription() != null)
                blog.setDescription(blogDTO.getDescription());
            if(blogDTO.getImage() != null)
                blog.setImage_url(blogDTO.getImage());
            if(blogDTO.getKeywords() != null)
                blog.setKeywords(blogDTO.getKeywords());
            if(blogDTO.getSlugTitle() != null)
                blog.setSlug_title(blogDTO.getSlugTitle());
        }
        else
            throw new objectNotFoundException("Blog with id " + blogId + " not found");

        return blogRepository.save(blog);
    }
}
