package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pluviometria {
    private int id;
    private double valor;
    private LocalDate data;
    private int posto;

    public Pluviometria(int id, double valor, LocalDate data, int posto) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.posto = posto;
    }

    public int getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public int getPosto() {
        return posto;
    }

    @Override
    public String toString() {
        return "Data: " + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " | Precipitacao: " + String.format("%.1f", valor) + " mm"
                + " | Posto: " + posto;
    }
}
