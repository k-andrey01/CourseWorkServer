package rssapijava.resource;

import rssapijava.entity.Place;

public class PlaceResource {
    private Integer id;
    private String name;
    private String address;
    private String phone;

    public PlaceResource() {}

    public PlaceResource(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.address = place.getAddress();
        this.phone = place.getPhone();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Place toEntity() {
        return new Place(
                this.id,
                this.name,
                this.address,
                this.phone
        );
    }
}
