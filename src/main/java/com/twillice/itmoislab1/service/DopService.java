package com.twillice.itmoislab1.service;


import com.twillice.itmoislab1.model.Chapter;
import com.twillice.itmoislab1.security.Security;
import com.twillice.itmoislab1.util.MessageManager;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.ZonedDateTime;

@Stateless
public class DopService {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private Security security;

    @Transactional(rollbackOn = Exception.class)
    public void updateChapterData(Long id, String newName, String newLegion) {
        Chapter chapter = em.find(Chapter.class, id);
        chapter.setName(newName);
        em.merge(chapter);
        System.out.println("---updated: " + newName);

        Chapter chapter1 = new Chapter();
        chapter1.setName("chapter"+System.currentTimeMillis());
        chapter1.setParentLegion(newLegion);
        chapter1.setWorld("aaa");
        chapter1.setMarinesCount(18);
        chapter1.setCreatedBy(security.getUser());
        chapter1.setCreatedTime(ZonedDateTime.now());
        em.persist(chapter1);
        System.out.println("---inserted new with legion " + newLegion);

        MessageManager.info("Chapter name and legion updated.", null);
    }

    @Transactional(rollbackOn = Exception.class)
    public String[] getChapterDataTwice(Long id, String legion) {
        String[] data = new String[4];

        data[0] = (String) em.createQuery("select name from Chapter where id = :_id").setParameter("_id", id).getSingleResult();
        data[2] = ""+em.createQuery("select sum(id) from Chapter where parentLegion = :_legion").setParameter("_legion", legion).getSingleResult();
        System.out.println("name0: " + data[0]);
        System.out.println("leg0 (" + legion + "): " + data[2]);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        data[1] = (String) em.createQuery("select name from Chapter where id = :_id").setParameter("_id", id).getSingleResult();
        data[3] = ""+em.createQuery("select sum(id) from Chapter where parentLegion = :_legion").setParameter("_legion", legion).getSingleResult();
        System.out.println("name1: " + data[1]);
        System.out.println("leg1 (" + legion + "): " + data[3]);

        MessageManager.info("Chapter #" + id + " names (1st and 2nd read): " + data[0] + " and " + data[1], null);
        MessageManager.info("Chapters id sum with legion '" + legion+ "' (1st and 2nd read): " + data[2] + " and " + data[3] + (data[2].equals(data[3]) ? " (they equals)" : ""), null);
        return data;
    }
}
