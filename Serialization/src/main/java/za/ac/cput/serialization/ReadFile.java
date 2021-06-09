/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.serialization;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * prints out the customer output file
 *
 * @author Luphiwe Sikupela 216060133
 * adp262s
 */
public class ReadFile {

    ObjectInputStream input;
    FileWriter fW;
    BufferedWriter bW;
    Customer customer;
    ArrayList<Customer> customers = new ArrayList<>();
    ArrayList<Supplier> suppliers = new ArrayList<>();

    public void openFile() {
        try {
            input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
            System.out.println("*** ser file created and opened for reading  ***");
        } catch (IOException ioe) {
            System.out.println("error opening ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    // Reading from file method.

    public void readFile() {
        try {
            while (true) {

                Object queue = input.readObject();
                String n = "Customer";
                String s = "Supplier";
                String name = queue.getClass().getSimpleName();
                if (name.equals(n)) {
                    customers.add((Customer) queue);
                } else if (name.equals(s)) {
                    suppliers.add((Supplier) queue);
                } else {
                    System.out.println("Error storing objects in the list");
                }

            }
        } catch (EOFException eofe) {
            System.out.println("End of file reached");
        } catch (ClassNotFoundException ioe) {
            System.out.println("Class error reading ser file: " + ioe);
        } catch (IOException ioe) {
            System.out.println("Error reading ser file: " + ioe);
        }

    }

    public void closeFile() {
        try {
            input.close();
        } catch (IOException ioe) {
            System.out.println("Error closing serialize file: " + ioe.getMessage());
            System.exit(1);
        }
    }

    public void sortCustomer() {
        // System.out.println("After sorting");

        String[] sortArr = new String[customers.size()];
        ArrayList<Customer> sortCustomer = new ArrayList<>();
        int data = customers.size();
        // Collection.sort(sortCustomer, new Comparator<Customers>(){
        for (int i = 0; i < data; i++) {
            sortArr[i] = customers.get(i).getStHolderId();
        }
        Arrays.sort(sortArr);

        for (int i = 0; i < data; i++) {
            for (int c = 0; c < data; c++) {
                if (sortArr[i].equals(customers.get(c).getStHolderId())) {
                    sortCustomer.add(customers.get(c));
                }
            }
        }
        customers.clear();
        customers = sortCustomer;

    }

    public int getAge(String birthDate) {
        String[] split = birthDate.split("-");

        LocalDate DoB = LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        LocalDate now = LocalDate.now();
        Period diff = Period.between(DoB, now);
        int age = diff.getYears();
        return age;
    }

    public String DoBFormatter(String DoB) {
        DateTimeFormatter formatDob = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate birth = LocalDate.parse(DoB);
        String date = birth.format(formatDob);
        return date;
    }

    public void writingToCustomerTxt() {
        try {

            fW = new FileWriter("customerOutFile.txt");
            bW = new BufferedWriter(fW);
            bW.write(String.format("%s\n", "===============================CUSTOMERS================================"));
            bW.write(String.format("%-10s %-10s %-10s %-10s %-10s\n", "ID", "Name", "Surname", "Date of Birth", "Age"));
            bW.write(String.format("%s\n", "==============================================================================="));
            for (int i = 0; i < customers.size(); i++) {
                bW.write(String.format("%-10s %-10s %-10s %-10s %-10s \n", customers.get(i).getStHolderId(), customers.get(i).getFirstName(), customers.get(i).getSurName(), DoBFormatter(customers.get(i).getDateOfBirth()), getAge(customers.get(i).getDateOfBirth())));
            }
            bW.write(String.format("%s\n", " "));
            bW.write(String.format("%s", rent()));
        } catch (IOException fnfe) {
            System.out.println(fnfe);
            System.exit(1);
        }
        try {
            bW.close();
        } catch (IOException ioe) {
            System.out.println("error closing text file: " + ioe.getMessage());
            System.exit(1);
        }
    }

    public String rent() {
        int data = customers.size();
        int canRent = 0;
        int cannotRent = 0;
        for (int i = 0; i < data; i++) {
            if (customers.get(i).getCanRent()) {
                canRent++;
            } else {
                cannotRent++;
            }
        }
//          System.out.println("Number of Customers who can rent" + canRent);
//          System.out.println("Number of customers who cannot rent" + cannotRent);

        String output = String.format("Number of customers who can rent: %s\nNumber of customers who cannot rent: %s\n", canRent, cannotRent);
        return output;

    }

}
