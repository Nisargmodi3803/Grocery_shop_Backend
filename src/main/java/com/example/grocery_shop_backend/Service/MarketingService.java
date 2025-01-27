package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.MarketingDTO;
import com.example.grocery_shop_backend.Entities.Marketing;
import com.example.grocery_shop_backend.Entities.Offer;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.MarketingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MarketingService
{
    @Autowired
    private MarketingRepository marketingRepository;

    // Find Marketing Tag by ID Service
    public Marketing findMarketingTagById(int id)
    {
        Marketing marketing = marketingRepository.getMarketingTagById(id);

        if (marketing == null)
            throw new objectNotFoundException("Marketing Tag not found");
        return marketing;
    }

    // Delete Marketing Tag Service
    @Transactional
    public void deleteMarketingTag(int id)
    {
        Marketing marketing = marketingRepository.getMarketingTagById(id);

        if(marketing == null)
            throw new objectNotFoundException("Marketing not found");
        else
        {
            marketing.setIsDeleted(2);
            marketingRepository.save(marketing);
        }
    }

    // Retrieve Marketing Tag Service
    @Transactional
    public boolean retrieveMarketingTag(int id)
    {
        Marketing marketing = marketingRepository.findById(id)
                .orElseThrow(() -> new objectNotFoundException("Offer id " + id + " not found"));

        if(marketing.getIsDeleted()==1)
            return false;
        else
        {
            marketing.setIsDeleted(1);
            return true;
        }
    }

    // Insert Marketing Tag Service
    public void insertMarketingTag(MarketingDTO marketingDTO)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(formatter);

        Marketing marketing = new Marketing();
        marketing.setMarketingTagTitle(marketingDTO.getTitle());
        marketing.setMarketingTagDescription(marketingDTO.getDescription());
        marketing.setMarketingTagImage(marketingDTO.getImage());
        marketing.setMarketingTagPriority(marketingDTO.getPriority());
        marketing.setIsDeleted(1);
        marketing.setcDate(date);
        marketingRepository.save(marketing);
    }

    // Update Marketing Tag Service
    public Marketing updateMarketingTag(int id, MarketingDTO marketingDTO)
    {
        Marketing existingMarketing = marketingRepository.getMarketingTagById(id);

        if(existingMarketing == null)
            throw new objectNotFoundException("Marketing id " + id + " not found");
        else
        {
            if(marketingDTO.getTitle() != null)
                existingMarketing.setMarketingTagTitle(marketingDTO.getTitle());
            if(marketingDTO.getDescription() != null)
                existingMarketing.setMarketingTagDescription(marketingDTO.getDescription());
            if(marketingDTO.getImage() != null)
                existingMarketing.setMarketingTagImage(marketingDTO.getImage());
            if(marketingDTO.getPriority()!=0)
                existingMarketing.setMarketingTagPriority(marketingDTO.getPriority());
        }

        return marketingRepository.save(existingMarketing);
    }
}


