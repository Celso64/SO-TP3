package org.hospital.model;

import lombok.*;

@Data
public class Paciente implements Runnable {
    private Integer id;
    private final Boolean esVip;
    private final Consultorio consultorio;

    public Paciente(Integer id, Boolean esVip, Consultorio consultorio) {
        this.id = id;
        this.esVip = esVip;
        this.consultorio = consultorio;
    }

    @Override
    public void run() {
        try {
            consultorio.ingresarCentro(this);
            consultorio.esperarAtencion(this);
            consultorio.abonarConsulta(this);
            consultorio.salirCentro(this);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getCanonicalName(){
        return this.esVip
                ? String.format("Paciente %s", this.id)
                :  String.format("Paciente %s (VIP)", this.id);
    }
}
