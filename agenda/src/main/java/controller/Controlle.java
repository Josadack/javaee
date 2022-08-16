package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavabBeans;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class Controlle.
 */
@WebServlet(urlPatterns = { "/Controlle", "/main", "/insert", "/select", "/update", "/delete", "/report" })
public class Controlle extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The dao. */
	DAO dao = new DAO();
	
	/** The jb contato. */
	JavabBeans jbContato = new JavabBeans();

	/**
	 * Instantiates a new controlle.
	 *
	 * @see HttpServlet#HttpServlet()
	 */
	public Controlle() {
		super();
	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			removerContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else {
			response.sendRedirect("index.html");
		}
	}

	/**
	 * Contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Criando um Objeto que irá receber os dados JavaBeans
		ArrayList<JavabBeans> lista = dao.listarContatos();
		// Encaminhar a lista ao documento agenda.jsp
		request.setAttribute("jbContato", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}

	/**
	 * Novo contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Novo Contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		jbContato.setNome(request.getParameter("nome"));
		jbContato.setFone(request.getParameter("fone"));
		jbContato.setEmail(request.getParameter("email"));

		/** Invocando o método inserirContatos passando o objeto beansContatos **/
		dao.inserirContatos(jbContato);

		// Redirecionamento para o documento agenda.jsp
		response.sendRedirect("main");
	}

	/**
	 * Listar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Editar Contatos
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recebimento do ID do contato que será editado
		String idcon = request.getParameter("idcon");

		/** --Setar a variavel Javabeans-- **/
		jbContato.setIdcon(idcon);

		// Executar o metodos selecionarContato(DAO)
		dao.selecionarContato(jbContato);

		// Setar os atributos do formularios com o conteudo JavaBeans
		request.setAttribute("idcon", jbContato.getIdcon());
		request.setAttribute("nome", jbContato.getNome());
		request.setAttribute("fone", jbContato.getFone());
		request.setAttribute("email", jbContato.getEmail());
		// Encaminhar ao documentos editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}

	/**
	 * Editar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Setar as variaveis Javabeans
		jbContato.setIdcon(request.getParameter("idcon"));
		jbContato.setNome(request.getParameter("nome"));
		jbContato.setFone(request.getParameter("fone"));
		jbContato.setEmail(request.getParameter("email"));
		// Execultar o método alterarContatos
		dao.alterarContato(jbContato);
		// redirecionamento para o documento agenda.jsp (atualizando as alterações)
		response.sendRedirect("main");
	}

	/**
	 * Remover contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Remover Contatos
	protected void removerContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recebimento do id do contato a ser excluido(Validador)
		String idcon = request.getParameter("idcon");
		// seta a variável idcon JavaBeans
		jbContato.setIdcon(idcon);
		// Executar o método DeletarContato
		dao.deletarContato(jbContato);
		// redirecionamento para o documento agenda.jsp (atualizando as alterações)
		response.sendRedirect("main");
	}
	
	/**
	 * Gerar relatorio.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//Gerar Relátorio em PDF
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();
		
		try {
			//tipo de conteudo
			response.setContentType("apllication/pdf");
			//nome do documento
			response.addHeader("Content-Disposition", "inline;filename=" + "jbContato.pdf");
			//Criar Documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			//abrir o documento -> Conteudo
			documento.open();
			documento.add(new Paragraph("Lista de contatos: "));
			documento.add(new Paragraph(" "));
			//Criar Tabela
			PdfPTable tabela = new PdfPTable(3);
			//Cabeçalho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			//Popular a tabela com os contatos
			ArrayList<JavabBeans> lista = dao.listarContatos();
			for (int i = 0; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}
			documento.add(tabela);
			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
	}
}

