package auxiliares;
public class NoLista<T> {
    
    private T info;
    private NoLista<T> proximo;

    // cosntrutores não utilizados
    //public NoLista(T info) { // construtor para declarar apenas uma informação
   //     this.info = info;
  //  }
    
  //  public NoLista(T info, NoLista<T> proximo) { // construtor para declarar duas informações
  //      this.info = info;
  //      this.proximo = null;
  //  }
    
    public T getInfo() { // busca a informação declarada
        return info;
    }

    public void setInfo(T info) { // insere as informações
        this.info = info;
    }

    public NoLista<T> getProximo() { // busca o próximo
        return proximo;
    }

    public void setProximo(NoLista<T> proximo) { // insere o próximo
        this.proximo = proximo;
    }
    
    
    
}
