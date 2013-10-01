package com.squarespace.v6.template.less;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.squarespace.v6.template.less.core.LessHarness;
import com.squarespace.v6.template.less.core.LessTestBase;
import com.squarespace.v6.template.less.model.Block;
import com.squarespace.v6.template.less.model.Node;
import com.squarespace.v6.template.less.parse.Parselets;


public class DirectiveTest extends LessTestBase {

  @Test
  public void testEquals() {
    Block block_xy = block(rule(prop("x"), anon("y")));
    assertEquals(dir("@page", block_xy), dir("@page", block_xy));
  }
  
  @Test
  public void testDirective() throws LessException {
    LessHarness h = new LessHarness(Parselets.DIRECTIVE);
    String[] strings = new String[] {
      "@font-face { color: #fff; }",
      "@page foo { font-size: 12px }",
      "@namespace foo \"blah\";",
      "@left-top { @bottom-left { color: white; } @bottom-right { font: small/12px } }"
    };
    for (String str : strings) {
      Node res = h.parse(str); 
      // XXX: convert to assert
      System.out.println(res);
    }
  }
  
  @Test
  public void testImport() throws LessException {
    LessHarness h = new LessHarness(Parselets.DIRECTIVE);
    String[] strings = new String[] {
        "@import-once 'foo';",
        "@import-once url(foo.less);"
    };
    for (String str : strings) {
      Node res = h.parse(str);
      System.out.println(res);
    }
  }
  
  
  
  @Test
  public void testMediaBlock() throws LessException {
    String[] strings = new String[] {
      "@media {}",
      "@media { color: #fff }",
      "@media foo, bar and (baz: @width) { font-size: 12px; color: black }"
    };

    LessHarness h = new LessHarness(Parselets.STYLESHEET);

    for (String str : strings) {
      Node res = h.parse(str);
      // XXX: convert to assertion
      System.out.println(res);
    }
  }
}