package pl.edu.pjwstk.jaz.blog.auth;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class AccountContext {
    @Inject
    private HttpServletRequest request;

    @Inject
    private AccountRepository accountRepository;

    public String getUsername() {
        var session = request.getSession(false);
        var usernameObj = session.getAttribute("username");
        if (usernameObj == null) {
            throw new IllegalStateException("No session/User not logged in.");
        }

        return (String) usernameObj;
    }

    public String getFullName() {
        String username;
        try {
            username = getUsername();
        }
        catch (IllegalStateException err) {
            return null;
        }
        var user = accountRepository.getAccount(username);

        return String.format("%s %s", user.getName(), user.getSurname());
    }

    public String logout() {
        request.getSession().setAttribute("logged", false);
        request.getSession().removeAttribute("username");
        return "/index.xhtml?faces-redirect=true";
    }
}
