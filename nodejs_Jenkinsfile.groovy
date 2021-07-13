pipeline {

    agent any
    currentBuild.result = "SUCCESS"
    
    stages {
      try {
       stage('Test'){

         env.NODE_ENV = "test"

         print "Environment will be : ${env.NODE_ENV}"

         bat 'node -v'
         bat 'npm prune'
         bat 'npm install'
         bat 'npm test'
       }

       stage('Cleanup'){
         echo 'prune and cleanup'
         bat 'npm prune'
         bat 'rm node_modules -rf'

         mail body: 'Jenkins - Build Successful',
              from: 'suresha@ilink-systems.com.com',
              replyTo: 'sureshkumar_a@outlook.com',
              subject: 'Jenkins - Build Passed',
              to: 'sureshkumar_a@outlook.com'

       }
    }
    catch (err) {
        currentBuild.result = "FAILURE"

            mail body: "Jenkins - Build Error: ${env.BUILD_URL}" ,
            from: 'suresha@ilink-systems.com.com',
            replyTo: 'sureshkumar_a@outlook.com',
            subject: 'Jenkins - Build failed',
            to: 'sureshkumar_a@outlook.com'

        throw err
      }
    }
}