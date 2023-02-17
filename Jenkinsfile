pipeline {
    agent any
    triggers {
        githubPush()
    }
    stages {
        stage('checkout GIT') {
            steps {
                echo 'Pulling ...'
                git branch: 'aicha',
                    url: 'https://github.com/nadaTLILI/ProjetDevops.git'
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
                sh 'mvn clean -U'
            }
        }
        stage('Setup Maven') {
          steps {
            sh '''
              mkdir -p $HOME/.m2
              cp settings.xml $HOME/.m2/
            '''
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
       stage('Deploy to Nexus') {
           steps {
               withMaven(maven: 'maven-3.0.5', mavenSettingsConfig: 'maven-settings') {
                   sh 'mvn deploy'
               }
           }
       }
    }
}
