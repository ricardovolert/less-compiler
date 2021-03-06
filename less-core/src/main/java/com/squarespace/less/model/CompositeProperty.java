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
import static com.squarespace.less.model.NodeType.COMPOSITE_PROPERTY;

import java.util.ArrayList;
import java.util.List;

import com.squarespace.less.LessException;
import com.squarespace.less.core.Buffer;
import com.squarespace.less.exec.ExecEnv;


/**
 * A property that consists of 2 or more segments, where each
 * segment is of type {@link Property} or curly {@link Variable}.
 */
public class CompositeProperty extends BaseNode implements PropertyMergeable {

  /**
   * List of segments of the composite property, alternately {@link Property}
   * and curl {@link Variable} nodes.
   */
  private final List<Node> segments;

  /**
   * Mode for merging properties having the same name.
   */
  private PropertyMergeMode mergeMode;

  /**
   * Constructs a composite property with the given segments.
   */
  public CompositeProperty(List<Node> segments) {
    this(segments, PropertyMergeMode.NONE);
  }

  /**
   * Construct a composite property with the given segments and merge mode.
   */
  public CompositeProperty(List<Node> segments, PropertyMergeMode mergeMode) {
    this.segments = segments;
    this.mergeMode = mergeMode;
  }

  /**
   * Returns the segments that make up this composite property.
   */
  public List<Node> segments() {
    return segments;
  }

  /**
   * Mode for merging properties having the same name.
   */
  public PropertyMergeMode mergeMode() {
    return mergeMode;
  }

  /**
   * Sets the merge mode for the property.
   */
  public void mergeMode(PropertyMergeMode mergeMode) {
    this.mergeMode = mergeMode;
  }

  /**
   * See {@link Node#type()}
   */
  @Override
  public NodeType type() {
    return COMPOSITE_PROPERTY;
  }

  /**
   * See {@link Node#eval(ExecEnv)}
   */
  @Override
  public Node eval(ExecEnv env) throws LessException {
    List<Node> values = new ArrayList<>(segments.size());
    for (Node segment : segments) {
      Node value = segment.needsEval() ? segment.eval(env) : segment;
      values.add(value);
    }
    return new CompositeProperty(values, mergeMode);
  }

  @Override
  public boolean needsEval() {
    return true;
  }

  /**
   * See {@link Node#repr(Buffer)}
   */
  @Override
  public void repr(Buffer buf) {
    for (Node segment : segments) {
      segment.repr(buf);
    }
    if (mergeMode == PropertyMergeMode.COMMA) {
      buf.append('+');
    } else if (mergeMode == PropertyMergeMode.SPACE) {
      buf.append("+_");
    }
  }

  /**
   * See {@link Node#modelRepr(Buffer)}
   */
  @Override
  public void modelRepr(Buffer buf) {
    typeRepr(buf);
    posRepr(buf);
    if (mergeMode != PropertyMergeMode.NONE) {
      buf.append(" MERGE_" + mergeMode);
    }
    buf.incrIndent();
    for (Node segment : segments) {
      buf.append('\n').indent();
      segment.modelRepr(buf);
    }
    buf.decrIndent();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CompositeProperty) {
      CompositeProperty other = (CompositeProperty)obj;
      return mergeMode == other.mergeMode && safeEquals(segments, other.segments);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

}
