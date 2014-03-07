/**
 * Copyright (c) 2014 SQUARESPACE, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.squarespace.less.model;


/**
 * A NodeType can refer to a specific type (COLOR) or an abstract type (ENTITY).
 * It is used for fast identification of nodes, registration of node parsers, etc.
 */
public enum NodeType {

  ALPHA,
  ANONYMOUS,
  ARGUMENT,
  ASSIGNMENT,
  BLOCK,
  BLOCK_DIRECTIVE,
  FUNCTION_CALL,
  COLOR,
  COMMENT,
  CONDITION,
  GENERIC_BLOCK,
  GUARD,
  DEFINITION,
  DIMENSION,
  DIRECTIVE,
  ELEMENT,
  EXPRESSION,
  FALSE,
  FEATURES,
  IMPORT,
  IMPORT_MARKER,
  KEYWORD,
  MEDIA,
  MIXIN,
  MIXIN_ARGS,
  MIXIN_CALL,
  MIXIN_MARKER,
  MIXIN_PARAMS,
  OPERATION,
  PARAMETER,
  PAREN,
  PARSE_ERROR,
  PROPERTY,
  QUOTED,
  RATIO,
  RULE,
  RULESET,
  SELECTOR,
  SELECTORS,
  SHORTHAND,
  STYLESHEET,
  TRUE,
  UNICODE_RANGE,
  URL,
  EXPRESSION_LIST,
  VARIABLE;

}
