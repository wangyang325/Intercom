# Intercom Assignment 

GitHub:
https://github.com/wangyang325/Intercom/

Git:
https://github.com/wangyang325/Intercom.git

# Rroudest Archievement
Github: ./ProudestAchievement_YangWang.pdf

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
java -jar assignment-1.0.jar
(Jre 1.8 needed)

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

    1. Input checks (Normal).
    
    2. No data (Normal).
    
    3. Wrong format file or text (Error).
    
    4. One record (Normal).
    
    5. Two or more records (Normal).
    
    6. Performance test (Did not do it for the time reason).

