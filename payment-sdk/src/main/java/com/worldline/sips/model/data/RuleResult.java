package com.worldline.sips.model.data;

public class RuleResult {
    private RuleCode ruleCode;
    private RuleType ruleType;
    private int ruleWeight;
    private RuleSetting ruleSetting;
    private RuleResultIndicator ruleResultIndicator;
    private String ruleDetailedInfo;

    public RuleCode getRuleCode() {
        return ruleCode;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public int getRuleWeight() {
        return ruleWeight;
    }

    public RuleSetting getRuleSetting() {
        return ruleSetting;
    }

    public RuleResultIndicator getRuleResultIndicator() {
        return ruleResultIndicator;
    }

    public String getRuleDetailedInfo() {
        return ruleDetailedInfo;
    }
}
