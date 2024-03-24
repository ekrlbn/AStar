import javax.swing.*;
import java.awt.*;

public class MazeGeneratorGUI extends JPanel {
    private static final int CELL_SIZE = 30;
    private int width;
    private int height;
    private int[][] maze;

    public MazeGeneratorGUI(int width, int height) {
        this.width = width;
        this.height = height;
        this.maze = new int[height][width];
        this.maze = new int[height][width];
        setPreferredSize(new Dimension(width * CELL_SIZE, height * CELL_SIZE));
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw maze cells
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int x = j * CELL_SIZE;
                int y = i * CELL_SIZE;
                if (maze[i][j] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                } else if (maze[i][j] != 0) {
                    g.setColor(Color.GRAY);
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
        }
        // Draw start and end points
        g.setColor(Color.GREEN);
        g.fillRect(CELL_SIZE, (height - 2) * CELL_SIZE, CELL_SIZE, CELL_SIZE); // Start point
        g.setColor(Color.RED);
        g.fillRect((width - 2) * CELL_SIZE, CELL_SIZE, CELL_SIZE, CELL_SIZE); // End point

        // Draw divisions between cells
        g.setColor(Color.BLACK);
        for (int i = 0; i <= height; i++) {
            g.drawLine(0, i * CELL_SIZE, width * CELL_SIZE, i * CELL_SIZE);
        }
        for (int j = 0; j <= width; j++) {
            g.drawLine(j * CELL_SIZE, 0, j * CELL_SIZE, height * CELL_SIZE);
        }

        // draw path contains reached cells in order number
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (maze[i][j] > 1) {
                    g.setColor(Color.BLUE);
                    g.drawString(String.valueOf(maze[i][j] - 1), j * CELL_SIZE + 5, i * CELL_SIZE + 15);
                }
            }
        }

    }

    public void draw(MazeGenerator mazeGenerator) {

        MazeGeneratorGUI mazePanel = new MazeGeneratorGUI(width, height);
        mazePanel.setMaze(mazeGenerator.getMaze());

        JFrame frame = new JFrame("Maze Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mazePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        int width = 21;
        int height = 21;

        MazeGenerator generator = new MazeGenerator(width, height);

        MazeGeneratorGUI mazePanel = new MazeGeneratorGUI(width, height);
        mazePanel.setMaze(generator.getMaze());

        JFrame frame = new JFrame("Maze Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mazePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
