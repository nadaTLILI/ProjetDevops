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

        /*stage ('deploy to Nexus') {
            steps {
                container ('maven') {
                    sh 'mvn deploy -DaltDeploymentRepository=nexus::default::http://192.168.1.80:8081/repository/deploymentRepo/'
                }
            }
        }*/

    stage('Publish') {
        environment {
            DOCKER_HUB_USERNAME = 'sassiaichaezzahra'
            DOCKER_HUB_PASSWORD = credentials('docker_hub_token')
        }
        steps {
            sh '''
                docker build -t sassiaichaezzahra/devops-exam-image:1 .
                docker login -u $DOCKER_HUB_USERNAME -p $DOCKER_HUB_PASSWORD
                docker push sassiaichaezzahra/devops-exam-image:1
            '''
        }
}

    }
}
