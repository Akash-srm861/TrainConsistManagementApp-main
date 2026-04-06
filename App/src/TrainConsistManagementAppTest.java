import org.junit.Test;

import static org.junit.Assert.*;

public class TrainConsistManagementAppTest {

    // ══════════════════════════════════════════
    // TEST 1
    // Safe cargo assignment must succeed
    // without any exception
    // ══════════════════════════════════════════
    @Test
    public void testCargo_SafeAssignment() {

        TrainConsistManagementApp.GoodsBogie bogie =
                new TrainConsistManagementApp.GoodsBogie("Cylindrical");

        bogie.assignCargo("Petroleum");

        assertEquals("Cargo must be Petroleum",
                "Petroleum", bogie.cargo);
    }

    // ══════════════════════════════════════════
    // TEST 2
    // Petroleum on Rectangular must throw
    // CargoSafetyException
    // ══════════════════════════════════════════
    @Test
    public void testCargo_UnsafeAssignmentHandled() {

        TrainConsistManagementApp.GoodsBogie bogie =
                new TrainConsistManagementApp.GoodsBogie("Rectangular");

        try {
            bogie.assignCargo("Petroleum");
            fail("CargoSafetyException expected");

        } catch (TrainConsistManagementApp.CargoSafetyException e) {
            assertNotNull("Exception must be thrown", e);
        }
    }

    // ══════════════════════════════════════════
    // TEST 3
    // Cargo must NOT be assigned after
    // a failed unsafe assignment
    // ══════════════════════════════════════════
    @Test
    public void testCargo_CargoNotAssignedAfterFailure() {

        TrainConsistManagementApp.GoodsBogie bogie =
                new TrainConsistManagementApp.GoodsBogie("Rectangular");

        try {
            bogie.assignCargo("Petroleum");
        } catch (TrainConsistManagementApp.CargoSafetyException e) {
            // expected
        }

        assertNull("Cargo must remain null after failed assignment",
                bogie.cargo);
    }

    // ══════════════════════════════════════════
    // TEST 4
    // Program must continue running after
    // exception is handled
    // ══════════════════════════════════════════
    @Test
    public void testCargo_ProgramContinuesAfterException() {

        TrainConsistManagementApp.GoodsBogie b1 =
                new TrainConsistManagementApp.GoodsBogie("Rectangular");
        TrainConsistManagementApp.GoodsBogie b2 =
                new TrainConsistManagementApp.GoodsBogie("Cylindrical");

        try {
            b1.assignCargo("Petroleum"); // will throw
        } catch (TrainConsistManagementApp.CargoSafetyException e) {
            // handled
        }

        // b2 must still work after b1 exception
        b2.assignCargo("Petroleum");
        assertEquals("b2 must have Petroleum assigned",
                "Petroleum", b2.cargo);
    }

    // ══════════════════════════════════════════
    // TEST 5
    // finally block must always execute
    // regardless of success or failure
    // ══════════════════════════════════════════
    @Test
    public void testCargo_FinallyBlockExecution() {

        boolean[] finallyRan = {false};

        TrainConsistManagementApp.GoodsBogie bogie =
                new TrainConsistManagementApp.GoodsBogie("Rectangular");

        try {
            bogie.assignCargo("Petroleum");
        } catch (TrainConsistManagementApp.CargoSafetyException e) {
            // caught
        } finally {
            finallyRan[0] = true;
        }

        assertTrue("finally block must always execute",
                finallyRan[0]);
    }
}