package ryan.mazer;

//Final project
//Landegger Theo
//turtle
//Lough Ryan
//rlough
interface IRoom {
 /**
  * Is this ILoRoom a wall?
  */
 public boolean isWall();
 /**
  * Are there any loops from the given room?
  * @param dir
  * @param that
  */
 public boolean isLoop(String dir, Room that);
}

class Wall implements IRoom {
 /**
  * Is this ILoRoom a wall?
  */
 public boolean isWall() {
     return true;
 }
 /**
  * Are there any loops from the given room?
  * @param that
  */
 public boolean isLoop(String dir, Room that) {
     return false;
 }
}
/**
* Represents a Room
* @author Ryan
* @author Theo
*
*/
class Room implements IRoom {
 Posn coord;
 IRoom north;
 IRoom south;
 IRoom east;
 IRoom west;
 public Room parent;
 /**
  * Constructs a room
  * @param coord
  * @param edges
  */
 public Room(Posn coord, IRoom north, IRoom south, IRoom east, IRoom west) {
     this.coord = coord;
     this.north = north;
     this.south = south;
     this.east = east;
     this.west = west;
     this.parent = null;
 }
 
 /**
  * Is this ILoRoom a wall?
  */
 public boolean isWall() {
     return false;
 }
 /**
  * Adds a room to this room at the given direction
  * @param dir
  * @param r
  */
 public void addRoom(String dir, Room r) {
     if (dir.equals("north")) {
         r.south = this;
         this.north = r;
     }
     else if (dir.equals("south")) {
         r.north = this;
         this.south = r;
     }
     else if (dir.equals("east")) {
         r.west = this;
         this.east = r;
     }
     else if (dir.equals("west")) {
         r.east = this;
         this.west = r;
     }
     else {
         throw new RuntimeException("Error: Not a direction");
     }
 }
 /**
  * Are there any loops from the given room?
  * @param dir
  * @param that
  */
 public boolean isLoop(String dir, Room that) {
     if (this.equals(that)) {
         return true;
     }
     else if (dir.equals("north")) {
         return this.east.isLoop("west", that) ||
                 this.west.isLoop("east", that) ||
                 this.south.isLoop("north", that);
     }
     else if (dir.equals("south")) {
         return this.north.isLoop("south", that) ||
                 this.east.isLoop("west", that) ||
                 this.west.isLoop("east", that);
     }
     else if (dir.equals("east")) {
         return this.north.isLoop("south", that) ||
                 this.west.isLoop("east", that) ||
                 this.south.isLoop("north", that);
     }
     else if (dir.equals("west")) {
         return this.north.isLoop("south", that) ||
                 this.east.isLoop("west", that) ||
                 this.south.isLoop("north", that);
     }
     else {
         throw new RuntimeException("Error: Not a direction");
     }
 }
 
 // Is there a wall in the given direction?
 public boolean hasWall(String dir) {
     if (dir.equals("north")) {
         return this.north.isWall();
     }
     else if (dir.equals("south")) {
         return this.south.isWall();
     }
     else if (dir.equals("east")) {
         return this.east.isWall();
     }
     else if (dir.equals("west")) {
         return this.west.isWall();
     }
     else {
         throw new RuntimeException("Invalid direction");
     }
 }
 
 // For printing tests
 public String toString() {
     return "" + coord.x + "X" + coord.y + "Y";// + east.isWall();
 }

public boolean reachable(Room pastRoom) {
    return this.north.equals(pastRoom) || this.east.equals(pastRoom) ||
            this.west.equals(pastRoom) || this.south.equals(pastRoom) ||
            this.equals(pastRoom);
}
}
