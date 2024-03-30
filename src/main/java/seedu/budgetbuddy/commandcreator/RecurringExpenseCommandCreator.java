package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.ExpenseList;
import seedu.budgetbuddy.RecurringExpensesList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.RecurringExpenseCommand;
import seedu.budgetbuddy.exception.BudgetBuddyException;

public class RecurringExpenseCommandCreator extends CommandCreator{
    private static final String LISTNUMBER_PREFIX = "to/";
    private static final String CATEGORY_PREFIX = "c/";
    private static final String AMOUNT_PREFIX = "a/";
    private static final String DESCRIPTION_PREFIX = "d/";
    private String input;
    private RecurringExpensesList recurringExpensesList;
    private ExpenseList expenses;


    /**
     * Constructs a RecurringExpenseCommandCreator with the provided input, recurringExpensesList and expenses
     * @param input The user input
     * @param recurringExpensesList The RecurringExpensesList containing a list of ExpenseList
     * @param expenses The ExpenseList containing user's overall expenses
     */
    public RecurringExpenseCommandCreator(String input, RecurringExpensesList recurringExpensesList
            , ExpenseList expenses) {
        this. input = input;
        this.recurringExpensesList = recurringExpensesList;
        this.expenses = expenses;
    }

    /**
     * Checks the input for the presence of all the required to/ , d/, a/ and c/ prefixes
     *
     * @param input The user input
     * @throws IllegalArgumentException if any of the required prefixes are not found
     */
    private static void checkForInvalidParameters(String input) throws IllegalArgumentException {
        if (!input.contains("to/") || !input.contains("d/") || !input.contains("a/") || !input.contains("c/")) {
            throw new IllegalArgumentException("Please Ensure that you include to/, c/, a/ and d/");
        }
    }

    /**
     * Parses the description from the input string
     *
     * @param input The user input
     * @return The extracted description from the d/ prefix
     * @throws BudgetBuddyException if the description is empty
     */
    public String parseDescription(String input) throws BudgetBuddyException {
        int indexOfDescriptionPrefix = input.indexOf(DESCRIPTION_PREFIX);
        int startIndexOfDescription = indexOfDescriptionPrefix + DESCRIPTION_PREFIX.length();

        int endIndexOfDescription = input.length();

        String description = input.substring(startIndexOfDescription,endIndexOfDescription);

        if(description.trim().isEmpty()) {
            throw new BudgetBuddyException("Please Ensure Description is NOT empty");
        }

        return description;
    }

    /**
     * Parses the amount from the input string
     *
     * @param input The user input
     * @return The extracted amount from the a/ prefix
     * @throws NumberFormatException If the extracted amount is not a valid double
     * @throws BudgetBuddyException If the extracted amount is empty
     */
    public Double parseAmount(String input) throws NumberFormatException, BudgetBuddyException{
        int indexOfAmountPrefix = input.indexOf(AMOUNT_PREFIX);
        int startIndexOfAmount = indexOfAmountPrefix + AMOUNT_PREFIX.length();

        int indexOfDescriptionPrefix = input.indexOf(DESCRIPTION_PREFIX);
        int endIndexOfAmount = indexOfDescriptionPrefix - 1;

        String amountAsString = input.substring(startIndexOfAmount, endIndexOfAmount);

        if(amountAsString.trim().isEmpty()) {
            throw new BudgetBuddyException("Please Ensure Amount is NOT empty");
        }

        Double amount = Double.parseDouble(amountAsString);

        return amount;
    }

    /**
     * Parses the category from the input string
     *
     * @param input The user input
     * @return The extracted category from the c/ prefix
     * @throws BudgetBuddyException If the category is empty
     */
    public String parseCategory(String input) throws BudgetBuddyException{
        int indexOfCategoryPrefix = input.indexOf(CATEGORY_PREFIX);
        int startIndexOfCategory = indexOfCategoryPrefix + CATEGORY_PREFIX.length();

        int indexOfAmountPrefix = input.indexOf(AMOUNT_PREFIX);
        int endIndexOfCategory = indexOfAmountPrefix - 1;

        String category = input.substring(startIndexOfCategory, endIndexOfCategory);

        if(category.trim().isEmpty()) {
            throw new BudgetBuddyException("Please Ensure Category is NOT empty");
        }

        return category;
    }

    /**
     * Parses the list number from the input string
     *
     * @param input The user input
     * @return The extracted list number from the to/ prefix
     * @throws NumberFormatException if the list number is not a valid number
     * @throws BudgetBuddyException if the list number is empty
     */
    public int parseListNumber(String input) throws NumberFormatException, BudgetBuddyException{
        int indexOfListNumberPrefix = input.indexOf(LISTNUMBER_PREFIX);
        int startIndexOfListNumber = indexOfListNumberPrefix + LISTNUMBER_PREFIX.length();

        int indexOfCategoryPrefix = input.indexOf(CATEGORY_PREFIX);
        int endIndexOfListNumber = indexOfCategoryPrefix - 1;

        String listNumberAsString = input.substring(startIndexOfListNumber, endIndexOfListNumber);

        if(listNumberAsString.trim().isEmpty()) {
            throw new BudgetBuddyException("Please Ensure List Number is NOT empty");
        }

        int listNumber = Integer.parseInt(listNumberAsString);

        return listNumber;
    }

    /**
     * Creates a RecurringExpenseCommand to view all expenses in a specific ExpenseList in recurringExpensesList
     * This method obtains the listNumber from the provided commandParts.
     *
     * @param commandParts The split parts of the input command string
     * @return RecurringExpenseCommand if list number is valid, returns null if list number is invalid or empty
     */
    public Command createViewExpensesCommand(String[] commandParts) {
        try {
            String listNumberAsString = commandParts[2];
            int listNumber = Integer.parseInt(listNumberAsString);
            return new RecurringExpenseCommand(listNumber, recurringExpensesList, "viewexpenses");
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid Integer");
            System.out.println("Command Format : rec viewexpenses [List Number]");
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("List Number Cannot be Empty");
            System.out.println("Command Format : rec viewexpenses [List Number]");
            return null;
        }
    }

    /**
     * Creates a RecurringExpenseCommand to add the expenses in a specific ExpenseList in recurringExpensesList into
     * the overall ExpenseList.
     * This method obtains the listNumber from the provided commandParts.
     *
     * @param commandParts The split parts of the input command string
     * @return RecurringExpenseCommand if the list number is valid, returns null if list number is invalid or empty
     */
    public Command createAddListToOverallExpensesCommand(String[] commandParts) {

        try {
            String listNumberAsString = commandParts[2];
            int listNumber = Integer.parseInt(listNumberAsString);
            return new RecurringExpenseCommand(listNumber, recurringExpensesList, expenses, "addrec");
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid Integer");
            System.out.println("Command Format : rec addrec [List Number]");
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("List Number Cannot be Empty");
            System.out.println("Command Format : rec addrec [List Number]");
            return null;
        }

    }

    /**
     * Creates a RecurringExpenseCommand to add an expense into a specific ExpenseList in recurringExpensesList
     *
     * @param input The user input
     * @return RecurringExpenseCommand if user input is valid, returns null if any of the user input is invalid
     */
    public Command createAddExpenseToListCommand(String input) {

        try {
            checkForInvalidParameters(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Command Format : rec newexpense to/ LISTNUMBER c/ CATEGORY" +
                    " a/ AMOUNT d/ DESCRIPTION");
            return null;
        }

        try {
            int listNumber = parseListNumber(input);
            String category = parseCategory(input);
            Double amount = parseAmount(input);
            String description = parseDescription(input);

            return new RecurringExpenseCommand(listNumber, recurringExpensesList, category,
                    amount, description, "newexpense");
        } catch (BudgetBuddyException e) {
            System.out.println(e.getMessage());
            System.out.println("Command Format : rec newexpense to/ LISTNUMBER c/ CATEGORY" +
                    " a/ AMOUNT d/ DESCRIPTION");
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Please ensure that listNumber and Amount are valid Numbers");
            System.out.println("Command Format : rec newexpense to/ LISTNUMBER c/ CATEGORY" +
                    " a/ AMOUNT d/ DESCRIPTION");
            return null;
        }

    }

    /**
     * Creates a RecurringExpenseCommand to remove a specified ExpenseList in the recurringExpensesList
     * This method uses the provided commandParts to obtain the list Number of the ExpenseList to remove
     *
     * @param commandParts The split parts of the user input
     * @return RecurringExpenseCommand if user input is valid, returns null if listNumber is empty or invalid
     */
    public Command createRemoveListCommand(String[] commandParts) {
        try {
            String listNumberAsString = commandParts[2];
            int listNumber = Integer.parseInt(listNumberAsString);
            return new RecurringExpenseCommand(listNumber, recurringExpensesList, "removelist");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("List Number Cannot be Empty");
            System.out.println("Command Format : rec removelist [List Number]");
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid Integer");
            System.out.println("Command Format : rec removelist [List Number]");
            return null;
        }
    }

    /**
     * Creates a RecurringExpenseCommand to print all the names of the ExpenseLists present in recurringExpensesList
     *
     * @return A RecurringExpenseCommand
     */
    public Command createViewListCommand() {
        return new RecurringExpenseCommand(recurringExpensesList, "viewlists");
    }


    /**
     * Creates a RecurringExpenseCommand to add a new RecurringExpenseList into recurringExpensesList
     * This method uses the provided `commandParts` to extract the listName of the new RecurringExpenseList
     *
     * @param commandParts The split parts of the user input
     * @return RecurringExpenseCommand if listName is valid, returns null if the listName extracted is empty
     */
    public Command createNewListCommand(String[] commandParts) {

        try {
            String listName = commandParts[2];
            return new RecurringExpenseCommand(listName, recurringExpensesList, "newlist");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please Input a Valid listName");
            System.out.println("Command Format : rec newlist [listName]");
            return null;
        }

    }

    /**
     * Handles the creation of the various types of RecurringExpenseCommand based on the extracted commandType
     * This method extracts the commandType from the user input, and calls methods based on the commandType
     *
     * @param input The user input
     * @return RecurringExpenseCommand if commandType extracted is a valid commandType,
     *         returns null if commandType is not valid
     */
    public Command handleRecCommand(String input){
        String[] commandParts = input.split(" ");
        String commandType = commandParts[1];
        commandType = commandType.trim();

        if (!RecurringExpenseCommand.commandTypes.contains(commandType)) {
            System.out.println("This Command Type does not exist for \"rec\"");
            return null;
        }

        switch(commandType) {
        case "newlist":
            return createNewListCommand(commandParts);
        case "viewlists":
            return createViewListCommand();
        case "removelist":
            return createRemoveListCommand(commandParts);
        case "newexpense":
            return createAddExpenseToListCommand(input);
        case "addrec":
            return createAddListToOverallExpensesCommand(commandParts);
        case "viewexpenses":
            return createViewExpensesCommand(commandParts);
        default:
            return null;
        }

    }

    @Override
    public Command createCommand() {
        return handleRecCommand(input);
    }
}
