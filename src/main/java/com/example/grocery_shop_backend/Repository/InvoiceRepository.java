package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.CouponCode;
import com.example.grocery_shop_backend.Entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>
{
    @Query("SELECT invoice FROM Invoice invoice WHERE invoice.customer.customerId = :customerId AND invoice.isDeleted=1")
    List<Invoice> findByCustomerId(int customerId);

    @Query("SELECT invoice FROM Invoice invoice WHERE invoice.customer.customerMobile = :customerMobile AND invoice.isDeleted=1")
    List<Invoice> findByCustomerMobile(String customerMobile);

    @Query("SELECT invoice FROM Invoice invoice WHERE invoice.invoiceNum = :num AND invoice.isDeleted=1")
    Invoice findByInvoiceNum(int num);

    @Query("SELECT MAX(invoice.invoiceNum) FROM Invoice invoice")
    int findMaxInvoiceNum();

    @Query("SELECT invoice FROM Invoice invoice WHERE invoice.invoiceId = :invoiceId AND invoice.isDeleted=1")
    Invoice findByInvoiceId(int invoiceId);

    @Query("SELECT invoice FROM Invoice invoice WHERE invoice.invoiceEmailId = :email AND invoice.isDeleted=1 ORDER BY invoice.invoiceNum DESC")
    List<Invoice> findInvoiceByEmail(String email);

    @Query("SELECT i FROM Invoice i WHERE STR_TO_DATE(i.invoiceDate, '%d-%m-%Y') BETWEEN STR_TO_DATE(:start, '%d-%m-%Y') AND STR_TO_DATE(:end, '%d-%m-%Y') AND i.isDeleted=1")
    List<Invoice> findInvoiceBetweenDates(@Param("start") String start, @Param("end") String end);

    @Query("SELECT COUNT(invoice) FROM Invoice invoice WHERE invoice.isDeleted=1")
    int countInvoice();

    @Query("SELECT COUNT(invoice) FROM Invoice invoice WHERE invoice.invoiceStatus=1 AND invoice.isDeleted=1")
    int countPendingInvoice();

    @Query("SELECT COUNT(invoice) FROM Invoice invoice WHERE invoice.invoiceStatus=2 AND invoice.isDeleted=1")
    int countConfirmInvoice();

    @Query("SELECT COUNT(invoice) FROM Invoice invoice WHERE invoice.invoiceStatus=3 AND invoice.isDeleted=1")
    int countDispatchedInvoice();

    @Query("SELECT COUNT(invoice) FROM Invoice invoice WHERE invoice.invoiceStatus=4 AND invoice.isDeleted=1")
    int countDeliveredInvoice();

    @Query("SELECT COUNT(invoice) FROM Invoice invoice WHERE invoice.invoiceStatus=5 AND invoice.isDeleted=1")
    int countRejectedInvoice();

    @Query("SELECT COUNT(invoice) FROM Invoice invoice WHERE invoice.invoiceStatus=6 AND invoice.isDeleted=1")
    int countCanceledInvoice();

    @Query("SELECT invoice FROM Invoice invoice WHERE invoice.invoiceStatus=1 AND invoice.invoiceDate= :date AND invoice.isDeleted=1")
    List<Invoice> findInvoiceByDatePending(String date);

    @Query("SELECT invoice FROM Invoice invoice WHERE invoice.invoiceStatus=2 AND invoice.invoiceDate= :date AND invoice.isDeleted=1")
    List<Invoice> findInvoiceByDateConfirm(String date);

    @Query("SELECT invoice FROM Invoice invoice WHERE invoice.invoiceStatus=4 AND invoice.invoiceDate= :date AND invoice.isDeleted=1")
    List<Invoice> findInvoiceByDateDelivered(String date);


//    @Query(value = "SELECT * FROM Invoice WHERE invoice_email_id = :email AND is_deleted = 1 AND invoice_date >= CURDATE() - INTERVAL 30 DAY ORDER BY invoice_num DESC", nativeQuery = true)
//    List<Invoice> findAllLast30DaysInvoice(String email);
//
//    @Query(value = "SELECT * FROM Invoice WHERE invoiceEmailId = :email AND is_deleted = 1 AND invoice_date >= CURDATE() - INTERVAL 90 DAY ORDER BY invoiceNum DESC", nativeQuery = true)
//    List<Invoice> findAllLast90DaysInvoice(String email);
//
//    @Query(value = "SELECT * FROM Invoice WHERE invoiceEmailId = :email AND is_deleted = 1 AND invoice_date >= CURDATE() - INTERVAL 180 DAY ORDER BY invoiceNum DESC", nativeQuery = true)
//    List<Invoice> findAllLast180DaysInvoice(String email);

}
