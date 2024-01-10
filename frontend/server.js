const express = require('express');
const { join } = require('path');
require('dotenv').config();

const app = express();
app.use(express.static(join(__dirname, 'public')));

// Endpoint to serve the configuration file
app.get("/auth_config.json", (_, res) => {
    const authConfig = {
      domain: process.env.AUTH0_DOMAIN,
      client_id: process.env.AUTH0_CLIENT_ID
    };
    res.json(authConfig);
});

app.get("/*", (_, res) => {
    res.sendFile(join(__dirname, 'datatable.html'));
});

const PORT = process.env.PORT;
app.listen(PORT, () => {
    console.log(`Application running on port ${PORT}`)
});