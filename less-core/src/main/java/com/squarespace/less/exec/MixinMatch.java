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

package com.squarespace.less.exec;

import com.squarespace.less.model.Mixin;
import com.squarespace.less.model.MixinParams;
import com.squarespace.less.model.Node;
import com.squarespace.less.model.Ruleset;
import com.squarespace.less.model.Selector;


/**
 * Represents a single match found during mixin resolution.
 */
public class MixinMatch {

  /**
   * The mixin being matched. This can be either a {@link Ruleset} or {@link Mixin}.
   */
  private final Node mixin;

  /**
   * {@link Selector} we're matching.
   */
  private final Selector selector;

  /**
   * Parameters for the {@link Mixin}.
   */
  private final MixinParams params;

  public MixinMatch(Node mixin, Selector selector, MixinParams params) {
    this.mixin = mixin;
    this.selector = selector;
    this.params = params;
  }

  public Node mixin() {
    return mixin;
  }

  public Selector selector() {
    return selector;
  }

  public MixinParams params() {
    return params;
  }

}
