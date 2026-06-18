package com.erp.commerce.sales.service;

import com.erp.commerce.sales.dto.CreateSaleRequest;
import com.erp.commerce.sales.dto.SaleDTO;
import com.erp.commerce.sales.entity.Sale;
import com.erp.commerce.sales.entity.Customer;
import com.erp.commerce.sales.repository.SaleRepository;
import com.erp.commerce.sales.repository.CustomerRepository;
import com.erp.commerce.common.exception.BusinessException;
import com.erp.commerce.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SalesService {
    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;

    public SaleDTO createSale(CreateSaleRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        if (request.getSubtotal().signum() <= 0) {
            throw new BusinessException("Subtotal must be greater than 0");
        }

        String invoiceNo = generateInvoiceNumber();
        Sale sale = Sale.builder()
                .invoiceNo(invoiceNo)
                .customerId(request.getCustomerId())
                .branchId(request.getBranchId())
                .soldBy(request.getSoldBy())
                .saleType(request.getSaleType())
                .subtotal(request.getSubtotal())
                .discount(request.getDiscount())
                .tax(request.getTax())
                .grandTotal(request.getSubtotal().subtract(request.getDiscount()).add(request.getTax()))
                .paymentStatus("UNPAID")
                .status("DRAFT")
                .saleDate(LocalDateTime.now())
                .build();

        sale = saleRepository.save(sale);
        log.info("Sale created: {}", sale.getId());

        return mapToDTO(sale, customer);
    }

    public SaleDTO getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
        Customer customer = customerRepository.findById(sale.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return mapToDTO(sale, customer);
    }

    public void confirmSale(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
        sale.setStatus("CONFIRMED");
        saleRepository.save(sale);
        log.info("Sale confirmed: {}", id);
    }

    private String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private SaleDTO mapToDTO(Sale sale, Customer customer) {
        return SaleDTO.builder()
                .id(sale.getId())
                .invoiceNo(sale.getInvoiceNo())
                .customerId(sale.getCustomerId())
                .customerName(customer.getName())
                .branchId(sale.getBranchId())
                .saleType(sale.getSaleType())
                .subtotal(sale.getSubtotal())
                .discount(sale.getDiscount())
                .tax(sale.getTax())
                .grandTotal(sale.getGrandTotal())
                .paymentStatus(sale.getPaymentStatus())
                .status(sale.getStatus())
                .saleDate(sale.getSaleDate())
                .createdAt(sale.getCreatedAt())
                .build();
    }
}
