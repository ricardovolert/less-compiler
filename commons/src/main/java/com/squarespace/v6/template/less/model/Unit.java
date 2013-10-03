package com.squarespace.v6.template.less.model;

import java.util.HashMap;
import java.util.Map;


public enum Unit {

  PERCENTAGE ("%", "percentage"),
  
  // ABSOLUTE LENGTHS

  // centimeters
  CM ("cm", "centimeters"),
  // millimeters
  MM ("mm", "millimeters"),
  // inches (1in == 2.54cm)
  IN ("in", "inches"),
  // pixels (1px == 1/96in)
  PX ("px", "pixels"),
  // points (1pt == 1/72in)
  PT ("pt", "points"),
  // picas  (1pc == 12pt)
  PC ("pc", "picas"),
  
  // FONT-RELATIVE LENGTHS
  
  // width of the '0' (ZERO U+0030) glyph in the element's font
  CH ("ch", "advance measure of '0' glyph"),
  // font size of element
  EM ("em", "element font size"),
  // x-height of the element's font
  EX ("ex", "x-height of element's font"),
  // font size of the root element
  REM ("rem", "font size of root element"),
  
  // VIEWPORT-RELATIVE LENGTHS

  // 1% of viewport's height
  VH ("vh", "viewport's height"),
  // 1% of viewport's width
  VW ("vw", "viewport's width"),
  // 1% of viewport's smaller dimension
  VMIN ("vmin", "viewport's smaller dimension"),
  // 1% of viewport's larger dimension
  VMAX ("vmax", "viewport's larger dimension"),
  // [bug / typo in less 1.3.3 'vm']
  VM ("vm", ""),
  
  
  // TIME
  
  // seconds
  S ("s", "seconds"),
  // milliseconds
  MS ("ms", "milliseconds"),
  
  // RESOLUTIONS
  
  // dots per inch
  DPI ("dpi", "dots per inch"),
  // dots per centimeter
  DPCM ("dpcm", "dots per centimeter"),
  // dots per 'px' unit (1dppx == 96dpi)
  DPPX ("dppx", "dots per 'px' unit"),
  
  // FREQUENCIES
  
  // Hertz
  HZ ("hz", "hertz"),
  // KiloHertz (1khz == 1000hz)
  KHZ ("khz", "kilohertz"),
  
  // ANGLES
  
  // Degrees
  DEG ("deg", "degrees"),
  // Gradians
  GRAD ("grad", "gradians"),
  // Radians
  RAD ("rad", "radians"),
  // Turns
  TURN ("turn", "turns")
  
  ;

  // Keep this sorted roughly by most-used first
  // NOTE: 'vm' is not a real unit, but is added here since less.js thinks its real. 
  public static final String REGEX = 
      "px|%|em|pc|ex|in|deg|s|ms|pt|cm|mm|rad|grad|turn|dpi|dpcm|dppx|rem|vw|vh|vmin|vmax|ch|hz|khz|vm";
  
  private static final Map<String, Unit> UNIT_MAP = new HashMap<>();
  
  static {
    for (Unit unit : Unit.values()) {
      UNIT_MAP.put(unit.repr(), unit);
    }
  }
  
  private final String repr;
  
  private final String humanRepr;
  
  private Unit(String repr, String humanRepr) {
    this.repr = repr;
    this.humanRepr = humanRepr;
  }
  
  public String repr() {
    return repr;
  }
  
  public String humanRepr() {
    return humanRepr;
  }
  
  public static Unit get(String raw) {
    return UNIT_MAP.get(raw.toLowerCase());
  }
  
  /**
   * Format a double to a unit-specific level of precision.
   */
  public String format(double val) {
    // XXX: precision
    return "";
  }
  
  public String toString() {
    return repr.toUpperCase() + (humanRepr.isEmpty() ? "" : " (" + humanRepr + ")");
  }
}
