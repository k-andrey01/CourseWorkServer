package rssapijava.controller;

import org.springframework.web.bind.annotation.*;
import rssapijava.entity.Term;
import rssapijava.repository.TermRepository;
import rssapijava.resource.OrganizationResource;
import rssapijava.resource.TermResource;
import rssapijava.resource.TypeResource;

import java.util.Arrays;
import java.util.Set;

@RestController
@RequestMapping(value = "/term")
@CrossOrigin
public class TermController {
    private final TermRepository termRepository;

    public TermController(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    TermResource[] getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Object expand)
    {
        Set<Term> entities = null;
        if((name == null))
        {
            entities = Set.of(termRepository.select());
        }
        else if((name != null))
        {
            Set<Term> entitiesByName = Set.of(termRepository.selectByName(name));
            entities = entitiesByName;
        }
        return entities.stream()
                .map(entity -> {
                    TermResource resource = new TermResource(entity);
                    return resource;
                })
                .toArray(TermResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    TermResource get(@PathVariable Integer id,
                      @RequestParam(required = false) Object expand) {
        Term entity = termRepository.select(id);
        if (entity == null) return null;
        TermResource resource = new TermResource(entity);
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    TermResource post(@RequestBody TermResource resource) {
        Term entity = termRepository.insert(resource.toEntity());
        if (entity == null) return null;
        return new TermResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    TermResource put(@PathVariable Integer id,
                      @RequestBody TermResource resource) {
        Term entity = termRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        return new TermResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    TermResource delete(@PathVariable Integer id) {
        Term entity = termRepository.delete(id);
        if (entity == null) return null;
        return new TermResource(entity);
    }
}
