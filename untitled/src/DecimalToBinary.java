import java.util.Scanner;

public class DecimalToBinary {

    public static void main(String[] args) {



        int decimalNumber = 23;
        System.out.println("Ingrese un numero del sistema decimal.");
        System.out.println("El número binario de " + decimalNumber + " es: " + Dec2Bin(decimalNumber));
    }

    public static String Dec2Bin(int decimal) {
        if (decimal == 0) {
            return "0";
        } else if (decimal == 1) {
            return "1";
        } else {
            return Dec2Bin(decimal / 2) + (decimal % 2);

        }
    }
}

