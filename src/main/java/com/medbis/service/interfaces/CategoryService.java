package com.medbis.service.interfaces;

import com.medbis.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CategoryService {
    List<Category> findAll();

    Category findById(int id);

    void save(Category category);

    void deleteById(int id);

}
