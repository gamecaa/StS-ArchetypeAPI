package archetypeAPI.patches;

import archetypeAPI.util.cardpoolClearance;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

import static archetypeAPI.archetypes.abstractArchetype.cardsOfTheArchetypesInUse;
import static archetypeAPI.util.cardpoolClearance.makeSureWeMeetMinimum;

@SpirePatch(
        clz = TheSilent.class,
        method = "getCardPool"
)

public class SilentCardPoolPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )

    public static void insert(TheSilent __instance, @ByRef ArrayList<AbstractCard> tmpPool) {
        if (!cardsOfTheArchetypesInUse.isEmpty()) {
            makeSureWeMeetMinimum();
            cardpoolClearance.replaceCardpool(tmpPool, cardsOfTheArchetypesInUse);
        } else {
            CardLibrary.addGreenCards(tmpPool);
        }
        System.out.println("Archetype API Log: Silent card pool patch. You are playing with: " + tmpPool.size() + " cards.");
        System.out.println("These cards are: " + tmpPool.toString());
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ModHelper.class, "isModEnabled");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            //  return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
        }
    }
}



