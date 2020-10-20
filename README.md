## Simulador de la ejecución de procesos en un sistema operativo

![Captura 1.1](screenshots/1_1.png)

Al crear un proceso nos indica la cantidad de memoria que ocupa así como las localidades de memoria en las que se posiciona.

![Captura 1.2](screenshots/1_2.png)

Si al momento de crear un proceso no hay localidades contiguas de memoria suficientes para crear el proceso entonces se muestra un mensaje con información de la memoria y recomendaciones para continuar con la ejecución del programa. 

![Captura 1.3](screenshots/1_3.png)

También se muestra un mensaje en caso de que no haya la memoria suficiente para crear el proceso, con la información de la memoria disponible y recomendaciones para continuar con el programa.

![Captura 1.4](screenshots/1_4.png)

---

Ejemplo 1 (listas de procesos vacías):

![Captura 2.1](screenshots/2_1.png)

Ejemplo 2 (listas de procesos con elementos):

![Captura 2.2](screenshots/2_2.png)


El estado actual del sistema nos muestra el número de procesos en cola, la lista de procesos terminados de manera exitosa y la lista de procesos eliminados antes de tiempo.

---

![Captura 3.1](screenshots/3_1.png)

![Captura 3.2](screenshots/3_2.png)

El estado actual de la memoria, como su nombre lo indica, nos muestra información acerca de la memoria disponible, imprime la tabla de memoria con las localidades ocupadas y disponibles, así como una tabla con los procesos creados junto con las localidades que cada uno ocupa.

---

![Captura 4](screenshots/4.png)

La cola de procesos nos muestra el ID y el nombre de los procesos en espera.

---

Ejemplo 1 (Ejecutando instrucciones sin terminar el proceso):

![Captura 5.1](screenshots/5_1.png)

Ejemplo 2 (Ejecutando instrucciones y finalizando proceso):

![Captura 5.2](screenshots/5_2.png)

Se ejecutan 5 instrucciones del proceso, si aún quedan instrucciones el proceso se mueve al final de la cola pero si el numero de instrucciones llega a 0 entonces se liberan las localidades de memoria utilizadas por dicho proceso.

---

![Captura 6](screenshots/6.png)

Imprime la información del proceso actual, es decir, el primero de la cola.
Muestra el nombre, ID, instrucciones y memoria.

---

![Captura 7](screenshots/7.png)

Muestra el proceso que se está omitiendo y nos indica qué proceso queda hasta al frente de la cola.

---

![Captura 8](screenshots/8.png)

Para matar un proceso, se desencola, se imprime su nombre y la información de la memoria que ocupaba.

---

![Captura 9](screenshots/9.png)

Mata cada proceso mientras imprime la información de cada proceso eliminado.

