package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Dto.ProductOrderListDTO;
import com.example.grocery_shop_backend.Entities.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer>
{
    @Query("SELECT detail FROM InvoiceDetail detail WHERE (detail.invoice.invoiceId = :invoiceId AND detail.invoice.isDeleted=1) AND detail.isDeleted=1")
    public List<InvoiceDetail> findByInvoiceId(int invoiceId);

    @Query("SELECT detail FROM InvoiceDetail detail WHERE detail.isDeleted=1 AND detail.invoice.invoiceNum = :invoiceNum")
    public List<InvoiceDetail> autoDeleteByInvoiceNum(int invoiceNum);
}
