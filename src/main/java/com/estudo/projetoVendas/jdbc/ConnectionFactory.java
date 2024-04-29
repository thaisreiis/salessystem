/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estudo.projetoVendas.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author thaisreis
 */
public class ConnectionFactory {
    public Connection getConnection() {
     
    try {
        
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bdvendas","usuario","123");
    } catch (Exception erro) {
    throw new RuntimeException(erro);
    }     
        
    }

}
