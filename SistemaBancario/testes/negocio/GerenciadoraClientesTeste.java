package negocio;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.is;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GerenciadoraClientesTeste {
	   // variáveis da classe (acessíveis para todos os testes)
    int IDCliente1 = 1;
    int IDCliente2 = 2;

    Cliente cliente01;
    Cliente cliente02;
    List<Cliente> clientesDoBanco;
    GerenciadoraClientes getClientes;
    
 // Esse método roda ANTES de cada teste
    @Before
    public void setUp() {

        // cria clientes
        cliente01 = new Cliente(IDCliente1, "Enzo Coelho", 31, "enzocoelho@outlook.com", 1, true);
        cliente02 = new Cliente(IDCliente2, "Nice Coelho", 65, "nice@outlook.com", 2, true);

        // cria lista e adiciona os clientes
        clientesDoBanco = new ArrayList<>();
        clientesDoBanco.add(cliente01);
        clientesDoBanco.add(cliente02);

        // cria gerenciadora de clientes
        getClientes = new GerenciadoraClientes(clientesDoBanco);
    }
	

	@Test
	public void testePesquisaClienteExistenteByID() {	
		
		//Chamando o metodo getClientes passando um ID
		Cliente cliente = getClientes.pesquisaCliente(IDCliente1);
		
		//Verificação 
		assertThat(cliente.getId(), is(1));
		//assertThat(cliente.getEmail(), is("enzocoelho@outlook.com"));		
		
	}
	
	@Test
	public void testePesquisaClienteInexistenteByID() {	
		
		//Chamando o metodo getClientes passando um ID
		Cliente cliente = getClientes.pesquisaCliente(55);
		
		//Verificação 
		assertNull(cliente);
		//assertThat(cliente.getEmail(), is("enzocoelho@outlook.com"));		
		
	}
	
	@Test
	public void testRemoveCliente() {
		
		boolean clienteRemovido = getClientes.removeCliente(IDCliente1);
		
		assertThat(clienteRemovido, is(true));
		assertThat(getClientes.getClientesDoBanco().size(), is(1));
		assertNull(getClientes.pesquisaCliente(IDCliente1));
	}
	
	@Test
	public void testAdicionaCliente() {
		 // Cria novo cliente para adicionar
	    Cliente novoCliente = new Cliente(3, "Maria", 40, "maria@gmail.com", 3, true);

	    // Ação: chama o método que será testado
	    getClientes.adicionaCliente(novoCliente);

	    // Verificações
	    assertThat(clientesDoBanco.size(), is(3));           // agora deve ter 3 clientes
	    assertThat(clientesDoBanco.contains(novoCliente), is(true));
		
	}
	
	@Test 
	public void testclienteAtivo() {
		boolean clienteAtivo = getClientes.clienteAtivo(IDCliente1);
		
		// Verificações
		assertThat(clienteAtivo, is(true));
	}
	
	@Test
	public void testeLimpa() {

	    // garantia inicial do teste (opcional, mas boa prática)
	    assertThat(clientesDoBanco.size(), is(2));

	    // Ação: limpar a lista
	    getClientes.limpa();

	    // Verificações
	    assertThat(clientesDoBanco.isEmpty(), is(true));
	    assertThat(clientesDoBanco.size(), is(0));
	}
	
	@Test
	public void testeValidaIdade_Valida() throws Exception {

	    boolean resultado = getClientes.validaIdade(30); // idade válida

	    assertThat(resultado, is(true));
	}
	
	@Test
	public void testeValidaIdade_Invalida() {

	    try {
	        getClientes.validaIdade(15);
	        fail("Era esperada uma IdadeNaoPermitidaException!");
	    } catch (IdadeNaoPermitidaException e) {
	        assertThat(e.getMessage(), 
	            is("A idade do cliente precisa estar entre 18 e 65 anos."));
	    }
	}

	
	


}
