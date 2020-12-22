package com.community.aspn.test.service;

import com.community.aspn.pojo.Dessert;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DessertService {
    void saveDessert(Dessert dessert);
    void deleteDessert(Integer id);
    void updateDessert(Dessert dessert);
    Dessert getDessert(Integer id);
    List<Dessert> getAll();
}
