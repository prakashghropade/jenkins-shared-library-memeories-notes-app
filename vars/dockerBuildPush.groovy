def call(Map config = [:]) {

    def image = config.image
    def tag = config.tag ?: "latest"
    def credentialsId = config.credentialsId
    def dockerfile = config.dockerfile ?: "Dockerfile"
    def context = config.context ?: "."
    def apiUrl = config.apiUrl ?: ""

     

    stage("Docker Build") {

        if (apiUrl){

            sh """
                docker build \
                    --build-arg REACT_APP_API_URL=${apiUrl} \
                    -f ${dockerfile} \
                    -t ${image}:${tag} \
                    ${context}
            
            """
        } else {

        sh """ 
            docker build \
            -f  ${dockerfile} \
            -t ${image}:${tag} \
            ${context}
           """ 
        }
    }

    stage("Docker Push"){
        withCredentials([
            usernamePassword(
                credentialsId: credentialsId,
                usernameVariable: 'DOCKER_USERNAME',
                passwordVariable: 'DOCKER_PASSWROD'
            )
        ]) {
            
            withEnv([
                "IMAGE_NAME=${image}"
                "IMAGE_TAG=${tag}"
            ]) {

                sh '''
                        echo "$DOCKER_PASSWORD" | docker login \
                        -u "$DOCKER_USERNAME" \
                        --password-stdin

                        docker push "$IMAGE_NAME:$IMAGE_TAG"

                        docker logout
                '''
                
            }

        }
    }
}