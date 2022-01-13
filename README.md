
# Gestión Cliente

Api que permite gestionar la información de un cliente, el cual puede registrar la
información personal (**MySQL**) y subir una foto de perfil (**MySQL** y **MongoDB**).




<img src="https://lh3.googleusercontent.com/6IKtWsR8q2GcMXHsd5pLO6jTObac9GtNwht0tFhoAtNQVtbMV2y-nLAZA5_0daCfAmo4dKR5UlBuUt9SCINa7Yci9G2wa3S06mMo_-yrzx2DfYn-FZLDExi53OtkSNWrX3WvVnZ125bQRK-iIrSRA3ewhN-lwEp-hLeqYOqDCw7IetenEKik0HkyDuk-84jfE6KpqdkamgxVudaNkqw7WQcBaetK9CJixCsibKwrjjWXhKSA36vtzApjbdkdKq_PlcIBMlusq4K2tLyKFPSAJpaMl9OekKHXb6Udvr22JaJwYzEYigqx7-ju5DrdW6YC_bwvBPO_WCZJ1KGM1KjZt6YLXSJTSLcAHXVn53hCldjKPpcJbkuoP4L8SMLnCtm6SV47g7kCgMpMTMoffzpKLQq9Q63BSvlV2OCxbo1JfVZ0wXqHsC9c2uTA9D6o4Y46LF1uXqTpfxR9O2RkWYGZd2aJPaz1FbaQW7PgSSyOanmnvvtgPdVm5KIKvFBhvoTOnSmBfN02FtrQY_GmOX-0RvR959OYf-1uLe3roNFGF4f2mPlEtz6Vk3D6DyZY5erYEpLif4HoHPciT0auhGWoGaFVS6iVgNlnM6ZJ7UQyABbPK8BIjHwUqZS6O8wezdV5T1lyZNz6os4-rWc8vSFHChsEvGJsYdESun_sDwy3ML6QSFWHY1nUVVsPnYkcN7bHDJyxQwH0QGY4sCk68V8BKGE=w1021-h531-no?authuser=0">

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
