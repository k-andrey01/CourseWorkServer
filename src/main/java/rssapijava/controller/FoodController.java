package rssapijava.controller;

import org.springframework.web.bind.annotation.*;
import rssapijava.entity.Food;
import rssapijava.repository.FoodRepository;
import rssapijava.resource.FoodResource;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/food")
@CrossOrigin
public class FoodController {
    private final FoodRepository foodRepository;

    public FoodController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    FoodResource[] getAll(@RequestParam(required = false) Object expand) {
        return Arrays.stream(foodRepository.select())
                .map(entity -> {
                    FoodResource resource = new FoodResource(entity);
                    return resource;
                })
                .toArray(FoodResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    FoodResource get(@PathVariable Integer id,
                      @RequestParam(required = false) Object expand) {
        Food entity = foodRepository.select(id);
        if (entity == null) return null;
        FoodResource resource = new FoodResource(entity);
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    FoodResource post(@RequestBody FoodResource resource) {
        Food entity = foodRepository.insert(resource.toEntity());
        if (entity == null) return null;
        return new FoodResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    FoodResource put(@PathVariable Integer id,
                      @RequestBody FoodResource resource) {
        Food entity = foodRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        return new FoodResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    FoodResource delete(@PathVariable Integer id) {
        Food entity = foodRepository.delete(id);
        if (entity == null) return null;
        return new FoodResource(entity);
    }
}
