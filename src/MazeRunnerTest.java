public class MazeRunnerTest {
    private MazeGenerator generator;
    private int width;
    private int height;
    private PathFinder pathFinder;

    MazeRunnerTest(int width, int height) {
        this.width = width;
        this.height = height;
        generator = new MazeGenerator(width, height);
    }

    private double percentOfCellsReached(int[][] maze) {
        pathFinder = new PathFinder(maze);
        int[][] result = pathFinder.getAStarPath();

        int total = 0;
        int reached = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (result[i][j] > 1) {
                    reached++;
                    total++;
                }
                if (result[i][j] == 0) {
                    total++;
                }
            }
        }

        return (double) reached / total;

    }

    public double averagePerformance(int numTrials) {
        double total = 0;
        for (int i = 0; i < numTrials; i++) {
            total += percentOfCellsReached(generator.generateNewMaze());
        }
        return total / numTrials;
    }

    public static void main(String[] args) {
        System.out.println("Maze Runner Test");
        int startWidth = 5;
        int startHeight = 5;
        int endWidth = 20;
        int endHeight = 20;
        int numTrials = 10;
        for (int i = startWidth; i <= endWidth; i += 5) {
            for (int j = startHeight; j <= endHeight; j += 5) {
                MazeRunnerTest mazeRunner = new MazeRunnerTest(i, j);
                System.out.printf("Width: %d, Height: %d, Average Performance: %.3f\n", i, j,
                        mazeRunner.averagePerformance(numTrials));
            }
        }

    }
}
