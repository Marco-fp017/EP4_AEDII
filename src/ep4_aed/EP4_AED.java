package ep4_aed;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import services.BuscaEmLargura;
import entities.IDs;

public class EP4_AED {
    
    public static void main(String[] args) {
        List<IDs> IDList = new ArrayList<>(); //lista de IDs
        
        Scanner sc = new Scanner(System.in);

        System.out.print("Informe o caminho de entrada do arquivo a ser lido: ");
        //Lendo o arquivo .csv contendo as informações dos encontros
        try (BufferedReader bf = new BufferedReader (new FileReader(sc.nextLine()))){
            String aux = bf.readLine(); // linha com número de ids 
            aux = bf.readLine();        // linha com numero de encontros
            aux = bf.readLine();

            while(aux != null){
                String[] line = aux.split(" ");
                
                //pegando os dois IDs que se encontraram
                IDs id1 = new IDs(Integer.parseInt(line[0]));
                IDs id2 = new IDs(Integer.parseInt(line[1]));
                
                //adicionando-os na lista de ajdacência um do outro
                
                if (!IDList.contains(id1)){ //id não existe na lista de IDs
                    IDList.add(id1);
                    id1.setInListaAdj(id2);
                } else{//id existe, preciso localizá-lo p/ adicionar o outro id na lista de adjacência
                    for (IDs auxId : IDList){
                        if(auxId.equals(id1)){
                            auxId.setInListaAdj(id2);
                            break;
                        }
                    }
                }
                
                if (!IDList.contains(id2)){ //id não existe na lista de IDs
                    IDList.add(id2);
                    id2.setInListaAdj(id1);
                } else{//id existe, preciso localizá-lo p/ adicionar o outro id na lista de adjacência
                    for (IDs auxId : IDList){
                        if(auxId.equals(id2)){
                            auxId.setInListaAdj(id1);
                            break;
                        }
                    }
                }
                aux = bf.readLine();
            }   
            
            bf.close();
            
            //componente conexa começando do meu 1° elemento 
            BuscaEmLargura BL = new BuscaEmLargura(IDList, IDList.get(0));
            List<IDs> componenteGigante = new ArrayList<>();
            componenteGigante.addAll(BL.getComponente());
            
/*            IDs[] edgeTo = BL.getEdgeTo();
            for(int i = 0; i < edgeTo.length; i++) if(edgeTo[i] != null) System.out.println(i + ": " + edgeTo[i].getId() + " ");

            //adicionando todas as componentes conexas na lista de componentes
            for(IDs ids : IDList){
                if(!ids.getVisitado()){
                    compo = new BuscaEmProfundidade(IDList, ids);
                    List<IDs> auxCompo1 = new ArrayList<>();
                    auxCompo1.addAll(compo.getComponente());
                    componentes.add(auxCompo1);
                    compo.closeComponente();
                }
            }
            
            /*percorre todas as componentes conexas e verifica se já existe uma componente com determinado grau na lista
                de componentes, se existir, apenas incrementa 1 no n° de componentes com aquele grau, se não, cria-se
                uma nova distribuição com esse novo tamanho
            
            for(List Lc : componentes){     
                int tamList = Lc.size();
                boolean tamExist = false;
                for(DistrGrausComponentes dg : tamComponentes){
                    if(dg.getGrauComponente() == tamList){
                        dg.incrementaQuantComponentes();
                        tamExist = true;
                        break;
                    }
                }
                if(!tamExist) tamComponentes.add(new DistrGrausComponentes(tamList));
            }
            int contEnc = 0;
            
            //contando todos os encontros existentes 
            for(DistrGrausComponentes d : tamComponentes) contEnc += d.getQuantidadeEncontros()*d.getGrauComponente();
            
            System.out.println("Quantidade de Encontros: "+contEnc);

            System.out.print("Informe o caminho de saída: ");
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(sc.nextLine()))){
                bw.write("Quantidade de Encontros: " + contEnc);
                bw.newLine();
                bw.write("Grau da componente,Frequencia do grau");
                bw.newLine();
                
                for(DistrGrausComponentes d : tamComponentes){ 
                    bw.write(d.getGrauComponente() + "," + d.getQuantidadeEncontros());
                    bw.newLine();
                }
                
                bw.close();
            }
*/        }catch (IOException e){
            e.getMessage();
        }
        finally{
            sc.close();
        }
    }
    //C:\\Users\\marco\\Desktop\\OD_graph.txt
    //C:\\Users\\marco\\Desktop\\out\\summary5.cvs  
}
