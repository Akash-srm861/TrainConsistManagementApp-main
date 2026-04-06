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
 * MAIN CLASS - TrainConsistManagementApp
 * ========================================================
 *
 * Use Case 14: Handle Invalid Bogie Capacity (Custom Exception)
 *
 * Description:
 * This class extends UC13 by introducing custom exception handling.
 * Invalid passenger bogie capacities (≤ 0) are rejected at creation
 * time using a checked custom exception: InvalidCapacityException.
 *
 * At this stage, the application:
 * - Retains UC13 loop vs stream performance benchmarking
 * - Defines a custom exception class InvalidCapacityException
 * - Validates capacity inside the PassengerBogie constructor
 * - Throws InvalidCapacityException when capacity is ≤ 0
 * - Demonstrates fail-fast validation and business rule enforcement
 *
 * Key Concepts Used:
 *   - Custom Exception (InvalidCapacityException extends Exception)
 *   - Exception Inheritance
 *   - throw keyword
 *   - throws declaration
 *   - Fail-Fast Validation
 *   - Business Rule Enforcement
 *
 * @author Developer
 * @version 14.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainConsistManagementApp {

    // --------------------------------------------------------
    // Custom Exception
    // --------------------------------------------------------

    /**
     * Thrown when a passenger bogie is constructed with an
     * invalid (zero or negative) seating capacity.
     */
    static class InvalidCapacityException extends Exception {
        public InvalidCapacityException(String message) {
            super(message);
        }
    }

    // --------------------------------------------------------
    // Generic Bogie model (used by UC13 benchmarking)
    // --------------------------------------------------------

    static class Bogie {
        String type;
        int    capacity;

        Bogie(String type, int capacity) {
            this.type     = type;
            this.capacity = capacity;
        }
    }

    // --------------------------------------------------------
    // PassengerBogie — validates capacity at construction time
    // --------------------------------------------------------

    static class PassengerBogie {
        String type;
        int    capacity;

        /**
         * Creates a PassengerBogie with the given type and capacity.
         *
         * @param type     bogie type (e.g., "Sleeper", "AC Chair")
         * @param capacity seating capacity — must be greater than zero
         * @throws InvalidCapacityException if capacity is ≤ 0
         */
        PassengerBogie(String type, int capacity) throws InvalidCapacityException {
            if (capacity <= 0) {
                throw new InvalidCapacityException("Capacity must be greater than zero");
            }
            this.type     = type;
            this.capacity = capacity;
        }
    }

    // --------------------------------------------------------
    // UC13 helpers — loop and stream filtering (kept intact)
    // --------------------------------------------------------

    public static List<Bogie> filterByLoop(List<Bogie> bogies, int threshold) {
        List<Bogie> result = new ArrayList<>();
        for (Bogie b : bogies) {
            if (b.capacity > threshold) {
                result.add(b);
            }
        }
        return result;
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
    public static List<Bogie> filterByStream(List<Bogie> bogies, int threshold) {
        return bogies.stream()
                .filter(b -> b.capacity > threshold)
                .collect(Collectors.toList());
    }

    // --------------------------------------------------------
    // main
    // --------------------------------------------------------

    public static void main(String[] args) {

        // ---- UC13: Performance Comparison (Loops vs Streams) ----
        System.out.println("======================================");
        System.out.println(" UC13 - Performance Comparison (Loops vs Streams) ");
        System.out.println("======================================\n");

        List<Bogie> bogies = new ArrayList<>();
        String[] types = {"Sleeper", "AC Chair", "First Class", "General", "Cargo"};
        int[]    caps  = {72, 56, 24, 90, 120};

        for (int i = 0; i < 100000; i++) {
            bogies.add(new Bogie(types[i % types.length], caps[i % caps.length]));
        }

        long loopStart  = System.nanoTime();
        List<Bogie> loopResult = filterByLoop(bogies, 60);
        long loopTime   = System.nanoTime() - loopStart;

        long streamStart = System.nanoTime();
        List<Bogie> streamResult = filterByStream(bogies, 60);
        long streamTime  = System.nanoTime() - streamStart;

        System.out.println("Loop Execution Time (ns): "   + loopTime);
        System.out.println("Stream Execution Time (ns): " + streamTime);
        System.out.println("\nUC13 performance benchmarking completed...");

        // ---- UC14: Handle Invalid Bogie Capacity (Custom Exception) ----
        System.out.println("\n======================================");
        System.out.println(" UC14 - Handle Invalid Bogie Capacity (Custom Exception) ");
        System.out.println("======================================\n");

        // Test 1: Valid capacity — bogie should be created successfully
        System.out.println("Test 1: Creating bogie with valid capacity (72)...");
        try {
            PassengerBogie validBogie = new PassengerBogie("Sleeper", 72);
            System.out.println("Bogie created successfully. Type: " + validBogie.type
                    + ", Capacity: " + validBogie.capacity);
        } catch (InvalidCapacityException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // Test 2: Negative capacity — must throw InvalidCapacityException
        System.out.println("\nTest 2: Creating bogie with negative capacity (-10)...");
        try {
            PassengerBogie negBogie = new PassengerBogie("AC Chair", -10);
            System.out.println("Bogie created: " + negBogie.type);
        } catch (InvalidCapacityException e) {
            System.out.println("Caught InvalidCapacityException: " + e.getMessage());
        }

        // Test 3: Zero capacity — must throw InvalidCapacityException
        System.out.println("\nTest 3: Creating bogie with zero capacity (0)...");
        try {
            PassengerBogie zeroBogie = new PassengerBogie("First Class", 0);
            System.out.println("Bogie created: " + zeroBogie.type);
        } catch (InvalidCapacityException e) {
            System.out.println("Caught InvalidCapacityException: " + e.getMessage());
        }

        // Test 4: Multiple valid bogies
        System.out.println("\nTest 4: Creating multiple valid bogies...");
        String[] passengerTypes = {"Sleeper", "AC Chair", "First Class"};
        int[]    validCaps      = {72, 56, 24};
        for (int i = 0; i < passengerTypes.length; i++) {
            try {
                PassengerBogie b = new PassengerBogie(passengerTypes[i], validCaps[i]);
                System.out.println("Created -> Type: " + b.type + ", Capacity: " + b.capacity);
            } catch (InvalidCapacityException e) {
                System.out.println("Exception for " + passengerTypes[i] + ": " + e.getMessage());
            }
        }

        System.out.println("\nUC14 custom exception handling completed...");
    }
}
