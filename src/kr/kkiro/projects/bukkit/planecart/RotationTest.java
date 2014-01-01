package kr.kkiro.projects.bukkit.planecart;

public class RotationTest {

  public static void main(String[] args) {
    int[][] original = {
        {1, 2, 3, 4},
        {0, 9, 8, 5},
        {9, 8, 7, 6}
    };
    System.out.println("Original:");
    display(original);
    int[][] transposed = transpose(original);
    System.out.println("90 degrees:");
    display(reverseRow(transposed));
    System.out.println("180 degrees:");
    display(reverseColumn(reverseRow(original)));
    System.out.println("270 degrees:");
    display(reverseColumn(transposed));
  }
  
  public static int[][] transpose(int[][] raw) {
    int width = raw.length;
    int height = raw[0].length;
    int[][] copy = new int[height][width];
    for(int y = 0; y < height; ++y) {
      for(int x = 0; x < width; ++x) {
        copy[y][x] = raw[x][y];
      }
    }
    return copy;
  }
  
  public static int[][] reverseRow(int[][] raw) {
    int width = raw[0].length;
    int height = raw.length;
    int[][] copy = new int[height][width];
    for(int y = 0; y < height; ++y) {
      for(int x = 0; x < width; ++x) {
        copy[y][x] = raw[y][width-x-1];
      }
    }
    return copy;
  }
  
  public static int[][] reverseColumn(int[][] raw) {
    int width = raw[0].length;
    int height = raw.length;
    int[][] copy = new int[height][width];
    for(int y = 0; y < height; ++y) {
      for(int x = 0; x < width; ++x) {
        copy[y][x] = raw[height-y-1][x];
      }
    }
    return copy;
  }

  public static void display(int[][] raw) {
    int width = raw[0].length;
    int height = raw.length;
    for(int y = 0; y < height; ++y) {
      for(int x = 0; x < width; ++x) {
        System.out.print(raw[y][x]);
        System.out.print("\t");
      }
      System.out.println();
    }
  }

}
