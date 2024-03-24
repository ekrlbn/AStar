import java.util.PriorityQueue;

public class PathFinder {

    private int[][] maze;
    private int cols;
    private int rows;
    private int startX;
    private int endY;
    private int startY;
    private int endX;
    private PriorityQueue<Node> pq;
    private static MazeGenerator generator;

    class Node implements Comparable<Node> {
        int x;
        int y;
        int cost;
        int pathCost;
        int order;

        public Node(int x, int y, int cost, int pathCost, int order) {
            this.x = x;
            this.y = y;
            this.pathCost = pathCost;
            this.cost = cost;
            this.order = order;
        }

        @Override
        public int compareTo(Node other) {
            // return Integer.compare(this.cost, other.cost);
            if (this.cost == other.cost) {
                return Integer.compare(other.order, this.order);
            } else {
                return Integer.compare(this.cost, other.cost);
            }
        }

    }

    public PathFinder(int[][] maze) {
        this.maze = maze;
        this.cols = maze[0].length;
        this.rows = maze.length;

        this.startX = 1;
        this.startY = this.rows - 2;

        this.endX = this.cols - 2;
        this.endY = 1;

        pq = new PriorityQueue<>();
    }

    private int distanceToEnd(int x, int y) {
        int xDist = endX - x;
        int yDist = y - endY;
        return xDist + yDist;
    }

    private void aStarShortestPath() {
        int currentX = startX;
        int currentY = startY;

        int directions[][] = {
                { 0, -1 }, // Up
                { 0, 1 }, // Down
                { -1, 0 }, // Left
                { 1, 0 } // Right
        };

        int pathCost = 0;
        int i = 1;
        while (currentX != endX || currentY != endY) {
            // maze[currentY][currentX] = pathCost + distanceToEnd(currentX, currentY);
            maze[currentY][currentX] = ++i;

            // possible actions
            int newX;
            int newY;
            pathCost++;
            for (int[] direct : directions) {
                newX = currentX + direct[0];
                newY = currentY + direct[1];
                int frontierCost;
                if (maze[newY][newX] == 0) {
                    frontierCost = pathCost + distanceToEnd(newX, newY);
                    pq.add(new Node(newX, newY, frontierCost, pathCost, i));
                }
            }

            // next state
            Node next = pq.poll();
            currentX = next.x;
            currentY = next.y;
            pathCost = next.pathCost;

        }
    }

    public int[][] getAStarPath() {
        aStarShortestPath();
        return maze;
    }

    public static void main(String[] args) throws Exception {
        int width = 15;
        int height = 15;

        generator = new MazeGenerator(width, height);

        PathFinder pathFinder = new PathFinder(generator.getMaze());
        // generator.setMaze(pathFinder.getShortestPath());
        // generator.printMaze();

        MazeGeneratorGUI mazePanel = new MazeGeneratorGUI(width, height);
        mazePanel.setMaze(pathFinder.getAStarPath());
        mazePanel.draw(generator);

    }
}
