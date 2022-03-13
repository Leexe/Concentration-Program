/** 
 * A game board of NxM board of tiles.
 * 
 *  @author PLTW
 * @version 2.0
 */

/** 
 * A Board class for concentration
 */
import java.util.Random;

public class Board
{  
  private static String[] tileValues = {"lion", "lion",
                                        "penguin", "penguin",
                                        "dolphin", "dolphin",
                                        "fox", "fox",
                                        "monkey", "monkey",
                                        "turtle", "turtle"}; 
  private Tile[][] gameboard = new Tile[3][4];

  /**  
   * Constructor for the game. Creates the 2D gameboard
   * by populating it with card values
   * 
   */
  public Board()
  {
    // Debug: The Board is in order of the tileValues array
    // int animalIndex = 0;
    // for (int x = 0; x < gameboard.length; x++) {
    //   for (int y = 0; y < gameboard[0].length; y++) {
    //     gameboard[x][y] = new Tile(tileValues[animalIndex]);
    //     animalIndex++;
    //     // Debug:
    //     // System.out.println(gameboard[x][y]);
    //  }
    //}
    String[] newRandomizeTileValues = new String[tileValues.length];
    randomizePermutation(newRandomizeTileValues);
    int animalIndex = 0;

    for (int x = 0; x < gameboard.length; x++) {
      for (int y = 0; y < gameboard[0].length; y++) {
        gameboard[x][y] = new Tile(newRandomizeTileValues[animalIndex]);
        animalIndex++;
      }
    }
  }

  /**
   * Randomizes a new array with the strings from the
   * tile values array
   * 
   * @param randomizeTileValue an empty string array that you want to populate
   */
  public void randomizePermutation(String[] randomizeTileValue) {
    Random rand = new Random();
    int upperBound = tileValues.length;

    for (int x = 0; x < tileValues.length; x++) {
      int randInt = rand.nextInt(upperBound);
      String tempStr = "";
      randomizeTileValue[x] = tileValues[randInt];
      // Swaps the string that was randomly picked with the upperBound
      tempStr = tileValues[randInt];
      tileValues[randInt] = tileValues[upperBound-1];
      tileValues[upperBound-1] = tempStr;
      upperBound--;
    }
  }

 /** 
   * Returns a string representation of the board, getting the state of
   * each tile. If the tile is showing, displays its value, 
   * otherwise displays it as hidden.
   * 
   * Precondition: gameboard is populated with tiles
   * 
   * @return a string represetation of the board
   */
  public String toString()
  {
    String boardString = "";
    for (Tile[] tileRow : gameboard) {
      for (Tile tile : tileRow){
        if (tile.isShowingValue() == true) {
          boardString += tile.getValue() + "\t";
        }
        else {
          boardString += tile.getHidden() + "\t";
        }
      }
      boardString += "\n";
    }
 
    return boardString;
  }

  /** 
   * Determines if the board is full of tiles that have all been matched,
   * indicating the game is over.
   * 
   * Precondition: gameboard is populated with tiles
   * 
   * @return true if all tiles have been matched, false otherwse
   */
  public boolean allTilesMatch()
  {
    for (Tile[] tileRow : gameboard) {
      for (Tile tile : tileRow) {
        if (tile.matched() != true) {
          return false;
        }
      }
    }

    return true;
  }

  /** 
   * Sets the tile to show its value (like a playing card face up)
   * 
   * Preconditions:
   *   gameboard is populated with tiles,
   *   row values must be in the range of 0 to gameboard.length,
   *   column values must be in the range of 0 to gameboard[0].length
   * 
   * @param row the row value of Tile
   * @param column the column value of Tile
   */
  public void showValue (int row, int column)
  {
    gameboard[row][column].show();
  }  

  /** 
   * Checks if the Tiles in the two locations match.
   * 
   * If Tiles match, show Tiles in matched state and return a "matched" message
   * If Tiles do not match, re-hide Tiles (turn face down).
   * 
   * Preconditions:
   *   gameboard is populated with Tiles,
   *   row values must be in the range of 0 to gameboard.length,
   *   column values must be in the range of 0 to gameboard[0].length
   * 
   * @param row1 the row value of Tile 1
   * @param col1 the column value of Tile 1
   * @param row2 the row value of Tile 2
   * @param col2 the column vlue of Tile 2
   * @return a message indicating whether or not a match occured
   */
  public String checkForMatch(int row1, int col1, int row2, int col2)
  {
    String msg = "";
    Tile tile1 = gameboard[row1][col1];
    Tile tile2 = gameboard[row2][col2];
     if (tile1.getValue().equals(tile2.getValue())) {
       msg = "Tiles Mached!";
       tile1.foundMatch();
       tile2.foundMatch();
     }
     else {
       msg = "Tiles Not Mached! :(";
       tile1.hide();
       tile2.hide();
     }

     return msg;
  }

  /** 
   * Checks the provided values fall within the range of the gameboard's dimension
   * and that the tile has not been previously matched
   * 
   * @param rpw the row value of Tile
   * @param col the column value of Tile
   * @return true if row and col fall on the board and the row,col tile is unmatched, false otherwise
   */
  public boolean validateSelection(int row, int col)
  {
    if(row < 0 || row >= gameboard.length) {
      return false;
    }
    else if(col < 0 || col >= gameboard[0].length) {
      return false;
    }
    else if(gameboard[row][col].matched() == true) {
      return false;
    }

    return true;
  }
}
