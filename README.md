# FirebaseEasy [![](https://jitpack.io/v/Irineu333/FirebaseEasy.svg)](https://jitpack.io/#Irineu333/FirebaseEasy)

## Instanciar
Para começar a utilizar a lib, obtenha uma intância da classe `Db`, por meio do construtor `new Db("path")` ou pelo método `Db.path("path")`.

``` java
// imports
import com.fb.easy;

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

// outros
db.getPushKey() : String //gera uma nova key inexistente no path
db.child(String) : Db //obtém uma nova instância de `Db` no caminho especificado dentro do path
```

## set
Escreve um valor, substuindo todo o conteúdo do path. Opcionalmente você pode passar o callback `Result.Set` para tratar o resultado, sobrescrevendo `onSuccess` e  `onFailure(Exception)`. 
``` java
// sem callback
Db.path("version").set("1.0.0");

// com callback

// imports
import com.fb.easy.callback.Result;

Db.path("version").set("1.0.0", new Result.Set() {
    @Override
    public void onSuccess() {
        Log.d("result", "Sucesso!!");
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("result", e.getMessage(), e);
    }
});
```
## update
Atualiza parte dos dados, preservando o que não foi alterado. Opcionalmente você pode passar o callback `Result.Update` para tratar o resultado, sobrescrevendo `onSuccess` e  `onFailure(Exception)`. 
``` java
// sem callback
Db.path("users").child(uid).update(user);

// com callback

// imports
import com.fb.easy.callback.Result;

Db.path("users").child(uid).update(user, new Result.Update() {
    @Override
    public void onSuccess() {
        Log.d("result", "Sucesso!!");
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("result", e.getMessage(), e);
    }
});
```

## post
Escreve o dado como um novo filho, gerando uma nova key. Opcionalmente você pode passar o callback `Result.Post` para tratar o resultado, sobrescrevendo `onSuccess` ou `onSuccess(String newKey)` e `onFailure(Exception)`.
``` java
// sem callback
Db.path("users").post(user);

// com callback

// imports
import com.fb.easy.callback.Result;

// sobrescrevendo onSuccess()
Db.path("users").post(newUser, new Result.Post() {
    @Override
    public void onSuccess() {
        Log.d("result", "Sucesso!!");
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("result", e.getMessage(), e);
    }
});

// sobrescrevendo onSuccess(String key)
Db.path("users").post(newUser, new Result.Post() {
    @Override
    public void onSuccess(String newKey) {
        Log.d("result", "key: " + newKey);
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("error", e.getMessage(), e);
    }
});
```
## get
Obtém um dado do banco, sendo a forma como obtém especificada pelo callback, que é dividido em três tipos principais: `Single`, `Listener` e `Listener.Children`, cada um deles possuindo os tipos; `Map`, `String`, `Boolean`, `Long`, `Double`, e o tipo genérico; `Generic<T>`, e suas versões em lista; `ListMap`, `ListString`, `ListBoolean`, `ListLong`, `ListDouble`, e `ListGeneric<T>`.

``` java
// callbacks
Single
Listener
Listener.Children

// tipos
Map // Map<String, Object>
String
Boolean
Long
Double
Generic<T> // assume qualquer tipo

// tipos versão lista
ListMap // List<Map<String, Object>>
ListString // List<String>
ListBoolean // List<Boolean>
ListLong // List<Long>
ListDouble //List<Double>
ListGeneric<T> //List<T> sendo T qualquer tipo

// padrão de chamada
callback.tipo
ex: Single.ListMap
```

### get single
Obtém todo o conteúdo apenas uma vez. Você pode sobrescrever os métodos `onResult(T)` e `onFailure(Exception)` para objetos e `onResult(List<T>)` ou `onAdded(T)` e  `onFailure(Exception)` para listas.

``` java
// imports
import com.fb.easy.callback.Single;

// obter objeto
Db.path("users").child(uid).get(new Single.Map() {
    @Override
    public void onResult(Map<String, Object> result) {
        Log.d("result", String.valueOf(result));
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("error", e.getMessage(), e);
    }
});

// obter lista
Db.path("users").get(new Single.ListMap() {
    @Override
    public void onResult(List<Map<String, Object>> result) {
        Log.d("result", String.valueOf(result));
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("error", e.getMessage(), e);
    }
});

// obter lista, objeto por objeto
Db.path("users").get(new Single.ListMap() {

    @Override
    public void onAdded(Map<String, Object> child, String key) {
        Log.d("child " + key, String.valueOf(child));
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("error", e.getMessage(), e);
    }
});
```
### get listener
Obtém todo o conteúdo e continua obtendo tudo sempre que tiver alteração. Assim como *get single*, você pode sobrescrever os métodos `onResult(T)` e `onFailure(Exception)` para objetos e `onResult(List<T>)` ou `onAdded(T)` e  `onFailure(Exception)` para listas.

Cuidado: não use em listas grandes ou que você sabe que podem crescer.

``` java
// imports
import com.fb.easy.callback.Listener;

// obter objeto
Db.path("users").child(uid).get(new Listener.Map() {
    @Override
    public void onResult(Map<String, Object> result) {
        Log.d("result", String.valueOf(result));
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("error", e.getMessage(), e);
    }
});

// obter lista
Db.path("users").get(new Listener.ListMap() {
    @Override
    public void onResult(List<Map<String, Object>> result) {
        Log.d("result", String.valueOf(result));
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("error", e.getMessage(), e);
    }
});

// obter lista, objeto por objeto
Db.path("users").get(new Listener.ListMap() {

    @Override
    public void onAdded(Map<String, Object> child, String key) {
        Log.d("child " + key, String.valueOf(child));
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("error", e.getMessage(), e);
    }
});
```
### get listener children
Obtém todo o conteúdo e continua obtendo, mas apenas os filhos que tiveram alterações, economizando mais dados que *get listener*. Sua implementação muda muito em relação as outras formas de obter listas, não tendo um método `onResult(List<T> result)`, os métodos que precisão ser sobrescritos são `onAdded(T child, String key)`, `onChanged(T child, String key)`, `onRemoved(T child, String key)` e `onFailure(Exception)`.
``` java

// imports
import com.fb.easy.callback.Listener;

Map<String, Map<String, Object>> resultList = new HashMap<>();

Db.path("users").get(new Listener.Children.ListMap() {

    @Override
    public void onAdded(Map<String, Object> child, String key) {
        resultList.put(key, child);
    }

    @Override
    public void onChanged(Map<String, Object> child, String key) {
        resultList.put(key, child);
    }

    @Override
    public void onRemoved(Map<String, Object> child, String key) {
        resultList.remove(key);
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("error", e.getMessage(), e);
    }
});
```

### job
Os callbacks `Listener` e `Listener.Children` retornam um objeto do tipo `Job`que pode ser usado para parar o listener por meio do méotdo `stop()`.
``` java 

// imports
import com.fb.easy.contract.Job;

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

getUsersJob.stop();

```
## Add to project

Add the jitpack to project in build.gradle or settings.gradle (gradle 7+)
``` groovy
maven { url 'https://jitpack.io' }
```

Add the dependence to module (normally app)
``` groovy
implementation "com.github.Irineu333:FirebaseEasy:1.0.0"
```
