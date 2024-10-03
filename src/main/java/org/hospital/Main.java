package org.hospital;

import org.hospital.config.Tiempo;
import org.hospital.model.Cajero;
import org.hospital.model.Consultorio;
import org.hospital.model.Medico;
import org.hospital.model.Paciente;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Tiempo.setRangoCoeficienteTiempo(5.0, 10.0);
        Consultorio consultorio = new Consultorio(28, 4); // Capacidad 28 pacientes, 4 médicos

        // Crear y lanzar hilos de médicos
        for (int i = 0; i < 4; i++) {
            Medico medico = new Medico(i, consultorio);
            new Thread(medico).start();
        }

        // Crear y lanzar hilos de cajeros
        for (int i = 0; i < 2; i++) {
            Cajero cajero = new Cajero(i, consultorio);
            new Thread(cajero).start();
        }

        Integer i = 0;
        while(true) {
            Thread.sleep(Tiempo.ajustarTiempo(500));
            boolean esVip = (i++ % 5 == 0); // Pacientes VIP cada 5 pacientes
            Paciente paciente = new Paciente(i, esVip, consultorio);
            new Thread(paciente).start();
        }
    }
}