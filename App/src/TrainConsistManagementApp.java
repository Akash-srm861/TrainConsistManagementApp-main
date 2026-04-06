/**
 * ========================================================
 * MAIN CLASS - UseCase9TrainConsistMgmnt
 * ========================================================
 *
 * Use Case 9: Group Bogies by Type (Collectors.groupingBy)
 *
 * Description:
 * This class groups bogies into categories using Stream
 * collectors for structured reporting.
 *
 * At this stage, the application:
 * - Creates a list of bogies with types
 * - Converts list into stream
 * - Applies groupingBy() collector
 * - Stores result in Map<String, List<Bogie>>
 * - Displays grouped bogie structure
 *
 * This maps data aggregation using Collectors.groupingBy.
 *
 * @author Developer
 * @version 9.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TrainConsistManagementApp {

    // Bogie model with name, type and capacity
    static class Bogie {
        String name;
        String type;
        int capacity;

        Bogie(String name, String type, int capacity) {
            this.name     = name;
            this.type     = type;
            this.capacity = capacity;
        }
    }

    // Reusable grouping method — used by main() and tests
    public static Map<String, List<Bogie>> groupByType(List<Bogie> bogies) {
        return bogies.stream()
                .collect(Collectors.groupingBy(b -> b.type));
    }

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" UC9 - Group Bogies by Type (Collectors.groupingBy) ");
        System.out.println("======================================\n");

        // Create list of bogies with different types
        List<Bogie> bogies = new ArrayList<>();

        // ---- Passenger bogies ----
        bogies.add(new Bogie("Sleeper-1",     "Passenger", 72));
        bogies.add(new Bogie("Sleeper-2",     "Passenger", 72));
        bogies.add(new Bogie("AC Chair-1",    "Passenger", 56));
        bogies.add(new Bogie("First Class-1", "Passenger", 24));

        // ---- Goods bogies ----
        bogies.add(new Bogie("Rectangular-1", "Goods", 120));
        bogies.add(new Bogie("Cylindrical-1", "Goods", 100));

        // ---- Apply groupingBy on bogie type ----
        Map<String, List<Bogie>> grouped = groupByType(bogies);

        // ---- Display grouped result ----
        System.out.println("Grouped Bogies by Type:");
        for (Map.Entry<String, List<Bogie>> entry : grouped.entrySet()) {
            System.out.println("\nType: " + entry.getKey());
            for (Bogie b : entry.getValue()) {
                System.out.println("  " + b.name + " -> Capacity: " + b.capacity);
            }
        }

        System.out.println("\nUC9 grouping completed...");
    }
}
