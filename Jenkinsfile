pipeline {
    agent any
    triggers {
        githubPush()
    }
    stages {
        stage('checkout GIT') {
            steps {
                echo 'Pulling ...'
                git branch: 'mehdi',
                    url: 'https://github.com/nadaTLILI/ProjetDevops.git'
            }
        }
        stage('Maven Clean') {
            steps {
            sh 'date'
            sh 'mvn -version'
            sh 'mvn clean package'
            }
        }
        stage('Maven Compile') {
            steps {
                sh 'mvn compiler:compile'
            }
        }
        stage('Maven test') {
            steps {
                sh 'mvn test'
            }
        }
         stage('Sonar analysis') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=password'
            }
        }
    }
}
