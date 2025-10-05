# Prueba-Tecnica-Spring-Boot-Gestion-de-Ventas-para-Supermercados
Proyecto 2 Grupo A


Uso de la aplicación
Simulación de una aplicación de gestión de Compra-Venta en un supermercado. A través de postman, debes realizar un registro y un login para que después puedas realizar las peticiones CRUD para gestionar ventas, sucursales y productos. 
Instrucciones de uso: 
Requiere tener persistence.xml con el usuario y contraseña correspondientes para el adecuado funcionamiento de la BBDD. En la BBDD se debe crear una base de datos bajo el nombre "supermercado". Para poner en marcha el programa, es necesario tener en funcionamiento MySql con la base de datos correspondiente y Apache. Abrir en un IDE el proyecto (/Prueba-Tecnica-Spring-Boot) y arrancarlo ejecutando la clase main (PruebaTecnicaSpringBootApplication). Iniciar Postman e importar la colección (ubicada en el repositorio), registrarse con el endpoint de registro e iniciar sesión con su usuario previamente registrado en el endpoint de login.

Tecnologías utilizadas:
Nombre de la BBDD: supermercado
XAMPP / MySQL / Apache (versiones recomendadas)
JDK
IDE Intelli
Smart Tomcat plugin
Java 17
Swagger

ENDPOINTS (a través de Postman)
Autenticación:
POST. Registro: /auth/register
POST. Login: /auth/login

Productos:
GET. Lista todos los productos: /api/productos
GET. Filtra el producto por ID: /api/productos{id}
POST. Crea un producto: /api/productos/nuevoproducto
PUT. Actualiza un producto existente: /api/productos/actualizarproducto/{id}
DELETE. Elimina un producto: /api/productos/eliminarproducto/{id}
GET. Producto más vendido: /api/estadisticas/producto-mas-vendido

Sucursales:
GET. Listar sucursales: /api/sucursales
POST. Crear sucursal: /api/sucursales/crear
PUT. Actualizar sucursal: /api/sucursales/actualizar
DELETE. Eliminar sucursal: /api/sucursales/eliminar/{id}

Ventas:
GET. Listar venta: /api/ventas
POST. Crear venta: /api/ventas/nuevaventa
DELETE. Eliminar venta: /api/ventas/eliminarventa/{id}

Autores
Alberto Escamilla
Andrea Suárez
Intissar Elmeskine
Pablo Corroto

