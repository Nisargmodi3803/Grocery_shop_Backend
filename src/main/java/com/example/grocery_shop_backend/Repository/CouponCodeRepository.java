package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.CouponCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CouponCodeRepository extends JpaRepository<CouponCode, Integer>
{
          // For testing
    @Query("SELECT coupon FROM CouponCode coupon WHERE coupon.couponMinimumBillAmount <= :amount AND coupon.isDeleted=1")
    List<CouponCode> findAllCouponCode(double amount);

//    @Query("SELECT coupon FROM CouponCode coupon " +
//            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', coupon.couponStartDate, '%d-%m-%Y') " +
//            "AND FUNCTION('STR_TO_DATE', coupon.couponEndDate, '%d-%m-%Y') " +
//            "AND coupon.isDeleted = 1")
//    List<CouponCode> findAllCouponCode(LocalDate userDate,double amount);

    @Query("SELECT coupon FROM CouponCode coupon " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', coupon.couponStartDate, '%d-%m-%Y') " +
            "AND FUNCTION('STR_TO_DATE', coupon.couponEndDate, '%d-%m-%Y') " +
            "AND coupon.couponStatus = 1 AND coupon.isDeleted = 1 AND coupon.couponCodeFor=2")
    List<CouponCode> findAllGeneralCouponCode(@Param("userDate") LocalDate userDate);

    @Query("SELECT coupon FROM CouponCode coupon " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', coupon.couponStartDate, '%d-%m-%Y') " +
            "AND FUNCTION('STR_TO_DATE', coupon.couponEndDate, '%d-%m-%Y') " +
            "AND coupon.couponStatus = 1 AND coupon.isDeleted = 1 AND coupon.couponCodeFor=1")
    List<CouponCode> findAllSecretCouponCode(@Param("userDate") LocalDate userDate);

    @Query("SELECT coupon FROM CouponCode coupon " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', coupon.couponStartDate, '%d-%m-%Y') " +
            "AND FUNCTION('STR_TO_DATE', coupon.couponEndDate, '%d-%m-%Y') " +
            "AND coupon.couponStatus = 1 AND coupon.isDeleted = 1 AND coupon.couponType=1")
    List<CouponCode> findAllFixedAmtCouponCode(@Param("userDate") LocalDate userDate);

    @Query("SELECT coupon FROM CouponCode coupon " +
            "WHERE :userDate BETWEEN FUNCTION('STR_TO_DATE', coupon.couponStartDate, '%d-%m-%Y') " +
            "AND FUNCTION('STR_TO_DATE', coupon.couponEndDate, '%d-%m-%Y') " +
            "AND coupon.couponStatus = 1 AND coupon.isDeleted = 1 AND coupon.couponType = 2")
    List<CouponCode> findAllPercentageCouponCode(@Param("userDate") LocalDate userDate);

    @Query("SELECT coupon FROM CouponCode coupon WHERE coupon.couponId = :couponId AND coupon.isDeleted=1")
    CouponCode findCouponById(int couponId);

}
