package oliveira.br.com.testetata.model;

public class Form {

    private Integer id;
    private Integer type;
    private String message;
    private String typefield;
    private String hidden;
    private Integer topSpacing;
    private String show;
    private String required;

    public Form() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {this.type = type;}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {this.message = message;}

    public String getTypefield() {
        return typefield;
    }

    public void setTypefield(String typefield) {
        this.typefield = typefield;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public Integer getTopSpacing() {
        return topSpacing;
    }

    public void setTopSpacing(Integer topSpacing) {
        this.topSpacing = topSpacing;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) { this.show = show; }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }
}
