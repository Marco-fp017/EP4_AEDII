package entities;

import java.util.ArrayList;
import java.util.List;

public class IDs{
    private final int id;
    private boolean visitado;
    private List<IDs> adj = new ArrayList<>();

    public IDs(int id) {
        this.id = id;
        this.visitado = false;
    }

    //retorna a lista de adjacência
    public List<IDs> getAdj() {
        return adj;
    }
    
    //adiciona um id na sua lista de adjacência
    public void setInListaAdj(IDs id){
        this.adj.add(id);
    }

    public int getId() {
        return id;
    }

    public boolean getVisitado() {
        return visitado;
    }

    //marca o elemento como visitado pela componente a que ele pertence
    public void setVisitado() {
        this.visitado = true;
    }
    
    public void desfazVisitado(){
        this.visitado = false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
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
        final IDs other = (IDs) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
