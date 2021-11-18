pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './mvnw package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}