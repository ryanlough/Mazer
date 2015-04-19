package ryan.mazer;

import java.util.ArrayList;
import java.util.Collections;


// Final project
// Landegger Theo
// turtle
// Lough Ryan
// rlough
/**
 * Represents a maze
 * @author Ryan
 * @author Theo
 *
 */
public class Maze {
    ArrayList<Room> rooms = new ArrayList<Room>();
    ArrayList<Integer> indexes = new ArrayList<Integer>(); 
    ArrayList<KObjs> possibles = new ArrayList<KObjs>();
    int width;
    int height;
    /**
     * Constructs a Maze
     * @param rooms
     * @param edges
     */
    public Maze(int x, int y) {
        this.width = x;
        this.height = y;
        this.fill();
        this.generate();
    }
    
    // Generates a list where each elements index is itself
    public void fill() {
        for (int i = 0; i < height * width; i++) {
            indexes.add(i);
        }
    }
    
    // Generates a maze. Starts with generating all the rooms with all walls
    // Also generates all possible room connections
    public void generate() {
        Wall w = new Wall();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Posn p = new Posn(i, j);
                Room room = new Room(p, w, w, w, w);
                rooms.add(room);
                for (int k = 0; k < 4; k++) {
                    possibles.add(new KObjs(p, getDir(k)));
                }
            }
        }
        Collections.shuffle(possibles);
        addPaths();
    }
    
    // Connects the rooms in the maze
    public void addPaths() {
        int x = 0;
        while (x < (width * height) - 1) {
            KObjs k = possibles.get(0);
            int index = getIndex(rooms, k.pos);
            if (goodDir(k.pos, k.dir) && rooms.get(index).hasWall(k.dir)) {
                Posn newP = getNewPosn(k.pos, k.dir);
                if (checkLoop(k.pos, newP)) {
                    rooms.get(index).addRoom(k.dir, 
                            rooms.get(getIndex(rooms, newP)));
                    x++;
                }
            }
            possibles.remove(0);
        }
    }
    
    // Uses Kruskells to check for loops in the maze
    public boolean checkLoop(Posn p1, Posn p2) {
        int i1 = getIndex(rooms, p1);
        int i2 = getIndex(rooms, p2);
        int par1 = getParent(i1);
        int par2 = getParent(i2); 
        if (par1 == par2) {
            return false;
        }
        else {
            indexes.set(par1, i2);
            return true;
        }
    }
    
    // Returns the final element that is pointed to in an Integer List
    public Integer getParent(int i) {
        int x = indexes.get(i);
        if (x == i) {
            return i;
        }
        else {
            return getParent(x);
        }
    }
    
    
    // Converts number direction to String direction
    public String getDir(int d) {
        if (d == 0) {
            return "north";
        }
        if (d == 1) {
            return "south";
        }
        if (d == 2) {
            return "east";
        }
        if (d == 3) {
            return "west";
        }
        throw new RuntimeException("Not a valid direction");
    }
    
    
    // Gets a rooms index in a list of rooms from the given posn
    public int getIndex(ArrayList<Room> given, Posn p) {
        for (int i = 0; i < given.size(); i++) {
            if (given.get(i).coord.equals(p)) {
                return i;
            }
        }
        throw new RuntimeException("Room not found");
    }
    
    // Gets a room from a list of rooms from the given posn
    public Room getRoom(ArrayList<Room> given, Posn p) {
        return given.get(this.getIndex(given, p));
    }
    
    // Checks if a direction is possible
    public boolean goodDir(Posn p, String dir) {
        if (dir.equals("north")) {
            return p.y > 0;        
        }
        if (dir.equals("south")) {
            return p.y < height - 1;
        }
        if (dir.equals("east")) {
            return p.x < width - 1;
        }
        if (dir.equals("west")) {
            return p.x > 0;
        }
        throw new RuntimeException("Not a valid direction");
    }
    
    // Finds a new Posn in the direction from the given Posn
    public Posn getNewPosn(Posn p, String dir) {
        if (dir.equals("north")) {
            return new Posn(p.x, p.y - 1);    
        }
        if (dir.equals("south")) {
            return new Posn(p.x, p.y + 1);
        }
        if (dir.equals("east")) {
            return new Posn(p.x + 1, p.y);
        }
        if (dir.equals("west")) {
            return new Posn(p.x - 1, p.y);
        }
        throw new RuntimeException("Not a valid direction");
    }
}
