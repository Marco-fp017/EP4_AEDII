package services;

public class DistEntreNos {
    private final int distancia;
    private int quantPares;

    public DistEntreNos(int distancia) {
        this.distancia = distancia;
        this.quantPares = 1;
    }

    public int getDistancia() {
        return distancia;
    }

    public int getQuantPares() {
        return quantPares;
    }

    public void incrementaQuantPares(){
        this.quantPares++;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.distancia;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DistEntreNos other = (DistEntreNos) obj;
        if (this.distancia != other.distancia) {
            return false;
        }
        return true;
    }

}
