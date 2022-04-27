package rssapijava.entity;

public class Term extends BaseEntity{
    public String name;
    public String text;

    public Term(Integer id, String name, String text){
        super(id);
        this.name = name;
        this.text = text;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }
}