package com.squarespace.v6.template.less.model;

import static com.squarespace.v6.template.less.core.LessUtils.safeEquals;

import java.util.List;

import com.squarespace.v6.template.less.core.Buffer;
import com.squarespace.v6.template.less.core.LessInternalException;
import com.squarespace.v6.template.less.core.LessUtils;


/**
 * Represents an attribute pattern match element.
 */
public class AttributeElement extends Element {

  private List<Node> parts;
  
  public AttributeElement(Combinator comb) {
    super(comb);
  }

  public void add(Node part) {
    if (part == null) {
      throw new LessInternalException("Serious error: part cannot be null.");
    }
    parts = LessUtils.initList(parts, 2);
    parts.add(part);
  }
  
  public List<Node> parts() {
    return LessUtils.safeList(parts);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof AttributeElement) {
      AttributeElement other = (AttributeElement)obj;
      return combinator == other.combinator && safeEquals(parts, ((AttributeElement)obj).parts);
    }
    return false;
  }
  
  @Override
  public boolean isWildcard() {
    return false;
  }
  
  @Override
  public Element copy() {
    return new AttributeElement(combinator);
  }
  
  @Override
  public void repr(Buffer buf) {
    buf.append('[');
    for (Node part : parts) {
      part.repr(buf);
    }
    buf.append(']');
  }
  
  @Override
  public void modelRepr(Buffer buf) {
    typeRepr(buf);
    buf.append('\n');
    buf.incrIndent();
    ReprUtils.modelRepr(buf, "\n", true, parts);
    buf.decrIndent();
  }
  
}
