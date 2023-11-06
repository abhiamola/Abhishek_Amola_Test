const express = require("express");
const app = express();
const port = 3001;

const data = new Map();

app.get("/:key", (req, res) => {
  const key = req.params.key;

  //adding dummy data 
  if (data.size == 0) {
    for (let i = 1; i <= 100; i++) {
      data.set(i+"", 2 * i+"");
    }
  }
  if (data.has(key)) {
    res.send(data.get(key));
  } else {
    res.send("Key not found in SF");
  }
});

app.post("/:key/:value", (req, res) => {
  const key = req.params.key;
  const value = req.params.value;
  data.set(key, value);
  res.send(`Stored in SF: ${key} => ${value}`);
});

app.listen(port, () => {
  console.log("San Francisco server is running on port", port);
});
