package ep4_aed;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import services.BuscaEmLargura;
import entities.IDs;
import java.io.BufferedWriter;
import java.io.FileWriter;
import services.DistEntreNos;

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
            List<DistEntreNos> distancias = new ArrayList<>();
            distancias.addAll(BL.getAllPaths());
            
            System.out.println("Distancia entre Nos | Frequencia");
            for(DistEntreNos d : distancias) System.out.println(d.getDistancia() + ": " + d.getQuantPares());
            
            //descobrindo qual é a componente gigante e fazendo a lista de distâncias entre vértices recebê-la
            for(IDs ids : IDList){
                if(!ids.getVisitado()){
                    try{
                        BuscaEmLargura otherComp = new BuscaEmLargura(IDList, ids);
                        if(otherComp.getComponente().size() > BL.getComponente().size()){
                            distancias.clear();
                            distancias.addAll(otherComp.getAllPaths());
                        }
                    //ignorando possível exceções em casos onde só exista 1 elemento não visitado    
                    }catch(NullPointerException | IndexOutOfBoundsException e){}
                }
            }

            System.out.print("Informe o caminho de saída: ");
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(sc.nextLine()))){
                bw.write("Distancia entre Nos,Frequencia");
                bw.newLine();
                
                for(DistEntreNos d : distancias){
                    bw.write(d.getDistancia() + "," + d.getQuantPares());
                    bw.newLine();
                }
                
                bw.close();
            }
        }catch (IOException e){
            e.getMessage();
        }
        finally{
            sc.close();
        }
    }
    //C:\\Users\\marco\\Desktop\\OD_graph.txt
    //C:\\Users\\marco\\Desktop\\gigante2.txt
    //C:\\Users\\marco\\Desktop\\out\\summary6.cvs
    //C:\\Users\\marco\\Desktop\\auxEP4.txt
}
