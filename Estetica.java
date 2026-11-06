import java.util.Scanner;
 
public class Estetica {
 
    // declaracion de constantes 
    static final double TASA_IVA = 0.16;
    static final double PRECIO_MANICURE = 150;
    static final double PRECIO_PEDICURE = 200;
    static final double PRECIO_CORTE_DAMA = 180;
    static final double PRECIO_CORTE_CABALLERO = 120;
    // llama al scanner para toda la clase
    static final Scanner sc = new Scanner(System.in);
 
    //Funciones
    //modulo precios 
    static double obtenerPrecio(int opcion, int tipoCorte) {
        switch (opcion) {
            case 1:
                return PRECIO_MANICURE;
            case 2:
                return PRECIO_PEDICURE;
            case 3:
                return (tipoCorte == 1) ? PRECIO_CORTE_DAMA : PRECIO_CORTE_CABALLERO;
            default:
                return 0;
        }
    }
 
    //modulo Servicios 
    static String obtenerNombre(int opcion, int tipoCorte) {
        switch (opcion) {
            case 1:
                return "Manicure";
            case 2:
                return "Pedicure";
            case 3:
                return (tipoCorte == 1) ? "Corte de cabello (dama)" : "Corte de cabello (caballero)";
            default:
                return "Desconocido";
        }
    }
 
    // modulo descuento
    static double calcularDescuento(int totalManicures, double precioManicure) {
        return (totalManicures / 2) * precioManicure; // division entera
    }
 
    static double calcularIVA(double base) {
        return base * TASA_IVA;
    }
 
    static double calcularTotal(double base, double iva) {
        return base + iva;
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
 
    static String dinero(double valor) {
        return String.format("$%.2f", valor);
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
            nombreCliente = sc.nextLine().trim();
        } while (nombreCliente.isEmpty());
 
        telefono = leerTelefono("Ingresa tu telefono (10 digitos): ");
 
        fechaAtencion = leerFecha("Fecha de la atencion:");
 
        // Menu de servicios (Sayid)
        String continuar;
        do {
            System.out.println();
            System.out.println("--- MENU DE SERVICIOS ---");
            System.out.println("1. Manicure (2x1)");
            System.out.println("2. Pedicure");
            System.out.println("3. Corte de cabello");
            int opcion = leerEntero("Elige una opcion: ", 1, 3);
 
            int tipoCorte = 0;
            if (opcion == 3) {
                tipoCorte = leerEntero("Tipo de corte (1 = Dama, 2 = Caballero): ", 1, 2);
            }
 
            int cantidad = leerEntero("Cantidad: ", 1, Integer.MAX_VALUE);
 
            double precioUnit = obtenerPrecio(opcion, tipoCorte);
            double importe = precioUnit * cantidad;
            subtotal += importe;
 
            if (opcion == 1) {
                totalManicures += cantidad;
            }
 
            System.out.println("Agregado: " + obtenerNombre(opcion, tipoCorte)
                    + " x" + cantidad + " = " + dinero(importe));
 
            System.out.print("Desea agregar otro servicio? (S/N): ");
            continuar = sc.nextLine().trim().toUpperCase();
        } while (!continuar.equals("N"));
 
        double descuento = calcularDescuento(totalManicures, PRECIO_MANICURE);
        double baseGravable = subtotal - descuento;
        double iva = calcularIVA(baseGravable);
        double total = calcularTotal(baseGravable, iva);
 
        // ===== Metodo de pago (DESPUES del ciclo y de calcular total) =====
        System.out.println();
        System.out.println("Total a pagar: " + dinero(total));
        System.out.println("1. Efectivo");
        System.out.println("2. Tarjeta");
        int metodoPago = leerEntero("Metodo de pago: ", 1, 2);
 
        double pagoCon = total;
        double cambio = 0;
        if (metodoPago == 1) {
            pagoCon = leerMonto("Efectivo recibido: ", total);
            cambio = pagoCon - total;
        }
 
        // ===== Ticket =====
        System.out.println();
        System.out.println("========== TICKET BARWOMEN =========");
        System.out.println("Cliente: " + nombreCliente);
        System.out.println("Telefono: " + telefono);
        System.out.println("Fecha de atencion: " + fechaAtencion);
        System.out.println("Subtotal:             " + dinero(subtotal));
        System.out.println("Descuento: -" + dinero(descuento));
        System.out.println("Subtotal c/descuento: " + dinero(baseGravable));
        System.out.println("IVA (16%):            " + dinero(iva));
        System.out.println("TOTAL:                " + dinero(total));
        if (metodoPago == 1) {
            System.out.println("Metodo de pago:       Efectivo");
            System.out.println("Efectivo recibido:    " + dinero(pagoCon));
            System.out.println("Cambio:               " + dinero(cambio));
        } else {
            System.out.println("Metodo de pago:       Tarjeta");
        }
        System.out.println("====================================");
        System.out.println("Gracias por su visita, " + nombreCliente + "!");
    }
}
