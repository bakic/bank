pipeline { 
    agent any 
    stages {
        stage('Compile') { 
            steps { 
				withMaven(maven: 'maven') {
					bat 'mvn clean compile'
				}
            }
        }
        stage('Test'){
            steps {
				withMaven(maven: 'maven') {
					bat 'mvn test'
				}
            }
        }
        stage('Deploy') {
            steps {
				withMaven(maven: 'maven') {
					bat 'mvn install'
				}
            }
		}
	}
}