const fs = require('fs');

console.log("1");
let infoArchivo = fs.readFileSync("hola.txt");

//aquí haría un ws
console.log("2");
console.log(infoArchivo.toString());
console.log("3");