package GameOfLife;
import BasicIO.BasicForm;

/**
 * @Name: Ameya Chindarkar
 * @Student#: 7023609
 * @Course: COSC 1P03
 * @Title: Game Of Life
 */

public class Grid<width, height, per> {

    int width;
    int height;
    int[][] grid;



    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new int[width][height];
    }

    /**
     * This method is used to write all the given inputs into the
     * output textarea in the form.
     */

    public void printGrid() {
        System.out.println("-----------------");
        for (int y = 0; y < height; y++) {
            String line = "|";
            for (int x = 0; x < width; x++) {
                if (this.grid[x][y] == 0) {
                    line += ".";
                } else {
                    line += "*";
                }
            }
            line += "|";

            System.out.println(line);
        }
        System.out.println("-----------------\n");
    }

    /**
     * This method is used to set the values on the grid to
     * Alive
     */

    public void setAlive(int x, int y) {
        this.grid[x][y] = 1;
    }

    /**
     * This method is used to count the number of alive
     * neighbours of a particular cell.
     */

    public int countAliveNeighbours(int x, int y) {
        int count = 0;

        count += getState(x - 1, y - 1);
        count += getState(x, y - 1);
        count += getState(x + 1, y - 1);

        count += getState(x - 1, y);
        count += getState(x + 1, y);

        count += getState(x - 1, y + 1);
        count += getState(x, y + 1);
        count += getState(x + 1, y + 1);

        return count;
    }

    /**
     * This method is used to identify whether the cell is
     * alive or dead.
     */

    public int getState(int x, int y) {
        if (x < 0 || x >= width) {
            return 0;
        }

        if (y < 0 || y >= height) {
            return 0;
        }

        return this.grid[x][y];
    }

    /**
     * This method is used to create a new grid to print the new
     * values of the grid after a cycle happens.
     */

    public void step() {
        int[][] newBoard = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int aliveNeighbours = countAliveNeighbours(x, y);

                if (getState(x, y) == 1) {
                    if (aliveNeighbours < 2) {
                        newBoard[x][y] = 0;
                    } else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        newBoard[x][y] = 1;
                    } else if (aliveNeighbours > 3) {
                        newBoard[x][y] = 0;
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        newBoard[x][y] = 1;
                    }
                }

            }
        }

        this.grid = newBoard;
    }


    /**
     * This method to write all the given inputs into the output textarea in the form
     *
     * @param form
     */


    private void writeOutput(BasicForm form) {
        step();
        printGrid();
    }

    /**
     * This method is used to create a form
     * to display the simulation.
     */

    private BasicForm createForm() {
        BasicForm form = new BasicForm("Run", "Exit");
        form.addTextArea("O", "OutPut", width, height);
        return form;
    }

    /**
     * Main.
     */

    public static void main(String[] args) {

        Grid grid = new Grid(15, 15);
        BasicForm form = grid.createForm();

        double per = 0.1;

        for (float i = 0; i < (grid.width * grid.height * per); i++) {
            int row = (int) (Math.random() * 15);
            int col = (int) (Math.random() * 15);
            grid.setAlive(row, col);
        }

        grid.printGrid();

        do {
            int button = form.accept();
            if (button == 1) {
                System.exit(0);
            }
            grid.createForm();
            grid.writeOutput(form);
        } while (true);

    }
}
