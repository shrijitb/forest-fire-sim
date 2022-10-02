public class MonteCarloOutput {
    private static final int NUM_RUNS = 1000;

    public static void main(String[] args) {
        for (int density = 100; density > 0 ; density--) {

            Simulator sim = new Simulator(100, 100);

            int total_burned = 0;
            // int total_alive = 0;
            for (int i = 0; i < NUM_RUNS; i++) {
                sim.initialize(density);
                sim.setFire();
                sim.runFullStep();
                total_burned += sim.getStats("ash");
            }
            double avg_burned = (double) total_burned / (NUM_RUNS * sim.getStats("trees")) * 100;
            System.out.println(density + "\t\t\t" + avg_burned);
        }
    }
}
