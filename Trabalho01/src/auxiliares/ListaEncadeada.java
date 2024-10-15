package auxiliares;
public class ListaEncadeada<T>{
    private NoLista<T> primeiro;
    //private NoLista<T> ultimo;

    
    // construtor define lista vazia
    public ListaEncadeada() { 
        this.primeiro = null;
       // this.ultimo = null; //não necessário
       
    }
    
    //getter do primeiro
    public NoLista<T> getPrimeiro() {
        return primeiro;
    }
    
       // inserir dados na lista
    public void inserir(T valor){ //inserindo na sequencia
        NoLista<T> novo = new NoLista<>();
            novo.setInfo(valor);
            novo.setProximo(this.primeiro);
            this.primeiro = novo;
      
       // if(this.obterComprimento() == 0){
       //    this.primeiro = elemento; // insere no primeiro item
        //   this.ultimo = elemento;
       // }else{
       //     this.ultimo.setProximo(elemento); 
       //     this.ultimo = elemento;
       // }
       
    }
    
    // verifica se vazia
    public boolean estaVazia(){
      return this.primeiro == null;
      //return this.obterComprimento() == 0;
        
    }
    
    /// busca por conteúdo
   public NoLista<T> buscar(T valor){
       NoLista<T> p = this.primeiro;
        while(p != null){
           if (p.getInfo().equals(valor)) {
               return p;
           } 
           p = p.getProximo();
        }
       return null;
    }
   
   // retira um item da lista
   public void retirar(T valor){
       NoLista<T>  anterior = null;
       NoLista<T> p = primeiro;
       
       while((p != null) && (!p.getInfo().equals(valor))){
           anterior = p;
           p = p.getProximo();
       }
       if (p != null) {
           if (p == primeiro) {
             primeiro =  p.getProximo();
          }else { 
              anterior.setProximo(p.getProximo());
           }
       }
   }
    
   public T retirarInicio(){
       if (estaVazia()) {
           throw new PilhaVaziaException("Lista vazia");
        }
       T info = primeiro.getInfo();
       primeiro = primeiro.getProximo();
       return info;
   }
   
       // quantidade de nós encadeados na lista
    public int obterComprimento(){
       int tamanho = 0;
       NoLista<T> p = primeiro;
       
       while(p != null){
           tamanho ++;
           p = p.getProximo();
       }
       return tamanho;
   }
  
    public T obterPrimeiro(){
        if (estaVazia()) {
            throw new PilhaVaziaException("Lista vazia");
            
        }
        return primeiro.getInfo();
    }
    
    
    //busca pela posição maneira incoreta
  public NoLista<T> obterNo(int idx){
      if ((idx < 0) || (idx >= this.obterComprimento())) {
          throw new IndexOutOfBoundsException("Posição inexistente: " + idx);
      }
      
      NoLista<T> atual = primeiro;
      
      int sequencia = 0;
      while((atual != null) && (sequencia <idx)){
       sequencia++;
      atual = atual.getProximo();
  }
      return atual;
      //for (int i = 0; i < idx; i++) {
     //     atual = atual.getProximo();
     // }
    //  return atual;    
  }
  
  // busca pela posição maneira correta
    public NoLista<T> obterNoCorreto(int idx){
        if (idx<0) {
            throw new IndexOutOfBoundsException("Posição inexistente: " + idx);            
        }
        NoLista<T> p = getPrimeiro();
        while((p!=null) && (idx>0)){
            idx--;
            p = p.getProximo();
        }
        
        if (p == null) {
            throw new IndexOutOfBoundsException();
        }
        return p;
    }
      
  // to string para impressão do conteudo da lista
    @Override
  public String toString(){
     String resultado = "";
     NoLista<T> atual = primeiro;
     
     while(atual != null){
         resultado += atual.getInfo();
         
         if (atual.getProximo()!= null) {
             resultado += ", ";
         }
        atual = atual.getProximo();
     }
       return resultado;
  }
  
  // esvazia a pilha
  public void liberar(){
      primeiro = null;
  }
  
  
  // insere dados no fim da lista
  public void inserirNoFim(T valor){
        NoLista<T> novo = this.primeiro;
        novo.setInfo(valor);
        novo.setProximo(null);
        
        if (primeiro == null) {
            primeiro = novo;
          
      }else{
            NoLista<T> p = this.primeiro;
            while(p.getProximo()!=null){
                p = p.getProximo();
            }
            p.setProximo(novo);
        }
        
  }
  
  public void inverterLista(){
      NoLista<T> anterior = null;
      NoLista<T> atual = primeiro;
      NoLista<T> proximo = null;
      
      while(atual != null){
          proximo = atual.getProximo();
          atual.setProximo(anterior);
          anterior = atual;
          atual = proximo;
      }
      
      primeiro = anterior;
  }
  
  
  
    
}

