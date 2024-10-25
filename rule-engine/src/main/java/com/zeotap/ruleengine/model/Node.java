package com.zeotap.ruleengine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    private String type;    // "operator" or "operand"
    private Node left;
    private Node right;
    private Object value;   // Can be age, salary, etc.
}
