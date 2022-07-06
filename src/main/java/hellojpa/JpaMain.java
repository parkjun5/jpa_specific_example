package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
//            save(em, tx);
//            update(em, tx);
//            remove(em, tx);
            List<Member> members = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(1)
                    .getResultList();

            for (Member member : members) {
                System.out.println("member.getName() = " + member.getName());
            }

        } catch (Exception e) {
            tx.rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void remove(EntityManager em, EntityTransaction tx) {
        Member findMember = em.find(Member.class, 3L);
        em.remove(findMember);
        tx.commit();
    }

    private static void update(EntityManager em, EntityTransaction tx) {
        Member findMember = em.find(Member.class, 2L);
        findMember.setName("변경된 아이디");
        tx.commit();
    }

    private static void save(EntityManager em, EntityTransaction tx) {
        Member member = new Member();
        member.setName("회원 3");
        em.persist(member);
        tx.commit();
    }
}
