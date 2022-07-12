# FirebaseEasy [![](https://jitpack.io/v/Irineu333/FirebaseEasy.svg)](https://jitpack.io/#Irineu333/FirebaseEasy)

## Imports

``` java
// Db class
import com.fb.easy.Db;

// callbacks
import com.fb.easy.callback.Result;
import com.fb.easy.callback.Single;
import com.fb.easy.callback.Listener;

// contracts
import com.fb.easy.contract.Job;
```

## Iniciar

Para começar a utilizar a lib, obtenha uma intância da classe `Db`, por meio do construtor `new Db("path")` ou pelo método estático `Db.path("path")`, no qual path é o caminho que deseja acessar no seu banco de dados.

``` java
// constructor
Db db = new Db("path");
        
// static method
Db db = Db.path("path");
```

A partir da instância de `Db` você pode utiliar os seguintes métodos

``` java
// enviar dados
db.set(...)
db.update(...)
db.post(...)

// obter dados
db.get(...)

// gerar child key
db.getPushKey() : String

// acessar um filho
db.child(String) : Db
```

## Enviar dados

### set
Escreve um valor, substuindo todo o conteúdo do path.

``` java
// sem callback
Db.path("version").set("1.0.0");
```

Opcionalmente você pode passar uma implementação de `Result.Set` para tratar o resultado, sobrescrevendo `onSuccess` e  `onFailure(Exception)`. 

``` java
// com callback
Db.path("version").set("1.0.0", new Result.Set() {
    @Override
    public void onSuccess() {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});
```

### update
Atualiza parte dos dados, preservando o que não foi alterado.

``` java
// sem callback
Db.path("users").child(uid).update(user);
```

Opcionalmente você pode passar uma implementação de `Result.Update` para tratar o resultado, sobrescrevendo `onSuccess` e  `onFailure(Exception)`. 

``` java
// com callback
Db.path("users").child(uid).update(user, new Result.Update() {
    @Override
    public void onSuccess() {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});
```

### post
Cria um novo filho no path especificado, cujo o child key é gerado com o `getPushKey`.

``` java
// sem callback
Db.path("users").post(user);
```

Opcionalmente você pode passar uma implementação de `Result.Post` para tratar o resultado, sobrescrevendo `onSuccess` e `onFailure(Exception)`.

``` java
// com callback
Db.path("users").post(newUser, new Result.Post() {
    @Override
    public void onSuccess() {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});

```

Alternativamente você pode sobrescrever o `onSuccess(String)` para obter a nova child key.

``` java
Db.path("users").post(newUser, new Result.Post() {
    @Override
    public void onSuccess(String childKey) {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});
```

## Obter dados

### get
Obtém um dado do banco, sendo a forma como obtém especificada pelo callback implementado, que é dividido em três tipos principais: `Single`, `Listener` e `Listener.Children`, cada um deles possuindo os tipos; `Map`, `String`, `Boolean`, `Long`, `Double`, e o tipo genérico; `Generic<T>`, e suas versões em lista; `ListMap`, `ListString`, `ListBoolean`, `ListLong`, `ListDouble`, e `ListGeneric<T>`.

``` java
// callbacks
Single
Listener
Listener.Children

// tipos
Map // HashMap<String, Object>
String
Boolean
Long
Double
Generic<T> // assume qualquer tipo

// tipos versão lista
ListMap // List<HashMap<String, Object>>
ListString // List<String>
ListBoolean // List<Boolean>
ListLong // List<Long>
ListDouble //List<Double>
ListGeneric<T> //List<T> sendo T qualquer tipo

// padrão de chamada
callback.tipo
ex: Single.ListMap
```

#### get single
Obtém todo o conteúdo apenas uma vez. Você pode sobrescrever os métodos `onResult(T)` e `onFailure(Exception)` para objetos e `onResult(List<T>)` e  `onFailure(Exception)` para listas.

``` java
// obter objeto
Db.path("users").child(uid).get(new Single.Map() {
    @Override
    public void onResult(HashMap<String, Object> result) {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});

// obter lista
Db.path("users").get(new Single.ListMap() {
    @Override
    public void onResult(List<HashMap<String, Object>> result) {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});
```

No caso de listas, alternativamente você pode sobrescrever o método `onAdded(T, int index, String key)`, que é chamado em ordem para cada um dos itens da lista. 

``` java
// obter lista, item por item
Db.path("users").get(new Single.ListMap() {

    @Override
    public void onAdded(HashMap<String, Object> child, int index, String key) {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});
```

Ainda no caso de listas, você também pode alternativamente passar uma lista alvo no construtor, essa lista será atualizada com o resultado da requisição.

``` java
//exemplo de lista alvo
List<HashMap<String, Object>> list = new ArrayList<>();

//obter lista
Db.path("users").get(new Single.ListMap(list) {
    @Override
    public void onResult(List<HashMap<String, Object>> result) {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});
```

Exclusivamente no callback `Single`, você pode obter quanto tempo a requisição levou em milissegundos chamando o método `getDuration()` dentro do escopo do callback.

``` java
// obter lista
Db.path("users").get(new Single.ListMap() {
    @Override
    public void onResult(List<HashMap<String, Object>> result) {
        long duration = getDuration();
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});
```

#### get listener
Obtém todo o conteúdo e escuta as alterações. Assim como *get single*, você pode sobrescrever os métodos `onResult(T)` e `onFailure(Exception)` para objetos e `onResult(List<T>)` e  `onFailure(Exception)` para listas.

Cuidado: não use em listas grandes ou que você sabe que podem crescer.

``` java
// obter objeto
Db.path("users").child(uid).get(new Listener.Map() {
    @Override
    public void onResult(HashMap<String, Object> result) {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});

// obter lista
Db.path("users").get(new Listener.ListMap() {
    @Override
    public void onResult(List<HashMap<String, Object>> result) {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});
```

Assim como _get single_, você pode alternativamente sobrescever `onAdded(T, int index, String key)`, e obter item por item da lista.

``` java
// obter lista, item por item
Db.path("users").get(new Listener.ListMap() {

    @Override
    public void onAdded(HashMap<String, Object> child, int index, String key) {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});
```

Assim como _get single_, você também pode passar uma lista alvo no construtor do callback.

``` java
//exemplo de lista alvo
List<HashMap<String, Object>> list = new ArrayList<>();

//escutando usuários
Db.path("users").get(new Listener.ListMap(list) {
    @Override
    public void onAdded(HashMap<String, Object> child, int index, String key) {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});
```

#### get listener children
Obtém todo o conteúdo e escuta as alterações dos filhos individualmente, economizando mais dados que *get listener*. Só pode ser usado em listas, podendo sobrescrever os métodos `onAdded(T child, int index, String key)`, `onChanged(T child, int index, String key)`, `onRemoved(T child, int index, String key)` e `onFailure(Exception)`.

``` java
//escutando usuários
Db.path("users").get(new Listener.Children.ListMap() {

    @Override
    public void onAdded(HashMap<String, Object> child, int index, String key) {
    
    }

    @Override
    public void onChanged(HashMap<String, Object> child, int index, String key) {
    
    }

    @Override
    public void onRemoved(HashMap<String, Object> child, int index, String key) {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});
```

Alternativamente você pode sobrescrever `onResult(List<T> result)`, que devolve com todos os filhos resolvida internamentes sempre que ocorre uma alteração.

``` java
//escutando usuários
Db.path("users").get(new Listener.Children.ListMap() {

    @Override
    public void onResult(List<HashMap<String, Object>> result) {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});
```

Assim como os callbacks anteriores, você pode passar uma lista alvo no construtor.

``` java
//exemplo de lista alvo
List<HashMap<String, Object>> list = new ArrayList<>();

//escutando usuários
Db.path("users").get(new Listener.Children.ListMap(list) {

    @Override
    public void onResult(List<HashMap<String, Object>> result) {
    
    }

    @Override
    public void onFailure(Exception e) {
    
    }
});
```

#### job
Os callbacks `Listener` e `Listener.Children` retornam um objeto do tipo `Job`que pode ser usado para parar o listener por meio do méotdo `stop()`.

``` java
//obtendo o Job
Job getUsersJob = Db.path("users").get(new Listener.ListMap() {

    @Override
    public void onResult(List<Map<String, Object>> result) {
        Log.d("result", String.valueOf(result));
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("error", e.getMessage(), e);
    }
});


//parando o listener
getUsersJob.stop();

```
## Adicionar ao projeto

Adicione o jitpack ao projeto em build.gradle or settings.gradle (gradle 7+)
``` groovy
maven { url 'https://jitpack.io' }
```

Adicione a dependência no modulo (normalmente o app)
``` groovy
implementation "com.github.Irineu333:FirebaseEasy:1.0.0"
```
