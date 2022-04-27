package rssapijava.resource;

import rssapijava.entity.Term;

public class TermResource {
    private Integer id;
    private String name;
    private String text;

    public TermResource() {}

    public TermResource(Term term) {
        this.id = term.getId();
        this.name = term.getName();
        this.text = term.getText();
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Term toEntity() {
        return new Term(
                this.id,
                this.name,
                this.text
        );
    }
}
