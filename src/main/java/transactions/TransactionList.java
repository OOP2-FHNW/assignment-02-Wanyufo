package transactions;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Dieter Holz
 * @author andreas
 */
    public class TransactionList {
        private final List<Transaction> allTransactions = new ArrayList<>();

        public void addTransaction(Transaction transaction) {
            allTransactions.add(transaction);
        }

        public int size() {
            return allTransactions.size();
        }

        public List<Transaction> transactionsInYear(int year) {
           return allTransactions.stream()
                    .filter(transactions -> transactions.getYear() == year)
                    .sorted(Comparator.comparingInt(Transaction::getValue))
                    .collect(Collectors.toList());
        }

        public List<String> cities() {
           return allTransactions.stream()
                   .map(transaction -> transaction.getTrader().getCity())
                   .distinct()
                   .collect(Collectors.toList());
        }

        /**
         * @param city the trader's city
         * @return all traders from given city sorted by name.
         */
        public List<Trader> traders(String city) {
            return allTransactions.stream()
                    .filter(transaction -> transaction.getTrader().getCity().equals(city))
                    .sorted(Comparator.comparing(Trader -> Trader.getTrader().getName()))
                    .map(Transaction::getTrader)
                    .distinct()
                    .collect(Collectors.toList());

        }

        /**
         * Returns a Map of all transactions.
         *
         * @return a Map with the year as key and a list of all transaction of this year as value
         */
        public Map<Integer, List<Transaction>> transactionsByYear() {
           return allTransactions.stream()
                    .collect(Collectors.groupingBy(Transaction::getYear));
        }

        /**
         * @param city the city
         * @return true if there are any trader based in given city
         */
        public boolean traderInCity(String city) {
            return allTransactions.stream()
                    .anyMatch(transaction -> transaction.getTrader().getCity().equals(city));

        }

        /**
         * @param from the trader's current location
         * @param to   the trader's new location
         */
        public void relocateTraders(String from, String to) {
            allTransactions.stream()
                    .filter(transaction -> transaction.getTrader().getCity().equals(from))
                    .forEach(transaction -> transaction.getTrader().setCity(to));
        }

        /**
         * @return the highest value in all the transactions
         */
        public int highestValue() {
           return allTransactions.stream()
                   .map(Transaction::getValue)
                   .max(Comparator.comparing(Integer::intValue))
                   .orElseThrow(NoSuchElementException::new);
        }

        /**
         * @return the sum of all transaction values
         */
        public int totalValue() {
            return allTransactions.stream()
                    .map(Transaction::getValue)
                    .reduce(0, Integer::sum);

        }

        /**
         * @return the Transaction with the lowest value
         */
        public Transaction getLowestValueTransaction(){
            return allTransactions.stream()
                    .min(Comparator.comparing(Transaction::getValue))
                    .orElseThrow(NoSuchElementException::new);
        }

        /**
         * @return a string of all traders’ names sorted alphabetically
         */
        public String traderNames() {
            return allTransactions.stream()
                    .map(transaction -> transaction.getTrader().getName())
                    .sorted(Comparator.comparing(String::toString))
                    .distinct()
                    .collect(Collectors.joining(""));
        }

    }

