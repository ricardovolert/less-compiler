package com.squarespace.v6.template.less.model;

import static com.squarespace.v6.template.less.core.Chars.hexchar;

import com.squarespace.v6.template.less.core.Buffer;
import com.squarespace.v6.template.less.core.Chars;


public class RGBColor extends BaseColor {

  private static final int[][] HSV_PERM = new int[][] {
    new int[] { 0, 3, 1 },
    new int[] { 2, 0, 1 },
    new int[] { 1, 0, 3 },
    new int[] { 1, 2, 0 },
    new int[] { 3, 1, 0 },
    new int[] { 0, 1, 2 }
  };

  private final int c0;
  
  private final int c1;

  private final int c2;
  
  private final double alpha;
  
  private boolean keyword;
  
  public RGBColor(double c0, double c1, double c2) {
    this(c0, c1, c2, 1.0);
  }

  public RGBColor(double c0, double c1, double c2, double alpha) {
    this((int)Math.round(c0), (int)Math.round(c1), (int)Math.round(c2), alpha);
  }
  
  public RGBColor(int c0, int c1, int c2) {
    this(c0, c1, c2, 1.0);
  }
  
  public RGBColor(int c0, int c1, int c2, boolean keyword) {
    this(c0, c1, c2, 1.0, keyword);
  }
  
  public RGBColor(int c0, int c1, int c2, double alpha) {
    this(c0, c1, c2, alpha, false);
  }

  public RGBColor(int c0, int c1, int c2, double alpha, boolean keyword) {
    this.c0 = (int)clamp(c0, 0, 255);
    this.c1 = (int)clamp(c1, 0, 255);
    this.c2 = (int)clamp(c2, 0, 255);
    this.alpha = clamp(alpha, 0.0, 1.0);
    this.keyword = keyword;
  }
  
  public Colorspace getColorspace() {
    return Colorspace.RGB;
  }

  public int red() {
    return c0;
  }
  
  public int green() {
    return c1;
  }
  
  public int blue() {
    return c2;
  }
  
  public double alpha() {
    return alpha;
  }
  
  public double luma() {
    return (0.2126 * (c0 / 255.0) + 0.7152 * (c1/255.0) + 0.0722 * (c2/255.0)) * alpha;
  }
  
  public boolean keyword() {
    return keyword && alpha == 1.0;
  }
  
  @Override
  public RGBColor toRGB() {
    return this;
  }
  
  @Override
  public HSLColor toHSL() {
    double r = c0 / 255.0;
    double g = c1 / 255.0;
    double b = c2 / 255.0;

    double max = Math.max(Math.max(r, g), b);
    double min = Math.min(Math.min(r, g), b);
    double h = 0.0;
    double s = 0.0;
    double d = max - min;
    double l = (max + min) / 2.0;

    if (max == min) {
      h = s = 0.0;
    } else {
      s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
      if (max == r) {
        h = (g - b) / d + (g < b ? 6 : 0);
      } else if (max == g) {
        h = (b - r) / d + 2;
      } else if (max == b) {
        h = (r - g) / d + 4;
      }
      h /= 6.0;
    }
    return new HSLColor(h, s, l, alpha);
  }

  public Anonymous toARGB() {
    StringBuilder buf = new StringBuilder();
    int alpha = (int)Math.round(this.alpha * 255);
    buf.append('#');
    hexdigit(buf, alpha);
    hexdigit(buf, c0);
    hexdigit(buf, c1);
    hexdigit(buf, c2);
    return new Anonymous(buf.toString());
  }

  private static void hexdigit(StringBuilder buf, int num) {
    buf.append(hexchar(num >> 4)).append(hexchar(num & 0x0F));

  }
  public static RGBColor fromHSVA(double hue, double saturation, double value, double alpha) {
    hue *= 360;
    int i = (int)Math.floor(( hue / 60) % 6);
    double f = (hue / 60.0) - i;
    double[] values = new double[] {
        value,
        value * (1 - saturation),
        value * (1 - f * saturation),
        value * (1 - (1 - f) * saturation)
    };
    double red = values[HSV_PERM[i][0]] * 255.0;
    double green = values[HSV_PERM[i][1]] * 255.0;
    double blue = values[HSV_PERM[i][2]] * 255.0;
    return new RGBColor(red, green, blue, alpha);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof RGBColor) {
      RGBColor other = (RGBColor)obj;
      return c0 == other.c0 
          && c1 == other.c1 
          && c2 == other.c2 
          && alpha == other.alpha
          && keyword == other.keyword;
    }
    return false;
  }
  
  @Override
  public void repr(Buffer buf) {

    // TODO: this is non-standard for less.js but could mimic what users expect. if
    // a color originates as a keyword (e.g. 'red') and no operations are performed on it,
    // emit it as a keyword. add a compiler option to enable this behavior.
    //
    // if (rgb.keyword()) {
    // String name = Colors.colorToName(rgb);
    // if (name != null) {
    // buf.append(name);
    // return;
    // }
    //
    // // No name matched, so fall through..
    // }

    if (alpha < 1.0) {
      buf.append("rgba(").append(c0).listSep();
      buf.append(c1).listSep();
      buf.append(c2).listSep();
      buf.append(alpha).append(')');

    } else {
      char r0 = Chars.hexchar(c0 >> 4);
      char r1 = Chars.hexchar(c0 & 0x0F);
      char g0 = Chars.hexchar(c1 >> 4);
      char g1 = Chars.hexchar(c1 & 0x0F);
      char b0 = Chars.hexchar(c2 >> 4);
      char b1 = Chars.hexchar(c2 & 0x0F);

      boolean hex3 = (r0 == r1 && g0 == g1 && b0 == b1);

      // Determine if an equivalent color keyword exists that is shorter
      // than its hex string. Some examples: red < #f00 beige < #f5f5dc
      String name = Colors.colorToName(this);
      if (name != null) {
        int len = name.length();
        if ((hex3 && len <= 4) || len <= 7) {
          buf.append(name);
          return;
        }
      }

      buf.append('#');
      if (hex3) {
        buf.append(r0).append(g0).append(b0);

      } else {
        buf.append(r0).append(r1).append(g0).append(g1).append(b0).append(b1);
      }
    }
  }
  
  @Override
  public void modelRepr(Buffer buf) {
    typeRepr(buf);
    buf.append(' ').append(getColorspace().toString()).append(' ');
    buf.append(c0).append(' ').append(c1).append(' ').append(c2).append(' ').append(alpha);
    if (keyword) {
      buf.append(" [from keyword]");
    }
  }


}
