import { Lexer, Rules } from "moo";
import { compileLexer } from "../Library/Parsing";

const lexingRules: Rules = {
  _: /[ \t]+/,
  float: /-?\d+(?:\.\d*)?/,
  name: /[A-Za-z]\w*/,
  plus: /\+/,
  times: /\*/,
  exponent: /\^/,
  hex: /-?\$[0-9A-Fa-f]+/,
  dash: /-/,
  parenL: /\(/,
  parenR: /\)/,
  equal: /=/,
  comma: /,/,
  char: /'(?:\\['nt\\]|[^'\\])'/
};

export const lexer: Lexer = compileLexer(lexingRules);
