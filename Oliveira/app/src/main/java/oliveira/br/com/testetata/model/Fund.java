package oliveira.br.com.testetata.model;

import java.util.List;

public class Fund {

    private String title;
    private String fundName;
    private String whatIs;
    private String definition;
    private String riskTitle;
    private String risk;
    private String infoTitle;
    private List<String> info;
    private List<String> downInfo;

    public Fund() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }


    public void setWhatIs(String whatIs) {
        this.whatIs = whatIs;
    }

    public String getWhatIs() {
        return whatIs;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDefinition() {
        return definition;
    }

    public void setRiskTitle(String riskTitle) {
        this.riskTitle = riskTitle;
    }

    public String getRiskTitle() {
        return riskTitle;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    public String getRisk() {
        return risk;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) { this.info = info; }

    public List<String> getDownInfo() {
        return downInfo;
    }

    public void setDownInfo(List<String> downInfo) { this.downInfo = downInfo; }
  }
