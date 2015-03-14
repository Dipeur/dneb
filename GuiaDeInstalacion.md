Esta es la guía de instalación de la aplicación Dneb.

1. Instalar MySQL 5.1

2. Crear un nuevo Schema vacío en MySQL para la aplicación(se le puede llamar como se quiera)

4. Crear un usuario con permisos para acceder al Schema creado

3. Descargar la aplicación Dneb (fichero Dneb.jar)

4. Descomprimir el contenido en el lugar deseado

5. Configurar el fichero jdbc.properties de la siguiente forma considerando la instalación por defecto de Mysql y que ésta se encuentra en local, en caso contrario configurar ip y puerto.

> jdbc.url=jdbc:mysql://localhost:3306/<Schema Mysql>

> jdbc.username=<Usuario creado>

> jdbc.password=<Contraseña creada>

6. Ejecutar el fichero dneb.bat