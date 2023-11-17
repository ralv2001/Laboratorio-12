const fs = require('fs');

const info = function(err,infoArchivo){
    if (err)
        throw err;

    console.log(infoArchivo.toString());

    //aquí haría un ws
}

console.log("1");
fs.readFile("hola.txt", info );

//aquí no puedo asumir que existe infoArchivo!! <- en esta línea

console.log("2");
console.log("3");