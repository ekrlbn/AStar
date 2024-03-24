import java.util.*;

public class MazeGenerator {
    private int width;
    private int height;
    private int[][] maze;

    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.maze = new int[height][width];
        generateMaze();
        addStartAndEnd();
    }

    public int[][] generateNewMaze() {
        generateMaze();
        addStartAndEnd();
        return maze;
    }

    private void generateMaze() {
        // Initialize maze with walls
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = 1;
            }
        }

        Random random = new Random();
        int startX = random.nextInt((width - 2) / 2) * 2 + 1;
        int startY = random.nextInt((height - 2) / 2) * 2 + 1;

        dfs(startX, startY);
    }

    private void dfs(int x, int y) {
        maze[y][x] = 0; // Mark current cell as part of the maze

        // Define the order in which to explore neighbors randomly
        int[][] dirs = { { 0, -2 }, { 0, 2 }, { -2, 0 }, { 2, 0 } };
        Collections.shuffle(Arrays.asList(dirs));

        for (int[] dir : dirs) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (newX > 0 && newY > 0 && newX < width - 1 && newY < height - 1 && maze[newY][newX] == 1) {
                int wallX = x + dir[0] / 2;
                int wallY = y + dir[1] / 2;
                maze[wallY][wallX] = 0; // Knock down the wall between current cell and its neighbor
                dfs(newX, newY);
            }
        }
    }

    private void addStartAndEnd() {
        maze[height - 2][1] = 0; // Place 'A' (start) at bottom left
        maze[1][width - 2] = 0; // Place 'B' (end) at top right
    }

    public void printMaze() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (maze[i][j] == 1) {
                    System.out.print("#  ");
                } else if (maze[i][j] == 0) {
                    System.out.print("   ");
                } else {
                    System.out.printf("%-3d", maze[i][j] - 1);
                }
            }
            System.out.println();
        }
    }

    public int[][] getMaze() {
        return maze;
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public static void main(String[] args) {
        int width = 21;
        int height = 21;

        MazeGenerator mazeGenerator = new MazeGenerator(width, height);
        mazeGenerator.generateMaze();
        mazeGenerator.addStartAndEnd(); // Add start and end points
        mazeGenerator.printMaze();
    }

}
