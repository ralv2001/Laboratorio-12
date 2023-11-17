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
app.get('/sedes', (req, res) => {
    const query = 'SELECT idsede, nombreSede, direccion FROM sedes;';

    conn.query(query, (err, results) => {
        if (err) {
            console.error('Error al obtener las sedes:', err);
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
