const express = require('express');
const { join } = require('path');
const app = express();

app.use(express.static(join(__dirname, 'public')));

app.get("/", (req, res) => {
    res.sendFile(join(__dirname, 'index.html'));
});

app.get("/", (req, res) => {
    res.sendFile(join(__dirname, 'index.html'));
});

app.get("/datatable", (req, res) => {
    res.sendFile(join(__dirname, 'datatable.html'));
});

app.listen(3000);


module.exports = app;