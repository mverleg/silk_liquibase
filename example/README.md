
To create changelogs from an SQLite database, use the Liquibase project:

Class:

    liquibase.integration.commandline.Main
    
Arguments:

    --url="jdbc:sqlite:/tmp/test.sqlite" 
    --changeLogFile="changelogs/generated.xml" 
    --classpath="/path/to/sqlite-jdbc-3.30.1.jar" 
    generateChangelog

