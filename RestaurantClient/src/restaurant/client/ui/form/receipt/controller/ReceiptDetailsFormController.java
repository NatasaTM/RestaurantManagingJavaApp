package restaurant.client.ui.form.receipt.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Natasa Todorov Markovic
 */
public class ReceiptDetailsFormController {

    public static void prepareView(JTextArea txtAreaReceipt) {
        StringBuilder stringBuilder = new StringBuilder();
        try ( FileReader fIn = new FileReader("receipt_details.txt");  BufferedReader in = new BufferedReader(fIn);) {
            
            boolean end = false;
            
            while(!end){
                String s = in.readLine();
                
                if(s==null) end = true;
                else{
                  stringBuilder.append(s).append("\n");  
                }
            }
            txtAreaReceipt.setText(stringBuilder.toString());

        } catch (Exception e) {

        }

        
    }

}
