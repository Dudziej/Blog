package pl.edu.pjwstk.jaz.blog.auth;

import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Named
@RequestScoped
public class LoginController {

    @Inject
    private LoginRequest loginRequest;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private HttpServletRequest request;

    public String login() {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Account account = accountRepository.getAccount(username);
        if (account != null) {
            if (BCrypt.checkpw(password, account.getPassword())) {
                request.getSession(true);
                request.getSession().setAttribute("logged", true);
                request.getSession().setAttribute("username", username);
                return "/index.xhtml?faces-redirect=true";
            }
        }
        FacesContext.getCurrentInstance().getExternalContext().getFlash()
                .put("error-message", "Wrong username or password");
        return "/login.xhtml?faces-redirect=true";
    }
}
