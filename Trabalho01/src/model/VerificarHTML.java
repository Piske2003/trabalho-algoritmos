package model;

import auxiliares.PilhaLista;
import auxiliares.PilhaVaziaException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gabrielle.piske
 */
public class VerificarHTML {
    
    public DefaultTableModel modeloTabela;
    public boolean estaFormatado = true;
    public PilhaLista<String> listaTags;
    public String tagEmAberto;
    public String tagFinalIncorreta;
    private boolean encontrouTagHtml = false;
    
    
    public VerificarHTML() {
        this.modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Tag");
        modeloTabela.addColumn("Número de Ocorrências");
        estaFormatado = true;
        listaTags = new PilhaLista<>();
        tagEmAberto = "";
        tagFinalIncorreta = "";
      
    }
    
    // verificar melhor como alterar esse código para algo compreensivel e simples
    public String lerArquivo(File htmlPage){
        try{
            Scanner scanner = new Scanner(htmlPage, "UTF-8");
            
            while(scanner.hasNextLine()){ 
                String linha = scanner.nextLine();
                
                Pattern htmlTagPattern = Pattern.compile("<([^>]+)>");
                Matcher htmlTagMatcher = htmlTagPattern.matcher(linha);
                
                if (htmlTagMatcher.find()) {
                    encontrouTagHtml = true;
                     limparTags(htmlTagMatcher); 
                    
                }
               
            }
           
            scanner.close();
            
            if (!encontrouTagHtml) {
                return "O arquivo não parecer ser no formato HTML.";
                
            }
            
            return retornoFormatacao();
        } catch(FileNotFoundException exception){
            return "O arquivo não foi encontrado.";
        }
    }
    
    
       
    public void limparTags(Matcher htmlTagMatcher){
        while (htmlTagMatcher.find()) {
            String formataTag = htmlTagMatcher.group(0).replaceAll("\\s+\\w+(=\"[^\"]*\")?", "");
            PilhaLista<String> pilhaLimpa = new PilhaLista<>();
            boolean tagFinal = false;
            
            for (int letra = formataTag.length()-2; letra > 0; letra--) {
                if(formataTag.charAt(letra) == '/' && letra == 1){
                    tagFinal = true;
                } else if(formataTag.charAt(letra) != '/' && formataTag.charAt(letra) != ' '){
                    pilhaLimpa.push(String.valueOf(formataTag.charAt(letra)).toLowerCase());
                }   
            }
            
            String tagSemSinal = "";
            
            while(!pilhaLimpa.estaVazia()){
                tagSemSinal += pilhaLimpa.pop();
            }
            
            redirecionaPorTipo(tagSemSinal, tagFinal);
        }
    }
    
    public void redirecionaPorTipo(String tagSemSinal, boolean isFinalTag){
        if(tagSemSinal.equals("meta") || tagSemSinal.equals("base") || tagSemSinal.equals("br") || tagSemSinal.equals("col") ||
            tagSemSinal.equals("command") || tagSemSinal.equals("embed") || tagSemSinal.equals("hr") || tagSemSinal.equals("img") ||
            tagSemSinal.equals("input") || tagSemSinal.equals("link") || tagSemSinal.equals("param") || tagSemSinal.equals("source") ||
            tagSemSinal.equals("!doctype") && !adicionaTagSeExiste(tagSemSinal)){
            Object[] linhaTabela = {tagSemSinal, "1"};
            modeloTabela.addRow(linhaTabela);                    
        } else if (isFinalTag){
            try{
                String openingTag = listaTags.pop();
                comparaTags(openingTag, tagSemSinal);
            } catch(PilhaVaziaException exception){}
        } else {
            listaTags.push(tagSemSinal);
        }
    }
    
    // compara o fechamento e abertura -- verificar sintaxe para outras variações que podem ocorrer
    public void comparaTags(String openingTag, String closingTag){
        if(!openingTag.equals(closingTag)){
            if(tagFinalIncorreta.equals("")){
                tagFinalIncorreta =  "Aguardava-se a tag final </" + openingTag + "> mas foi encontrada a tag </" + closingTag + ">";
                tagEmAberto = openingTag;
            }
            estaFormatado = false;
        } else if(!adicionaTagSeExiste(openingTag)){
            Object[] linhaTabela = {openingTag, "1"};// a ser alterado
            modeloTabela.addRow(linhaTabela);
        }
    }
    
    // método a ser revisado - verifica se uma tag existe e conta a quantidade
    public boolean adicionaTagSeExiste(String tag) {
        boolean foundTag = false;
        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            String firstCellValue = (String) modeloTabela.getValueAt(i, 0); // Pega o valor da tag
            if (firstCellValue.equals(tag)) {
                // Pega a quantidade da segunda coluna e a converte para inteiro
                String quantidadeStr = (String) modeloTabela.getValueAt(i, 1);
                try {
                    int qtdeOcorrencias = Integer.parseInt(quantidadeStr) + 1; // Incrementa
                    modeloTabela.setValueAt(String.valueOf(qtdeOcorrencias), i, 1); // Atualiza
                    foundTag = true;
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter a quantidade: " + e.getMessage());
                }
                break; // Tag encontrada, não precisa continuar o loop
            }
        }
        return foundTag;
}


    

    public String retornoFormatacao(){    
        if(estaFormatado){  
            return "O arquivo está bem formatado.";
        } else if(listaTags.estaVazia()){
            return tagFinalIncorreta;
        } else {
           return retornoDeTagsSemFechamento();
        }
    }
    
    // retorna tags sem fechamento
 public String retornoDeTagsSemFechamento(){
        String penultimateTag = "";
        String ultimaTag = listaTags.pop();
        StringBuilder feedbackTagsWithoutClosing = new StringBuilder("Aguardava-se as seguintes tags finais:\n");

        while (!listaTags.estaVazia()) {
            penultimateTag = ultimaTag;
            feedbackTagsWithoutClosing.append("</").append(penultimateTag).append(">\n");
            ultimaTag = listaTags.pop();
        }
        
        feedbackTagsWithoutClosing.append("</").append(tagEmAberto).append(">\n").append("mas não foi encontrada.");
        
        return feedbackTagsWithoutClosing.toString();
    }


    

    
}
