package com.zeotap.ruleengine.controller;

import com.zeotap.ruleengine.model.Node;
import com.zeotap.ruleengine.services.RuleService;
import com.zeotap.ruleengine.dto.RuleEvalutionRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping("/create")
    public Node createRule(@RequestParam String ruleString) {
        return ruleService.createRule(ruleString);
    }

    @PostMapping("/combine")
    public Node combineRules(@RequestBody String[] ruleStrings) {
        return ruleService.combineRules(ruleStrings);
    }

    @PostMapping("/evaluate")
    public boolean evaluateRule(@RequestBody RuleEvalutionRequest request) {
        return ruleService.evaluateRule(request.getRule(), request.getData());
    }
}
