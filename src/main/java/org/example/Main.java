package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistence");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        Team teamA = new Team();
        teamA.setTeamName("teamA");

        Team teamB = new Team();
        teamB.setTeamName("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member memberA = new Member();
        memberA.setName("memberA");
        memberA.setTeam(teamA);

        Member memberB = new Member();
        memberB.setName("memberB");
        memberB.setTeam(teamA);

        Member memberC = new Member();
        memberC.setName("memberC");
        memberC.setTeam(teamB);

        em.persist(memberA);
        em.persist(memberB);
        em.persist(memberC);

        em.flush();
        em.clear();

//        String query = "select m from Member m";
//        List<Member> findMembers = em.createQuery(query, Member.class).getResultList();
//        for (Member findMember : findMembers) {
//            System.out.println("findMember.getName() = " + findMember.getName());
//            System.out.println("findMember.getTeam().getTeamName() = " + findMember.getTeam().getTeamName());
//        }

        System.out.println("=====================================");

        String query = "select t from Team t join fetch t.members where t.teamName ='teamA'";
        List<Team> teams = em.createQuery(query, Team.class).getResultList();
        System.out.println("teams.size() = " + teams.size());
        for (Team team : teams) {
            System.out.println("team.getTeamName() = " + team.getTeamName());
            System.out.println("team = " + team);

            for (Member member : team.getMembers()) {
                System.out.println("member.getName() = " + member.getName());
            }
        }

        tx.commit();

        em.close();
        emf.close();
    }
}