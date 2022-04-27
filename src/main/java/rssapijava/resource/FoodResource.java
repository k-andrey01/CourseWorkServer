package rssapijava.resource;

import rssapijava.entity.Food;

public class FoodResource {
    private Integer id;
    private String name;
    private String address;
    private String site;

    public FoodResource() {}

    public FoodResource(Food food) {
        this.id = food.getId();
        this.name = food.getName();
        this.address = food.address;
        this.site = food.site;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Food toEntity() {
        return new Food(
                this.id,
                this.name,
                this.address,
                this.site
        );
    }
}
