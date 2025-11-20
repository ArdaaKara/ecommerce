package com.example.ecommerce.service;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

private final CategoryRepository categoryRepository;

    // Constructor injection (Spring otomatik doldurur)
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Bu metod lazım! Admin panelinde kategorileri göstermek için
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // İstersen ek olarak bunları da koy (ileride lazım olabilir)
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

}
