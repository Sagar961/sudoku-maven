pipeline {

    agent any

    environment {
        IMAGE_NAME = "sagar961/sudoku"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                echo '===== CHECKOUT ====='
                checkout scm
            }
        }

        stage('Maven Build') {
            steps {
                echo '===== MAVEN BUILD ====='
                sh 'mvn clean compile'
            }
        }

        stage('Unit Test') {
            steps {
                echo '===== UNIT TEST ====='
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo '===== SONARQUBE ANALYSIS ====='

                withSonarQubeEnv('SonarQube') {

                    withCredentials([
                        string(
                            credentialsId: 'sonar-token',
                            variable: 'SONAR_TOKEN'
                        )
                    ]) {

                        sh '''
                            echo "SonarQube URL: $SONAR_HOST_URL"

			    curl -s "$SONAR_HOST_URL/api/system/status"

			    mvn org.sonarsource.scanner.maven:sonar-maven-plugin:5.8.0.7211:sonar \
			    -Dsonar.projectKey=sudoku \
			    -Dsonar.projectName=Sudoku \
			    -Dsonar.host.url=$SONAR_HOST_URL \
			    -Dsonar.token=$SONAR_TOKEN
                        '''
                    }
                }
            }
        }

        stage('Package') {
            steps {
                echo '===== PACKAGE ====='
                sh 'mvn package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                echo '===== DOCKER BUILD ====='

                sh '''
                    docker build \
                    -t ${IMAGE_NAME}:${IMAGE_TAG} \
                    -t ${IMAGE_NAME}:latest .
                '''
            }
        }

        stage('Docker Push') {
            steps {
                echo '===== DOCKER PUSH ====='

                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {

                    sh '''
                        echo "$DOCKER_PASSWORD" | docker login \
                        -u "$DOCKER_USER" \
                        --password-stdin

                        docker push ${IMAGE_NAME}:${IMAGE_TAG}

                        docker push ${IMAGE_NAME}:latest
                    '''
                }
            }
        }

        stage('Deploy Kubernetes') {
            steps {
                echo '===== KUBERNETES DEPLOYMENT ====='

                sh '''
                    kubectl set image deployment/sudoku \
                    sudoku=${IMAGE_NAME}:${IMAGE_TAG}

                    kubectl rollout status deployment/sudoku
                '''
            }
        }
    }

    post {

        success {
            echo '======================================'
            echo 'SUDOKU CI/CD PIPELINE SUCCESSFUL!'
            echo '======================================'
        }

        failure {
            echo '======================================'
            echo 'SUDOKU CI/CD PIPELINE FAILED!'
            echo '======================================'
        }
    }
}
