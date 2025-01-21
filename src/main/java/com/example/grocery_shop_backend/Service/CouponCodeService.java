package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.CouponDatesUpdateDTO;
import com.example.grocery_shop_backend.Entities.CouponCode;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CouponCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CouponCodeService
{
    @Autowired
    private CouponCodeRepository couponCodeRepository;

    // Find All Coupon Service
    public List<CouponCode> findAllCoupons()
    {
        List<CouponCode> couponCodes = couponCodeRepository.findAllCouponCode();
        if (couponCodes.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return couponCodes;
    }

    // Find All General Coupons Service
    public List<CouponCode> findAllGeneralCoupons(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<CouponCode> validCoupons = couponCodeRepository.findAllGeneralCouponCode(date);
        if (validCoupons.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return validCoupons;
    }

    // Find All Secret Coupons Service
    public List<CouponCode> findAllSecretCoupons(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<CouponCode> validCoupons = couponCodeRepository.findAllSecretCouponCode(date);
        if (validCoupons.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return validCoupons;
    }

    // Find All Fixed Amount Coupons Service
    public List<CouponCode> findAllFixedAmtCoupons(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<CouponCode> validCoupons = couponCodeRepository.findAllFixedAmtCouponCode(date);
        if (validCoupons.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return validCoupons;
    }

    //Find All Percentage Coupons Service
    public List<CouponCode> findAllPercentageCoupons(String userDate)
    {
        LocalDate date = LocalDate.parse(userDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<CouponCode> validCoupons = couponCodeRepository.findAllPercentageCouponCode(date);
        if (validCoupons.isEmpty())
            throw new objectNotFoundException("No coupons found");
        return validCoupons;
    }

    // Update start-date & end-date Service
    public CouponCode updateDates(int couponCodeId,CouponDatesUpdateDTO couponDates)
    {
        CouponCode couponCode = couponCodeRepository.findById(couponCodeId)
                .orElseThrow(() -> new objectNotFoundException("Coupon Code Not Found"));
        if(couponDates!=null)
        {
            if(couponDates.getStartDate()!=null)
                couponCode.setCouponStartDate(couponDates.getStartDate());
            if (couponDates.getEndDate()!=null)
                couponCode.setCouponEndDate(couponDates.getEndDate());
        }
        else
        {
            throw new objectNotFoundException("No Update Date Found");
        }
        return couponCodeRepository.save(couponCode);
    }
}
