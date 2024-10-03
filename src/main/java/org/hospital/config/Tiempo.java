package org.hospital.config;

import java.util.Random;

public abstract class Tiempo {

    private static double minCoeficienteTiempo = 1.0; // Tiempo mínimo
    private static double maxCoeficienteTiempo = 1.0; // Tiempo máximo
    private static final Random random = new Random();

    public static void setRangoCoeficienteTiempo(double min, double max) {
        minCoeficienteTiempo = min;
        maxCoeficienteTiempo = max;
    }

    public static long ajustarTiempo(long tiempoOriginalMs) {
        double coeficienteAleatorio = minCoeficienteTiempo +
                (maxCoeficienteTiempo - minCoeficienteTiempo) * random.nextDouble();
        return (long) (tiempoOriginalMs * coeficienteAleatorio);
    }
}
