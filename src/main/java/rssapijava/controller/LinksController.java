package rssapijava.controller;

import org.springframework.web.bind.annotation.*;
import rssapijava.entity.Links;
import rssapijava.repository.LinksRepository;
import rssapijava.repository.OrganizationRepository;
import rssapijava.repository.TypeRepository;
import rssapijava.resource.LinksResource;
import rssapijava.resource.OrganizationResource;
import rssapijava.resource.PlaceResource;
import rssapijava.resource.TypeResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/links")
@CrossOrigin
public class LinksController
{
    private final LinksRepository linksRepository;
    private final TypeRepository typeRepository;
    private final OrganizationRepository organizationRepository;

    public LinksController(LinksRepository linksRepository, TypeRepository typeRepository, OrganizationRepository organizationRepository)
    {
        this.linksRepository = linksRepository;
        this.typeRepository = typeRepository;
        this.organizationRepository = organizationRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    LinksResource[] getAll(
            @RequestParam(required = false) Integer type_id,
            @RequestParam(required = false) Integer org_name,
            @RequestParam(required = false) Object expand)
    {
        Set<Links> entities = null;
        if((type_id == null) && (org_name == null))
        {
            entities = Set.of(linksRepository.select());
        }
        else if((type_id == null) && (org_name != null))
        {
            entities = Set.of(linksRepository.selectByOrgName(org_name));
        }
        else if((type_id != null) && (org_name == null))
        {
            entities = Set.of(linksRepository.selectByTypeId(type_id));
        }
        else if((type_id != null) && (org_name != null))
        {
            Set<Links> entitiesByPlace = Set.of(linksRepository.selectByTypeId(type_id));
            Set<Links> entitiesByPerformance = Set.of(linksRepository.selectByOrgName(org_name));
            entities = intersection(entitiesByPlace, entitiesByPerformance);
        }
        return entities.stream()
                .map(entity -> {
                    LinksResource resource = new LinksResource(entity);
                    if (expand != null)
                    {
                        System.out.println(entity.getTypeId().toString());
                        resource.setType(
                                new TypeResource(typeRepository.select(entity.getTypeId()))
                        );
                        resource.setOrganization(
                                new OrganizationResource(organizationRepository.select(entity.getOrgName()))
                        );
                    }
                    return resource;
                })
                .toArray(LinksResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    LinksResource get(
            @PathVariable Integer id,
            @RequestParam(required = false) Object expand) {
        Links entity = linksRepository.select(id);
        if (entity == null) return null;
        LinksResource resource = new LinksResource(entity);
        if (expand != null)
        {
            resource.setType(
                    new TypeResource(typeRepository.select(entity.getTypeId()))
            );
            resource.setOrganization(
                    new OrganizationResource(organizationRepository.select(entity.getOrgName()))
            );
        }
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    LinksResource post(@RequestBody LinksResource resource) {
        Links entity = linksRepository.insert(resource.toEntity());
        if (entity == null) return null;
        return new LinksResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    LinksResource put(@PathVariable Integer id,
                            @RequestBody LinksResource resource) {
        Links entity = linksRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new LinksResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    LinksResource delete(@PathVariable Integer id) {
        Links entity = linksRepository.delete(id);
        if (entity == null) return null;
        LinksResource resource = new LinksResource(entity);
        return resource;
    }

    private Set<Links> intersection(Set<Links> A, Set<Links> B)
    {
        ArrayList<Links> preanswer = new ArrayList<>();
        for(Links a : A)
        {
            for(Links b : B)
            {
                if (a.getId()==(b.getId()))
                {
                    preanswer.add(b);
                }
            }
        }
        return new HashSet<Links>(preanswer);
    }
}