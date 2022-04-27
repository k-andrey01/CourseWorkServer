package rssapijava.entity;

public class Food extends BaseEntity{
    public String name;
    public String address;
    public String site;


    public Food(Integer id, String name, String address, String site){
        super(id);
        this.name = name;
        this.address = address;
        this.site = site;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getSite() { return site; }

    public void setSite(String site) { this.site = site; }
}