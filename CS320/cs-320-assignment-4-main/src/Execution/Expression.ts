// Our expression evaluation code is almost the same as in the previous
// assignments, just with some type assertions scattered throughout.

// Note that TypeScript will produce a static type error if some of these
// assertions are removed: review src/Execution/TypeAssertions for the
// explanation of how this works.

import { Expr, Value } from "../AST";

import { Scope, lookup } from "./Scope";
import { assertNum, assertBool, assertSameType } from "./TypeAssertions";

export function interpretExpr(
  scope: Scope,
  expr: Expr
): Value {
  switch (expr.tag) {
    case "plus": {
      const leftValue = interpretExpr(scope, expr.leftSubexpr);
      const rightValue = interpretExpr(scope, expr.rightSubexpr);
      assertNum(leftValue);
      assertNum(rightValue);
      return leftValue + rightValue;
    }

    case "minus": {
      const leftValue = interpretExpr(scope, expr.leftSubexpr);
      const rightValue = interpretExpr(scope, expr.rightSubexpr);
      assertNum(leftValue);
      assertNum(rightValue);
      return leftValue - rightValue;
    }

    case "times": {
      const leftValue = interpretExpr(scope, expr.leftSubexpr);
      const rightValue = interpretExpr(scope, expr.rightSubexpr);
      assertNum(leftValue);
      assertNum(rightValue);
      return leftValue * rightValue;
    }

    case "divide": {
      const leftValue = interpretExpr(scope, expr.leftSubexpr);
      const rightValue = interpretExpr(scope, expr.rightSubexpr);
      assertNum(leftValue);
      assertNum(rightValue);
      return leftValue / rightValue;
    }

    case "exponent": {
      const leftValue = interpretExpr(scope, expr.leftSubexpr);
      const rightValue = interpretExpr(scope, expr.rightSubexpr);
      assertNum(leftValue);
      assertNum(rightValue);
      return leftValue ** rightValue;
    }

    case "and": {
      const leftValue = interpretExpr(scope, expr.leftSubexpr);
      const rightValue = interpretExpr(scope, expr.rightSubexpr);
      assertBool(leftValue);
      assertBool(rightValue);
      return leftValue && rightValue;
    }

    case "or": {
      const leftValue = interpretExpr(scope, expr.leftSubexpr);
      const rightValue = interpretExpr(scope, expr.rightSubexpr);
      assertBool(leftValue);
      assertBool(rightValue);
      return leftValue || rightValue;
    }

    case "lessThan": {
      const leftValue = interpretExpr(scope, expr.leftSubexpr);
      const rightValue = interpretExpr(scope, expr.rightSubexpr);
      assertNum(leftValue);
      assertNum(rightValue);
      return leftValue < rightValue;
    }

    case "equal": {
      const leftValue = interpretExpr(scope, expr.leftSubexpr);
      const rightValue = interpretExpr(scope, expr.rightSubexpr);
      assertSameType(leftValue, rightValue);
      return leftValue == rightValue;
    }

    case "negate": {
      const value = interpretExpr(scope, expr.subexpr);
      assertNum(value);
      return - value;
    }

    case "not": {
      const value = interpretExpr(scope, expr.subexpr);
      assertBool(value);
      return ! value;
    }

    case "var":
      return lookup(expr.name, scope);

    case "num":
    case "bool":
      return expr.value;

    case "conditional": {
      const conditionValue = interpretExpr(scope, expr.condition);
      assertBool(conditionValue);
      return interpretExpr(scope, conditionValue ? expr.trueExpr : expr.falseExpr);
    }
  }
}
