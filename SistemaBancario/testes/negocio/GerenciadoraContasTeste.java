package negocio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class GerenciadoraContasTeste {
	
	   // variáveis da classe (acessíveis para todos os testes)
 int IDConta1 = 1;
 int IDConta2 = 2;
 int IDConta3 = 3;
 int IDConta4 = 4;
 int IDConta5 = 55;


 ContaCorrente conta01;
 ContaCorrente conta02;
 ContaCorrente conta03;
 ContaCorrente conta04;
 ContaCorrente conta05;
 
 List<ContaCorrente> contasCorrentes;
 GerenciadoraContas getContasDoBanco;
	
	@Before
	   public void setUp() {
	        // cria clientes
	        conta01 = new ContaCorrente(IDConta1, 200.0, true);
	        conta02 = new ContaCorrente(IDConta2, 50.0,  true);
	        conta03 = new ContaCorrente(IDConta3, 10.0, true);
	        conta04 = new ContaCorrente(IDConta4, 405.0,  true);
	        conta05 = new ContaCorrente(IDConta5, 200.0, false);
	       

	        // cria lista e adiciona os clientes
	        contasCorrentes = new ArrayList<>();
	        contasCorrentes.add(conta01);
	        contasCorrentes.add(conta02);
	        contasCorrentes.add(conta03);
	        contasCorrentes.add(conta04);
	        contasCorrentes.add(conta05);

	        // cria gerenciadora de clientes
	        getContasDoBanco = new GerenciadoraContas(contasCorrentes);
	    }
	
	@Test
	public void testePesquisaContaIdExistente() {
		
		//Chama o metodo pesquisa conta passando um ID
		ContaCorrente Conta = getContasDoBanco.pesquisaConta(IDConta1);
		
		//Verificacao 
		assertThat(Conta.getId(), is(1));				
		
	}
	
	@Test
	public void testePesquisaContaIdInexistente() {
		
		//Chama o metodo pesquisa conta passando um ID
		ContaCorrente Conta = getContasDoBanco.pesquisaConta(5);
		
		//Verificacao 
		assertNull(Conta);			
		
	}
	
	@Test
	public void testeAdicionaConta() {
		 // Cria nova conta para adicionar na lista
		ContaCorrente novaConta = new ContaCorrente(5, 100, true);

	    // Ação: chama o método que será testado
		getContasDoBanco.adicionaConta(novaConta);

	    // Verificações
		assertThat(contasCorrentes.size(), is(6)); 
	    assertThat(contasCorrentes.contains(novaConta), is(true));
		
	}
	
	@Test
	public  void testeContaAtiva() {
		boolean contaAtiva = getContasDoBanco.contaAtiva(IDConta1);
		
		assertThat(contaAtiva, is(true));
	}
	
	@Test
	public  void testeContaInativa() {
		boolean contaAtiva = getContasDoBanco.contaAtiva(8);
		
		assertThat(contaAtiva, is(false));
	}
	
	@Test
	public void testeTransfereValorExistente() {
	    try {
	        boolean sucesso = getContasDoBanco.transfereValor(1, 50.0, 2);
	        assertTrue(sucesso);
	        assertThat(conta01.getSaldo(), is(150.0));
	        assertThat(conta02.getSaldo(), is(100.0));
	    } catch (ContaInativaException e) {
	        fail("Não era esperada uma ContaInativaException aqui!");
	    }
	}

	
	@Test
	public void testeTransfereValorInexistente() {
	    try {
	        boolean sucesso = getContasDoBanco.transfereValor(3, 50.0, 4);
	        assertFalse(sucesso);
	    } catch (ContaInativaException e) {
	        fail("Não era esperada uma ContaInativaException aqui!");
	    }
	}

	
	@Test
	public void testeTransfereContaInativa() {

	    try {
	        // tenta transferir DA conta ativa (1) PARA a conta inativa (3, por exemplo)
	        getContasDoBanco.transfereValor(4, 50.0, 55);
	        fail("Era esperada uma ContaInativaException!");
	    } catch (ContaInativaException e) {
	        assertThat(e.getMessage(),
	            is("A transferência não pode ser realizada pois uma das contas está inativa."));
	    }
	}

	

}
