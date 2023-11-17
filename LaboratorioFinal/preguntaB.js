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
app.get('/trabajadores/:dni', (req, res) => {
    const dni = req.params.dni;

    // Asegúrate de cambiar los nombres de las columnas si es necesario para que coincidan con tu esquema de base de datos
    const query = `
        SELECT t.nombres, t.apellidos, t.correo, t.dni, t.idsede, s.nombreSede 
        FROM trabajadores t
        INNER JOIN sedes s ON t.idsede = s.idsede
        WHERE t.dni = ?;
    `;

    conn.query(query, [dni], (err, results) => {
        if (err) {
            console.error('Error al obtener el trabajador:', err);
            res.status(500).send('Error en el servidor');
            return;
        }

        if (results.length > 0) {
            // Suponiendo que los nombres de columna en tu base de datos sean 'nombreSede' para la sede
            // y 'nombres', 'apellidos', etc., para los trabajadores.
            res.json({
                nombres: results[0].nombres,
                apellidos: results[0].apellidos,
                correo: results[0].correo,
                dni: results[0].dni,
                idsede: results[0].idsede,
                nombreSede: results[0].nombreSede
            });
        } else {
            res.status(404).send('Trabajador no encontrado');
        }
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
