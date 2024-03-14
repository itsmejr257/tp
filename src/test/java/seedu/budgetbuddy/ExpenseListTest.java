package seedu.budgetbuddy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ExpenseListTest {

    @Test
    public void calculateTotalExpenses_addingIntegers_success() {
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Transport", "50", "Bus Fare");
        expenseList.addExpense("Food", "30", "Lunch");

        assertEquals(80, expenseList.calculateTotalExpenses());
    }

    @Test
    public void calculateTotalExpenses_addingNegativeIntegers_exceptionThrown() {
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Transport", "-50", "Bus Fare");
        expenseList.addExpense("Food", "-30", "Lunch");

        try {
            assertEquals(0, expenseList.calculateTotalExpenses());
            fail();
        } catch (Exception e) {
            assertEquals("java.lang.Exception: Expenses should not be negative", e.getMessage());
        }
    }

    @Test
    public void addExpense_addingExpense_success() {
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Transport", "50", "Bus Fare");
        assertEquals(1, expenseList.getExpenses().size());
    }

    @Test
    public void addExpense_addingNegativeExpense_exceptionThrown() {
        ExpenseList expenseList = new ExpenseList();
        try {
            expenseList.addExpense("Transport", "-50", "Bus Fare");
            fail();
        } catch (Exception e) {
            assertEquals("java.lang.Exception: Expenses should not be negative", e.getMessage());
        }
    }

    @Test
    public void editExpense_validInput_success() {
        //Create an ExpenseList and add two expenses
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Transport", "50", "Bus Fare");
        expenseList.addExpense("Food", "30", "Lunch");

        //Edit the first expense
        expenseList.editExpense("Transport", 1, 70.0, "Updated Bus Fare");

        // Assert: Check if the edited expense details are correct
        assertEquals(2, expenseList.getExpenses().size());
        assertEquals("Transport", expenseList.getExpenses().get(0).getCategory());
        assertEquals(70.0, expenseList.getExpenses().get(0).getAmount(), 0.01); // using delta for double comparison
        assertEquals("Updated Bus Fare", expenseList.getExpenses().get(0).getDescription());
    }

    @Test
    public void editExpense_invalidCategoryOrIndex_failure() {
        // Create an ExpenseList and add two expenses
        ExpenseList expenseList = new ExpenseList();
        expenseList.addExpense("Transport", "50", "Bus Fare");
        expenseList.addExpense("Food", "30", "Lunch");

        // Edit an expense with an invalid category
        expenseList.editExpense("InvalidCategory", 1, 70.0, "Updated Bus Fare");

        // Assert: Check if the expense list remains unchanged
        assertEquals(2, expenseList.getExpenses().size()); // Should not change size
        // Check if the expense details remain unchanged
        assertEquals("Transport", expenseList.getExpenses().get(0).getCategory());
        assertEquals(50.0, expenseList.getExpenses().get(0).getAmount(), 0.01);
        assertEquals("Bus Fare", expenseList.getExpenses().get(0).getDescription());
    }
}
