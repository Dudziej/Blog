package pl.edu.pjwstk.jaz.blog.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class AccountRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addAccount(Account account) {
        em.persist(account);
    }

    @Transactional
    public Account getAccount(String username) {
        return em.find(Account.class, username);
    }

}
