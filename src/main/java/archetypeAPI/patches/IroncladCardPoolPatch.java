package archetypeAPI.patches;

import archetypeAPI.util.cardpoolClearance;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

import static archetypeAPI.archetypes.abstractArchetype.UsedArchetypesCombined;

@SpirePatch(
        clz = Ironclad.class,
        method = "getCardPool"
)

public class IroncladCardPoolPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )

    public static void insert(Ironclad __instance, @ByRef ArrayList<AbstractCard> tmpPool) {
        if (!UsedArchetypesCombined.isEmpty()) {
            ArrayList<AbstractCard> testPool = new ArrayList<>(tmpPool);

            cardpoolClearance.replaceCardpool(tmpPool, UsedArchetypesCombined);

            if (!UsedArchetypesCombined.isEmpty()) {
                testPool.removeIf(card -> {
                            boolean idCheckBool = false;
                            for (AbstractCard c : tmpPool) {
                                if (card.cardID.equals(c.cardID)) {
                                    idCheckBool = true;
                                }
                            }
                            return idCheckBool;
                        }
                );
                if (!testPool.isEmpty()) {
                    for (AbstractCard c : testPool) {
                        System.out.println("You missed a couple: ");
                        System.out.println("Name: " + c.name + " ID: " + c.cardID);
                        System.out.println("(This list excludes starter deck cards.)");
                    }
                }
            }
        } else {
            CardLibrary.addRedCards(tmpPool);
        }
        System.out.println("Archetype API Log: Ironclad card pool patch. You are playing with: " + tmpPool.size() + " cards.");
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


