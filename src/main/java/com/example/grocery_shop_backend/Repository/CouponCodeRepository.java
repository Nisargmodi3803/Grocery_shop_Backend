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
    @Query("SELECT coupon FROM CouponCode coupon")
    List<CouponCode> findAllCouponCode();

//    @Query("SELECT coupon FROM CouponCode coupon WHERE (coupon.couponCodeFor=2 AND coupon.couponStatus=1) AND coupon.isDeleted=1")
//    List<CouponCode> findAllGeneralCouponCode();

//    @Query("SELECT coupon FROM CouponCode coupon WHERE (coupon.couponCodeFor=1 AND coupon.couponStatus=1) AND coupon.isDeleted=1")
//    List<CouponCode> findAllSecretCouponCode();

//    @Query("SELECT coupon FROM CouponCode coupon WHERE (coupon.couponType=1 AND coupon.couponStatus=1) AND coupon.isDeleted=1")
//    List<CouponCode> findAllFixedAmtCouponCode();

//    @Query("SELECT coupon FROM CouponCode coupon WHERE (coupon.couponType=2 AND coupon.couponStatus=1) AND coupon.isDeleted=1")
//    List<CouponCode> findAllPercentageCouponCode();

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
