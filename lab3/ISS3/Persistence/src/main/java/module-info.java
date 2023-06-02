module Persistence{
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires net.bytebuddy;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    requires com.fasterxml.classmate;
    requires java.persistence;

    exports Domain;
    exports Repository;
    exports Repository.JDBC;
    exports Repository.ORM;

}