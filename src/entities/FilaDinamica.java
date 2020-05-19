package entities;

public class FilaDinamica {
    private No inicio;
    private No fim;

    //criar pilha
    public FilaDinamica(){
        inicio = null;
        fim = null;
    }
    
    //inserir elemento
    public void inserirNo(IDs ID){
        No novo = new No(ID);
        novo.setProximo(null);
        //inserção em fila vazia
        if(inicio == null) inicio = novo;
        
        //inserção em fila já com elementos
        else fim.setProximo(novo);
            
        fim = novo;
    }
    
    //remover elemento e retorná-lo
    public IDs removerNo(){
        if(inicio == null) return null; //   throw new NullPointerException("a pilha está vazia");

        else {
            No idAux = inicio;
            inicio = inicio.getProximo();
            if(inicio == null) fim = null;
            return idAux.getID();
        }
    }
    
    //imprime diretamente no método
    public void imprimirFila1(){
        if(inicio != null){ 
            No end = inicio;
            System.out.print(end.getID().getId());
            while(end.getProximo() != null){
                System.out.print(" " + end.getID().getId());
                end = end.getProximo();
            }
            System.out.println();
        }
        else{
            System.out.println();
        }
    }    

    //retorna uma string com os valores que devem ser imprimidos
    public String imprimirFila2(){
        if(inicio == null) return "\n";
        String saida = "" + inicio.getID().getId();
        No end = inicio.getProximo();
        while(end != null){
            saida += " " + end.getID().getId();
            end = end.getProximo();
        }
        saida += "\n";
        return saida;
    }

    //verificar se determinado elemento existe na pilha
    public No buscarNaFila(IDs IdBuscado){
        if(inicio.getID().equals(IdBuscado)) return inicio;
        No end = inicio;
        
        while(end != null){
            if(end.getID().equals(IdBuscado)) return end;
            end = end.getProximo();
        } 
        return null;
    }

    public void limparFila(){
        this.inicio = null;
        this.fim = null;
    }
    
    //retorna o tamalho da pilha
    public int tamanhoFila(){
        int tam = 0;
        No end = inicio;
        while(end != null){
            tam++;
            end = end.getProximo();
        }
        return tam;
    }
    
    //verifica se existem elementos na pilha
    public boolean estaVazia(){
        if(inicio == null) return true;
        return false;
    }

    public No getInicio() {
        return inicio;
    }

    public No getFim() {
        return fim;
    }
    
    public No getProximo(No no){
        return no.getProximo();
    }
}
