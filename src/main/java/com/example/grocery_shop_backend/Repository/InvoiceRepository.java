package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

//    @Query(value = "SELECT * FROM Invoice WHERE invoice_email_id = :email AND is_deleted = 1 AND invoice_date >= CURDATE() - INTERVAL 30 DAY ORDER BY invoice_num DESC", nativeQuery = true)
//    List<Invoice> findAllLast30DaysInvoice(String email);
//
//    @Query(value = "SELECT * FROM Invoice WHERE invoiceEmailId = :email AND is_deleted = 1 AND invoice_date >= CURDATE() - INTERVAL 90 DAY ORDER BY invoiceNum DESC", nativeQuery = true)
//    List<Invoice> findAllLast90DaysInvoice(String email);
//
//    @Query(value = "SELECT * FROM Invoice WHERE invoiceEmailId = :email AND is_deleted = 1 AND invoice_date >= CURDATE() - INTERVAL 180 DAY ORDER BY invoiceNum DESC", nativeQuery = true)
//    List<Invoice> findAllLast180DaysInvoice(String email);


}
