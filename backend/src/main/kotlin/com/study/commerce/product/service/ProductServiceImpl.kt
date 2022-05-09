package com.study.commerce.product.service

import com.study.commerce.product.domain.Product
import com.study.commerce.product.exception.NoDataFoundException
import com.study.commerce.product.repository.ProductRepository
import com.study.commerce.product.service.dto.ProductDto
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly=true)
@Service
class ProductServiceImpl(var productRepository: ProductRepository) : ProductService {

    @Transactional
    override fun addProduct(product: Product): String {
        var result = ""
        if(product.productId != null) {
            productRepository.save(product)
            result = "success"
        }else{
            result="fail"
        }
        return result
    }

    override fun getProduct(): List<Product> {
        return productRepository.findAll()
    }

    override fun getProducts(productIds: List<Long>): List<ProductDto> {
        return productRepository.findAllById(productIds)
            .map { ProductDto.fromEntity(it) }
            .toList()
    }

    @Transactional
    override fun deleteProduct(id: Long) {
        try {
            productRepository.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw NoDataFoundException()
        }
    }
}