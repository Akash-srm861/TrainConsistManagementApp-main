public class TrainConsistManagementApp {

    // ─────────────────────────────────────────
    // UC15: Custom Runtime Exception
    // ─────────────────────────────────────────
    static class CargoSafetyException extends RuntimeException {
        CargoSafetyException(String message) {
            super(message);
        }
    }

    // ─────────────────────────────────────────
    // GoodsBogie with cargo assignment rule
    // Rule: Rectangular cannot carry Petroleum
    // ─────────────────────────────────────────
    static class GoodsBogie {
        String shape;
        String cargo;

        GoodsBogie(String shape) {
            this.shape = shape;
            this.cargo = null;
        }

        void assignCargo(String cargo) {
            if (shape.equals("Rectangular") &&
                    cargo.equals("Petroleum")) {
                throw new CargoSafetyException(
                        "Unsafe: Petroleum cannot be assigned " +
                                "to Rectangular bogie!");
            }
            this.cargo = cargo;
        }

        @Override
        public String toString() {
            return shape + " | Cargo: " +
                    (cargo != null ? cargo : "None");
        }
    }

    // ─────────────────────────────────────────
    // Safe assignment method with
    // try-catch-finally
    // ─────────────────────────────────────────
    static void attemptCargoAssignment(GoodsBogie bogie,
                                       String cargo) {
        System.out.println("\nAssigning '" + cargo +
                "' to " + bogie.shape + " bogie...");
        try {
            bogie.assignCargo(cargo);
            System.out.println("  Assignment successful.");
        } catch (CargoSafetyException e) {
            System.out.println("  CargoSafetyException: " +
                    e.getMessage());
        } finally {
            System.out.println("  [LOG] Validation complete.");
        }
    }

    // ─────────────────────────────────────────
    // MAIN METHOD
    // ─────────────────────────────────────────
    public static void main(String[] args) {

        System.out.println("=== Train Consist Management App ===");
        System.out.println("UC15: Safe Cargo Assignment\n");

        // Safe: Cylindrical + Petroleum
        GoodsBogie cyl = new GoodsBogie("Cylindrical");
        attemptCargoAssignment(cyl, "Petroleum");
        System.out.println("  Status: " + cyl);

        // Unsafe: Rectangular + Petroleum
        GoodsBogie rect = new GoodsBogie("Rectangular");
        attemptCargoAssignment(rect, "Petroleum");
        System.out.println("  Status: " + rect);

        // Safe: Rectangular + Coal
        GoodsBogie rect2 = new GoodsBogie("Rectangular");
        attemptCargoAssignment(rect2, "Coal");
        System.out.println("  Status: " + rect2);

        System.out.println("\nProgram continues safely...");
    }
}