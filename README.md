
# Gestión Cliente

Api que permite gestionar la información de un cliente, el cual puede registrar la
información personal (**MySQL**) y subir una foto de perfil (**MySQL** y **MongoDB**).




<img src="https://drive.google.com/file/d/1pLf5TnveYQBYgMaMaiYD1FyUuxIGv8No/view?usp=sharing">

## Rutas

`Host:` http://localhost:8090

### Cliente

- Consultar por el **id**
  | Metodo | Ruta             |
  |--------|------------------|
  | GET    |  /{id}           |
  | GET    |  /find/id/{id}   |

- Consultar información y imagen del cliente por el **id**
  | Metodo | Ruta                   |
  |--------|------------------------|
  | GET    |  /find/image/id/{id}   |

- Consultar cliente por su **tipo documento** y **documento**
  | Metodo | Ruta                                   |
  |--------|----------------------------------------|
  | GET    | /find/type/{type}/document/{document}  |

- Listar clientes
  | Metodo | Ruta     |
  |--------|----------|
  | GET    |    /     |
  | GET    |   /all   |

- Listar clientes que la **edad** sea mayor o igual a la indicada
  | Metodo | Ruta                       |
  |--------|----------------------------|
  | GET    |    /all/find/age/{age}     |

- Listar clientes que por su **tipo de documento**
  | Metodo | Ruta                         |
  |--------|------------------------------|
  | GET    |    /all/find/type/{type}     | 
  
- Registrar cliente
  | Metodo | Ruta                    |
  |--------|-------------------------|
  | POST   | /                       |

  **JSON**
  ```sh
  {
    "name" : String,
    "subname" : String,
    "type": String,
    "cityBirth": String
    "age": Integer,
    "document": Long,  
  }
  ```

- Actualizar Cliente
  | Metodo | Ruta                    |
  |--------|-------------------------|
  | PUT    | /                       |

  **JSON**
  ```sh
  {
    "name" : String,
    "subname" : String,
    "type": String,
    "cityBirth": String
    "age": Integer,
    "id": Long,
    "document": Long,  
  }
  ```

- Eliminar cliente por su **id**
  | Metodo    | Ruta     |
  |-----------|----------|
  | DELETE    |  /{id}   |
