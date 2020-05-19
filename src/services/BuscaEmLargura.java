package services;

import entities.IDs;
import entities.FilaDinamica;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BuscaEmLargura {
    private IDs[] edgeTo;   // último vértice encontrado antes de encontrar um vértice (caminho entre quaisquer dois vértices)
    private final IDs source;   //id de início da componente
    
    List<IDs> componente = new ArrayList<>();   //lista para guardar a componente conexa que parte do id 'source'
    List<DistEntreNos> allPaths = new ArrayList<>();    //lista para guardar as distâncias entre pares de nós
    
    public BuscaEmLargura(List<IDs> Grafo, IDs source) {
        this.edgeTo = new IDs[tamEdgeTo(Grafo)+1];
        this.source = source;
        bfs(Grafo, source);
    }

    private void bfs(List<IDs> Grafo, IDs s) {
        FilaDinamica fila = new FilaDinamica();  //fila de IDs
        
        Grafo.get(Grafo.indexOf(s)).setVisitado();  //marcar a fonte como visitada na lista e na fila
        s.setVisitado();
        fila.inserirNo(s);  // e colocá-la na fila.
        
        while(!fila.estaVazia()){
            IDs v = fila.removerNo();  // Remova o próximo vértice da fila e guarda ele
            if(!componente.contains(v)) componente.add(v);

            for(IDs w : Grafo.get(Grafo.indexOf(v)).getAdj()){
            //if(!Grafo.get(Grafo.indexOf(v)).getAdj().get(Grafo.get(Grafo.indexOf(v)).getAdj().indexOf(w)).getVisitado()){
                if(!w.getVisitado()){   // Para cada vértice adjacente não marcado
                    if(edgeTo[w.getId()] == null) edgeTo[w.getId()] = v; // indico que o último vértice que eu visitei antes de chegar neste foi o v
                    Grafo.get(Grafo.indexOf(w)).setVisitado();  //marcando todos os vértices adjacentes a v como visitados
                    w.setVisitado();
                    fila.inserirNo(w); //impilho todos os adjacentes a v na fila
                    
                    if(!componente.contains(w)) componente.add(w);  //adicionando na lista todos os elementos conexos à source 
                }
            }
        }
        fila.limparFila();
        calculaDistancias();
    }
    
   /*calcular o histograma das distâncias entre pares de nós. Ou seja, indicar 
    * quantos pares de nós s˜ao adjacentes, quantos est˜ao a 2 passos de distância, 
    * quantos est˜ao a três passos de distância e assim por diante
    */
    private List<DistEntreNos> calculaDistancias(){

        List<IDs> auxComponente = new ArrayList<>();
        auxComponente.addAll(componente);        
        
       /*itera sobre cada id, fazendo um novo edgeTo a partir 
        * deste, e assim, mostra o menor caminho entre este id
        * e todos os outros do grafo (exceto aqueles em que já foram
        * computadas as suas distâncias, pois o id atual também já vai
        * ter sido)
        */
        for(IDs s : auxComponente){
            for(int i = 0; i < allPaths.size(); i++) 
                System.out.println(allPaths.get(i).getDistancia() + ": " + allPaths.get(i).getQuantPares());
            System.out.println("");
            this.edgeTo = newEdgeSource(auxComponente, s);  //método que cria um novo EdgeTo com 'source' no id atual
        
            //adicionando a distância entra cada dois vértices não computados do grafo na lista de distâncias  
            for(IDs end : componente.subList(componente.indexOf(s), componente.size())){
                Stack<Integer> dist = caminhoEntre(s, end);
                //se não existir um caminho ou se o id do fim do caminho já foi verificado, não faço nada
                if(dist == null) {} 
                else{
                    //cria um novo objeto de distância entre nós 
                    DistEntreNos distanciaX = new DistEntreNos(dist.size()-1);      

                    /*verifico se essa distância já existe na minha lista de distâncias
                    * se não existir, eu adiciono uma nova distância, se existir eu 
                    * encontro essa distância e incremento 1 nela
                    */
                    if(!allPaths.contains(distanciaX)) allPaths.add(distanciaX);
                    else{

                        for(DistEntreNos d : allPaths){
                            if(d.equals(distanciaX)){
                                d.incrementaQuantPares();
                                break;
                            }
                        }
                    }
                }
            }
        }    
        for(DistEntreNos d : allPaths) System.out.println(d.getDistancia() + ": " + d.getQuantPares());
        return allPaths;
    }
    
    //retorna o menor caminho entre dois IDs
    private Stack<Integer> caminhoEntre (IDs inicio, IDs fim){
        if(!existeCaminho(inicio, fim)) return null;
        
        Stack<Integer> caminho = new Stack<>();

        for(int i = fim.getId(); i != inicio.getId(); i = edgeTo[i].getId()) caminho.push(i);
        caminho.push(inicio.getId());
        
        return caminho;
    }

    //verifica se existe um caminho entre quaisquer dois vértices do grafo
    private boolean existeCaminho(IDs inicio, IDs fim){
        if(edgeTo[inicio.getId()] != null && edgeTo[fim.getId()] != null) return true;
        
        return false;
    }    

   /* método que faz o edgeTo com base em um vértice qualquer, este 
    * faz basicamente a mesma coisa que o 'bfs', porém, sem alterar
    * a componente
    */        
    private IDs[] newEdgeSource(List<IDs> subGrafo, IDs s) {
        IDs[] auxEdgeTo = new IDs[tamEdgeTo(subGrafo)+1];
        FilaDinamica fila = new FilaDinamica();  

        for(int i = 0; i < subGrafo.size(); i++) subGrafo.get(i).desfazVisitado();
        
        subGrafo.get(subGrafo.indexOf(s)).setVisitado();  
        s.setVisitado();
        fila.inserirNo(s); 
        auxEdgeTo[s.getId()] = s;
        while(!fila.estaVazia()){
            IDs v = fila.removerNo(); 

            for(IDs w : subGrafo.get(subGrafo.indexOf(v)).getAdj()){
                if(auxEdgeTo[w.getId()] == null && w.getVisitado()) w.desfazVisitado();
                if(!w.getVisitado()){ 
                    if(auxEdgeTo[w.getId()] == null) auxEdgeTo[w.getId()] = v; 
                    subGrafo.get(subGrafo.indexOf(w)).setVisitado();  
                    w.setVisitado();
                    fila.inserirNo(w); 
                }
            }
        }
        return auxEdgeTo;
    }    

    public List<IDs> getComponente() {
        return componente;
    }
    
    public void closeComponente(){
        componente.clear();
    }    

    public IDs[] getEdgeTo() {
        return edgeTo;
    }

   /* retorna o tamanho que deve conter o método toEdge 
    * para que não haja estouro de array, já que os índices
    * são os ids dos vértices
    */
    private int tamEdgeTo(List<IDs> Grafo){
        int maiorID = 0;
        for(IDs id : Grafo) if(id.getId() > maiorID) maiorID = id.getId();

        return maiorID;
    }
}
