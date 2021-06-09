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
import java.util.ArrayList;
import java.util.Arrays;

/**
 *Prints out the supplier output in the file
 * @author Luphiwe Sikupela
 * 216060133
 * adp262s
 */
public class ReadSupplierFile {
      ObjectInputStream input;
FileWriter fW;
BufferedWriter bW;
Customer customer;
ArrayList<Customer> customers= new ArrayList<>();
ArrayList<Supplier> suppliers= new ArrayList<>();
       public void openFile(){
        try{
            input = new ObjectInputStream( new FileInputStream( "stakeholder.ser" ) ); 
            System.out.println("*** ser file created and opened for reading  ***");               
        }
        catch (IOException ioe){
            System.out.println("error opening ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
       // Reading from file method.
           public void readFile(){
               
           try{
               
           
        while(true){
            
               Object queue = input.readObject();
            String n ="Customer";
               String s = "Supplier";
             String name = queue.getClass().getSimpleName();
              if (name.equals(n)){
                customers.add((Customer)queue);
               } else if (name.equals(s)){
                   suppliers.add((Supplier)queue);
             } else {
                   System.out.println("Error storing objects in the list");
               }
        }
           }
        
           

           catch (EOFException eofe) {
            System.out.println("End of file reached"+ eofe.getMessage());
        }
        catch (ClassNotFoundException ioe) {
            System.out.println("Class error for reading ser file: "+ ioe);
        }
        catch (IOException ioe) {
            System.out.println("Error reading ser file: "+ ioe);
        }
        
     
        }
       public void closeFile(){
        try{
            input.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("Error closing serialize file: " + ioe.getMessage());
            System.exit(1);
        }
    }
       public void sortSupplier(){
          // System.out.println("After sorting");
      
           
        String[] sortArr = new String[suppliers.size()];
        ArrayList<Supplier> sortSupp= new ArrayList<>();
        int data = suppliers.size();
       // Collection.sort(sortCustomer, new Comparator<Customers>(){
      for (int i = 0; i < data; i++) {
           sortArr[i] = suppliers.get(i).getName();
        }
        Arrays.sort(sortArr);
        
        for (int i = 0; i < data; i++) {
            for (int c = 0; c < data; c++) {
                if (sortArr[i].equals(suppliers.get(c).getName())){
                    sortSupp.add(suppliers.get(c));
                }
            }
        }
        suppliers.clear();
        suppliers= sortSupp;
       }
      
       
       public void writingToSupplierTxt(){
        try{
           
            fW = new FileWriter("supplierOutFile.txt");
            bW = new BufferedWriter(fW);
            bW.write(String.format("%s\n","===============================SUPPLIERS================================"));
            bW.write(String.format("%-10s %-10s %-10s %-10s\n", "ID","Name","Prod Type","Description"));
            bW.write(String.format("%s\n","==============================================================================="));
            for (int i = 0; i < suppliers.size(); i++) {
                bW.write(String.format("%-10s %-10s %-10s %-10s \n", suppliers.get(i).getStHolderId(), suppliers.get(i).getName(), suppliers.get(i).getProductType(), suppliers.get(i).getProductDescription()));
            }
            
            bW.write(String.format("%s\n"," "));
            
        }
        catch(IOException fnfe )
        {
            System.out.println(fnfe);
            System.exit(1);
        }
        try{
            bW.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing text file: " + ioe.getMessage());
            System.exit(1);
        }
       }
        
   
    

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ReadSupplierFile obj = new ReadSupplierFile();
        obj.openFile();
        obj.readFile();
   
        obj.sortSupplier();
        obj.writingToSupplierTxt();
        // TODO code application logic here
    }
    
}
