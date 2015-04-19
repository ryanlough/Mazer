package ryan.mazer;

//import Posn;

//Final project
//Landegger Theo
//turtle
//Lough Ryan
//rlough

/**
* Represents a Posn
* @author Ryan
* @author Theo
*/
public class Posn {
    float x;
    float y;
 /**
  * Constructs a Posn
  * @param f
  * @param g
  */
 public Posn(float f, float g) {
     this.x = f;
     this.y = g;
 }
 /**
  * Is this Posn equal to the given?
  */
 public boolean equals(Object o) {
     if (o instanceof Posn) {
         return this.x == ((Posn)o).x &&
                 this.y == ((Posn)o).y;
     }
     else {
         return false;
     }
 }
public boolean intersect(float x0, float y0, float x1, float y1) {
    return Math.abs((dist(x0, y0, x, y) + dist(x1, y1, x, y)) -
            dist(x0, y0, x1, y1)) < 2;
}

public double dist(float x0, float y0, float x1, float y1) {
    return Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2));
}
}