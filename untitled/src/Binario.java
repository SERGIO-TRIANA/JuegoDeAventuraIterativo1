import java.util.Scanner;

public class Binario {
    public static void main(String[] args) {
        int answer = 0;
        System.out.println(toBinary(answer));

    }

    public static StringBuilder toBinary(int decimal_number) {
        Scanner scanner = new Scanner(System.in);


        int residue;

        System.out.println("ingrese un numero del sistema decimal para convertirlo a binario");
        decimal_number = scanner.nextInt();

        StringBuilder binarioConcatenado = new StringBuilder();

        do {
            residue = decimal_number % 2;
            binarioConcatenado.append(residue);
            decimal_number = decimal_number / 2;


        } while (decimal_number != 0);
        binarioConcatenado = new StringBuilder(new StringBuilder(binarioConcatenado).reverse().toString());

        return binarioConcatenado;
    }
}
