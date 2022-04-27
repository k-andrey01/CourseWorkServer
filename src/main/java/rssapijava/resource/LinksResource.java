package rssapijava.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import rssapijava.entity.Links;
import rssapijava.entity.Place;

public class LinksResource extends BaseResource {
    private Integer id;
    private Integer type_id;
    private Integer org_name;
    private String text;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TypeResource type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrganizationResource organization;

    public LinksResource() {}

    public LinksResource(Links links) {
        this.id = links.getId();
        this.type_id = links.getTypeId();
        this.org_name = links.getOrgName();
        this.text = links.getText();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public Integer getOrg_name() {
        return org_name;
    }

    public void setOrg_name(Integer org_name) {
        this.org_name = org_name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TypeResource getType() {
        return type;
    }

    public void setType(TypeResource type) {
        this.type = type;
    }

    public OrganizationResource getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationResource organization) {
        this.organization = organization;
    }

    public Links toEntity() {
        return new Links(
                this.id,
                this.type_id,
                this.org_name,
                this.text
        );
    }
}
