// server.js

const express = require('express');
const mysql = require('mysql2');
const app = express();
const port = 3000;

// Configuración de la conexión a la base de datos
let conn = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '123456',
    database: 'bicicentro'
});

conn.connect(function(err){
    if(err) throw err;
    console.log("Conexión exitosa a base de datos");
});


// Endpoint para obtener un trabajador por DNI y el nombre de la sede
// Endpoint para obtener las ventas de un trabajador por DNI
app.get('/trabajadores/ventas/:dni', (req, res) => {
    const dni = req.params.dni;

    // Asegúrate de cambiar los nombres de las columnas y las tablas si es necesario para que coincidan con tu esquema de base de datos
    const query = `
        SELECT v.fecha, i.nombre, i.numeroserie, m.nombre AS marca
        FROM ventas v
        JOIN inventario i ON v.id_inventario = i.idinventario
        JOIN trabajadores t ON v.dniTrabajador = t.dni
        JOIN marcas m ON i.idmarca = m.idmarca
        WHERE t.dni = ?;
    `;

    conn.query(query, [dni], (err, results) => {
        if (err) {
            console.error('Error al obtener las ventas del trabajador:', err);
            res.status(500).send('Error en el servidor');
            return;
        }

        res.json(results);
    });
});

// Iniciar el servidor y manejar errores de escucha del puerto
app.listen(port, () => {
    console.log(`Servidor escuchando en http://localhost:${port}`);
}).on('error', (err) => {
    if (err.code === 'EADDRINUSE') {
        console.error(`El puerto ${port} ya está en uso.`);
    } else {
        console.error(`Error al iniciar el servidor: ${err.message}`);
    }
});
