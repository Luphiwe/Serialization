/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.serialization;

/**
 *
 * @author CPUT
 */
public class RunReadFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         ReadFile obj = new ReadFile();
        obj.openFile();
        obj.readFile();
     //  obj.closeFile();
        obj.sortCustomer();
        obj.writingToCustomerTxt();
        
    }
    
}
