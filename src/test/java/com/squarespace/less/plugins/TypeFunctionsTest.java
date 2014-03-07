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

package com.squarespace.less.plugins;

import static com.squarespace.less.core.Constants.FALSE;
import static com.squarespace.less.core.Constants.TRUE;

import org.testng.annotations.Test;

import com.squarespace.less.LessException;
import com.squarespace.less.core.LessHarness;
import com.squarespace.less.core.LessTestBase;
import com.squarespace.less.model.GenericBlock;
import com.squarespace.less.parse.Parselets;


public class TypeFunctionsTest extends LessTestBase {

  @Test
  public void testFunctions() throws LessException {
    GenericBlock defs = defs(
        def("@color", color("#aaa")),
        def("@number", dim(12)),
        def("@string", quoted('"', false, "foo"))
    );

    LessHarness h = new LessHarness(Parselets.FUNCTION_CALL, defs);

    // Colors
    h.evalEquals("iscolor(#123)", TRUE);
    h.evalEquals("iscolor(rgb(1, 2, 3))", TRUE);
    h.evalEquals("iscolor(@color)", TRUE);
    h.evalEquals("iscolor('foo')", FALSE);
    h.evalEquals("iscolor(@number)", FALSE);

    // Keywords
    h.evalEquals("iskeyword(foo)", TRUE);
    h.evalEquals("iskeyword(true)", TRUE);
    h.evalEquals("iskeyword(false)", TRUE);
    h.evalEquals("iskeyword(blue)", FALSE);
    h.evalEquals("iskeyword('abc')", FALSE);
    h.evalEquals("iskeyword(@color)", FALSE);

    // Numbers
    h.evalEquals("isnumber(3.14)", TRUE);
    h.evalEquals("isnumber(10px)", TRUE);
    h.evalEquals("isnumber('foo')", FALSE);
    h.evalEquals("isnumber(@number)", TRUE);
    h.evalEquals("isnumber(@color)", FALSE);

    // Ems
    h.evalEquals("isem(12.3em)", TRUE);
    h.evalEquals("isem(12.3)", FALSE);
    h.evalEquals("isem(1dpi)", FALSE);
    h.evalEquals("isem('foo')", FALSE);

    // Pixels
    h.evalEquals("ispixel(1px)", TRUE);
    h.evalEquals("ispixel(3.14px)", TRUE);
    h.evalEquals("ispixel(3)", FALSE);
    h.evalEquals("ispixel('foo')", FALSE);

    // Strings
    h.evalEquals("isstring('foo')", TRUE);
    h.evalEquals("isstring(@string)", TRUE);
    h.evalEquals("isstring(12)", FALSE);
    h.evalEquals("isstring(@number)", FALSE);

    // Urls
    h.evalEquals("isurl(url('foo'))", TRUE);
    h.evalEquals("isurl(url(http://foo.com/))", TRUE);
    h.evalEquals("isurl(xurl(foo))", FALSE);
    h.evalEquals("isurl(1)", FALSE);
  }

}
