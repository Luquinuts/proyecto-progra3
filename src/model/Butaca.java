package model;

public class Butaca {

    private int idButaca;
    private int idSala;
    private String fila;
    private int numero;

    public Butaca() {
    }

    public Butaca(int idButaca, int idSala, String fila, int numero) {
        this.idButaca = idButaca;
        this.idSala = idSala;
        this.fila = fila;
        this.numero = numero;
    }

    public int getIdButaca() {
        return idButaca;
    }

    public void setIdButaca(int idButaca) {
        this.idButaca = idButaca;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Butaca{idButaca=" + idButaca
                + ", idSala=" + idSala
                + ", fila='" + fila + '\''
                + ", numero=" + numero + '}';
    }
}
