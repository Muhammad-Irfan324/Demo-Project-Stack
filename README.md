
**Once Instance for CI/CD codepipeline is created then**

- Steps Performed on EC2 Instance After Creation

**- Installing Java 17** 

      sudo apt install software-properties-common
      sudo add-apt-repository ppa:linuxuprising/java
      sudo apt install oracle-java17-installer --install-recommends
      java --version
      #OUTPUT
      java 17.0.6 2023-01-17 LTS
      Java(TM) SE Runtime Environment (build 17.0.6+9-LTS-190)
      Java HotSpot(TM) 64-Bit Server VM (build 17.0.6+9-LTS-190, mixed mode, sharing)

**- Installing SpringBoot and gradle**

      curl -s https://get.sdkman.io | bash
      source "/home/ubuntu/.sdkman/bin/sdkman-init.sh"
      sdk install springboot
      sdk install gradle

      nginx already installed via userdata, modify the default vhost and 
      add this content in order to proxy pass to our app which is running on port 8081
      And Restart or Reload nginx service 
       location / {
            proxy_pass http://localhost:8081/;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
            proxy_set_header X-Forwarded-Port $server_port;
        }

Port set for app -- [file](./src/main/resources/application.properties).

########################################


- Deploing this simple hello world SpringBoot Application via CodePipeline which will Trigger in case if there's any commit in the main branch
- First create application in code Deploy
- Then Create Deployment group with required service role which has code deployment permission and check Amazon-EC2 Instance
- Then Create Pipeline by setting the name and chosing the service role and choose location for your artifact
- Choose Source provider and GitHub Version 2 and configure repository name and branch and check the box to start pipeline on source code change
- Then choose Build Provider AWS CodeBuild
- Create Project and set name of project 
- and choose OS Ubuntu with runtime standard and image latest and service role which can push logs to cloudwatch and put artifact on S3
- select buildspec file as we'll be using that from this repo
- Deployer provider - AWS CodeDeploy with Application name and Deploygroup which we created in start
- On creating Pipeline will run or on commiting in repo will also trigger the pipeline
  

**Tag OF EC2 Instance for this is Task-01-EC2-CI/CD-SpringBoot-App**  