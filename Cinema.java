package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();

        int menuOption;
        String[][] seating = new String[rows][seats];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                seating[i][j] = "S";
            }
        }

        do {
            System.out.println("\n1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            menuOption = scanner.nextInt();

            switch (menuOption) {
                case 1:
                    showSeating(seating);
                    break;
                case 2:
                    buyTicket(seating);
                    break;
                case 3:
                    statistics(seating);
                    break;
                default:
                    break;
            }
        } while(menuOption != 0);
    }

    static void showSeating(String[][] seating) {
        System.out.println("\nCinema:");

        /* print numbers top */
        System.out.print(" ");
        for (int i = 1; i <= seating[0].length; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        /* print rows */
        int i = 0;
        for (String[] seats : seating) {
            i++;
            System.out.print(i);
            Arrays.stream(seats).forEach(e -> System.out.print(" " + e));
            System.out.println();
        }
    }

    static void buyTicket(String[][] seating) {
        Scanner scanner = new Scanner(System.in);
        boolean wrongCoordinate = true;

        int rowNum = 0;
        int seatNum = 0;
        boolean wrongRow;
        boolean wrongSeat;

        while (wrongCoordinate) {
            System.out.println("\nEnter a row number:");
            rowNum = scanner.nextInt();

            System.out.println("Enter a seat number in that row:");
            seatNum = scanner.nextInt();

            wrongRow = rowNum > seating.length || rowNum <= 0;
            wrongSeat = seatNum > seating[0].length || seatNum <= 0;

            if (wrongRow || wrongSeat) {
                System.out.println("\nWrong input!");
            } else if ("B".equals(seating[rowNum - 1][seatNum - 1])) {
                System.out.println("\nThat ticket has already been purchased!");
            } else {
                wrongCoordinate = false;
            }
        }

        System.out.printf("\nTicket price: $%d", ticketPrice(seating, rowNum));

        seating[rowNum - 1][seatNum - 1] = "B";
    }

    static int ticketPrice(String[][] seating, int rowNum) {
        return seating.length * seating[0].length < 60 ? 10 : rowNum <= seating.length / 2 ? 10 : 8;
    }

    static void statistics(String[][] seating) {
        int numberTickets = 0;
        int income = 0;

        for (int i = 0; i < seating.length; i++) {
            for (int j = 0; j < seating[i].length; j++) {
                if ("B".equals(seating[i][j])) {
                    numberTickets++;
                    income += ticketPrice(seating, i + 1);
                }
            }
        }

        int numTotalTickets = seating.length * seating[0].length;
        double percentage = (double) numberTickets / (double) numTotalTickets * 100;

        int frontCinema = seating.length / 2 * seating[0].length;
        int backCinema = seating.length % 2 == 0 ? frontCinema : (seating.length / 2 + 1) * seating[0].length;
        boolean smallCinema = seating.length * seating[0].length < 60;
        int totalIncome = smallCinema ? numTotalTickets * 10 : frontCinema * 10 + backCinema * 8;

        System.out.printf("%nNumber of purchased tickets: %d%n", numberTickets);
        System.out.printf("Percentage: %.2f%%%n", percentage);
        System.out.printf("Current income: $%d%n", income);
        System.out.printf("Total income: $%d%n", totalIncome);
    }
}
