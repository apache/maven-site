/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

 pipeline {
    agent {
        label 'svn-websites'
    }
    stages {
        stage('Checkout') {
            steps {
                // Clean before build
                cleanWs()
                // We need to explicitly checkout from SCM here
                checkout scm
            }
        }
        stage('Build only') {
            when {
                not { branch 'master' }
            }
            steps {
                withEnv(["JAVA_HOME=${ tool "jdk_17_latest" }",
                         "PATH+MAVEN=${ tool "jdk_17_latest" }/bin:${tool "maven_3_latest"}/bin",
                         "MAVEN_OPTS=-Xms2g -Xmx4g -Djava.awt.headless=true"]) {                 
                    sh "mvn -U clean site -Dmaven.repo.local=.repository"
                }
            }
        }
        stage('Site Deploy') {
            when {
                branch 'master'
            }
            steps {
                withEnv(["JAVA_HOME=${ tool "jdk_17_latest" }",
                         "PATH+MAVEN=${ tool "jdk_17_latest" }/bin:${tool "maven_3_latest"}/bin",
                         "MAVEN_OPTS=-Xms2g -Xmx4g -Djava.awt.headless=true"]) {                 
                    sh "mvn -U clean site-deploy -Dmaven.repo.local=.repository"
                }             
            }
        }
    }
    post {
        always {
            jenkinsNotify()
        }
    }
    options {
        buildDiscarder(logRotator(numToKeepStr:'15'))
        timeout(time: 10, unit: 'MINUTES')
        skipStagesAfterUnstable()
        //timestamps()
        disableConcurrentBuilds()
        ansiColor('xterm')
        // This is required if you want to clean before build
        skipDefaultCheckout(true)
    }
}
