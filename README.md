# portfolioapplication
```cmd
docker build -t ai-portfolio:latest .
docker run -p 8081:8081 --name my-ai-portfolio -d ai-portfolio:latest
```

# Jenkins - docker
```cmd
docker run -p 8080:8080 -p 50000:50000 --name jenkins_server --restart=on-failure \
  -v jenkins_home:/var/jenkins_home \
  jenkins/jenkins:lts
  
docker run -p 8080:8080 -p 50000:50000 --name jenkins_server --restart=on-failure -v jenkins_home:/var/jenkins_home -v //var/run/docker.sock:/var/run/docker.sock jenkins/jenkins:lts
```