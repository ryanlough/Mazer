package ryan.mazer;

//Final project
//Landegger Theo
//turtle
//Lough Ryan
//rlough
/** A class for representing Kruskell Algorithm Objects
* Stores a location and a dirrection
* 
* @author Ryan
* @author Theo
*/
public class KObjs {
 Posn pos;
 String dir;
 KObjs(Posn pos, String dir) {
     this.pos = pos;
     this.dir = dir;
 }
 
 // Equals
 public boolean equals(Object o) {
     if (o instanceof KObjs) {
         KObjs k = (KObjs) o;
         return pos.equals(k.pos) && dir.equals(k.dir);
     }
     return false;
 }
}
