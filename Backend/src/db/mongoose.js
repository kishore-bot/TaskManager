const mongoose = require("mongoose");

mongoose
  .connect("mongodb://localhost:27017/task-manager")
.then(() => console.log("MongoDB connected"))
  .catch((err) => console.error(err));
