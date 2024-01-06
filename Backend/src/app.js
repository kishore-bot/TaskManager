const express = require("express");
require("./db/mongoose");
const taskRoute = require("./routes/task");
const userRoute = require("./routes/user");


const port = process.env.PORT || 3000;

const app = express();
app.use(express.json());
app.use(userRoute);
app.use(taskRoute);


app.listen(port, () => {
  console.log("Server listening on port", port);
});
