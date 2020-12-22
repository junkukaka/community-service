package com.community.aspn.test.service;

import com.community.aspn.pojo.Dessert;
import com.community.aspn.test.mapper.DessertMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DessertServiceImpl implements DessertService {

    @Resource
    DessertMapper dessertMapper;

    @Override
    public void saveDessert(Dessert dessert) {
        dessertMapper.insert(dessert);
    }

    @Override
    public void deleteDessert(Integer id) {
        dessertMapper.deleteById(id);
    }

    @Override
    public void updateDessert(Dessert dessert) {
        dessertMapper.updateById(dessert);
    }

    @Override
    public Dessert getDessert(Integer id) {
        return dessertMapper.selectById(id);
    }

    @Override
    public List<Dessert> getAll() {
        return dessertMapper.selectList(null);
    }
}
