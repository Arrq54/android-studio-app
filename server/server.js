var express = require("express")
var app = express()
const PORT = 3000;
app.use(express.json());
app.use(express.urlencoded({
    extended: true
}));
const fs = require('fs');
var formidable = require('formidable');
// app.get('/', function (req, res) {
//     res.sendFile(__dirname + "/static/index.html")
// })


app.get('/upload', function (req, res) {
    console.log("UPLOAD GET");
    let form = formidable({});
    form.keepExtensions = true 
    form.uploadDir = __dirname + '/static/upload/'       // folder do zapisu zdjęcia

    form.parse(req, function (err, fields, files) {

        res.send({status: "plik przesłany!"})
    });
})

app.post('/upload', function (req, res) {
    console.log("UPLOAD POST");
    let form = formidable({});
    form.keepExtensions = true 
    form.uploadDir = __dirname + '/static/upload/'       // folder do zapisu zdjęcia

    form.parse(req, function (err, fields, files) {

        res.send({status: "plik przesłany!"})
    });
})


app.get("/getFiles", function(req,res){
    console.log("getFiles");
    let resData = []
    fs.readdir(__dirname+"/static/upload", function (err, files) {
        console.log(files);
        for(let i=0;i<files.length;i++){
            let stats = fs.statSync("static/upload/"+files[i])
            resData.push({
                name: files[i],
                url: "/upload/"+files[i],
                size: stats.size,
                creationTime: stats.birthtime
            })
        }
        res.send(JSON.stringify(resData))
     });
})


app.use(express.static('static'))
app.listen(PORT, function () {
    console.log("start serwera na porcie " + PORT)
})
