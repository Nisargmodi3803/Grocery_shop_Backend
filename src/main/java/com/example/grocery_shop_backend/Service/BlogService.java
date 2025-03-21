package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.BlogDTO;
import com.example.grocery_shop_backend.Entities.Blog;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService
{
    @Autowired
    private BlogRepository blogRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    public List<Blog> getAllBlogs()
    {
        return blogRepository.findAllBlogs();
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
    public void addBlog(BlogDTO blogDTO) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        String blogDate = now.format(dateFormatter);
        String cDate = now.format(formatter);

        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setDescription(blogDTO.getDescription());
        blog.setDate(blogDate);
        blog.setKeywords(blogDTO.getKeywords());
        blog.setSlug_title(blogDTO.getSlugTitle());
        blog.setIs_deleted(1);
        blog.setC_date(cDate);

        MultipartFile imageFile = blogDTO.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Delete old image if present
            String oldImageName = blog.getImage_url();
            if (oldImageName != null && !oldImageName.isEmpty()) {
                Path oldImagePath = uploadPath.resolve(oldImageName);
                if (Files.exists(oldImagePath)) {
                    Files.delete(oldImagePath);
                }
            }

            // Save new image with brand name (force jpg if needed)
            String fileName = blogDTO.getTitle();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            blog.setImage_url(blogDTO.getTitle());
        }
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
    public void updateBlog(int blogId, BlogDTO blogDTO) throws IOException {
        Blog blog = blogRepository.findBlogById(blogId);

        if (blog != null)
        {
            if(blogDTO.getTitle() != null)
                blog.setTitle(blogDTO.getTitle());
            if(blogDTO.getDescription() != null)
                blog.setDescription(blogDTO.getDescription());
            if(blogDTO.getKeywords() != null)
                blog.setKeywords(blogDTO.getKeywords());
            if(blogDTO.getSlugTitle() != null)
                blog.setSlug_title(blogDTO.getSlugTitle());
            if(blogDTO.getDate() != null){
                blog.setDate(blogDTO.getDate());
            }

            MultipartFile imageFile = blogDTO.getImageFile();
            if (imageFile != null && !imageFile.isEmpty()) {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Delete old image if present
                String oldImageName = blog.getImage_url();
                if (oldImageName != null && !oldImageName.isEmpty()) {
                    Path oldImagePath = uploadPath.resolve(oldImageName);
                    if (Files.exists(oldImagePath)) {
                        Files.delete(oldImagePath);
                    }
                }

                // Save new image with brand name (force jpg if needed)
                String fileName = blogDTO.getTitle();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                blog.setImage_url(blogDTO.getTitle());
            }
        }
        else
            throw new objectNotFoundException("Blog with id " + blogId + " not found");

        blogRepository.save(blog);
    }

    public List<Blog> searchBrand(String keyword){
        List<Blog> blogs = blogRepository.findByTitleContainingIgnoreCaseAndIsDeleted(keyword,1);

        if(blogs.isEmpty()){
            throw new objectNotFoundException("Brand with keyword " + keyword + " not found");
        }
        return blogs;
    }

    // Check Slug Title Service
    public boolean checkSlugTitles(String slugTitle){
        String slug_title = blogRepository.checkSlugTitles(slugTitle);
        if(slug_title != null && !slug_title.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}
