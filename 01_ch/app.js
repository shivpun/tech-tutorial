const express = require('express')
const app = express()
const port = 3000

app.get('/', (req, res) => {
  //res.send('Hello World!')
  //res.sendFile("index.html", {root:__dirname});
  res.sendFile("shop/index.html", {root:__dirname})
});

// static endpoint ==> CDN
app.get('/addiTest', (req, res) => {
    //res.send('Hello World!')
    //res.sendFile("index.html", {root:__dirname});
    res.sendFile("static/addition.js", {root:__dirname})
});

app.put('/user', (req, res) => {
    res.send('Got a PUT request at /user')
  });



app.use(express.static('static'));



app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})