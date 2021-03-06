package com.moowork.gradle.node

import com.moowork.gradle.node.util.PlatformHelper

class NodePluginTest
    extends AbstractProjectTest
{
    private Properties props

    def setup()
    {
        this.props = new Properties()
        PlatformHelper.INSTANCE = new PlatformHelper( this.props )
    }

    def 'check default tasks'()
    {
        when:
        this.project.apply plugin: 'com.moowork.node'
        this.project.evaluate()

        then:
        this.project.extensions.getByName( 'node' )
        this.project.tasks.getByName( 'nodeSetup' )
        this.project.tasks.getByName( 'npmInstall' )
        this.project.tasks.getByName( 'npmSetup' )
    }

    def 'check repository and dependencies (no download)'()
    {
        when:
        this.project.apply plugin: 'com.moowork.node'
        this.project.evaluate()

        then:
        this.project.repositories.size() == 0
        !this.project.configurations.contains( 'nodeDist' )
    }

    def 'check npm rule task'()
    {
        when:
        this.project.apply plugin: 'com.moowork.node'
        this.project.evaluate()

        then:
        this.project.tasks.getRules().size() == 1
        def rule = this.project.tasks.getRules().get( 0 )

        !this.project.tasks.getAsMap().containsKey( 'npm_install' )
        rule.apply( 'npm_install' )
        this.project.tasks.getAsMap().containsKey( 'npm_install' )
    }
}
