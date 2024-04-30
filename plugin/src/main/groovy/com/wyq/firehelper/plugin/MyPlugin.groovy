package com.wyq.firehelper.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        println "MyPlugin exec"

        project.task("pluginTest"){
            doLast{
                println "hello gradle!"
            }
        }
    }
}