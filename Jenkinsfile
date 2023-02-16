pipeline {
    agent any
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M2_HOME"
    }

stages {
    
        stage('GIT') { 
            steps { 
                git url: 'https://github.com/nadaTLILI/ProjetDevops.git', branch:'main' 
            }
        }
        stage('GIT CHECKOUT') { 
            steps { 
                echo 'checkout';
                sh 'git checkout main' 
            }
        }
     
        stage('CLEAN') {
            steps {

               sh 'mvn clean install'

            }
        }

    }
}