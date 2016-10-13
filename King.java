import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import static kz.Print.*;

/**
 * Created by buffalobill571 on 10/13/2016.
 */
public class King {
    public static void main(String[] args) throws Exception{
      int[][] original = buildMatrice("input.txt");
      print2D(original);
      print();
      long start = System.currentTimeMillis();
      int[][] solution = solve(original);
      print2D(solution);
      print();
      print("Running time of programm is " +
              (System.currentTimeMillis() - start)/1000.0 +
              " second");
    }

}
