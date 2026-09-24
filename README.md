# Manual de Usuario - Escáner de Red

## 1. Requisitos del Sistema

* **Sistema Operativo:** Windows 7 / 10 / 11.
* **Java:** Tener instalado Java (JRE o JDK 17 o superior) en la computadora.

## 2. Descarga e Instalación

1. **Descargar el ejecutable:**
   * Entrá a la sección de publicaciones (*Releases*) en GitHub o descargá el archivo `EscanerRed.jar` directamente desde el repositorio.
2. **Guardar el archivo:**
   * Ubicá el archivo `EscanerRed.jar` en la carpeta que prefieras (por ejemplo, en el Escritorio).
   * Si lo descargaste y se descargo como winrar, toca clic derecho, pone abrir con y eleji la version de java que se necesita
3. **Ejecutar el programa:**
   * **Opción A (Directa):** Hacé doble clic sobre el archivo `EscanerRed.jar`.
   * **Opción B (Desde la consola):** Abrí la consola de comandos (`cmd`), andá hasta la carpeta del archivo y escribí:
     ```bash
     java -jar EscanerRed.jar
     ```
     
## 3. Guía de Uso Paso a Paso

### Ingresar el Rango de IPs
* En **IP Inicio**, ingresá la primera IP a revisar (Ejemplo: `192.168.1.1`).
* En **IP Fin**, ingresá la última IP del rango (Ejemplo: `192.168.1.5`).

### Ajustar el Tiempo de Espera
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
