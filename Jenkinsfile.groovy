node {
    try {
        stage('Checkout') {
            echo "checkout from GitHub"
            git branch:'main',url:'https://github.com/Mamatha1296/Jenkins_assignment.git'   
        }

        stage('Build') {
            echo "Building the project using Maven"
            // Use Maven to build the project and create the WAR file
            sh 'mvn -f spring-tomcat/pom.xml clean package'
        }

        stage('Deploy to Tomcat') {
            echo "deployment to Tomcat server"
           
            // Move WAR file to Tomcat's webapps directory with sudo
            sh '''
                ssh -o StrictHostKeyChecking=no ec2-user@172.31.2.39 "sudo mv /tmp/spring-tomcat.war /tomcat/webapps/" || { echo "SSH failed"; exit 1; }
            '''
            echo "Deployment to Tomcat completed"
        }
    } 
        }
    

