const express = require("express");
const bodyParser = require("body-parser");
const mysql = require("mysql2");
const e = require("express");

const app = express();

const conn = mysql.createConnection({
    host:"localhost",
    port: 3306,
    user: "root",
    password: "root",
    database: "hr"
});

app.get("/jobs",(req,res) => {
    let sql = "select * from jobs";
    conn.query(sql,(err, result, fields) => {
        if(err) throw err;

        let horaActual = new Date();

        for(let i = 0; i < result.length;i++ ){
            result[i]["job_id"] += "_HOLAAAA"
        }

        res.json({
            horaActual: horaActual,
            jobs: result
        });
    });

});

app.get("/employees/:id",(req,res) => {

    let id = req.params["id"];

    let sql = "select * from hr.employees where employee_id = ?";

    conn.query(sql,[id], (err, result, fields) => {
       if(err) throw err;

       res.json(result);
    });

});

app.post("/jobsJson",bodyParser.json(),(req,res) =>{
    let jobTitle = req.body.job_title;
    let jobId = req.body["job_id"];
    let minSalary = req.body.min_salary;
    let maxSalary = req.body.max_salary;

    console.log("jobTitle: ",jobTitle, "| jobId:", jobId );

    conn.query("select * from jobs where job_id = ?",[jobId], function (e, r) {
        if(e) throw e;

        if(r.length > 0){ //ya existe ese trabajo
            res.json({
                err:"un trabajo con ese ID ya existe!"
            })
        }else{
            let sql = "insert into jobs (job_id, job_title, min_salary, max_salary) VALUES (?,?,?,?)";
            let params = [jobId,jobTitle,minSalary,maxSalary];
            conn.query(sql,params,(err) => {
                if(err) throw err;

                conn.query("select * from jobs",(err2, result) => {
                    if(err2) throw err2;

                    res.json(result);
                });
            });
        }
    });

});

app.listen(3000,function(){
    console.log("Servidor desplegado correctamente");
});