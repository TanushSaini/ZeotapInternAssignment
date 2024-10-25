package com.zeotap.ruleengine.dto;

import com.zeotap.ruleengine.model.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleEvalutionRequest {
    private Node rule;
    private Map<String, Object> data;
}
