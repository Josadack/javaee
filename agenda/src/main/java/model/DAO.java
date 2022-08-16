package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
  
  /**  Modulo de conexao *. */
  //Parâmetros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://localhost:3306/dbagenda";
	
	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "JOSADAQUEFERREIRASANTOS";
	
	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	//MÉTODOS DE CONEXAO
	private Connection conectar(){
		Connection con = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
			
		} catch (Exception e) {
			System.out.println(e);
			
			return null;	
		}
	}
	
	/**
	 *  CRUD CREATE *.
	 *
	 * @param jbcontato the jbcontato
	 */
	public void inserirContatos(JavabBeans jbcontato) {
		String create = "insert into contatos (nome, fone,email) values (?,?,?)";
		try {
			//abrir conexao
			Connection con = conectar();
			
			//Prepara a Query para execução no banco de dados
			PreparedStatement pst = con.prepareStatement(create);
			
			//Substituir os parametros (?) pelo conteudo das variaveis Javabeans 
			pst.setString(1, jbcontato.getNome());
			pst.setString(2, jbcontato.getFone());
			pst.setString(3, jbcontato.getEmail());
			
			//Executar a Query
			pst.executeUpdate();
			
			//Encerrar a conexao c/ o banco
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Listar contatos.
	 *
	 * @return the array list
	 */
	/*--- CRUD READ ----*/
	public ArrayList<JavabBeans> listarContatos(){
		//Criando um objeto para acessar a clase JavaBeans
		ArrayList<JavabBeans> jbcontatos = new ArrayList<>();
		String raed = "select * from contatos order by nome";
		try {
			//conectar ao banco
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(raed);
			ResultSet rs = pst.executeQuery();
			// o laço abaixo será executado enquanto houver contatos
			while (rs.next()) {
				//variaveis de apoio que recebem os dados do banco
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				//Populando o ArrayList
				jbcontatos.add(new JavabBeans(idcon,nome,fone,email));
			}
			con.close();
			return jbcontatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 *  CRUD UPDATE *.
	 *
	 * @param jbContato the jb contato
	 */
	public void selecionarContato(JavabBeans jbContato) {
		String raed2 = "select * from contatos where idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(raed2);
			pst.setString(1, jbContato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				//setar as variaveis Javabeans 
				jbContato.setIdcon(rs.getString(1));
				jbContato.setNome(rs.getString(2));
				jbContato.setFone(rs.getString(3));
				jbContato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Alterar contato.
	 *
	 * @param jbContato the jb contato
	 */
	//Alterar Contato
	public void alterarContato(JavabBeans jbContato) {
		String create = "update contatos set nome=?, fone=?,email=? where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, jbContato.getNome());
			pst.setString(2, jbContato.getFone());
			pst.setString(3, jbContato.getEmail());
			pst.setString(4, jbContato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Deletar contato.
	 *
	 * @param jbContato the jb contato
	 */
	//CRUD DELETE
	public void deletarContato(JavabBeans jbContato) {
		String delete = "delete from contatos where idcon=?";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, jbContato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}


