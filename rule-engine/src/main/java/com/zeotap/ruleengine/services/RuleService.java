package com.zeotap.ruleengine.services;

import com.zeotap.ruleengine.model.Node;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Stack;

@Service
public class RuleService {

    public Node createRule(String ruleString) {
        Stack<Node> stack = new Stack<>();
        String[] tokens = ruleString.split(" ");

        for (String token : tokens) {
            if (token.equals("AND") || token.equals("OR")) {
                Node right = stack.pop();
                Node left = stack.pop();
                stack.push(new Node("operator", left, right, token));
            } else if (token.contains(">") || token.contains("=") || token.contains("<")) {
                stack.push(new Node("operand", null, null, token));
            }
        }
        return stack.pop(); // Returns the root of the AST
    }

    public Node combineRules(String[] ruleStrings) {
        Node combinedRoot = createRule(ruleStrings[0]);

        for (int i = 1; i < ruleStrings.length; i++) {
            Node nextRule = createRule(ruleStrings[i]);
            combinedRoot = new Node("operator", combinedRoot, nextRule, "AND"); // Combine with AND
        }
        return combinedRoot;
    }

    public boolean evaluateRule(Node root, Map<String, Object> data) {
        if (root.getType().equals("operand")) {
            return evaluateCondition(root.getValue().toString(), data);
        }

        boolean leftResult = evaluateRule(root.getLeft(), data);
        boolean rightResult = evaluateRule(root.getRight(), data);

        if (root.getValue().equals("AND")) {
            return leftResult && rightResult;
        } else if (root.getValue().equals("OR")) {
            return leftResult || rightResult;
        }
        return false;
    }

    private boolean evaluateCondition(String condition, Map<String, Object> data) {
        String[] parts = condition.split(" ");
        String key = parts[0];
        String operator = parts[1];
        Object value = parts[2];

        Object dataValue = data.get(key);

        switch (operator) {
            case ">":
                return (Integer) dataValue > Integer.parseInt(value.toString());
            case "<":
                return (Integer) dataValue < Integer.parseInt(value.toString());
            case "=":
                return dataValue.equals(value);
            default:
                return false;
        }
    }
}
