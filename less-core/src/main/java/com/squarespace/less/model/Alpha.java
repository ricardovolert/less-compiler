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

import static com.squarespace.less.core.LessUtils.safeEquals;
import static com.squarespace.less.model.NodeType.ALPHA;

import com.squarespace.less.LessException;
import com.squarespace.less.core.Buffer;
import com.squarespace.less.core.LessInternalException;
import com.squarespace.less.exec.ExecEnv;


/**
 * Special property for image transparency in IE8 and earlier.
 */
public class Alpha extends BaseNode {

  /**
   * Opacity value.
   */
  protected final Node value;

  /**
   * Construct a node with the given opacity value.
   */
  public Alpha(Node value) {
    if (value == null) {
      throw new LessInternalException("Serious error: value cannot be null.");
    }
    this.value = value;
  }

  /**
   * Returns a copy of this node.
   */
  public Alpha copy() {
    return new Alpha(value);
  }

  /**
   * Return the opacity value.
   */
  public Node value() {
    return value;
  }

  /**
   * See {@link Node#needsEval()}
   */
  @Override
  public boolean needsEval() {
    return value.needsEval();
  }

  /**
   * See {@link Node#eval(ExecEnv)}
   */
  @Override
  public Node eval(ExecEnv env) throws LessException {
    return needsEval() ? new Alpha(value.eval(env)) : this;
  }

  /**
   * See {@link Node#type()}
   */
  @Override
  public NodeType type() {
    return ALPHA;
  }

  /**
   * See {@link Node#repr(Buffer)}
   */
  @Override
  public void repr(Buffer buf) {
    buf.append("alpha(opacity=");
    value.repr(buf);
    buf.append(')');
  }

  /**
   * See {@link Node#modelRepr(Buffer)}
   */
  @Override
  public void modelRepr(Buffer buf) {
    typeRepr(buf);
    posRepr(buf);
    buf.append('\n');
    buf.incrIndent();
    buf.indent();
    value.modelRepr(buf);
    buf.decrIndent();
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Alpha) ? safeEquals(value, ((Alpha)obj).value) : false;
  }

  @Override
  public int hashCode() {
    return hashCode == 0 ? buildHashCode(value) : hashCode;
  }

}
