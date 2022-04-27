package rssapijava.controller;

import org.springframework.web.bind.annotation.*;
import rssapijava.entity.Place;
import rssapijava.repository.PlaceRepository;
import rssapijava.resource.PlaceResource;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/place")
@CrossOrigin
public class PlaceController {
    private final PlaceRepository placeRepository;

    public PlaceController(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    PlaceResource[] getAll(@RequestParam(required = false) Object expand) {
        return Arrays.stream(placeRepository.select())
                .map(entity -> {
                    PlaceResource resource = new PlaceResource(entity);
                    return resource;
                })
                .toArray(PlaceResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    PlaceResource get(@PathVariable Integer id,
                             @RequestParam(required = false) Object expand) {
        Place entity = placeRepository.select(id);
        if (entity == null) return null;
        PlaceResource resource = new PlaceResource(entity);
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    PlaceResource post(@RequestBody PlaceResource resource) {
        Place entity = placeRepository.insert(resource.toEntity());
        if (entity == null) return null;
        return new PlaceResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    PlaceResource put(@PathVariable Integer id,
                             @RequestBody PlaceResource resource) {
        Place entity = placeRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        return new PlaceResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    PlaceResource delete(@PathVariable Integer id) {
        Place entity = placeRepository.delete(id);
        if (entity == null) return null;
        return new PlaceResource(entity);
    }
}
