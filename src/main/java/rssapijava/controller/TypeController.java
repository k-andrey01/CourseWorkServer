package rssapijava.controller;

import org.springframework.web.bind.annotation.*;
import rssapijava.entity.Type;
import rssapijava.repository.LinksRepository;
import rssapijava.repository.TypeRepository;
import rssapijava.resource.LinksResource;
import rssapijava.resource.TypeResource;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/type")
@CrossOrigin
public class TypeController {
    private final TypeRepository typeRepository;
    private final LinksRepository linksRepository;

    public TypeController(TypeRepository typeRepository, LinksRepository linksRepository) {
        this.typeRepository = typeRepository;
        this.linksRepository = linksRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    TypeResource[] getAll(@RequestParam(required = false) Object expand) {
        return Arrays.stream(typeRepository.select())
                .map(entity -> {
                    TypeResource resource = new TypeResource(entity);
                    if (expand != null)
                        resource.setLinks(
                                Arrays.stream(linksRepository.selectByTypeId(entity.getId()))
                                        .map(e -> new LinksResource(e))
                                        .toArray(LinksResource[]::new)
                        );
                    return resource;
                })
                .toArray(TypeResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    TypeResource get(@PathVariable Integer id,
                     @RequestParam(required = false) Object expand) {
        Type entity = typeRepository.select(id);
        if (entity == null) return null;
        TypeResource resource = new TypeResource(entity);
        if (expand != null)
            resource.setLinks(
                    Arrays.stream(linksRepository.selectByTypeId(entity.getId()))
                            .map(e -> new LinksResource(e))
                            .toArray(LinksResource[]::new)
            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    TypeResource post(@RequestBody TypeResource resource) {
        Type entity = typeRepository.insert(resource.toEntity());
        if (entity == null) return null;
        return new TypeResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    TypeResource put(@PathVariable Integer id,
                     @RequestBody TypeResource resource) {
        Type entity = typeRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        return new TypeResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    TypeResource delete(@PathVariable Integer id) {
        Type entity = typeRepository.delete(id);
        if (entity == null) return null;
        return new TypeResource(entity);
    }
}

