package org.hospital.model;

import lombok.Data;
import org.hospital.config.Tiempo;

@Data
public class Cajero implements Runnable {
    private final Integer id;
    private final Consultorio consultorio;

    public Cajero(Integer id, Consultorio consultorio) {
        this.id = id;
        this.consultorio = consultorio;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Paciente paciente = consultorio.cobrarPaciente();
                if (paciente != null) {
                    Thread.sleep(Tiempo.ajustarTiempo(1000));
                    System.out.printf("\nCajero %s cobró al paciente %s" , id, paciente.getId());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
