package com.medbis.service.impl;

import com.medbis.entity.Category;
import com.medbis.repository.CategoryRepository;
import com.medbis.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category findById(int id) {
        Optional<Category> category = this.categoryRepository.findById(id);
        if(!category.isPresent()){
            throw new RuntimeException("item with" + id + "not found");
        }
        return category.get();
    }

    @Override
    public void save(Category category) {
        this.categoryRepository.save(category);
    }

    @Override
    public void deleteById(int id) {
        this.categoryRepository.deleteById(id);
    }


}
