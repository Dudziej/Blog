package pl.edu.pjwstk.jaz.blog.auth;

import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.sql.Date;

@Named
@RequestScoped
public class RegisterController {

    @Inject
    private RegisterRequest registerRequest;

    @Inject
    private AccountRepository accountRepository;

    public String register() {
        String username = registerRequest.getUsername();
        String name = registerRequest.getName();
        String surname = registerRequest.getSurname();
        String password = registerRequest.getPassword();
        String passwordCheck = registerRequest.getPasswordCheck();
        String birth = registerRequest.getBirth();

        if (password.equals(passwordCheck)) {
            if (accountRepository.getAccount(username) == null) {
                password = BCrypt.hashpw(password, BCrypt.gensalt());
                Account account = new Account(username, password, name, surname, birth);
                accountRepository.addAccount(account);
                return "/login.xhtml?faces-redirect=true";
            }
            else {
                FacesContext.getCurrentInstance().getExternalContext().getFlash()
                        .put("error-message", "Username already taken!");
                return "/register.xhtml";
            }
        }
        FacesContext.getCurrentInstance().getExternalContext().getFlash()
                .put("error-message", "Passwords are not identical!");
        return "/register.xhtml";
    }

}
