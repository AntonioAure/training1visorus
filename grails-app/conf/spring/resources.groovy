import groovy.sql.Sql

// Place your Spring DSL code here
beans = {
    sqlS(Sql, ref("dataSource"))

}


