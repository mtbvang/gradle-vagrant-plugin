/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.plugins.vagrant.utils

final class OsUtils {
    static final String VAGRANT_HOME_ENV_VAR = 'VAGRANT_HOME'
    static final String USERPROFILE_ENV_VAR = 'USERPROFILE'

    private OsUtils() {}

    static boolean isOSWindows() {
        System.properties['os.name'].toLowerCase().contains('windows')
    }

    static List<String> prepareEnvVars(Map<String, String> providedEnvVars) {
        providedEnvVars[VAGRANT_HOME_ENV_VAR] = determineVagrantHome()
        flattenEnvVars(providedEnvVars)
    }

    private static String determineVagrantHome() {
        String vagrantHome = System.getenv()[VAGRANT_HOME_ENV_VAR]

        if(!vagrantHome) {
            String userProfile = System.getenv()[USERPROFILE_ENV_VAR]

            if(userProfile) {
                vagrantHome = "$userProfile/.vagrant.d"
            }
            else {
                vagrantHome = "${System.properties['user.home']}/.vagrant.d"
            }
        }

        vagrantHome
    }

    private static List<String> flattenEnvVars(Map<String, String> providedEnvVars) {
        providedEnvVars.collect { key, value -> "$key=$value" }
    }
}