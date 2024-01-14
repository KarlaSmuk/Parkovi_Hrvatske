const express = require("express");
const { auth, requiresAuth } = require('express-openid-connect');
const { join } = require("path");
require("dotenv").config();

const app = express();
app.use(express.static(join(__dirname, "public")));

const config = {
  authRequired: false,
  auth0Logout: true,
  idpLogout: true,
  secret: process.env.SECRET,
  baseURL: process.env.AUTH0_BASE_URL,
  clientID: process.env.AUTH0_CLIENT_ID,
  clientSecret: process.env.AUTH0_CLIENT_SECRET,
  issuerBaseURL: process.env.AUTH0_ISSUER_BASE_URL,
  authorizationParams: {
    response_type: 'code id_token',
    scope: 'openid profile email'
  }
};

// auth router attaches /login, /logout, and /callback routes to the baseURL
app.use(auth(config));

// req.isAuthenticated is provided from the auth router
app.get("/checkAuthentication", (req, res) => {
  res.send(req.oidc.isAuthenticated() ? true : false);
});

app.get('/userInfo', requiresAuth(), async (req, res) => {
    const userInfo = await req.oidc.fetchUserInfo();

    console.log(userInfo)
    res.json(userInfo);
});

const checkAuth = (req, res, next) => {
    if (req.oidc.isAuthenticated()) {
      next();
    } else {
      res.status(401).send('Unauthorized');
    }
};

app.get('/', (_, res) => {
  res.sendFile(join(__dirname, "datatable.html"));
});

app.get('/profile', checkAuth,(_, res) => {
  res.redirect('/');
});

app.get('/refreshData', checkAuth,(_, res) => {
  res.redirect('/');
});


const PORT = process.env.PORT;
app.listen(PORT, () => {
  console.log(`Application running on port ${PORT}`);
});
