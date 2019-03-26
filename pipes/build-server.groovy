node {
   def mvnHome
   stage('Preparation') {
      dir('moria') {
        git 'https://github.com/Martin819/moria.git'
      }
      mvnHome = tool 'M3'
   }
   stage('Build') {
      dir('moria/server') {
        sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
      }
   }
   stage('Results') {
       dir('moria/server') {
         archiveArtifacts 'target/*.jar'
       }
   }
}