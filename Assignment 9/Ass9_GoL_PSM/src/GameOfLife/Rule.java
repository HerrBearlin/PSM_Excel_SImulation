package GameOfLife;

public class Rule {

    public String ruleForAliveCells;

    public String ruleForDeadCells;
    public Rule() {
        this.ruleForAliveCells = "2,3";
        this.ruleForDeadCells = "3";
    }

    public String getRuleForAliveCells() {
        return ruleForAliveCells;
    }

    public void setRuleForAliveCells(String ruleForAliveCells) {
        this.ruleForAliveCells = ruleForAliveCells;
    }

    public String getRuleForDeadCells() {
        return ruleForDeadCells;
    }

    public void setRuleForDeadCells(String ruleForDeadCells) {
        this.ruleForDeadCells = ruleForDeadCells;
    }
}
