package seedu.budgetbuddy.commandcreator;

import seedu.budgetbuddy.ExpenseList;
import seedu.budgetbuddy.command.Command;
import seedu.budgetbuddy.command.FindExpensesCommand;
import seedu.budgetbuddy.exception.BudgetBuddyException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for creating a FindExpensesCommand based on user input
 * It parses input to extract the description, minimum and maximum amount to filter expenses.
 */
public class FindExpensesCommandCreator extends CommandCreator {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String DESCRIPTION_PREFIX = "d/";
    private static final String MINAMOUNT_PREFIX = "morethan/";
    private static final String MAXAMOUNT_PREFIX = "lessthan/";
    private ExpenseList expenses;
    private String input;

    /**
     * Constructs a FindExpensesCommandCreator with the specified input and expenses
     *
     * @param input The user input
     * @param expenses The ExpenseList to filter
     */
    public FindExpensesCommandCreator(String input, ExpenseList expenses) {
        this.input = input;
        this.expenses = expenses;
    }

    /**
     * Checks input for the presence of the required d/ , morethan/ and lessthan/ prefixes
     *
     * @param input The user input
     * @throws IllegalArgumentException If any of the prefixes are not found
     */
    private static void checkForInvalidParameters(String input) throws IllegalArgumentException {
        if (!input.contains("d/") || !input.contains("morethan/") || !input.contains("lessthan/")) {
            throw new IllegalArgumentException("Please Ensure that you include d/, morethan/ and lessthan/");
        }
    }

    /**
     * Parses the maximum amount from user input
     *
     * @param input The user input
     * @return The parsed maximum amount from the lessthan/ prefix
     * @throws NumberFormatException if the parsed maximum amount is not a valid double
     */
    public Double parseMaxAmount(String input) throws NumberFormatException{
        int indexOfMaxAmountPrefix = input.indexOf(MAXAMOUNT_PREFIX);
        int startIndexOfMaxAmount = indexOfMaxAmountPrefix + MAXAMOUNT_PREFIX.length();

        int endIndexOfMaxAmount = input.length();

        String maxAmountAsString = input.substring(startIndexOfMaxAmount, endIndexOfMaxAmount);

        if (maxAmountAsString.trim().isEmpty()) {
            return null;
        }

        Double maxAmount = Double.parseDouble(maxAmountAsString);

        return maxAmount;
    }

    /**
     * Parses the minimum amount from user input
     *
     * @param input The user input
     * @return The parsed minimum amount from the morethan/ prefix
     * @throws NumberFormatException if the parsed minimum amount is not a valid double
     */
    public Double parseMinAmount(String input) throws NumberFormatException {
        int indexOfMinAmountPrefix = input.indexOf(MINAMOUNT_PREFIX);
        int startIndexOfMinAmount = indexOfMinAmountPrefix + MINAMOUNT_PREFIX.length();

        int indexOfMaxAmountPrefix = input.indexOf(MAXAMOUNT_PREFIX);
        int endIndexOfMinAmount = indexOfMaxAmountPrefix - 1;

        String minAmountAsString = input.substring(startIndexOfMinAmount, endIndexOfMinAmount);

        if (minAmountAsString.trim().isEmpty()) {
            return null;
        }

        Double minAmount = Double.parseDouble(minAmountAsString);

        return minAmount;
    }

    /**
     * Parses the description from user input
     *
     * @param input The user input
     * @return The parsed description from the d/ prefix. If it is empty, returns null
     */
    public String parseDescription(String input) {

        int indexOfDescriptionPrefix = input.indexOf(DESCRIPTION_PREFIX);
        int startIndexOfDescription = indexOfDescriptionPrefix + DESCRIPTION_PREFIX.length();

        int indexOfMinAmountPrefix = input.indexOf(MINAMOUNT_PREFIX);
        int endIndexOfDescription = indexOfMinAmountPrefix - 1;

        String description = input.substring(startIndexOfDescription, endIndexOfDescription).trim();

        if (description.isEmpty()) {
            return null;
        }

        return description;
    }

    /**
     * Checks for duplicate usages of a specified parameter in the user input
     *
     * @param input The user input
     * @param parameter The parameter to be checked for duplicates
     * @throws IllegalArgumentException if duplicates of parameter is found
     */
    private static void checkForDuplicateParameters(String input, String parameter) {

        int count = 0;

        Pattern pattern = Pattern.compile(parameter);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            count++;
        }

        if (count > 1) {
            throw new IllegalArgumentException("The parameter '" + parameter + "' can only be used once.");
        }

    }

    /**
     * Compares the minimum and maximum amount and throws an exception if the minimum amount
     * is larger than or equals to the maximum amount
     *
     * @param minAmount The minimum amount
     * @param maxAmount The maximum amount
     * @throws BudgetBuddyException If minimum amount is larger or equals to maximum amount
     */
    private static void compareMinAndMaxAmount(Double minAmount, Double maxAmount) throws BudgetBuddyException{

        if (minAmount != null && maxAmount != null) {
            if (minAmount >= maxAmount) {
                throw new BudgetBuddyException("Ensure minimum amount is smaller than maximum amount");
            }
        }

    }

    /**
     * Handles the creation of the FindExpensesCommand based on the provided user inputs.
     * This method validates the input, parses the parameters and constructs the command if the user input
     * is valid.
     *
     * @param input The user input
     * @param expenses The ExpenseList to be filtered
     * @return A FindExpensesCommand with the parsed description, minimum amount,
     *         maximum amount and the ExpenseList to be filtered
     */
    public Command handleFindExpensesCommand(String input, ExpenseList expenses) {
        assert input != null : "Input cannot be null";
        assert !input.isEmpty() : "Input cannot be empty";
        assert input.startsWith("find expenses") : "Input must be a find expenses command";

        LOGGER.log(Level.INFO, "Begin parsing parameters in find expenses command");

        try {
            checkForInvalidParameters(input);
            checkForDuplicateParameters(input, "d/");
            checkForDuplicateParameters(input, "morethan/");
            checkForDuplicateParameters(input, "lessthan/");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }

        try {
            String description = parseDescription(input);
            Double minAmount = parseMinAmount(input);
            Double maxAmount = parseMaxAmount(input);

            compareMinAndMaxAmount(minAmount, maxAmount);

            return new FindExpensesCommand(expenses, description, minAmount, maxAmount);

        } catch (NumberFormatException e) {
            System.out.println("Please input a valid amount.");
            return null;
        } catch (BudgetBuddyException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public Command createCommand() {
        return handleFindExpensesCommand(input, expenses);
    }
}
