/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fdw.web.adm;

import br.com.fdw.negocio.entidades.Cep;
import br.com.fdw.negocio.entidades.Cliente;
import br.com.fdw.negocio.fachada.ManterCep;
import br.com.fdw.negocio.fachada.ManterCliente;
import com.sun.rave.web.ui.appbase.AbstractPageBean;
import fdw.web.ApplicationBean1;
import fdw.web.RequestBean1;
import fdw.web.SessionBean1;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.event.ValueChangeEvent;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 *
 * @version manterCliente.java
 * @version Created on 21/03/2010, 15:00:57
 * @author pedro
 */

public class manterCliente extends AbstractPageBean {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }

    // </editor-fold>

    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public manterCliente() {
    }

    /**
     * <p>Callback method that is called whenever a page is navigated to,
     * either directly via a URL, or indirectly via page navigation.
     * Customize this method to acquire resources that will be needed
     * for event handlers and lifecycle methods, whether or not this
     * page is performing post back processing.</p>
     *
     * <p>Note that, if the current request is a postback, the property
     * values of the components do <strong>not</strong> represent any
     * values submitted with this request.  Instead, they represent the
     * property values that were saved for this view when it was rendered.</p>
     */
    @Override
    public void init() {
        // Perform initializations inherited from our superclass
        super.init();
        // Perform application initialization that must complete
        // *before* managed components are initialized
        // TODO - add your own initialiation code here

        // <editor-fold defaultstate="collapsed" desc="Managed Component Initialization">
        // Initialize automatically managed components
        // *Note* - this logic should NOT be modified
        try {
            _init();
        } catch (Exception e) {
            log("manterCliente Initialization Failure", e);
            throw e instanceof FacesException ? (FacesException) e: new FacesException(e);
        }

        // </editor-fold>
        // Perform application initialization that must complete
        // *after* managed components are initialized
        // TODO - add your own initialization code here
        atualizaLista();
        getRequestBean1().setCepTemporario("");
        getSessionBean1().getSexoOptionsList().setSelectedValue(getSessionBean1().getSexoOptionsList().getOptions()[0]);
    }

    /**
     * <p>Callback method that is called after the component tree has been
     * restored, but before any event processing takes place.  This method
     * will <strong>only</strong> be called on a postback request that
     * is processing a form submit.  Customize this method to allocate
     * resources that will be required in your event handlers.</p>
     */
    @Override
    public void preprocess() {
    }

    /**
     * <p>Callback method that is called just before rendering takes place.
     * This method will <strong>only</strong> be called for the page that
     * will actually be rendered (and not, for example, on a page that
     * handled a postback and then navigated to a different page).  Customize
     * this method to allocate resources that will be required for rendering
     * this page.</p>
     */
    @Override
    public void prerender() {
    }

    /**
     * <p>Callback method that is called after rendering is completed for
     * this request, if <code>init()</code> was called (regardless of whether
     * or not this was the page that was actually rendered).  Customize this
     * method to release resources acquired in the <code>init()</code>,
     * <code>preprocess()</code>, or <code>prerender()</code> methods (or
     * acquired during execution of an event handler).</p>
     */
    @Override
    public void destroy() {
    }

     /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected SessionBean1 getSessionBean1() {
        return (SessionBean1) getBean("SessionBean1");
    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected RequestBean1 getRequestBean1() {
        return (RequestBean1) getBean("RequestBean1");
    }

    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected ApplicationBean1 getApplicationBean1() {
        return (ApplicationBean1) getBean("ApplicationBean1");
    }

     private void limparCamposFormulario() {
        getRequestBean1().setCliente(new Cliente());
        getRequestBean1().setCepTemporario("");
    }

    private String atualizaLista(){
        try {
            ManterCliente facade = new ManterCliente();
            List clientes = facade.listarClientes();
            getSessionBean1().getClienteProvider().setList(clientes);
        } catch (Exception e) {
            error(e.getMessage());
        }
            return null;
    }

    public String hyperlink1_action() {
       try {
            int selecionado = Integer.parseInt(getValue("#{currentRow.value['codigoCliente']}").toString());
            ManterCliente facade = new ManterCliente();
            Cliente cli = facade.listarCliente(selecionado);
            getRequestBean1().setCepTemporario(cli.getCep().getNumeroCep());
            getSessionBean1().getSexoOptionsList().setSelectedValue(cli.getSexo());
            getRequestBean1().setCliente(cli);            
        }
        catch (Exception e) {
                error(e.getMessage());
        }
       return null;
    }

    public String btnLimpar_action() {
        limparCamposFormulario();
        return null;
    }

    public String btnSalvarNovo_action() {
        try {
            Cliente cli = getRequestBean1().getCliente();
            ManterCliente facade = new ManterCliente();
            if (facade.incluirCliente(cli) == null)
                info("Falha ao inserir registro!");
            else
                info("Registro inserido com sucesso!");
            limparCamposFormulario();
            return atualizaLista();
        } catch (Exception e) {
            error(e.getMessage());
        }
         return null;
    }

    public String btnAtualizar_action() {
       try {
            Cliente cli = getRequestBean1().getCliente();
            ManterCliente facade = new ManterCliente();
            facade.alterarCliente(cli);
            info("Registro atualizado com sucesso!");
            limparCamposFormulario();
            return atualizaLista();
        } catch (Exception e) {
            error(e.getMessage());
        }
        return null;
    }

    public String btnExcluir_action() {
        try {
            Cliente cli = getRequestBean1().getCliente();
            ManterCliente facade = new ManterCliente();

            facade.excluirCliente(cli.getCodigoCliente());
            info("Registro excluído com sucesso!");
            limparCamposFormulario();
            return atualizaLista();
        } catch (Exception e) {
            error(e.getMessage());
        }
        return null;
    }

    public void cmbSexo_processValueChange(ValueChangeEvent event) {
        String sexo = (String) event.getNewValue();
        getRequestBean1().getCliente().setSexo(sexo.charAt(0));
    }

    public void txtCep_processValueChange(ValueChangeEvent event) {
        String cep = (String) event.getNewValue();

        ManterCep facade = new ManterCep();
        Cep objCep = facade.getByNumeroCep(cep);
        if (objCep == null)
            info("Cep inválido, não exite cadastrado");
        else
            getRequestBean1().getCliente().setCep(objCep);
    }

}

