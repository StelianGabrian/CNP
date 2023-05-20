import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<String> getSocialSecurityNumbersFromFile(String filename) {
        ArrayList<String> securityNumbers = new ArrayList<>();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                securityNumbers.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return securityNumbers;
    }

    public static void main(String[] args) {
        ArrayList<String> socialSecurityNumbers = getSocialSecurityNumbersFromFile("CNP.txt");

        for (int i = 0; i < socialSecurityNumbers.size(); i++) {
            String CNP = socialSecurityNumbers.get(i);
            //Validarea unui C.N.P. constă în calcularea componentei C și
            // compararea acesteia cu valoarea primită a aceleiași componente.
            // Dacă acestea sunt identice, înseamnă că C.N.P. verificat este valid.
            // Calcularea componentei C se face folosind constanta "279146358279", după cum urmează:
            //  - fiecare cifră din primele 12 cifre ale C.N.P. este înmulțită cu corespondentul său din constantă
            //  - rezultatele sunt însumate și totalul se împarte la 11
            //  - dacă restul împărțirii este mai mic de 10, acela reprezintă valoarea componentei C
            //  - dacă restul împărțirii este 10, valoarea componentei C este 1
            int digit1 = 2 * Integer.parseInt(CNP.substring(0, 1));
            int digit2 = 7 * Integer.parseInt(CNP.substring(1, 2));
            int digit3 = 9 * Integer.parseInt(CNP.substring(2, 3));
            int digit4 = 1 * Integer.parseInt(CNP.substring(3, 4));
            int digit5 = 4 * Integer.parseInt(CNP.substring(4, 5));
            int digit6 = 6 * Integer.parseInt(CNP.substring(5, 6));
            int digit7 = 3 * Integer.parseInt(CNP.substring(6, 7));
            int digit8 = 5 * Integer.parseInt(CNP.substring(7, 8));
            int digit9 = 8 * Integer.parseInt(CNP.substring(8, 9));
            int digit10 = 2 * Integer.parseInt(CNP.substring(9, 10));
            int digit11 = 7 * Integer.parseInt(CNP.substring(10, 11));
            int digit12 = 9 * Integer.parseInt(CNP.substring(11, 12));
            int componentCReceived = Integer.parseInt(CNP.substring(12, 13));
            //System.out.println("Received C component is: " + componentCReceived);

            int productSum = digit1 + digit2 + digit3 + digit4 + digit5 + digit6 + digit7 + digit8 + digit9 + digit10 + digit11 + digit12;

            int componentCcalculated = '0';
            if (productSum % 11 < 10) {
                componentCcalculated = productSum % 11;
            } else {
                componentCcalculated = 1;
            }
            //System.out.println("Calculated C component is: " + componentCcalculated);

            if (componentCReceived != componentCcalculated) {
                System.out.println("Invalid CNP!!!!");
                System.out.println();

            } else {
                System.out.println("Valid CNP");

                // In Romania, the first character of the social security number
                // represents the biological sex of a person.
                // Below, we take from the social security number the first character
                // and check whether they are male or female.
                char gender = ' ';
                switch (CNP.charAt(0)) {
                    case '1', '3', '5', '7' -> gender = 'M';
                    case '2', '4', '6', '8' -> gender = 'F';
                }

                //In Romania, the second and the third character of the social security number
                // represents the birth year (YY) of a person.
                //If the first character of the social security number is 1 or 2, then that person birth year is 19YY
                //If the first character of the social security number is 3 or 4, then that person birth year is 18YY
                //If the first character of the social security number is 5 or 6, then that person birth year is 20YY
                String prefix = "";
                switch (CNP.charAt(0)) {
                    case '1', '2' -> prefix = "19";
                    case '3', '4' -> prefix = "18";
                    case '5', '6' -> prefix = "20";
                }
                //I simplified the formula for birthdate and made it a string in order to avoid 01-09 month
                // and 01-09 day error parsing.

                String birthDateString = (prefix + CNP.substring(1, 3) + "-" + CNP.substring(3, 5) + "-" + CNP.substring(5, 7));

                //The birthdate was formatted the same as the current date (yyyy-MM-dd) such that we can calculate the
                //difference of two dates.
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate birthDate = LocalDate.parse(birthDateString, formatter);

                //We use current computer's date (the date of the day when we run the program) to calculate the age from CNP.
                LocalDate currentDate = LocalDate.now();

                //The eight and the ninth character of the social security number
                //represents the county in which a person is born
                //County's code are presented in county's alphabetical order, with some exception.
                int county = Integer.parseInt(CNP.substring(7, 9));
                String stringCounty = "";
                switch (county) {
                    case 1 -> stringCounty = "Alba";
                    case 2 -> stringCounty = "Arad";
                    case 3 -> stringCounty = "Arges";
                    case 4 -> stringCounty = "Bacau";
                    case 5 -> stringCounty = "Bihor";
                    case 6 -> stringCounty = "Bistrita Nasaud";
                    case 7 -> stringCounty = "Botosani";
                    case 8 -> stringCounty = "Brasov";
                    case 9 -> stringCounty = "Braila";
                    case 10 -> stringCounty = "Buzau";
                    case 11 -> stringCounty = "Caras Severin";
                    case 12 -> stringCounty = "Cluj";
                    case 13 -> stringCounty = "Constanta";
                    case 14 -> stringCounty = "Covasna";
                    case 15 -> stringCounty = "Dambovita";
                    case 16 -> stringCounty = "Dolj";
                    case 17 -> stringCounty = "Galati";
                    case 18 -> stringCounty = "Gorj";
                    case 19 -> stringCounty = "Harghita";
                    case 20 -> stringCounty = "Hunedoara";
                    case 21 -> stringCounty = "Ialomita";
                    case 22 -> stringCounty = "Iasi";
                    case 23 -> stringCounty = "Ilfov";
                    case 24 -> stringCounty = "Maramures";
                    case 25 -> stringCounty = "Mehedinti";
                    case 26 -> stringCounty = "Mures";
                    case 27 -> stringCounty = "Neamt";
                    case 28 -> stringCounty = "Olt";
                    case 29 -> stringCounty = "Prahova";
                    case 30 -> stringCounty = "Satu Mare";
                    case 31 -> stringCounty = "Salaj";
                    case 32 -> stringCounty = "Sibiu";
                    case 33 -> stringCounty = "Suceava";
                    case 34 -> stringCounty = "Teleorman";
                    case 35 -> stringCounty = "Timis";
                    case 36 -> stringCounty = "Tulcea";
                    case 37 -> stringCounty = "Vaslui";
                    case 38 -> stringCounty = "Valcea";
                    case 39 -> stringCounty = "Vrancea";
                    case 40 -> stringCounty = "Bucuresti";
                    case 41 -> stringCounty = "Bucuresti - Sector 1";
                    case 42 -> stringCounty = "Bucuresti - Sector 2";
                    case 43 -> stringCounty = "Bucuresti - Sector 3";
                    case 44 -> stringCounty = "Bucuresti - Sector 4";
                    case 45 -> stringCounty = "Bucuresti - Sector 5";
                    case 46 -> stringCounty = "Bucuresti - Sector 6";
                    case 47 -> stringCounty = "Bucuresti - Sector 7 (desfiintat";
                    case 48 -> stringCounty = "Bucuresti - Sector 8 (desfiintat";
                    case 49 -> stringCounty = "CNP eronat!";
                    case 50 -> stringCounty = "CNP eronat!";
                    case 51 -> stringCounty = "Calarasi";
                    case 52 -> stringCounty = "Giurgiu";
                }


                System.out.println("Your CNP is: " + CNP);
                System.out.println("Gender: " + gender);
                System.out.println("Birth date:   " + birthDate);
                System.out.println("Current date: " + currentDate);

                System.out.print("Age: ");
                Period age = Period.between(birthDate, currentDate);
                System.out.printf("%d years, %d months and %d days ",
                        age.getYears(),
                        age.getMonths(),
                        age.getDays());

                System.out.println();
                System.out.println("County: " + stringCounty);
                System.out.println();
            }
        }
    }
}