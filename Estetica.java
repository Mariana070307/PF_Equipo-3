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
public class Estetica {

    public static void main(String[] args) {
        String nombreCliente;
        String telefono;
        String fechaAtencion;
        double subtotal = 0;
        int totalManicures = 0;

        System.out.println("===== BarWomen =====");
        System.out.println("PROMOCION: Manicure 2x1");

        do {
            System.out.print("Ingresa tu nombre: ");
            nombreCliente = Entrada.sc.nextLine().trim();
        } while (nombreCliente.isEmpty());

        telefono = Entrada.leerTelefono("Ingresa tu telefono (10 digitos): ");

        fechaAtencion = Entrada.leerFecha("Fecha de la atencion:");

        // Menu de servicios (Sayid)
        String continuar;
        do {
            System.out.println();
            System.out.println("--- MENU DE SERVICIOS ---");
            System.out.println("1. Manicure (2x1)");
            System.out.println("2. Pedicure");
            System.out.println("3. Corte de cabello");
            int opcion = Entrada.leerEntero("Elige una opcion: ", 1, 3);

            int tipoCorte = 0;
            if (opcion == 3) {
                tipoCorte = Entrada.leerEntero("Tipo de corte (1 = Dama, 2 = Caballero): ", 1, 2);
            }

            int cantidad = Entrada.leerEntero("Cantidad: ", 1, Integer.MAX_VALUE);

            double precioUnit = Servicios.obtenerPrecio(opcion, tipoCorte);
            double importe = precioUnit * cantidad;
            subtotal += importe;

            if (opcion == 1) {
                totalManicures += cantidad;
            }

            System.out.println("Agregado: " + Servicios.obtenerNombre(opcion, tipoCorte)
                    + " x" + cantidad + " = " + Entrada.dinero(importe));

            System.out.print("Desea agregar otro servicio? (S/N): ");
            continuar = Entrada.sc.nextLine().trim().toUpperCase();
        } while (!continuar.equals("N"));

        double descuento = Calculos.calcularDescuento(totalManicures, Servicios.PRECIO_MANICURE);
        double baseGravable = subtotal - descuento;
        double iva = Calculos.calcularIVA(baseGravable);
        double total = Calculos.calcularTotal(baseGravable, iva);

        // ===== Metodo de pago =====
        System.out.println();
        System.out.println("Total a pagar: " + Entrada.dinero(total));
        System.out.println("1. Efectivo");
        System.out.println("2. Tarjeta");
        int metodoPago = Entrada.leerEntero("Metodo de pago: ", 1, 2);

        double pagoCon = total;
        double cambio = 0;
        if (metodoPago == 1) {
            pagoCon = Entrada.leerMonto("Efectivo recibido: ", total);
            cambio = pagoCon - total;
        }

        // ===== Ticket =====
        System.out.println();
        System.out.println("========== TICKET BARWOMEN =========");
        System.out.println("Cliente: " + nombreCliente);
        System.out.println("Telefono: " + telefono);
        System.out.println("Fecha de atencion: " + fechaAtencion);
        System.out.println("Subtotal:             " + Entrada.dinero(subtotal));
        System.out.println("Descuento: -" + Entrada.dinero(descuento));
        System.out.println("Subtotal c/descuento: " + Entrada.dinero(baseGravable));
        System.out.println("IVA (16%):            " + Entrada.dinero(iva));
        System.out.println("TOTAL:                " + Entrada.dinero(total));
        if (metodoPago == 1) {
            System.out.println("Metodo de pago:       Efectivo");
            System.out.println("Efectivo recibido:    " + Entrada.dinero(pagoCon));
            System.out.println("Cambio:               " + Entrada.dinero(cambio));
        } else {
            System.out.println("Metodo de pago:       Tarjeta");
        }
        System.out.println("====================================");
        System.out.println("Gracias por su visita, " + nombreCliente + "!");
    }
}
