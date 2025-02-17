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
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class BlogController
{
    @Autowired
    private BlogService blogService;

    // GET API {Find All Blog}
    @GetMapping("/blogs")
    public ResponseEntity<List<Blog>> getAllBlogs()
    {
        try{
            return new ResponseEntity<>(blogService.getAllBlogs(), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find Blog by ID}
    @GetMapping("/blog-id/{blogId}")
    public ResponseEntity<Blog> getBlogById(@PathVariable int blogId)
    {
        try{
            return new ResponseEntity<>(blogService.getBlogById(blogId), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find Blog By Slug Title}
    @GetMapping("/blog-slug/{slug}")
    public ResponseEntity<Blog> getBlogBySlug(@PathVariable String slug)
    {
        try{
            return new ResponseEntity<>(blogService.getBlogBySlug(slug), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
            return new ResponseEntity<>("Blog Already Present", HttpStatus.BAD_REQUEST);
    }

    // PATCH API {Update Blog}
    @PatchMapping("update-blog/{blogId}")
    public Blog updateBlog(@PathVariable int blogId, @RequestBody BlogDTO blogDTO)
    {
        return blogService.updateBlog(blogId, blogDTO);
    }
}
