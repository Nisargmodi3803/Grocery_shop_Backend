package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.BlogDTO;
import com.example.grocery_shop_backend.Entities.Blog;
import com.example.grocery_shop_backend.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class BlogController
{
    @Autowired
    private BlogService blogService;

    // GET API {Find All Blog}
    @GetMapping("/blogs")
    public List<Blog> getAllBlogs()
    {
        return blogService.getAllBlogs();
    }

    // GET API {Find Blog by ID}
    @GetMapping("/blog-id/{blogId}")
    public Blog getBlogById(@PathVariable int blogId)
    {
        return blogService.getBlogById(blogId);
    }

    // GET API {Find Blog By Slug Title}
    @GetMapping("/blog-slug/{slug}")
    public Blog getBlogBySlug(@PathVariable String slug)
    {
        return blogService.getBlogBySlug(slug);
    }

    // POST API {Add Blog}
    @PostMapping("/add-blog")
    public ResponseEntity<String> addBlog(@RequestBody BlogDTO blogDTO)
    {
        try {
            blogService.addBlog(blogDTO);
            return new ResponseEntity<>("Successfully added blog", HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // PATCH API {Delete Blog}
    @PatchMapping("/delete-blog/{blogId}")
    public ResponseEntity<String> deleteBlog(@PathVariable int blogId)
    {
        blogService.deleteBlog(blogId);
        return new ResponseEntity<>("Successfully deleted blog", HttpStatus.OK);
    }

    // PATCH API {Retrieve Blog}
    @PatchMapping("/retrieve-blog/{blogId}")
    public ResponseEntity<String> retrieveBlog(@PathVariable int blogId)
    {
        boolean success = blogService.retrieveBlog(blogId);

        if(success)
            return new ResponseEntity<>("Successfully retrieved blog", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to retrieve blog", HttpStatus.BAD_REQUEST);
    }

    // PATCH API {Update Blog}
    @PatchMapping("update-blog/{blogId}")
    public Blog updateBlog(@PathVariable int blogId, @RequestBody BlogDTO blogDTO)
    {
        return blogService.updateBlog(blogId, blogDTO);
    }
}
