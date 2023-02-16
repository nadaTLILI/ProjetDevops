pipeline {
    // agent { docker { image 'maven:3.9.0-eclipse-temurin-11' } }
    stages {
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
        stage('Maven Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Finish') {
            steps {
                sh 'echo "Finish..."'
                sh 'date'
            }
        }
        /*
        stage('Construction du livrable') {
            steps {
                sh 'mvn compiler:compile'
            }
        }
        
        stage('Maven test') {
            steps {
                sh 'mvn test'
            }
        }
       stage('Maven SonarQube Analysis') {
           environment {
               SONAR_TOKEN = credentials('sonarqube_token')
           }
           steps {
               sh 'mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN'
           }
       }
       */
    }
}