package org.hospital.model;

import org.hospital.config.Tiempo;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class Consultorio {

    private final Semaphore capacidadCentro;
    private final BlockingQueue<Paciente>[] filasMedicos;
    private final BlockingQueue<Paciente> filaVIP;
    private final BlockingQueue<Paciente> filaCobro;

    public Consultorio(int capacidad, int numMedicos) {
        this.capacidadCentro = new Semaphore(capacidad);
        this.filasMedicos = new LinkedBlockingQueue[numMedicos];
        for (int i = 0; i < numMedicos; i++) {
            filasMedicos[i] = new LinkedBlockingQueue<>();
        }
        this.filaVIP = new LinkedBlockingQueue<>();
        this.filaCobro = new LinkedBlockingQueue<>();
    }



    public void ingresarCentro(Paciente paciente) throws InterruptedException {
        capacidadCentro.acquire();
        System.out.printf("\n%s ingresó al centro médico.", paciente.getCanonicalName());
    }

    public void esperarAtencion(Paciente paciente) throws InterruptedException {
        Thread.sleep(Tiempo.ajustarTiempo(1000));
        if (paciente.getEsVip()) {
            filaVIP.put(paciente);
            System.out.printf("\n%s esperando en fila VIP.", paciente.getCanonicalName());
        } else {
            Integer medicoId = paciente.getId() % filasMedicos.length;
            filasMedicos[medicoId].put(paciente);
            System.out.printf("\n%s esperando en la fila del médico %s", paciente.getCanonicalName(),  medicoId);
        }
    }

    public Paciente atenderPaciente(Medico medico) throws InterruptedException {
        Thread.sleep(Tiempo.ajustarTiempo(1000));
        Integer idMedico = medico.getId();
        if (!filasMedicos[idMedico].isEmpty()) {
            return filasMedicos[idMedico].take();
        } else if (!filaVIP.isEmpty()) {
            return filaVIP.take();
        } else {
            return null;
        }
    }

    public void abonarConsulta(Paciente paciente) throws InterruptedException {
        filaCobro.put(paciente);
    }

    public Paciente cobrarPaciente() throws InterruptedException {
        return filaCobro.take();
    }

    public void salirCentro(Paciente paciente) {
        try {
            Thread.sleep(Tiempo.ajustarTiempo(1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        capacidadCentro.release();
        System.out.printf("\n%s salió del centro médico.", paciente.getCanonicalName());
    }
}
