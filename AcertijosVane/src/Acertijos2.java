import java.util.*;

public class Acertijos2 {
    private List<String> acertijos;
    private List<String> respuestasCorrectas;
    private Scanner scanner;

    public Acertijos2() {
        acertijos = new ArrayList<>();
        respuestasCorrectas = new ArrayList<>();
        scanner = new Scanner(System.in);

        // Agregar acertijos y sus respuestas correctas
        agregarAcertijos();
    }

    private void agregarAcertijos() {
        acertijos.add("¿Qué número es mayor que 3 y menor que 7 pero es numero primo?"); // Acertijo numérico
        respuestasCorrectas.add("5");

        acertijos.add("Tengo ciudades, pero no casas. Tengo montañas, pero no árboles. ¿Qué soy?"); // Acertijo textual
        respuestasCorrectas.add("un mapa");

        acertijos.add("¿Qué palabra se escribe incorrectamente en todos los diccionarios?"); // Acertijo textual
        respuestasCorrectas.add("incorrectamente");

        acertijos.add("1. Piensa en un número del 1 al 5. 2. Multiplicalo por 2. 3. Suma 8 unidades. 4. Divide por 2. ¿Cuál es el resultado?"); // Secuencia
        respuestasCorrectas.add("4"); // Resulta en x + 4, si x = 0, respuesta 4.

        acertijos.add("Si sumas 3 a un número y el resultado es 10, ¿cuál es el número?"); // Nuevo acertijo
        respuestasCorrectas.add("7"); // 10 - 3

        // Acertijo dependiente
        acertijos.add("Si el número que encontraste en el acertijo anterior es x, ¿cuánto es x multiplicado por 2?"); // Acertijo dependiente
        respuestasCorrectas.add("14"); // 7 * 2
    }

    public void resolverAcertijos() {
        for (int i = 0; i < acertijos.size(); i++) {
            System.out.println("Acertijo " + (i + 1) + ": " + acertijos.get(i));
            System.out.print("Tu respuesta: ");
            String respuestaUsuario = scanner.nextLine().trim().toLowerCase();

            if (respuestaUsuario.equals(respuestasCorrectas.get(i).toLowerCase())) {
                System.out.println("¡Correcto!");
            } else {
                System.out.println("Incorrecto. La respuesta correcta es: " + respuestasCorrectas.get(i));
            }
            System.out.println(); // Espacio entre acertijos
        }
    }

    public static void main(String[] args) {
        Acertijos2 juego = new Acertijos2();
        juego.resolverAcertijos();
    }
}
