node {
   def mvnHome
   stage('Preparation') {
      dir('moria') {
        git 'https://github.com/Martin819/moria.git'
      }
      mvnHome = tool 'M3'
   }
   stage('Build') {
      dir('moria/client') {
        nodejs(nodeJSInstallationName: 'NodeJS') {
          sh "npm install --no-optional"
          sh "npm install jquery@1.9.1 --no-optional"
          sh "npm install bootstrap@4.3.1 --no-optional"
          sh "npm install typescript@* --no-optional"
          sh "npm install ts-pnp@1.0.1 --no-optional"
          sh "npm install --no-optional"
          sh "npm run build"
        }
      }
   }
   stage('Results') {
       dir('moria/client') {
           sh "mkdir -p target"
           sh "tar -czvf target/build.tar.gz build"
        archiveArtifacts 'target/build.tar.gz'
        println("Results")
       }
   }
}