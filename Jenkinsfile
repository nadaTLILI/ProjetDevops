pipeline {
    // agent { docker { image 'maven:3.9.0-eclipse-temurin-11' } }
    agent any
    tools {
        maven "M2_HOME"
    }
    environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "localhost:8081"
        NEXUS_REPOSITORY = "maven-releases"
        NEXUS_CREDENTIAL_ID = "nexus-user-credentials"
    }
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
               sh 'mvn sonar:sonar   -Dsonar.projectKey=devops   -Dsonar.host.url=http://localhost:9000   -Dsonar.login=$SONAR_TOKEN'
               sh 'echo "Running SonaQube"'
           }
       }
                stage('Maven Package') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage("Publish to Nexus Repository Manager") {
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

        stage('Build image back') {
                        steps {
                script {
            dockerImage = docker.build("longyearbyenr/devopsback:latest")
                }}
        }

        stage('Push image') {
                        steps {
                script {
            withDockerRegistry([ credentialsId: "dockerhubaccount", url: "" ]) {
            dockerImage.push()
            }
                }}
        }
    }
}