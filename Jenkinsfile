pipeline {
    agent any
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M2_HOME"
    }
    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "192.168.2.10:8081"
        NEXUS_REPOSITORY = "maven-nexus-repo"
        NEXUS_CREDENTIAL_ID = "nexus-user-credentials"
    }

stages {
    
        stage('GIT CLONE') { 
            steps { 
                echo 'getting project from git';
                git 'https://github.com/nadaTLILI/ProjetDevops.git' 
            }
        }
        
        stage('GIT CHECKOUT') { 
            steps { 
                echo 'checkout';
                sh 'git checkout targetbranch'
                sh 'git pull'
                sh 'git reset --hard origin/targetbranch'
            }
        }

        stage('MVN Package') {
            steps {

               sh "mvn package -DskipTests=true"

            }
        }
     
        stage('MVN CLEAN') {
            steps {

               sh 'mvn clean install'

            }
        }

         stage('SONARQUBE') {
            steps {
               sh 'mvn sonar:sonar -Dsonar.login=43a44e1b84de49d7353d0e70778f7b2b52146f30'

            }
        }
        stage("Nexus Repository Manager") {
            steps {
                script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }
        stage('Build Docker Image') {
            steps {
               sh 'docker build -t nadatlili/spring .'
            }
        }
        stage('Run Docker Compose') {
            steps {
                sh 'docker pull nadatlili/angular'
                sh 'docker-compose up --build -d'
            }
        }
        stage('Push Docker Image') {
            steps {
              withCredentials([usernamePassword(credentialsId: 'dockerhubcredentials', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
            	sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                sh 'docker push nadatlili/spring'}
            }
        }
    }
    post {
        always {
            emailext body: 'A Test EMail', recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Test'
        }
    }

}
