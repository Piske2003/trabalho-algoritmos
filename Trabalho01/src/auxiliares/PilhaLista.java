package auxiliares;
public class PilhaLista<T> implements Pilha<T> {
    
    private ListaEncadeada<T> lista;

    public PilhaLista() {
        this.lista = new ListaEncadeada<>();
    }

    @Override
    public void push(T info){
        lista.inserir(info);
    }
    @Override
    public T pop(){
        if (lista.estaVazia()) {
            throw new PilhaVaziaException("A pilha está vazia");
        }
        return lista.retirarInicio();
    }
    
    @Override
    public T peek(){
        if (lista.estaVazia()) {
            throw new PilhaVaziaException("A pilha está vazia");     
        }
        return lista.obterPrimeiro();
        
    }
    
    @Override
    public boolean estaVazia(){
        return lista.estaVazia();
        
    }
    @Override
    public void liberar(){
        lista.liberar();

    }
    
    @Override
    public String toString(){
        return lista.toString();
        
    }
    
   
    public void inverterLista(){
        lista.inverterLista();
    }
    
    
    
    
    
}
