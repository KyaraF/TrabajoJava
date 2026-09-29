# Manual de Usuario - Escáner de Red

## 1. Requisitos del Sistema

* **Sistema Operativo:** Windows 7 / 10 / 11.
* **Java:** Tener instalado Java (JRE o JDK 17 o superior) en la computadora.

## 2. Descarga e Instalación (Capturas instructivas en la documentacion)

1. **Descargar el ejecutable:**
   * Abrí el repositorio de GitHub y busca la sección (A la derecha de la página) Releases y clickea en el texto EscanerRed.Jar
   * Te va a aparecer un espacio con el título EscanerRed.Jar en grande, de las 3 opciones debajo de ese título, elegí la que dice “EscanerRed.Jar”, eso va a descargar un archivo .Jar
     
2. **Guardar el archivo:** Ubicá el archivo EscanerRed.jar en la carpeta que prefieras (por ejemplo, en el Escritorio). Si no se abrió automaticamente con la versión de Java que descargaste (Por ejemplo, si está abierto con Winrar o algo así) toca click derecho y busca la opción “Abrir con”, después elegí la opción que dice “OpenJDK Plataform Binary” y hace doble click en el archivo.
          
## 3. Guía de Uso Paso a Paso

### Ingresar el Rango de IPs y Ajustar el Tiempo de Espera:
* En **IP Inicio**, ingresá la primera IP a revisar (Ejemplo: `192.168.1.1`).
* En **IP Fin**, ingresá la última IP del rango (Ejemplo: `192.168.1.5`).
* En **Timeout (ms)** podés cambiar el tiempo máximo de espera por equipo (el valor predeterminado es `1000` ms).

### Realizar el Escaneo
* Hacé clic en **Iniciar Escaneo**. La barra de progreso mostrará el avance mientras se analiza la red.

### Filtrar y Ordenar
* En el menú **Mostrar**, elegí **Solo Conectados** para ocultar las IP inactivas.
* Hacé clic en los nombres de las columnas en la tabla para ordenar la lista.

### Guardar el Reporte
* Presioná **Guardar Resultados** para exportar la información a un archivo de texto `.txt` en tu computadora.

## 4. Preguntas Frecuentes (FAQ)

**¿Por qué no abre el programa al hacer doble clic?**  
Verificá si tenés Java instalado. Podés comprobarlo abriendo la consola (`cmd`) y escribiendo `java -version`.

**¿Por qué indica "Formato de IP inválido"?**  
Asegurate de escribir la IP con cuatro números separados por puntos (Ejemplo: `192.168.1.1`).

**¿Por qué dice "Desconocido" o "Sin Nombre"?**  
Ocurre cuando la IP responde a la conexión pero no entrega un nombre DNS registrado en la red.cuando el equipo de la red no responde al comando de búsqueda de nombre DNS (`nslookup`).
