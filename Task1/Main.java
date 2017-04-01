import java.awt.*;
import java.math.BigInteger;
import java.util.Random;
import java.util.jar.Pack200;

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


/*

Questions:
    Q1: -"use a different function to implement different protocols at each layer"
            -do we implement a seperate layer object for each layer?
                -what protocols do you want us to implement?
            -

*/



public class Main {

    private static final String dest = "2485229321";
    private static final String port = "909";

    public static void main(String[] args){
        String message = "hello";
        String totalMessage = applicationLayerInterface(message, message.length());

        String sequence = toBits(totalMessage);
        System.out.println("Final message size: "+ totalMessage.length());
        System.out.println("Final message bit sequence length: " + sequence.length());
        System.out.println("Final message in bits: \n" + sequence);

    }

    private static String genRandChars(int size){
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final int N = alphabet.length();
        String returnVal = "";

        Random rand = new Random();

        for(int i = 0; i < size; i++){
            returnVal += alphabet.charAt(rand.nextInt(N));
        }

        return returnVal;
    }

    private static String applicationLayerInterface(String message, int size){
        final int applicationHeaderSize = 64;

        if (!message.isEmpty()){
            message = genRandChars(applicationHeaderSize) + message;
            size += applicationHeaderSize;
            System.out.println("Application Layer");
            System.out.println("Size: " + size + "\nmessage: " + message);
            System.out.println();
        }
        return presentationLayerInterface(message, message.length());
    }

    private static String presentationLayerInterface(String message, int size){
        final int presentationHeaderSize = 52;

        if (!message.isEmpty()) {
            message = genRandChars(presentationHeaderSize) + message;
            size += presentationHeaderSize;

            System.out.println("Presentation Layer");
            System.out.println("Size: " + size + "\nmessage: " + message);
            System.out.println();
        }

        return sessionLayerInterface(message, message.length());
    }

    private static String sessionLayerInterface(String message, int size){
        final int sessionHeaderSize = 48;

        if (!message.isEmpty()) {
            message = genRandChars(sessionHeaderSize) + message;
            size += sessionHeaderSize;

            System.out.println("Session Layer");
            System.out.println("Size: " + size + "\nmessage: " + message);
            System.out.println();
        }

        return transportLayerInterface(message, message.length());
    }

    private static String transportLayerInterface(String message, int size){
        final int transportHeaderSize = 32;

        if (!message.isEmpty()) {
            message = genRandChars(transportHeaderSize) + message;
            size += transportHeaderSize;

            System.out.println("Transport Layer");
            System.out.println("Size: " + size + "\nmessage: " + message);
            System.out.println();
        }

        return networkLayerInterface(message, message.length());
    }

    private static String networkLayerInterface(String message, int size){
        final int networkHeaderSize = 16;

        if (!message.isEmpty()) {
            message = genRandChars(networkHeaderSize) + message + dest + port;
            size += networkHeaderSize;

            System.out.println("Network Layer");
            System.out.println("Size: " + size + "\nmessage: " + message);
            System.out.println();
        }

        return dataLinkLayerInterface(message, message.length());
    }

    private static String dataLinkLayerInterface(String message, int size){
        //assuming we are using byte count framing method so we prepend amount of bytes in message
        final int dataLinkHeaderSize = 8;

        if (!message.isEmpty()) {
            message = genRandChars(dataLinkHeaderSize) + message;
            size += dataLinkHeaderSize;

            System.out.println("Network Layer");
            System.out.println("Size: " + size + "\nmessage: " + message);
            System.out.println();

        }

        return Integer.toString(size) + message;
    }


    private static String toBits(String message){
        //returns message in a string of bits
        return new BigInteger(message.getBytes()).toString(2);
    }


}