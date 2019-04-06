node {
   def mvnHome
   stage('Preparation') {
      dir('moria') {
        git 'https://github.com/Martin819/moria.git'
      }
      mvnHome = tool 'M3'
   }
   stage('Set Backend env') {
        dir('moria/client') {
          sh "echo 'REACT_APP_API_HOST=localhost' > .env"
          sh "echo 'REACT_APP_API_PORT=8083' >> .env"
          println("Using following backend configuraion: ")
          sh "cat .env"
        }
   }
   stage('Install NPM') {
      dir('moria/client') {
        nodejs(nodeJSInstallationName: 'NodeJS') {
          sh "npm install --no-optional"
          sh "npm list jquery || npm install jquery@1.9.1 --no-optional"
          sh "npm list bootstrap || npm install bootstrap@4.3.1 --no-optional"
          sh "npm list typescript || npm install typescript@* --no-optional"
          sh "npm list ts-pnp || npm install ts-pnp@1.0.1 --no-optional"
          sh "npm install --no-optional"
          sh "npm run build"
        }
      }
   }
   stage('Build') {
      dir('moria/client') {
        nodejs(nodeJSInstallationName: 'NodeJS') {
          sh "npm run build"
        }
      }
   }
   stage('Results') {
       dir('moria/client') {
           sh "cp package.json build/package.json"
           sh "mkdir -p target"
           sh "tar -czvf target/build.tar.gz build"
        archiveArtifacts 'target/build.tar.gz'
        println("Results")
       }
   }
}