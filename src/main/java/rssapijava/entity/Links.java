package rssapijava.entity;

public class Links extends BaseEntity {
    private Integer type_id;
    private Integer org_name;
    private String text;

    public Links(Integer id, Integer org_name, Integer type_id, String text) {
        super(id);
        this.type_id = type_id;
        this.org_name = org_name;
        this.type_id = type_id;
        this.text = text;
    }

    public Integer getTypeId() {
        return type_id;
    }

    public void setTypeId(Integer type_id) {
        this.type_id = type_id;
    }

    public Integer getOrgName() {
        return org_name;
    }

    public void setOrgName(Integer org_name) {
        this.org_name = org_name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}