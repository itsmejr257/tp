package seedu.budgetbuddy;

import java.util.ArrayList;


/**
 * Represents a list of expenses for recurring expenses. This class extends
 * the ExpenseList class. Currently, this class holds a name.
 */
public class RecurringExpenseList extends ExpenseList{
    String name;


    /**
     * Constructs a new RecurringExpenseList with the provided name and list of expenses
     * @param name The provided name for the recurring expense list
     * @param expenses An arraylist of Expense objects
     */
    public RecurringExpenseList(String name, ArrayList<Expense> expenses) {
        this.name = name;
        super.expenses = expenses;
    }


    /**
     * Returns the name of this recurring expense list
     *
     * @return The name of this recurring expense list.
     */
    @Override
    public String getName() {
        return this.name;
    }
}
