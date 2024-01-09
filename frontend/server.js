const express = require('express');
const { join } = require('path');
const { auth } = require('express-openid-connect');
require('dotenv').config();

const config = {
    authRequired: false,
    auth0Logout: true,
    secret: process.env.SECRET,
    baseURL: process.env.BASEURL,
    clientID: process.env.CLIENTID,
    issuerBaseURL: process.env.ISSUER
  };

const app = express();
app.use(express.static(join(__dirname, 'public')));
// auth router attaches /login, /logout, and /callback routes to the baseURL
app.use(auth(config));

app.get("/", (req, res) => {
    console.log(req.oidc.isAuthenticated());
    res.sendFile(join(__dirname, 'index.html'));
});

app.get("/datatable", (req, res) => {
    res.sendFile(join(__dirname, 'datatable.html'));
});

const PORT = process.env.PORT;
app.listen(PORT, () => {
    console.log(`Express is running on port ${PORT}`)
});