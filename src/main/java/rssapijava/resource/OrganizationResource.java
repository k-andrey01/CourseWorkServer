package rssapijava.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import rssapijava.entity.Organization;
import rssapijava.entity.Type;

public class OrganizationResource {
    private Integer id;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LinksResource[] links;

    public OrganizationResource() {}

    public OrganizationResource(Organization organization) {
        this.id = organization.getId();
        this.name = organization.getName();
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public LinksResource[] getLinks() { return links; }

    public void setLinks(LinksResource[] links) { this.links = links; }

    public Organization toEntity() {
        return new Organization(
                this.id,
                this.name
        );
    }
}