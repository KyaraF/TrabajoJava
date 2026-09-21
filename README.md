# Manual de Usuario

## 1. Requisitos del Sistema
* **Sistema Operativo:** Windows 7 / 10 / 11.
* **Entorno de Ejecución:** Java Development Kit (JDK) o Java Runtime Environment (JRE) 17 o superior.
* **Entorno de Desarrollo (Opcional):** Eclipse IDE.

## 2. Instalación e Importación en Eclipse
1. Clonar o descargar el repositorio del proyecto desde GitHub.
2. Abrir **Eclipse IDE** y seleccionar un espacio de trabajo (*workspace*).
3. Ir al menú superior: **File > Import...**
4. Seleccionar **General > Existing Projects into Workspace** y hacer clic en **Next**.
5. Marcar la opción **Select root directory** y presionar **Browse...** para seleccionar la carpeta donde se descargó el proyecto desde GitHub.
6. Asegurarse de que el proyecto esté seleccionado en la lista y hacer clic en **Finish**.
7. Para ejecutar la aplicación, abrir la clase `Main.java` (ubicada en el paquete `principal`) dentro de Eclipse y presionar el botón **Run** (`Ctrl + F11`).

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
