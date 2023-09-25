**DEPENCENCY**
      - SSH Key needs to be created first with name KEY or change it in parameter accordingly (Used same key for All Env for Simplicity)
      - Best practice to use separate keys and rotate keys after some time for security reasons

**Task 1: Setting Up a CI/CD Pipeline**

- Create VPC and SNS Topic with [01-vpc.yml](./01-vpc.yml).      

- Create EC2 Stack with [02-ec2-ci-cd.yaml](./02-ec2-ci-cd.yaml). For CI/CD used CodePipeline
      
      - EC2 in Public VPC with security group
      - With required user data for cloudwatch and code deploy agent
      - Cloudwatch Alarms for memory,disk,CPU
      - IAM Profile and Role with specific permissions for cloudwatch logs and codedeploy and reading artifact fromm s3
      - CodePipeline and rest of the setup explaination is [here](../README.md).
# Cloudwatch Alarms For EC2 Instance (CI/CD)
 
 ![Cloudwatch1](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(293).png)   
 ![Cloudwatch2](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(294).png)   

# SNS Topic

 ![SNS](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(295).png)

**Task 2: Auto Scaling, Load Balancing, and Reverse Proxy**

- Create VPC and SNS Topic with [01-vpc.yml](./01-vpc.yml).

- Create Security Groups For ASG Instances, ALB, Bastion & RDS [03-security-Group.yml](./03-security-Group.yml).

- Create ASG Stack with [04-ASG.yml](./04-ASG.yml).
      
      - Create ALB in private subnet with internal schema coz of this `Test the setup by accessing the application through the reverse proxy.`
      - Target group with `port 8080` as we'll be running application on this port
      - Listener with `port 80` which will forward traffic to the target group
      - Autoscaling Group with all required settings with update and termination policy in private subnets with previously created target group & Launch Config with tag `Task-02-ASG-Instances`
      - Scale up and Scale Down policies based on CPU (Add Instance if CPU > 90 & Remove Instance if CPU < 20)
      - Launce config with cloudwatch agent, nginx which will be running on port 8080 with a simple hello world page with Instance ID & Private IP
      - Cloudwatch Alarms for ALB & ASG{CPU High & CPU Low}
      - IAM Profile and IAM Role with necessary permissions
      - Nginx Reverse Proxy is Configured on Bastion host which will access this hello world by proxy pass to ALB DNS name with this modification
         location / {
             proxy_pass http://ALB-DNS-NAME/;
             proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
             proxy_set_header X-Forwarded-Proto $scheme;
             proxy_set_header X-Forwarded-Port $server_port;
         } 
      - Bastion Reverse proxy access ALB port 80, ALB Port 80 can access ASG Instance port 8080 and inside ASG instances nginx running on port 8080

# Security-Group

 ![Security-Group](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(323).png)

# IAM Role

 ![ASG-IAM](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(297).png)

# ASG

 ![ASG1](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(298).png)
 ![ASG2](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(299).png)
 ![ASG3](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(300).png)

# Cloudwatch Alarms

 ![ASG-cloudwatch](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(301).png)

# ALB & TG
 
 ![ALB1](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(302).png)
 ![ALB2](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(303).png)
 ![TG](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(304).png)

# Launch Config

 ![Launch-Config](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(305).png)

# Reverse Proxy On Bastion 

 ![Reverse-Proxy1](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(306).png) 
 ![Reverse-Proxy2](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(307).png) 
 ![Reverse-Proxy3](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(308).png) 

**Task 3: Bastion Host and SSH Security**

- Create VPC and SNS Topic with [01-vpc.yml](./01-vpc.yml).

- Create Security Groups For ASG Instances, ALB, Bastion & RDS [03-security-Group.yml](./03-security-Group.yml).

- Create Bastion Host with [05-Bastion.yml](./05-Bastion.yml).

      - Bastion Host in Public Subnet with security group created previous and Elastic IP attached
      - Clouwatch Alarms set on mem, disk & CPU
      - Bastion can access private instances in ASG and can access the application running in ASG through Nginx by proxypass to ALB
      - IAM Roles with neccessary policies

# Bastion Host

 ![Bastion](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(309).png) 

# IAM Role

 ![Bastion-IAM](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(311).png) 

**Task 4: Monitoring, Alerts, and CloudWatch Logs**

      - For Monitoring Cloudwatch detailed monitoring and Alarms been set on CPU, mem and Disk
      - For Application logs, nginx logs been sent to cloudwatch logs which can be check in log groups
      - And Cloudformation logs and user data setup logs 
      - Logs configure for all ASG Instances, Bastion and EC2-CI/CD instance
      - Alarms set for CPU, Mem and Disk on Instances and For Alb alarm set for latency
      - All Alarms will sent notification to SNS Topic Created earlier

# LOG Groups

 ![LOG-Groups1](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(313).png) 
 ![LOG-Groups2](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(313).png) 

# Alarms

 ![Alarms](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(319).png) 

**Task 5: IAM Roles and Security**

      - IAM Roles Created and Attached to IAM Profile for All instances with necessary permissions
      - Security Group used for Bastion to access ASG instances & ALB running in Private Subnet 
      - Security Group used For ASG Instances which will access the RDS also running in Private Subnet
      - Secret Manager will store RDS Password, which ASG instances will read because of the attached policy
      - Bastion attached IAM Profile has IAM Role policy for cloudwatch metrics, Log groups, cloudformation, ec2 and Route53
      - ASG Instances attached IAM Profile has IAM Role policy for cloudwatch metrics, Log groups, cloudformation, ec2 and secret manager
      - ec2-ci-cd Instances attached IAM Profile has IAM Role policy for cloudwatch metrics, Log groups, cloudformation, ec2, s3, codedeploy and secret manager

# IAM ROLES

![IAM-ROLES](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(315).png) 

# Security Groups 

![SEC-GROUPOS](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(321).png) 


**Task 6: Database Setup**

- Create VPC and SNS Topic with [01-vpc.yml](./01-vpc.yml).

- Create Security Groups For ASG Instances, ALB, Bastion & RDS [03-security-Group.yml](./03-security-Group.yml).

- Create RDS with [06-rds.yml](./06-rds.yml).

      - Create Subnet Group using private subnets
      - Create db instance with mysql engine and store password in secret manager 
      - ASG Instances has the permission for accessing secret manager, haven't use any application which is using DB 
      - But db pass can be retrive with below command
      aws secretsmanager get-secret-value --secret-id MyDatabaseSecret --query SecretString --output text
      - Once the value is reterived it can be used in the configuration of application

# RDS 

![RDS](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(316).png) 

# Secret Manager

![Secret-Manager](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(317).png) 

# Get Password and connect mysql from ASG Instances

![MYSQL](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(318).png) 


# CLOUDFORMATION STACKS

![STACKS](https://github.com/Muhammad-Irfan324/Demo-Project-Stack/blob/cloudformation-stacks/Selection_999(321).png) 