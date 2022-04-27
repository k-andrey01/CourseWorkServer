package rssapijava.entity;

public class Organization extends BaseEntity{
    public String name;

    public Organization(Integer id, String name){
        super(id);
        this.name = name;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
