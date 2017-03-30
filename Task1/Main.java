/***** ELEC 373 Project Question 1 *****

This program implements message flow from the top layer to the bottom layer of the OSI model.

The details below outline the problem specifics
    -different functions are used to implement different protocols at each layer
    -protocol headers are sequence up to 64 characters with ASCII representations
    -each protocol function has two parameters: a message passed from the higher layer (char array) and size of message
    -function attaches its header in front of the message, prints the new message on the standard output, and invokes the lower protocol
    -program input is a sequence of 80 characters or less
    -assume destination IP address and port number and append it to appropriate message
    -define the appropriate size in bits of IP address version and port number being used
    -output should also show message + headers (bits) and print the bit sequence at the physical layer
    -format output by using a character as a delimiter to partition headers from original message
    -program should be able to accept arbitrary bit stream

*/



public class Main {
    public static void main(String[] args){
        
    }
    
    public static char[] applicationLayerInterface(char[] message, int size){
        
    }
    
    public static char[] presentationLayerInterface(char[] message, int size){
        
    }
    
    public static char[] sessionLayerInterface(char[] message, int size){
        
    }
    
    public static char[] transportLayerInterface(char[] message, int size){
        
    }
    
    public static char[] networkLayerInterface(char[] message, int size){
        
    }
    
    public static char[] dataLinkLayerInterface(char[] message, int size){
        
    }
    
    public static char[] physicalLayerInterface(char[] message, int size){
        
    }
    
}