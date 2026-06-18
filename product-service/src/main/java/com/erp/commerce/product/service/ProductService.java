package com.erp.commerce.product.service;

import com.erp.commerce.product.dto.CreateProductRequest;
import com.erp.commerce.product.dto.ProductDTO;
import com.erp.commerce.product.entity.Product;
import com.erp.commerce.product.repository.ProductRepository;
import com.erp.commerce.product.repository.CategoryRepository;
import com.erp.commerce.product.repository.BrandRepository;
import com.erp.commerce.common.exception.BusinessException;
import com.erp.commerce.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public ProductDTO createProduct(CreateProductRequest request) {
        if (request.getBarcode() != null && productRepository.findByBarcode(request.getBarcode()).isPresent()) {
            throw new BusinessException("Barcode already exists");
        }

        var category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        var brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));

        Product product = Product.builder()
                .name(request.getName())
                .modelNo(request.getModelNo())
                .barcode(request.getBarcode())
                .category(category)
                .brand(brand)
                .buyPrice(request.getBuyPrice())
                .salePrice(request.getSalePrice())
                .warrantyMonths(request.getWarrantyMonths())
                .status("ACTIVE")
                .build();

        product = productRepository.save(product);
        log.info("Product created: {}", product.getId());

        return mapToDTO(product);
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return mapToDTO(product);
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(this::mapToDTO);
    }

    public ProductDTO updateProduct(Long id, CreateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(request.getName());
        product.setModelNo(request.getModelNo());
        product.setBuyPrice(request.getBuyPrice());
        product.setSalePrice(request.getSalePrice());
        product.setWarrantyMonths(request.getWarrantyMonths());

        product = productRepository.save(product);
        return mapToDTO(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setStatus("DELETED");
        productRepository.save(product);
        log.info("Product deleted: {}", id);
    }

    private ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .modelNo(product.getModelNo())
                .barcode(product.getBarcode())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .brandId(product.getBrand().getId())
                .brandName(product.getBrand().getName())
                .buyPrice(product.getBuyPrice())
                .salePrice(product.getSalePrice())
                .warrantyMonths(product.getWarrantyMonths())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
