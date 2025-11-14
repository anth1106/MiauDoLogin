# Project Login API MiauDo

This project implements an API REST base for managing login, register and the respective encrypt for the user credentials with **BCrypt**.

## 🚀 Requirements

- Java JDK 11 o superior
- IntelliJ IDEA (or compatible IDE)
- Extern Libraries:
    - [jbcrypt-0.4.jar](https://mvnrepository.com/artifact/org.mindrot/jbcrypt/0.4)

## ⚙️ Configuración

1. Descarga el archivo `jbcrypt-0.4.jar` y colócalo en la carpeta `lib/`.
2. En IntelliJ:
    - Abre **Project Structure** (`Ctrl+Alt+Shift+S`).
    - Ve a **Libraries** → pulsa **+** → selecciona **Java**.
    - Añade el archivo `lib/jbcrypt-0.4.jar`.
3. Importa en tu código:
   ```java
   import org.mindrot.jbcrypt.BCrypt;
Necesitas añadir json-20210307.jar (o versión más reciente) a tu carpeta lib/ y configurarlo en IntelliJ igual que hiciste con BCrypt.


