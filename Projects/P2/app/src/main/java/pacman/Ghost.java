package pacman;
import java.util.ArrayList;
import java.util.HashSet;

public class Ghost {
  String myName;
  Location myLoc;
  Map myMap;

  public Ghost(String name, Location loc, Map map) {
    this.myLoc = loc;
    this.myName = name;
    this.myMap = map;
  }

  public boolean valid_move(Location loc){
    int x = loc.x;
    int y = loc.y;
    HashSet<Type> moves = this.myMap.getLoc(new Location(x,y));
    if(moves.contains(Map.Type.WALL)) {
      return false;
    }
    return true;
  }

  public ArrayList<Location> get_valid_moves() {
    int x = this.myLoc.x;
    int y = this.myLoc.y;
    ArrayList<Location> return_val = new ArrayList();
    return_val.add(new Location(x, y+1));
    return_val.add(new Location(x+1, y+1));
    return_val.add(new Location(x-1, y+1));
    return_val.add(new Location(x+1, y-1));
    return_val.add(new Location(x-1, y-1));
    return_val.add(new Location(x, y-1));
    return_val.add(new Location(x+1, y));
    return_val.add(new Location(x-1, y));
    int i = 0;
    for (Location loc: return_val){
      if (valid_move(loc) == true) {
        i++;
      }else{
        return_val.remove(i);
      }
    }
    return return_val;
  }


  public boolean move() {
    ArrayList<Location> moves = get_valid_moves();
    if (moves.size() == 0) {
      return false;
    }

    // Generating random 3 digit number between 0 and 0.999
    String timeString = String.valueOf(System.currentTimeMillis());
    double rand = (timeString.substring(timeString.length() - 3).parseDouble()) / 1000.0;
    int locNum = (int) (rand * moves.size());

    // Moving ghost
    myLoc = moves.get(locNum);
    myMap.move(myName, myLoc, Map.Type.GHOST);

    return true;
  }

  public boolean is_pacman_in_range() {
    int x = myLoc.x;
    int y = myLoc.y;

    //Right
    HashSet<Type> moves = myMap.getLoc(new Location(x+1,y));
    if(moves.contains(Map.Type.PACMAN)) {
      return true;
    }


    //Up
    moves = myMap.getLoc(new Location(x,y+1));
    if(moves.contains(Map.Type.PACMAN)) {
      return true;
    }

    //Down
    moves = myMap.getLoc(new Location(x,y-1));
    if(moves.contains(Map.Type.PACMAN)) {
      return true;
    }

    //Left
    moves = myMap.getLoc(new Location(x-1,y));
    if(moves.contains(Map.Type.PACMAN)) {
      return true;
    }

  // Diagonals
    moves = myMap.getLoc(new Location(x+1,y+1));
    if(moves.contains(Map.Type.PACMAN)) {
      return true;
    }

    moves = myMap.getLoc(new Location(x-1,y+1));
    if(moves.contains(Map.Type.PACMAN)) {
      return true;
    }

    moves = myMap.getLoc(new Location(x+1,y-1));
    if(moves.contains(Map.Type.PACMAN)) {
      return true;
    }

    moves = myMap.getLoc(new Location(x-1,y-1));
    if(moves.contains(Map.Type.PACMAN)) {
      return true;
    }

    return false;
  }

  public boolean attack() {
    if(is_pacman_in_range()) 
      return myMap.attack(myName);
    else return false;
  }
}
