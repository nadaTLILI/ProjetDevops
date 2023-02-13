pipeline {
    agent any
     triggers {
        pollSCM('H/5 * * * *')
    }
    stages {
        stage('checkout GIT') {
            steps {
                echo 'Pulling ...'
                git branch: 'eminechakroun', url: 'https://github.com/nadaTLILI/ProjetDevops.git'
            }
        }
        stage('Affichage de la date système') {
            steps {
                sh 'date'
            }
        }
         stage('maven version') {
            steps {
                sh 'mvn -version'
            }
        }
         stage('Maven Clean') {
            steps {
                sh 'mvn clean'
            }
        }
         stage('Maven Compile') {
            steps {
                sh 'mvn clean package'
            }
        }
         stage('Construction du livrable') {
            steps {
                sh 'mvn compiler:compile'
            }
        }
        stage('Maven SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }
    }
}