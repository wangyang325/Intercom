### Intercom Assignment 

# Question
We have some customer records in a text file (customers.txt) -- one customer per line, JSON lines formatted. We want to invite any customer within 100km of our Dublin office for some food and drinks on us. Write a program that will read the full list of customers and output the names and user ids of matching customers (within 100km), sorted by User ID (ascending).

    •	You must use the first formula from this Wikipedia article to calculate distance. Don't forget, you'll need to convert degrees to radians.
  
    •	The GPS coordinates for our Dublin office are 53.339428, -6.257664.
  
    •	You can find the Customer list here.

# Answer
GitHub:

https://github.com/wangyang325/Intercom/

Git:

https://github.com/wangyang325/Intercom.git

Demo:

http://34.247.85.101/index

# Proudest Archievement
Github:

./ProudestAchievement_YangWang.pdf

# Technologies
  1. Springboot
  2. Log4j2
  3. Docker
  4. AWS (1 VPC + 2 Subnet for LoadBalance Architecture)
    (for a budget reason only used 1 EC2:Ubuntu: free tier)

# Architecture (MVC + Restful + Microservice)
1. index.html (View) -> GetCustomerCtl (Controller) -> CalculateService (Model)
2. Restful Api -> -> GetCustomerCtl (Controller) -> CalculateService (Model)

3. I divided all functions to methods, can also use the CalculateService's API to run it as a bach on server.
4. I plan to split the index (public) and calculate (private) modules to two microservices and deploy them in a docker bridge network to protect the calculate module in future.

5. Because the time reason, did not to deal with the performance issue.
    
    Some suggestions for performance:

    a. deploy more services to increase the process ability and availability. (distributed system).

    b. use Message queue tools (Kafka) to distribute the data to services to avoid the data losing.

    c. use map and reduce ideas to split the data. (big data).

    d. use multi-thread to process the data on a service. (one service).

# Run (jre 1.8) on local
Copy the assignment-1.0.jar (Github:./assignment-1.0.jar) to local.

java -jar assignment-1.0.jar(Jre 1.8 needed)

Two ways to use:
  1. Upload the customer list by file.
  2. Copy the list to web page.

Demo:

http://34.247.85.101/index

# Deployment on AWS
used Docker to deploy on AWS

Restful API -> AWS (EC2:Docker server) 

# Test
UT: I used Junit to make some tests for the service module.

IT: Tested it on AWS.

Cases:

    1. Input checks (Normal-Special).
    
    2. No data (Normal-Special).
    
    3. Wrong format file or text (Error).
    
    4. Wrong center location. (Normal-Special)
    
    5. One record (Normal).
    
    6. Two or more records (Normal).
      
       6.1 No hit
       6.2 One hit
       6.3 all hits
       6.4 partial hits
    
    7. Performance test (Did not do it for the time reason).
      
       7.1 standard test
       7.2 Load test
       7.3 stress test
       7.4 statable test
       7.5 concurrent test
