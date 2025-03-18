package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.MarketingDTO;
import com.example.grocery_shop_backend.Entities.Marketing;
import com.example.grocery_shop_backend.Service.MarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class MarketingController
{
    @Autowired
    private MarketingService marketingService;

    // GET API {Find Marketing Tag by ID}
    @GetMapping("/marketing-tag/{id}")
    public Marketing findMarketingTagById(@PathVariable int id)
    {
        return marketingService.findMarketingTagById(id);
    }

    // PATCH API {Delete Marketing Tag}
    @PatchMapping("delete-marketing-tag/{id}")
    public ResponseEntity<String> deleteMarketingTag(@PathVariable int id)
    {
        marketingService.deleteMarketingTag(id);
        return new ResponseEntity<>("Marketing tag deleted", HttpStatus.OK);
    }

    // PATCH API {Retrieve Marketing Tag}
    @PatchMapping("retrieve-marketing-tag/{id}")
    public ResponseEntity<String> retrieveOffer(@PathVariable int id)
    {
        try {
            if(marketingService.retrieveMarketingTag(id))
                return ResponseEntity.ok("Successfully retrieved offer");
            else
                return ResponseEntity.ofNullable("Already Present[Not Deleted]");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST API {Insert Marketing Tag}
    @PostMapping("/insert-marketing-tag")
    public ResponseEntity<String> insertMarketingTag(@RequestBody MarketingDTO marketingDTO)
    {
        try {
            marketingService.insertMarketingTag(marketingDTO);
            return ResponseEntity.ok("Successfully inserted marketing tag");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PATCH API {Update Marketing Tag}
    @PatchMapping("update-marketing-tag/{id}")
    public Marketing updateMarketingTag(@PathVariable int id, @RequestBody MarketingDTO marketingDTO)
    {
        return marketingService.updateMarketingTag(id, marketingDTO);
    }

}
