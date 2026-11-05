import java.util.Scanner;

public class Entrada {

    // Scanner compartido por todo el programa
    static final Scanner sc = new Scanner(System.in);

    static String dinero(double valor) {
        return String.format("$%.2f", valor);
    }

    static int leerEntero(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor < minimo) {
                    System.out.println("El valor debe ser al menos " + minimo + ".");
                } else if (valor > maximo) {
                    System.out.println("El valor debe ser como maximo " + maximo + ".");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un numero entero valido.");
            }
        }
    }

    static double leerMonto(String mensaje, double minimo) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                double valor = Double.parseDouble(linea);
                if (valor < minimo) {
                    System.out.println("El monto no cubre el total (" + dinero(minimo) + ").");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un monto valido.");
            }
        }
    }

    // modulo telefono
    static String leerTelefono(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String tel = sc.nextLine().trim();
            if (tel.matches("\\d{10}")) {
                return tel;
            }
            System.out.println("Ingresa un telefono valido de 10 digitos (solo numeros).");
        }
    }

    // modulo fecha de atencion
    static boolean esBisiesto(int anio) {
        return (anio % 4 == 0 && anio % 100 != 0) || anio % 400 == 0;
    }

    static int diasEnMes(int mes, int anio) {
        switch (mes) {
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return esBisiesto(anio) ? 29 : 28;
            default:
                return 31;
        }
    }

    static String leerFecha(String titulo) {
        System.out.println(titulo);
        int anio = leerEntero("  Año (2026-2100): ", 2026, 2100);
        int mes = leerEntero("  Mes (1-12): ", 1, 12);
        int dia = leerEntero("  Dia: ", 1, diasEnMes(mes, anio));
        return String.format("%02d/%02d/%04d", dia, mes, anio);
    }
}