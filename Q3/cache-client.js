const express = require("express");
const axios = require("axios");
const { LRUCache } = require("lru-cache"); // Using the 'lru-cache' package
const app = express();
const port = 8000;

const nyCacheServer = "http://localhost:3000";
const sfCacheServer = "http://localhost:3001";

const lruOptions = {
  max: 5, // Maximum number of items in the cache
  ttl: 5*1000, // Maximum age for cache entries (5 second)
};
const nyCache = new LRUCache(lruOptions);
const sfCache = new LRUCache(lruOptions);

app.get("/:key", async (req, res) => {
  const key = req.params.key;

  // Try to get the value from the LRU cache
  const nyValue = nyCache.get(key);
  if (nyValue !== undefined) {
    res.send(`NY Cache (LRU): ${nyValue}`);
    return;
  }

  const sfValue = sfCache.get(key);
  if (sfValue !== undefined) {
    res.send(`SF Cache (LRU): ${sfValue}`);
    return;
  }

  // If the value is not in the LRU cache, fetch it from the servers
  const [nyResponse, sfResponse] = await Promise.all([
    axios.get(`${nyCacheServer}/${key}`),
    axios.get(`${sfCacheServer}/${key}`),
  ]);

  // Update the LRU cache with the fetched value
  if (nyResponse.data !== "Key not found in NY cache") {
    nyCache.set(key, nyResponse.data);
  }

  if (sfResponse.data !== "Key not found in SF cache") {
    sfCache.set(key, sfResponse.data);
  }

  res.send(
    `NY server (HTTP): ${nyResponse.data}, SF server (HTTP): ${sfResponse.data}`
  );
});

app.post("/:key/:value", async (req, res) => {
  const key = req.params.key;
  const value = req.params.value;
  await Promise.all([
    axios.post(`${nyCacheServer}/${key}/${value}`),
    axios.post(`${sfCacheServer}/${key}/${value}`),
  ]);

  // Update the LRU cache with the stored value
  nyCache.set(key, value);
  sfCache.set(key, value);

  res.send(`Stored in both NY and SF caches (LRU): ${key} => ${value}`);
});

app.listen(port, () => {
  console.log("Cache client is running on port", port);
});
