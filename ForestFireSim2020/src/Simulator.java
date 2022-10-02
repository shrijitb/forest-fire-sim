import java.util.ArrayList;

class Simulator {
    private static int[][] forest;
    private static int row, col;

    final static int EMPTY_SPACE  = 0; // must be zero for proper initialization
    final static int LIVING_TREE  = 1;
    final static int BURNING_TREE = 2;
    final static int ASH = 3;

    private int stats_trees   = 0;
    private int stats_alive   = 0;
    private int stats_burning = 0;
    private int stats_burned  = 0;

    Simulator(int r, int c) {
        row = r;
        col = c;
        forest = new int[row][col];
    }


    void initialize(int n){
        forest = new int[row][col];
        stats_trees = (row * col * n) / 100;
        stats_alive = stats_trees;
        stats_burning = 0;
        stats_burned = 0;

        int num_trees = 0;
        do {
            int r = (int)(Math.random()*row);
            int c = (int)(Math.random()*col);
            if ( forest[r][c] == EMPTY_SPACE ){
                num_trees++;
                forest[r][c] = LIVING_TREE;
            }
        } while( num_trees < stats_trees);
    }


    void setFire() {
        int r, c;
        do {
            r = (int)(Math.random()*row);
            c = (int)(Math.random()*col);
        } while (forest[r][c] != LIVING_TREE);

        forest[r][c] = BURNING_TREE;
        stats_burning++;
        stats_alive--;
    }
/*
    public void setFire(int r, int c) {
        forest[r][c] = BURNING_TREE;
        stats_burning++;
        stats_alive--;
    }
*/

    void runOneStep() {
        ArrayList<Location> new_fires = new ArrayList<>();
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (forest[r][c] == BURNING_TREE) {
                    // Set on fire each of the neighbors
                    for (int dr = -1; dr <= 1; dr++) {
                        for (int dc = -1; dc <= 1; dc++) {
                            if ( isInBound(r + dr, c + dc) && forest[r + dr][c + dc] == LIVING_TREE ) {
                                Location loc = new Location(r+dr, c+dc);
                                if ( !new_fires.contains( loc ) ) {
                                    new_fires.add( loc );
                                }
                            }
                        }
                    }
                    forest[r][c] = ASH;
                    stats_burned++;
                }
            }
        }

        for ( Location loc : new_fires ) {
            forest[ loc.getRow() ][ loc.getCol() ] = BURNING_TREE;
        }
        stats_burning = new_fires.size();
        stats_alive -= stats_burning;
    }

    void runFullStep(){
        do {
            runOneStep();
        } while (!isOver());
    }

    private boolean isInBound(int r, int c) {
        return (r >= 0 && r < row && c >=0 && c < col);
    }

    void displayStats() {
        System.out.println( "trees: " + stats_trees + "\talive: " + stats_alive + "\tburned: " + stats_burned + "\ton fire: " + stats_burning);
    }

    boolean isOver() {
        return stats_burning == 0;
    }

    int getWidth() {
        return col;
    }

    int getHeight() {
        return row;
    }

    int[][] getDisplayGrid() {
        return forest;
    }

    int getStats(String string){
        switch (string) {
            case "trees":
                return stats_trees;
            case "alive":
                return stats_alive;
            case "fire":
                return stats_burning;
            case "ash":
                return stats_burned;
        }
        return 0;
    }

    /*public int getStats_trees() {
        return stats_trees;
    }

    public int getStats_alive() {
        return stats_alive;
    }

    public int getStats_burning() {
        return stats_burning;
    }

    public int getStats_burned() {
        return stats_burned;
    }

     */
}
