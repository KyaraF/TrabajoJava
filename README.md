# Manual de Usuario

## 1. Requisitos del Sistema
* **Sistema Operativo:** Windows 7 / 10 / 11.
* **Entorno de Ejecución:** Java Development Kit (JDK) o Java Runtime Environment (JRE) 17 o superior.
* **Entorno de Desarrollo (Opcional):** Eclipse IDE.

## 2. Descarga e Instalación
1. Ir a la sección de descargas del proyecto en GitHub (o descargar el archivo `EscanerRed.jar` adjunto).
2. Guardar el archivo `EscanerRed.jar` en cualquier carpeta de su preferencia (por ejemplo, el Escritorio).
3. **Para ejecutar la aplicación:**
   * **Opción 1:** Hacer doble clic sobre el archivo `EscanerRed.jar`.
   * **Opción 2 (Consola):** Abrir la consola de comandos (`cmd`), navegar hasta la carpeta donde se guardó el archivo y ejecutar:
     ```bash
     java -jar EscanerRed.jar
     ```
   
## 3. Guía de Uso Paso a Paso

### Ingresar Rango de IPs
* En **IP Inicio**, escriba la IP inicial (Ejemplo: `192.168.1.1`).
* En **IP Fin**, escriba la IP final (Ejemplo: `192.168.1.5`).

### Configurar el Tiempo de Espera
* En el campo **Timeout (ms)** ingrese el tiempo límite para esperar a cada equipo (por defecto `1000` ms).

### Iniciar el Escaneo
* Haga clic en **Iniciar Escaneo**. La barra indicará el progreso hasta completar el rango.

### Filtrar u Ordenar
* Seleccione **Solo Conectados** en el campo **Mostrar** para listar únicamente los dispositivos activos en la tabla.
* Haga clic sobre la cabecera de las columnas de la tabla para ordenar la información.

### Guardar el Reporte
* Presione el botón **Guardar Resultados** para elegir la carpeta donde se descargará el archivo de texto `.txt`.

## 4. Preguntas Frecuentes (FAQ)
**¿Por qué aparece el mensaje "Formato de IP inválido"?**  
Verifique haber escrito las IPs en el formato estándar de cuatro números separados por puntos (Ejemplo: `192.168.1.1`).

**¿Por qué dice "Desconocido" o "Sin Nombre" en un dispositivo?**  
Ocurre cuando el equipo de la red no responde al comando de búsqueda de nombre DNS (`nslookup`).
