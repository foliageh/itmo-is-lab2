package com.twillice.itmoislab1.view;

import com.twillice.itmoislab1.model.SpaceMarine;
import com.twillice.itmoislab1.service.ActionsService;
import com.twillice.itmoislab1.service.DopService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Named @ViewScoped
@Getter @Setter
public class ActionsView implements Serializable {
    @Inject
    private ActionsService actionsService;

    private Long maxHealth;

    private Long health;
    private List<SpaceMarine> healthMarines;
    private List<Boolean> uniqueLoyalValues;

    @Inject
    private DopService dopService;
    private Long chapterId0, chapterId1;
    private String chapterName0, chapterLegion0, chapterLegion1;
    private String[] chapterData;

    public void updateChapterName() {
        dopService.updateChapterData(chapterId0, chapterName0, chapterLegion0);
        System.out.println("---transaction completed");
    }

    public void getChapterDataTwice() {
        chapterData = dopService.getChapterDataTwice(chapterId1, chapterLegion1);
        System.out.println("transaction completed");
    }

    public void calculateTotalHealth() {
        health = actionsService.calculateTotalHealth();
    }

    public void calculateMarinesWithHealthLessThan() {
        healthMarines = actionsService.getMarinesWithHealthLessThan(maxHealth);
    }

    public void calculateUniqueLoyalValues() {
        uniqueLoyalValues = actionsService.getUniqueLoyalValues();
    }
}
