import java.io.BufferedReader;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 * Created by stuartbourne on 2017-03-30.
 */
public class Main {

    public static void main(String[] args){
        CRC myCRC = new CRC();

        int msg = 0b01001001;
        int poly = 0b00000111;
        int error = 1;


        myCRC.setMessage(msg);
        myCRC.setGenerator(poly);

        myCRC.generator();
        System.out.println(myCRC.getCodeWords());
        myCRC.verifier();

        myCRC.alteration(error, 0); //add error into line




    }
}
