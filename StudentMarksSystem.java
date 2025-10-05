package student;

import java.util.Scanner;
import java.util.Locale;

public class StudentMarksSystem {
    private static String studentName = "";
    private static String studentId = "";
    private static int numCourses = 0;
    private static double[] marks;
    private static boolean detailsEntered = false;
    private static boolean marksEntered = false;
    
    public static void main(String[] args) {
        // Set English locale
        Locale.setDefault(Locale.US);
        
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    enterStudentDetails(scanner);
                    break;
                case 2:
                    enterMarks(scanner);
                    break;
                case 3:
                    displayMarksInfo();
                    break;
                case 4:
                    displayStudentDetails();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter 1-5.");
            }
            System.out.println();
        } while (choice != 5);
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Enter Student Details");
        System.out.println("2. Enter Marks");
        System.out.println("3. Display Marks Info.");
        System.out.println("4. Display Student Details");
        System.out.println("5. Exit");
    }
    
    private static void enterStudentDetails(Scanner scanner) {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine().trim();
        studentName = toTitleCase(name);
        
        boolean validId = false;
        while (!validId) {
            System.out.print("Enter ID Number: ");
            studentId = scanner.nextLine().trim();
            
            if (studentId.matches("^4\\d{8}$")) {
                validId = true;
            } else {
                System.out.println("Invalid ID! KKU ID must start with 4 and be 9 digits long.");
            }
        }
        
        boolean validCourses = false;
        while (!validCourses) {
            System.out.print("How many courses last semester? ");
            numCourses = scanner.nextInt();
            scanner.nextLine();
            
            if (numCourses > 0 && numCourses <= 5) {
                validCourses = true;
            } else {
                System.out.println("Invalid number! Maximum 5 courses allowed.");
            }
        }
        
        marks = new double[numCourses];
        detailsEntered = true;
        marksEntered = false;
        System.out.println("Student details entered successfully!");
    }
    
    private static void enterMarks(Scanner scanner) {
        if (!detailsEntered) {
            System.out.println("Please enter student details first (Option 1)!");
            return;
        }
        
        System.out.print("Enter Marks for " + numCourses + " subjects: ");
        for (int i = 0; i < numCourses; i++) {
            boolean validMark = false;
            while (!validMark) {
                double mark = scanner.nextDouble();
                
                if (mark >= 0 && mark <= 100) {
                    marks[i] = mark;
                    validMark = true;
                } else {
                    System.out.println("Invalid mark! Please enter a value between 0-100:");
                }
            }
        }
        scanner.nextLine();
        
        marksEntered = true;
        System.out.println("Marks entered successfully!");
        
        System.out.println("\nAutomatically displaying marks info:");
        displayMarksInfo();
    }
    
    private static void displayMarksInfo() {
        if (!detailsEntered) {
            System.out.println("Please enter student details first (Option 1)!");
            return;
        }
        
        if (!marksEntered) {
            System.out.println("Please enter marks first (Option 2)!");
            return;
        }
        
        double total = 0;
        double max = marks[0];
        double min = marks[0];
        
        System.out.println("\nCourse Marks Information:");
        System.out.println("No.\tMark\tP/F");
        
        for (int i = 0; i < numCourses; i++) {
            String passFail = marks[i] >= 60 ? "Pass" : "Fail";
            System.out.println((i + 1) + "\t" + marks[i] + "\t" + passFail);
            
            total += marks[i];
            
            if (marks[i] > max) max = marks[i];
            if (marks[i] < min) min = marks[i];
        }
        
        double average = total / numCourses;
        
        System.out.println("\nAverage: " + average);
        System.out.println("Maximum Mark: " + max);
        System.out.println("Minimum Mark: " + min);
    }
    
    private static void displayStudentDetails() {
        if (!detailsEntered) {
            System.out.println("Please enter student details first (Option 1)!");
            return;
        }
        
        System.out.println("Name: " + studentName);
        System.out.println("KKU ID: " + studentId);
        
        if (marksEntered) {
            double total = 0;
            for (double mark : marks) {
                total += mark;
            }
            double average = total / numCourses;
            System.out.println("Course Mark P/F Avg: " + average);
            
            System.out.println("\nNo.\tMark\tP/F\tNote");
            double max = marks[0];
            double min = marks[0];
            int maxIndex = 0;
            int minIndex = 0;
            
            for (int i = 0; i < numCourses; i++) {
                if (marks[i] > max) {
                    max = marks[i];
                    maxIndex = i;
                }
                if (marks[i] < min) {
                    min = marks[i];
                    minIndex = i;
                }
            }
            
            for (int i = 0; i < numCourses; i++) {
                String passFail = marks[i] >= 60 ? "Pass" : "Fail";
                String note = "";
                
                if (i == maxIndex) note = "Max";
                if (i == minIndex) note = "Min";
                
                System.out.println((i + 1) + "\t" + marks[i] + "\t" + passFail + "\t" + note);
            }
        } else {
            System.out.println("Marks not entered yet.");
        }
    }
    
    private static String toTitleCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        
        String[] words = str.split("\\s+");
        StringBuilder titleCase = new StringBuilder();
        
        for (String word : words) {
            if (!word.isEmpty()) {
                titleCase.append(Character.toUpperCase(word.charAt(0)))
                         .append(word.substring(1).toLowerCase())
                         .append(" ");
            }
        }
        
        return titleCase.toString().trim();
    }
}