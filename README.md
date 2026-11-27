# Sistema de gestion de pedidos con arbol binario

Aplicacion de escritorio desarrollada en Java para administrar pedidos de una tienda en linea. El proyecto utiliza un arbol binario de busqueda, donde el numero de pedido actua como clave para insertar, buscar, actualizar y eliminar registros.

## Funcionalidades

- Registrar pedidos sin numeros duplicados.
- Buscar pedidos por numero.
- Actualizar el estado de un pedido.
- Eliminar pedidos.
- Mostrar los pedidos ordenados mediante recorrido inorden.
- Interfaz grafica desarrollada con Java Swing.
- Version de consola disponible.

## Estructura del proyecto

```text
src/
`-- main/
    `-- java/
        |-- app/
        |   |-- InterfazPedidos.java
        |   |-- Main.java
        |   `-- MainGrafico.java
        |-- estructura/
        |   |-- ArbolPedidos.java
        |   `-- NodoPedido.java
        `-- modelo/
            |-- Pedido.java
            `-- EstadoPedido.java
docs/
`-- Guion_Presentacion_Arbol_Binario.pdf
pom.xml
```

## Funcionamiento del arbol binario

Cada nodo contiene un pedido y puede tener dos hijos:

- Un subarbol izquierdo con numeros menores.
- Un subarbol derecho con numeros mayores.

La clase `ArbolPedidos` realiza las operaciones principales. `Integer.compare` compara el numero nuevo con el numero del nodo actual:

- Resultado negativo: el pedido va al subarbol izquierdo.
- Resultado cero: el numero ya existe y no se permite duplicarlo.
- Resultado positivo: el pedido va al subarbol derecho.

El recorrido inorden visita primero el subarbol izquierdo, despues el nodo actual y finalmente el subarbol derecho. De esta manera, los pedidos se muestran de menor a mayor.

## Requisitos

- Java JDK 17 o superior.
- Maven.
- IDE compatible con Java, como IntelliJ IDEA.

## Ejecucion

Desde la raiz del proyecto:

```bash
mvn clean compile
```

Interfaz grafica:

```bash
java -cp target/classes app.MainGrafico
```

Version de consola:

```bash
java -cp target/classes app.Main
```

Tambien puede ejecutar `MainGrafico` o `Main` directamente desde el IDE.

## Estados disponibles

- PENDIENTE
- PREPARANDO
- ENVIADO
- ENTREGADO
