# Test Q3

To come up with a simple yet scalable architecture I have made some assumptions and have used some node libraries.
After researching current technologies available, there are multiple ways in which this problem can be solved. One way is to use Redis which can be quite convenient as we can leverage Redis clusters (across geolocations). It also provide LRU cahche option, Redis Sentinel (which can help in network failures), and many other functionalities. But I felt it beats the purpose of this exercise where Redis itself provides a library which can solve much of our problem. So, I chose not use Redis. 

Following is the detailed design.

The provided code demonstrate a Geo Distributed LRU (Least Recently Used) cache with time expiration implemented using Node.js and Express.js. Let's break down how the code works:

### server-sf.js and server-ny.js:
These files create two separate Express.js servers for San Francisco (server-sf.js) and New York (server-ny.js). Each server handles GET and POST requests for key-value pairs. I have populated dummy data in key,value pair (using a Map).

GET Endpoint: When a GET request is made to /:key, the server checks if the requested key exists in its local cache (data Map). If the key is found, it responds with the corresponding value. Otherwise, it responds with "Key not found in SF" or "Key not found in NY", depending on the server.

POST Endpoint: When a POST request is made to /:key/:value, the server stores the key-value pair in its local cache (data Map) and responds with a confirmation message.

### cache-client.js:
This file creates a cache client using Express.js. It uses the lru-cache package to implement an LRU cache.  
The cache-client serves as an intermediary between the user and the SF and NY servers.

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

## The provided architecture attempts to satisfy the specified conditions as follows:

### Simplicity:
- The codebase is relatively simple, utilizing basic Node.js and Express.js for server implementation.
- The cache client handles requests and responses, making integration straightforward.

### Resilience to Network Failures or Crashes:
- The architecture uses separate servers for San Francisco and New York, making them independent of each other.
- The cache client can handle failures by falling back to the other server if one server is unresponsive.

### Near Real-Time Data Replication:
- The cache client fetches data from both SF and NY servers in real-time when a cache miss occurs, ensuring near real-time data replication.
- Writes are done immediately to both servers in parallel, ensuring real-time updates across geolocations.

### Data Consistency Across Regions:
- Data consistency is maintained by updating both SF and NY servers in real-time for every write operation.
- Reads are served from the cache, ensuring consistency and reducing the load on the servers.

### Locality of Reference:
- The cache client uses a local cache (sfCache and nyCache) for each region, serving data from the closest available cache.
- If the data is not found in the local cache, it fetches the data from the closest server, minimizing latency.

### Flexible Schema:
- The implementation does not enforce a strict schema for the stored data, allowing flexibility in the key-value pairs that can be stored in the cache.

### Cache Expiry:
- The LRU cache implemented using the lru-cache package allows setting a time-to-live (TTL) for cache entries (5 seconds in this case). Entries in the cache will automatically expire after the specified TTL, ensuring cache freshness and preventing stale data.

However, it's important to note that this implementation has limitations, especially in a production environment. For example, in the event of server failures, there is no mechanism for automatic failover or data recovery. Additionally, in a real-world scenario, much more robust solutions can be used such as distributed databases, load balancers, etc. Also, I have already talked about Redis.

### Improvement that I could have made

- Use a database instead of dummy data
- Creating separate LRU cache files for NY and SF servers and the created an intermediary mecahnism file for data consistency. Also can play around with diffent ttl for NY and SF cache, different size of cache, etc.
- Not to use lru-cache library and create LRU cache from scratch using Doubly linked list and Hash Map


### To run the provided code, you'll need to follow these steps:

#### Install Node.js:
Ensure you have Node.js installed on your system. You can download and install it from the official Node.js website.

#### Install Dependencies:
In the directory where your code files are located, open a terminal or command prompt and run the following command for each of the server files and the cache-client file. This will install the necessary dependencies (Express.js, Axios, and lru-cache).
##### npm install 

#### Run Servers:
Open two separate terminal windows or tabs. In one window, navigate to the directory where server-sf.js is located and run the SF server:
##### node server-sf.js
In the other window, navigate to the directory where server-ny.js is located and run the NY server:
##### node server-ny.js
This will start the SF server on port 3001 and the NY server on port 3000.

#### Run Cache Client:
Open another terminal window or tab. Navigate to the directory where cache-client.js is located and run the cache client:
##### node cache-client.js
This will start the cache client on port 8000.

Now, your SF and NY servers, along with the cache client, should be running. You can make requests to the cache client by accessing endpoints like http://localhost:8000/:key for GET requests a replacing :key with the desired key. Example - http://localhost:8000/1

![image](https://github.com/abhiamola/Abhishek_Amola_Test/assets/15613143/21f3e6e1-71b1-4852-839d-094926531b6d)

![image](https://github.com/abhiamola/Abhishek_Amola_Test/assets/15613143/386af245-9678-42af-84e5-bf3e34c015f8)
It will stay in cache for only 5 seconds


