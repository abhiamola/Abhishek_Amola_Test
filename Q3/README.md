# Test Q3

To come up with a simple yet scalable architecture many assumptions were made and have used node libraries.
After researching current technologies available, there are multiple ways in which this problem can be solved. One way is to use Redis which can be quite convenient as we can leverage Redis clusters (across geolocations). It also provide LRU cahche option, Redis Sentinel (which can help in network failures), and many other functionalities. But I felt it beats the purpose of this exercise where Redis itself provides a library which solve much of our problem. So, I chose not use Redis. 

Following is the detailed design.

The provided code demonstrate a Geo Distributed LRU (Least Recently Used) cache with time expiration implemented using Node.js and Express.js. Let's break down how the code works:

## server-sf.js and server-ny.js:
These files create two separate Express.js servers for San Francisco (server-sf.js) and New York (server-ny.js). Each server handles GET and POST requests for key-value pairs.

GET Endpoint: When a GET request is made to /:key, the server checks if the requested key exists in its local cache (data Map). If the key is found, it responds with the corresponding value. Otherwise, it responds with "Key not found in SF" or "Key not found in NY", depending on the server.

POST Endpoint: When a POST request is made to /:key/:value, the server stores the key-value pair in its local cache (data Map) and responds with a confirmation message.

## cache-client.js:
This file creates a cache client using Express.js. It uses the lru-cache package to implement an LRU cache. The client serves as an intermediary between the user and the SF and NY servers.

GET Endpoint: When a GET request is made to /:key, the client first checks if the key is present in either the SF cache (sfCache) or the NY cache (nyCache). If found, it responds with the cached value. If not, it fetches the value from both SF and NY servers, updates the respective caches, and responds with the retrieved values.

POST Endpoint: When a POST request is made to /:key/:value, the client stores the key-value pair in both the SF and NY servers using POST requests. It then updates the local caches (sfCache and nyCache) with the stored value.

How the Flow Works:
- The client receives a GET request for a specific key.
- It first checks its local caches (sfCache and nyCache) for the key.
- If found in any cache, it responds with the cached value.
- If not found, it fetches the value from both SF and NY servers using Axios GET requests.
- The fetched values are then stored in the respective caches and returned in the response to the user.

Similarly, for the POST request, the client stores the key-value pair in both servers and updates its local caches.

Please note that the code assumes that the SF and NY servers are running locally on ports 3001 and 3000, respectively, and the client is running on port 8000. The cache eviction policy is implemented using the LRU cache mechanism provided by the lru-cache package, with a maximum of 5 items and a time-to-live (TTL) of 5 seconds.
