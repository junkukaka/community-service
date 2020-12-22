package com.community.aspn.test.controller;

import com.community.aspn.pojo.Dessert;
import com.community.aspn.test.service.DessertService;
import com.community.aspn.util.AjaxResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins="*",maxAge=3600)
public class DessertController {

    @Resource
    DessertService dessertService;

    @GetMapping("/desserts/{id}")
    public @ResponseBody AjaxResponse getDessert(@PathVariable Integer id){
        Dessert dessert = dessertService.getDessert(id);
        return AjaxResponse.success(dessert);
    }

    @PostMapping("/desserts")
    public @ResponseBody AjaxResponse insertDessert(@RequestBody Dessert dessert){
        dessertService.saveDessert(dessert);
        return AjaxResponse.success();
    }

    @RequestMapping(value = "/desserts",method = RequestMethod.GET)
    public @ResponseBody AjaxResponse getAll(){
        List<Dessert> all = dessertService.getAll();
        return AjaxResponse.success(all);
    }

    @PutMapping("/desserts")
    public @ResponseBody AjaxResponse updateDessert(@RequestBody Dessert dessert){
        dessertService.updateDessert(dessert);
        return AjaxResponse.success();
    }

    @DeleteMapping("/desserts/{id}")
    public @ResponseBody AjaxResponse deleteDessertById(@PathVariable Integer id){
        dessertService.deleteDessert(id);
        return AjaxResponse.success();
    }


}
