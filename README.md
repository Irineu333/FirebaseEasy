# FirebaseEasy [![](https://jitpack.io/v/Irineu333/FirebaseEasy.svg)](https://jitpack.io/#Irineu333/FirebaseEasy)

## Instanciar
Para começar a utilizar a lib, obtenha uma intância da classe Db, por meio do construtor `new Db("path")` ou pelo método `Db.path("path")`.

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
Escrever um valor, substuindo todo o conteúdo do path. Opcionalmente você pode passar o callback `Result.Set` para tratar o resultado, sobrescrevendo `onSuccess` e  `onFailure(Exception)`. 
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
        Log.d("result", "Sucesso!!");
        Log.d("result", "key: " + newKey);
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("result", e.getMessage(), e);
    }
});
```
## get
Obtém um dado do banco, sendo a forma como obtém especificada pelo callback, que é dividido em três tipos principais: `Single`, `Listener` e `Listener.Children`, cada um deles possuindo os tipos padrões; `Map`, `String`, `Boolean`, `Long`, `Double`, e o tipo genérico; `Generic<T>`, e suas versões em lista; `ListMap`, `ListString`, `ListBoolean`, `ListLong`, `ListDouble`, e `ListGeneric<T>`, seguindo o padrão [callback].[tipo].

``` java
// callbacks
Single
Listener // 
Listener.Children // obtém todo o conteúdo e continua obtendo os filhos que tiveram alterações

// padrões
Map // Map<String, Object>
String
Boolean
Long
Double

// padrões versão lista
ListMap // List<Map<String, Object>>
ListString // List<String>
ListBoolean // List<Boolean>
ListLong // List<Long>
ListDouble //List<Double>
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
        Log.d("result", e.getMessage(), e);
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
        Log.d("result", e.getMessage(), e);
    }
});

// obter lista, objeto por objeto
Db.path("users").get(new Single.ListMap() {

    @Override
    public void onAdded(Map<String, Object> result, String key) {
        result.put("key", key);
        Log.d("result", String.valueOf(result));
    }

    @Override
    public void onFailure(Exception e) {
        Log.d("result", e.getMessage(), e);
    }
});
```
### get listener
Obtém todo o conteúdo e continua obtendo tudo sempre que tiver alteração. Assim como get single, você pode sobrescrever os métodos `onResult(T)` e `onFailure(Exception)` para objetos e `onResult(List<T>)` ou `onAdded(T)` e  `onFailure(Exception)` para listas.
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
        Log.d("result", e.getMessage(), e);
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
        Log.d("result", e.getMessage(), e);
    }
});

// obter lista, objeto por objeto
Db.path("users").get(new Listener.ListMap() {

    @Override
    public void onAdded(Map<String, Object> result, String key) {
        result.put("key", key);
        Log.d("result", String.valueOf(result));
    }

    @Override
    public void onFailure(Exception e) {
        Log.d("result", e.getMessage(), e);
    }
});
```

