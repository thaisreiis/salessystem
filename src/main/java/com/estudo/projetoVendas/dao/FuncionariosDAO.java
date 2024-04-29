/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estudo.projetoVendas.dao;

import com.estudo.projetoVendas.jdbc.ConnectionFactory;
import com.estudo.projetoVendas.model.Funcionarios;
import com.estudo.projetoVendas.model.WebServiceCep;
import com.estudo.projetoVendas.view.FrmLogin;
import com.estudo.projetoVendas.view.Frmmenu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author thaisreis
 */
public class FuncionariosDAO {
    
    private Connection con;
    
    public FuncionariosDAO() {
        this.con = new ConnectionFactory().getConnection();
    }
    
    public void cadastrarFuncionarios(Funcionarios obj) {
        try {
            String sql = "insert into tb_funcionarios(nome,rg,cpf,email,senha,cargo,nivel_Acesso,telefone,celular,cep,endereco,numero, complemento, bairro, cidade, estado) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getRg());
            stmt.setString(3, obj.getCpf());
            stmt.setString(4, obj.getEmail());
            stmt.setString(5, obj.getSenha());
            stmt.setString(6, obj.getCargo());
            stmt.setString(7, obj.getNivel_acesso());
            stmt.setString(8, obj.getTelefone());
            stmt.setString(9, obj.getCelular());
            stmt.setString(10, obj.getCep());
            stmt.setString(11, obj.getEndereco());
            stmt.setInt(12, obj.getNumero());
            stmt.setString(13, obj.getComplemento());
            stmt.setString(14, obj.getBairro());
            stmt.setString(15, obj.getCidade());
            stmt.setString(16, obj.getUf());
            
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }
    }
    
    public List<Funcionarios> listarFuncionarios() {
        
        try {
            List<Funcionarios> lista = new ArrayList<>();
            String sql = "select * from tb_funcionarios";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Funcionarios obj = new Funcionarios();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setSenha(rs.getString("senha"));
                obj.setCargo(rs.getString("cargo"));
                obj.setNivel_acesso(rs.getString("nivel_acesso"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setUf(rs.getString("estado"));
                
                lista.add(obj);
            }
            return lista;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
            return null;
        }
    }
    
    public void alterarFuncionario(Funcionarios obj) {
        try {
            String sql = "update tb_funcionarios set nome=?,rg=?,cpf=?,email=?,senha=?,cargo=?,nivel_acesso=?,telefone=?,celular=?,cep=?,endereco=?,numero=?,complemento=?,bairro=?,cidade=?,estado=? where id=? ";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getRg());
            stmt.setString(3, obj.getCpf());
            stmt.setString(4, obj.getEmail());
            stmt.setString(5, obj.getSenha());
            stmt.setString(6, obj.getCargo());
            stmt.setString(7, obj.getNivel_acesso());
            stmt.setString(8, obj.getTelefone());
            stmt.setString(9, obj.getCelular());
            stmt.setString(10, obj.getCep());
            stmt.setString(11, obj.getEndereco());
            stmt.setInt(12, obj.getNumero());
            stmt.setString(13, obj.getComplemento());
            stmt.setString(14, obj.getBairro());
            stmt.setString(15, obj.getCidade());
            stmt.setString(16, obj.getUf());
            stmt.setInt(17, obj.getId());
            
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }
    }
    
    public void excluirFuncionario(Funcionarios obj) {
        try {
            String sql = "delete from tb_funcionarios where id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getId());
            
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }
    }

    //Busca Cep
    public Funcionarios buscaCep(String cep) {
        
        WebServiceCep webServiceCep = WebServiceCep.searchCep(cep);
        
        Funcionarios obj = new Funcionarios();
        
        if (webServiceCep.wasSuccessful()) {
            obj.setEndereco(webServiceCep.getLogradouroFull());
            obj.setCidade(webServiceCep.getCidade());
            obj.setBairro(webServiceCep.getBairro());
            obj.setUf(webServiceCep.getUf());
            return obj;
        } else {
            JOptionPane.showMessageDialog(null, "Erro numero: " + webServiceCep.getResulCode());
            JOptionPane.showMessageDialog(null, "DescriÁ„o do erro: " + webServiceCep.getResultText());
            return null;
        }
        
    }

    //Metodo consultaFuncionario por nome (retorna um objeto)
    public Funcionarios consultaPorNome(String nome) {
        try {
            //Criar o sql
            String sql = "select * from tb_funcionarios where nome = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            
            ResultSet rs = stmt.executeQuery();
            Funcionarios obj = new Funcionarios();
            
            if (rs.next()) {
                
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setSenha(rs.getString("senha"));
                obj.setCargo(rs.getString("cargo"));
                obj.setNivel_acesso(rs.getString("nivel_acesso"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setUf(rs.getString("estado"));
            }
            return obj;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
            return null;
        }
    }

    //Metodo listaFuncionarioPorNome- retorna uma lista 
    public List<Funcionarios> listaFuncionariosPorNome(String nome) {
        try {
            List<Funcionarios> lista = new ArrayList<>();
            String sql = "select * from tb_funcionarios where nome like ?";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Funcionarios obj = new Funcionarios();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setRg(rs.getString("rg"));
                obj.setCpf(rs.getString("cpf"));
                obj.setEmail(rs.getString("email"));
                obj.setSenha(rs.getString("senha"));
                obj.setCargo(rs.getString("cargo"));
                obj.setNivel_acesso(rs.getString("nivel_acesso"));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setCep(rs.getString("cep"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setNumero(rs.getInt("numero"));
                obj.setComplemento(rs.getString("complemento"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                obj.setUf(rs.getString("estado"));
                
                lista.add(obj);
            }
            return lista;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
            return null;
        }
    }

    //Metodo Login
    public void efetuaLogin(String email, String senha) {
        
     try {
            // 1 passo -SQL
            String sql = "select * from tb_funcionarios where email = ? and senha = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                //Usuario logou
                //caso usuario for Admin
                if(rs.getString("nivel_acesso").equals("Administrador")){
                    JOptionPane.showMessageDialog(null, "Seja bem vindo ao Sistema");
                Frmmenu tela = new Frmmenu();
                tela.usuariologado = rs.getString("nome");
                tela.setVisible(true);
                }
                //Apenas usuario
                else if(rs.getString("nivel_acesso").equals("Usuário")){
                    JOptionPane.showMessageDialog(null, "Seja bem vindo ao Sistema");
                    Frmmenu tela = new Frmmenu();
                    tela.usuariologado = rs.getString("nome");
                    
                    //desabilitar os Menus
                    tela.vendas_historico.setEnabled(false);
                    tela.vendas_posicao.setVisible(false);
                    tela.setVisible(true);
                }
                
            }else{
                //Dados incorretos
                JOptionPane.showMessageDialog(null,"Dados incorretos!");
                 new FrmLogin().setVisible(true);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ops... Aconteceu um erro no login!! ->" +e);
           
        }
    }
    
}
