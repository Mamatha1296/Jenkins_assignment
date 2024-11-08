node {
    try {
        stage('Checkout') {
            echo "checkout from GitHub"
            git branch:'main',url:'https://github.com/Mamatha1296/Jenkins_assignment.git'   
        }

        stage('Build') {
            echo "Building the project using Maven"
            // Use Maven to build the project and create the WAR file
            sh 'mvn -f sprint-tomcat/pom.xml clean package'
        }

        stage('Deploy to Tomcat') {
            echo "deployment to Tomcat server"
           
            // Move WAR file to Tomcat's webapps directory with sudo
            sh '''
                ssh -o StrictHostKeyChecking=no ec2-user@172.31.2.39 "sudo mv /tmp/MyWebApp.war /apache-tomcat-9.0.96/webapps/" || { echo "SSH failed"; exit 1; }
            '''
            echo "Deployment to Tomcat completed"
        }
    } catch (Exception e) {
        echo "Error occurred: ${e.message}"
        currentBuild.result = 'FAILURE'
    } finally {
        if (currentBuild.result == 'SUCCESS') {
            echo "Deployment successful!"
        } else {
            echo "Deployment failed."
        }
    }
}
