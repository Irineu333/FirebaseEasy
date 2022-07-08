# FirebaseEasy [![](https://jitpack.io/v/Irineu333/FirebaseEasy.svg)](https://jitpack.io/#Irineu333/FirebaseEasy)

## Instanciar
Para começar a utilizar a lib, obtenha uma intância da classe Db, por meio do construtor `new Db("path")` ou pelo método `Db.path("path")`.

``` java
//imports
import com.fb.easy;

//constructor
Db db = new Db("path");
        
//static method
Db db = Db.path("path");
```

A partir da instância de `Db` você pode utiliar os seguintes métodos
``` java
db.set(...)
db.update(...)
db.post(...)

db.getPushKey() : String //gera uma nova key inexistente no path
db.child(String) : Db //obtém uma nova instância de `Db` no caminho especificado dentro do path
```

## set
Escrever um valor, substuindo todo o conteúdo do path. Opcionalmente você pode passar o callback `Result.Set` para tratar o resultado, sobrescrevendo `onSuccess` e  `onFailure(Exception)`. 
``` java
//sem callback
Db.path("version").set("1.0.0");

//com callback

//imports
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
//sem callback
Db.path("users").child(uid).update(user);

//com callback

//imports
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
Escreve o dado como um novo filho, gerando uma nova key. Opcionalmente você pode passar o callback `Result.Post` para tratar o resultado, sobrescrevendo `onSuccess` ou `onSuccess(String key)` (key é a nova chave) e `onFailure(Exception)`.
``` java
//sem callback
Db.path("users").post(user);

//com callback

//imports
import com.fb.easy.callback.Result;

//sobrescrevendo onSuccess()
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

//sobrescrevendo onSuccess(String key)
Db.path("users").post(newUser, new Result.Post() {
    @Override
    public void onSuccess(String key) {
        Log.d("result", "key: " + key);
    }

    @Override
    public void onFailure(Exception e) {
        Log.e("result", e.getMessage(), e);
    }
});
```
