package rssapijava.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import rssapijava.entity.Type;

public class TypeResource {
    private Integer id;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LinksResource[] links;

    public TypeResource() {}

    public TypeResource(Type type) {
        this.id = type.getId();
        this.name = type.getName();
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public LinksResource[] getLinks() { return links; }

    public void setLinks(LinksResource[] links) { this.links = links; }

    public Type toEntity() {
        return new Type(
                this.id,
                this.name
        );
    }
}