package org.hospital.model;

import lombok.Data;
import org.hospital.config.Tiempo;

@Data
public class Medico implements Runnable {
    private Integer id;
    private Consultorio consultorio;
    private Integer pacientesAtendidos;

    public Medico(Integer id, Consultorio consultorio) {
        this.id = id;
        this.consultorio = consultorio;
        this.pacientesAtendidos = 0;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(Tiempo.ajustarTiempo(1000));
                Paciente paciente = consultorio.atenderPaciente(this);
                if (paciente != null) {
                    Thread.sleep(Tiempo.ajustarTiempo(1000));
                    pacientesAtendidos++;
                    System.out.printf("\nMédico %s atendió al paciente %s" , id, paciente.getId());
                } else {
                    System.out.printf("\nMédico %s está durmiendo...", id);
                    Thread.sleep(Tiempo.ajustarTiempo(2000));
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
