const express = require("express");
const User = require("../model/user");
const auth = require("../middleware/auth");
const routes = express.Router();

routes.post("/sign-up", async (req, res) => {
  const user = new User(req.body);
  try {
    await user.save();
    const token = await user.generateAuthToken();
    
    res.status(201).send({ message:"Success", token });
  } catch (e) {
    res.status(400).send({ message:"Error", token:"" });
  }
});

routes.post("/log-in", async (req, res) => {
  try {
    const user = await User.findByCredentials(
      req.body.email,
      req.body.password
    );
    const token = await user.generateAuthToken();
    res.send({ message:"Success", token });
  } catch (e) {
    res.status(400).send({ message:"Error", token:"" });
  }
});

routes.get("/stars", auth, async (req, res) => {
  try {

    const star = req.user.star+"";
    res.status(200).send({star});
  } catch (e) {
    res.status(500).send(e); 
  }
});


module.exports = routes;
