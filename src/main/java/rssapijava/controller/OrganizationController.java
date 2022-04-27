package rssapijava.controller;

import org.springframework.web.bind.annotation.*;
import rssapijava.entity.Organization;
import rssapijava.repository.LinksRepository;
import rssapijava.repository.OrganizationRepository;
import rssapijava.repository.TypeRepository;
import rssapijava.resource.LinksResource;
import rssapijava.resource.OrganizationResource;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/organization")
@CrossOrigin
public class OrganizationController {
    private final OrganizationRepository organizationRepository;
    private final LinksRepository linksRepository;

    public OrganizationController(OrganizationRepository organizationRepository, LinksRepository linksRepository) {
        this.organizationRepository = organizationRepository;
        this.linksRepository = linksRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    OrganizationResource[] getAll(@RequestParam(required = false) Object expand) {
        return Arrays.stream(organizationRepository.select())
                .map(entity -> {
                    OrganizationResource resource = new OrganizationResource(entity);
                    if (expand != null)
                        resource.setLinks(
                                Arrays.stream(linksRepository.selectByOrgName(entity.getId()))
                                        .map(e -> new LinksResource(e))
                                        .toArray(LinksResource[]::new)
                        );
                    return resource;
                })
                .toArray(OrganizationResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    OrganizationResource get(@PathVariable Integer id,
                     @RequestParam(required = false) Object expand) {
        Organization entity = organizationRepository.select(id);
        if (entity == null) return null;
        OrganizationResource resource = new OrganizationResource(entity);
        if (expand != null)
            resource.setLinks(
                    Arrays.stream(linksRepository.selectByOrgName(entity.getId()))
                            .map(e -> new LinksResource(e))
                            .toArray(LinksResource[]::new)
            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    OrganizationResource post(@RequestBody OrganizationResource resource) {
        Organization entity = organizationRepository.insert(resource.toEntity());
        if (entity == null) return null;
        return new OrganizationResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    OrganizationResource put(@PathVariable Integer id,
                     @RequestBody OrganizationResource resource) {
        Organization entity = organizationRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        return new OrganizationResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    OrganizationResource delete(@PathVariable Integer id) {
        Organization entity = organizationRepository.delete(id);
        if (entity == null) return null;
        return new OrganizationResource(entity);
    }
}