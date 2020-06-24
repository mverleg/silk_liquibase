
To create changelogs from an SQLite database, use the Liquibase project:

Class:

    liquibase.integration.commandline.Main
    
Arguments:

    --url="jdbc:sqlite:/tmp/test.sqlite" 
    --changeLogFile="changelogs/generated.xml" 
    --classpath="/path/to/sqlite-jdbc-3.30.1.jar" 
    generateChangelog

Then to generate the Silk files from this, this was my **failed** attempt:

    --url="jdbc:silk:/home/mark/silk/tmp.silk.json"
    --changeLogFile="/home/mark/silk/silk_liquibase/example/changelog.xml"
    --classpath="/home/mark/silk/silk_liquibase/target/silk_liquibase-0.1.0.jar"
    --databaseClass="liquibase.database.silk.SilkDatabase"
    --referenceDriver="liquibase.database.silk.connection.SilkDriver"
    update

It fails with:

    Unexpected error running Liquibase: java.lang.RuntimeException: Cannot find database driver: Driver class was not specified and could not be determined from the url (jdbc:silk:/home/mark/silk/tmp.silk.json)

Possibly check liquibase.properties https://stackoverflow.com/questions/14501332/cannot-find-database-driver-org-postgresql-driver
