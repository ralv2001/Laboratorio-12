const express = require("express");
const path = require("path");
const bodyParser = require("body-parser");

//nueva instancia de express
const app = express();
const port = 3000;

app.get("/prueba1",function (req, res){
    res.send("hola mundo");
});

app.get("/pruebaHtml", (req,res) => {
    res.send("<h1>Hola mundo</h1>");
});

//windows __dirname -> C:\\Users\\stuar\\Documents\\IdeaProjects\\holaMundoNode
//linux __dirname -> /home/stuardo/Documents/holaMundoNode
app.get("/prueba2", (req,res) => {
    res.sendFile(path.join(__dirname ,"vista1.html"));
});

// ?usuario=<nombre>&apellido=<apellido>
app.get("/prueba3", (req,res) => {
    let usuario = req.query.usuario;
    let apellido = req.query.apellido;

    let myJson = {
        nombre : usuario,
        apellido: apellido
    };
    res.json(myJson);
});

app.get("/prueba4/:nombre/:apellido", (req,res) => {
    let nombre = req.params.nombre;
    let apellido = req.params.apellido;

    console.log("nombre: ", nombre , "| apellido: ", apellido);

    let myJson = {
        nombre : nombre,
        apellido: apellido
    };
    res.json(myJson);
});

app.post("/jobs",bodyParser.urlencoded({extended:true}),(req,res) =>{
    let jobTitle = req.body.job_title;
    let jobId = req.body["job_id"];
    console.log("jobTitle: ",jobTitle, "| jobId:", jobId );

    res.json({
        jobTitle:jobTitle,
        jobId:jobId
    });
});

app.post("/jobsJson",bodyParser.json(),(req,res) =>{
    let jobTitle = req.body.job_title;
    let jobId = req.body["job_id"];
    let minSalary = req.body.min_salary;
    let maxSalary = req.body.max_salary;

    console.log("jobTitle: ",jobTitle, "| jobId:", jobId );

    res.json({
        jobTitle:jobTitle,
        jobId:jobId
    });
});

app.listen(port,function(){
    console.log("Servidor desplegado correctamente");
});

