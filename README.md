# Simulación de Cajero Automático estilo Mainframe

## Descripción
Desarrollaremos un programa interactivo en línea de comandos que simule las operaciones básicas de un cajero automático.

## Índice
- [Funcionalidad](#funcionalidad)
- [Requerimientos](#requerimientos)
- [Compilación y Ejecución](#compilación-y-ejecución)
- [Configuración de la Base de Datos](#configuración-de-la-base-de-datos)

## Funcionalidad

### 1. Inicialización del Programa:
- Solicitar que se ingrese el usuario y un PIN de 4 dígitos.
- Tres intentos permitidos para ingresar las credenciales.
- Si es incorrecto después de 3 intentos, se cierra el programa.

### 2. Menú Principal:
Opciones disponibles:
1. Realizar un depósito.
2. Realizar un retiro.
3. Consultar extraxto.
4. Cambiar PIN.
5. Cerrar sesion.

### 3. Consultar extraxto:
- Visualizar el saldo actual del usuario.
- Visualizar el historico de transacciones del usuario.

### 4. Realizar un Depósito:
- Ingreso de la cantidad a depositar.
- Validación de cantidad positiva y numerica.
- Confirmación de depósito.

### 5. Realizar un Retiro:
- Ingreso de la cantidad a retirar.
- Validación de cantidad y fondos suficientes.
- Confirmación de retiro.

### 6. Cambiar PIN:
- Ingreso y validación del PIN actual.
- Ingreso y confirmación del nuevo PIN.

### 7. Cierre sesion:
- Vuelta a la pantalla de inicio de sesion.

## Requerimientos
- **Java**: Versión 11
- **Maven**: Versión 3.8.4

## Compilación y Ejecución

### Compilación:
```bash
mvn clean install
```

### Ejecución:
```bash
mvn exec:java -Dexec.mainClass="bo.edu.ucb.sis213.VLogin"
```

## Configuración de la Base de Datos

1. **Inicio de MySQL en Docker**:
```bash
docker run --name mysql-atm -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -d mysql:8
```

2. **Conexión a la Base de Datos**:

> **Contraseña**: `123456`

```bash
docker exec -it mysql-atm mysql -u root -p
```

3. **Creación de la Base de Datos**:
```sql
CREATE DATABASE atm;
```

4. **Seleccionar la Base de Datos**:
```sql
use atm;
```

5. **Inicialización**:
Ejecuta el script `init.sql` ubicado en la carpeta `database`.