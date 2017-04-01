import java.util.*;
import java.lang.*;

public class CRC{

    private static Integer generator;
    private static ArrayList<Integer> message;
    private static ArrayList<Integer> codeWord;


    public CRC(){
        generator = 0;
        message = new ArrayList<Integer>();
        codeWord = new ArrayList<Integer>();
    }

    public static void setGenerator(int myGenerator){
        generator = myGenerator;
    }

    public static void setMessage(Integer myMessage){
        message.add(myMessage);
    }

    public static ArrayList<Integer> getCodeWords(){
        return codeWord;
    }

    public static void generator(){
        System.out.println("Starting Generator");

        int degree = (int) (Math.log(generator)/Math.log(2));        //generate base 2 of generator
        int output; //output codeword
        int remainder;


        for (int i =0 ; i < message.size(); i++){
            //message shifted by degree
            output = (message.get(i) << degree);
            System.out.println("message after shift: " + Integer.toBinaryString(output));
            System.out.println("generatorval: " + Integer.toBinaryString(generator));
            System.out.println("degree: " + degree);

            //get the remainder
            remainder = getRemainder(output, generator);
            System.out.println("remainder: " + Integer.toBinaryString(remainder));
            output = output ^ remainder;

            codeWord.add(output);
            System.out.println("codeword: " + Integer.toBinaryString(output));
        }
        System.out.println();

    }

    public static void verifier(){
        System.out.println("Starting Verifier");

        boolean error = false;
        int intErr = -2;

        for (int i = 0 ; i < message.size(); i++){
            //generate degree
            int degree = (int) (Math.log(generator)/Math.log(2));        //generate base 2 of generator

            int verification = getRemainder(codeWord.get(i), generator);

            if (verification != 0) {
                error = true;
                intErr = i;
            }
        }

        if (error){
            if (message.size() == 1){
                System.out.println("Bit stream contains an error");
            } else {
                System.out.println("Bit stream contains an error at byte " + (intErr + 1));
            }
        } else {
            System.out.println("Bit stream is error free.");
        }

    }

    public static int alteration(int bitNum, int index){
         System.out.println("Altering bits");

        if (index < codeWord.size()){
            int codeWordBits = (int) (Math.log(codeWord.get(index))/(Math.log(2))) + 1;
            int error = 1 << (codeWordBits - bitNum);

            codeWord.set(index, codeWord.get(index) ^ error);   //generate error
            System.out.println("Generated error: " + Integer.toBinaryString(error));
            System.out.println("Erroneous Codeword: " + Integer.toBinaryString(codeWord.get(index)));
            return codeWord.get(index);
        } else {
            System.out.println("invalid index");
            return 0;
        }
    }

    public static int getRemainder(int message, int generator){


        int shift;
        int sizeDifference;

        //get degree of generator
        int degreeGen = (int) (Math.log(generator) / Math.log(2));
        //get degree of message
        int degreeMsg = (int) (Math.log(message) / Math.log(2));

        System.out.println("generator: " + message);
        System.out.println("messagebits: " + degreeMsg);
        System.out.println();


        if (degreeMsg >= degreeGen){
            sizeDifference = degreeMsg - degreeGen;
        } else {
            return message;        //if degree of msg less than generator, message is remainder
        }

        shift = message;

        for (int i = 0 ; i <= sizeDifference; i++){
            if ((shift >> (degreeMsg - i)) == 1){
                shift = shift ^ (generator << (sizeDifference - i));
            }
        }

        return shift;
    }
}